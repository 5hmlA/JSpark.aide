package com.android.tools.idea.actions;

import android.util.Log;
import com.android.SdkConstants;
import com.android.tools.idea.gradle.project.GradleProjectInfo;
import com.android.tools.idea.gradle.project.model.AndroidModuleModel;
import com.android.tools.idea.gradle.project.model.JavaModuleModel;
import com.android.tools.idea.gradle.util.GradleUtil;
import com.android.tools.idea.profiling.capture.CaptureService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.intellij.util.io.Compressor;
import jzy.taining.plugins.jspark.log.EventLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiPredicate;

public class ExportProject {

    public static void save(@NotNull File zipFile, @NotNull Project project) {
        File parentFile = zipFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Task.Backgroundable task = new Task.Backgroundable(project, "S P Z") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                zipFile.setLastModified(System.currentTimeMillis());
                save(zipFile, project, indicator);
            }
        };
        ProgressManager.getInstance().run(task);
//        ProgressManager.getInstance().runProcessWithProgressAsynchronously(task, new BackgroundableProcessIndicator(task));
    }


    /// com.android.tools.idea.actions.ExportProjectZip
    static void save(@NotNull File zipFile, @NotNull Project project, @Nullable ProgressIndicator indicator) {
        Set<File> allRoots = new HashSet<>();
        Set<File> excludes = new HashSet<>();
        excludes.add(zipFile);

        assert project.getBasePath() != null;
        File basePath = new File(project.getBasePath());
        allRoots.add(basePath);

        excludes.add(new File(basePath, SdkConstants.FN_LOCAL_PROPERTIES));

        boolean gradle = GradleProjectInfo.getInstance(project).isBuildWithGradle();
        if (gradle) {
            excludes.add(new File(basePath, SdkConstants.DOT_GRADLE));
            excludes.add(new File(basePath, GradleUtil.BUILD_DIR_DEFAULT_NAME));
            excludes.add(new File(basePath, Project.DIRECTORY_STORE_FOLDER));
            excludes.add(new File(basePath, CaptureService.FD_CAPTURES));
        }

        for (Module module : ModuleManager.getInstance(project).getModules()) {
            if (gradle) {
                // if this is a gradle project, exclude .iml file
                VirtualFile moduleFile = module.getModuleFile();
                if (moduleFile != null) {
                    excludes.add(VfsUtilCore.virtualToIoFile(moduleFile));
                }
            }

            ModuleRootManager roots = ModuleRootManager.getInstance(module);

            VirtualFile[] contentRoots = roots.getContentRoots();
            for (VirtualFile root : contentRoots) {
                allRoots.add(VfsUtilCore.virtualToIoFile(root));
            }

            VirtualFile[] exclude = roots.getExcludeRoots();
            for (VirtualFile root : exclude) {
                excludes.add(VfsUtilCore.virtualToIoFile(root));
            }

            AndroidModuleModel androidModel = AndroidModuleModel.get(module);
            if (androidModel != null) {
                excludes.add(androidModel.getAndroidProject().getBuildFolder());
            }
            JavaModuleModel model = JavaModuleModel.get(module);
            if (model != null) {
                excludes.add(model.getBuildFolderPath());
            }
        }

        File commonRoot = null;
        for (File root : allRoots) {
            commonRoot = commonRoot == null ? root : FileUtil.findAncestor(commonRoot, root);
            if (commonRoot == null) {
                throw new IllegalArgumentException("no common root found");
            }
        }
        assert commonRoot != null;

        FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        BiPredicate<String, File> filter = (entryName, file) -> {
            if (fileTypeManager.isFileIgnored(file.getName()) || excludes.stream().anyMatch(root -> FileUtil.isAncestor(root, file, false))) {
                return false;
            }

            if (!file.exists()) {
                Logger.getInstance(ExportProjectZip.class).info("Skipping broken symlink: " + file);
                return false;
            }

            // if it's a folder and an ancestor of any of the roots we must allow it (to allow its content) or if a root is an ancestor
            boolean isDir = file.isDirectory();
            if (allRoots.stream().noneMatch(root -> (isDir && FileUtil.isAncestor(file, root, false)) || FileUtil.isAncestor(root, file, false))) {
                return false;
            }

            if (indicator != null) {
                indicator.setText(entryName);
            }

            return true;
        };

        try (Compressor zip = new Compressor.Zip(zipFile)) {
            zip.filter(filter);

            File[] children = commonRoot.listFiles();
            if (children != null) {
                for (File child : children) {
                    String childRelativePath = (FileUtil.filesEqual(commonRoot, basePath) ? commonRoot.getName() + '/' : "") + child.getName();
                    if (child.isDirectory()) {
                        zip.addDirectory(childRelativePath, child);
                    } else {
                        zip.addFile(childRelativePath, child);
                    }
                }
            }
            zipFile.setLastModified(System.currentTimeMillis());
            Logger.getInstance(ExportProjectZip.class).info(" >>>>>>>> okk ======== ");
        } catch (Exception ex) {
            Logger.getInstance(ExportProjectZip.class).info("error making zip", ex);
            ApplicationManager.getApplication().invokeLater(() -> Messages.showErrorDialog(project, "Error: " + ex, "Error!"));
        }
    }


    /// com.android.tools.idea.actions.ExportProjectZip
