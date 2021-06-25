package org.jetbrains.plugins.template.listeners

import com.google.protobuf.Message
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ComponentManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.util.messages.MessageBus
import org.jetbrains.plugins.template.services.MyApplicationService
import org.jetbrains.plugins.template.services.MyProjectService
import javax.swing.PopupFactory

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        if (ApplicationManager.getApplication().isUnitTestMode) {
            return
        }

        project.service<MyProjectService>()
        val applicationService = service<MyApplicationService>()
        applicationService.incrProjectCount();
        //判断是否超过限制了
        if (applicationService.projectLimitExceeded()) {
            val title = "正打开项目 ${project.name}"
            val message = "<br> 插线限制了打开项目的数量。<br> <br>这不是错误<br>"
            Messages.showMessageDialog(project,message,title, Messages.getInformationIcon())
        }
//        val applicationService = service<MyApplicationService>()
//        val projectService = project.service<MyProjectService>()
    }

    override fun projectClosed(project: Project) {
        if (ApplicationManager.getApplication().isUnitTestMode) {
            return
        }
        val applicationService = service<MyApplicationService>()
        applicationService.decrProjectCount()
    }
}
