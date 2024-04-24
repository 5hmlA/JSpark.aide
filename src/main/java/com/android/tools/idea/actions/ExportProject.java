package com.android.tools.idea.actions;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator;
import com.intellij.openapi.project.Project;
import jzy.taining.plugins.jspark.log.EventLogger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ExportProject {

    public static void save(@NotNull File zipFile, @NotNull Project project) {
        File parentFile = zipFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Task.Backgroundable task = new Task.Backgroundable(project, "S P Z") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try {
                    Class<?> aClass = Class.forName("com.android.tools.idea.actions.ExportProjectZip");
                    Method save = aClass.getDeclaredMethod("save", File.class, Project.class, ProgressIndicator.class);
                    save.setAccessible(true);
                    save.invoke(null,zipFile, project, indicator);
                    // 获取东八区的时区
                    ZoneId zoneId = ZoneId.of("Asia/Singapore");
                    // 获取东八区的当前时间
                    ZonedDateTime dateTime = ZonedDateTime.now(zoneId);
                    // 将ZonedDateTime转换为FileTime
                    FileTime fileTime = FileTime.from(dateTime.toInstant());
                    Files.setLastModifiedTime(zipFile.toPath(), fileTime);
                    EventLogger.Companion.log("ooook");
//                    try {
//                         //获取Desktop对象
//                        Desktop desktop = Desktop.getDesktop();
//                         //打开文件夹
//                        desktop.open(parentFile);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        ProgressManager.getInstance().runProcessWithProgressAsynchronously(task, new BackgroundableProcessIndicator(task));
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
//                    } else {
//                        zip.addFile(childRelativePath, child);
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getInstance(ExportProjectZip.class).info("error making zip", ex);
//            ApplicationManager.getApplication().invokeLater(() -> Messages.showErrorDialog(project, "Error: " + ex, "Error!"));
//        }
//    }
}
