package com.android.tools.idea.actions;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator;
import com.intellij.openapi.project.Project;
import jzy.taining.plugins.jspark.log.EventLogger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
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
                //必须在后面修改时间 生成文件后才可以
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
                    EventLogger.Companion.log(zipFile.getAbsolutePath());
                    EventLogger.Companion.notify("ooook");
                    try {
                         //获取Desktop对象
                        Desktop desktop = Desktop.getDesktop();
                         //打开文件夹
                        desktop.open(parentFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        ProgressManager.getInstance().runProcessWithProgressAsynchronously(task, new BackgroundableProcessIndicator(task));
    }
}
