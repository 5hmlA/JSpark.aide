package org.jetbrains.plugins.template.listeners;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.icon.Icons;

import java.util.List;

public class MyBulkFileListener implements BulkFileListener {
    @Override
    public void before(@NotNull List<? extends VFileEvent> events) {
        BulkFileListener.super.before(events);
    }

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        // handle the events
    }
}