//    static void save(@NotNull File zipFile, @NotNull Project project, @Nullable ProgressIndicator indicator) {
//        Set<File> allRoots = new HashSet<>();
//        Set<File> excludes = new HashSet<>();
//        excludes.add(zipFile);
//
//        assert project.getBasePath() != null;
//        File basePath = new File(project.getBasePath());
//        allRoots.add(basePath);
//
//        excludes.add(new File(basePath, SdkConstants.FN_LOCAL_PROPERTIES));
//
//        boolean gradle = GradleProjectInfo.getInstance(project).isBuildWithGradle();
//        if (gradle) {
//            excludes.add(new File(basePath, SdkConstants.DOT_GRADLE));
//            excludes.add(new File(basePath, GradleUtil.BUILD_DIR_DEFAULT_NAME));
//            excludes.add(new File(basePath, Project.DIRECTORY_STORE_FOLDER));
//            excludes.add(new File(basePath, CaptureService.FD_CAPTURES));
//        }
//
//        for (Module module : ModuleManager.getInstance(project).getModules()) {
//            if (gradle) {
//                // if this is a gradle project, exclude .iml file
//                VirtualFile moduleFile = module.getModuleFile();
//                if (moduleFile != null) {
//                    excludes.add(VfsUtilCore.virtualToIoFile(moduleFile));
//                }
//            }
//
//            ModuleRootManager roots = ModuleRootManager.getInstance(module);
//
//            VirtualFile[] contentRoots = roots.getContentRoots();
//            for (VirtualFile root : contentRoots) {
//                allRoots.add(VfsUtilCore.virtualToIoFile(root));
//            }
//
//            VirtualFile[] exclude = roots.getExcludeRoots();
//            for (VirtualFile root : exclude) {
//                excludes.add(VfsUtilCore.virtualToIoFile(root));
//            }
//        }
//
//        File commonRoot = null;
//        for (File root : allRoots) {
//            commonRoot = commonRoot == null ? root : FileUtil.findAncestor(commonRoot, root);
//            if (commonRoot == null) {
//                throw new IllegalArgumentException("no common root found");
//            }
//        }
//        assert commonRoot != null;
//
//        FileTypeManager fileTypeManager = FileTypeManager.getInstance();
//        BiPredicate<String, Path> filter = (entryName, file) -> {
//            if (fileTypeManager.isFileIgnored(file.getFileName().toString()) || excludes.stream().anyMatch(root -> file.startsWith(root.toPath()))) {
//                return false;
//            }
//
//            if (!Files.exists(file)) {
//                Logger.getInstance(ExportProjectZip.class).info("Skipping broken symlink: " + file);
//                return false;
//            }
//
//            // if it's a folder and an ancestor of any of the roots we must allow it (to allow its content) or if a root is an ancestor
//            boolean isDir = Files.isDirectory(file);
//            if (allRoots.stream().noneMatch(root -> isDir && root.toPath().startsWith(file) || file.startsWith(root.toPath()))) {
//                return false;
//            }
//
//            if (indicator != null) {
//                indicator.setText(entryName);
//            }
//
//            return true;
//        };
//
//        try (Compressor zip = new Compressor.Zip(zipFile)) {
//            zip.filter(filter);
//
//            File[] children = commonRoot.listFiles();
//            if (children != null) {
//                for (File child : children) {
//                    String childRelativePath = (FileUtil.filesEqual(commonRoot, basePath) ? commonRoot.getName() + '/' : "") + child.getName();
//                    if (child.isDirectory()) {
//                        zip.addDirectory(childRelativePath, child);
//                    }
//                    else {
//                        zip.addFile(childRelativePath, child);
//                    }
//                }
//            }
//            Logger.getInstance(ExportProjectZip.class).info(" >>>>>>>> okk ======== ");
//        } catch (Exception ex) {
//            Logger.getInstance(ExportProjectZip.class).info("error making zip", ex);
//            ApplicationManager.getApplication().invokeLater(() -> Messages.showErrorDialog(project, "Error: " + ex, "Error!"));
//        }
//    }
}
