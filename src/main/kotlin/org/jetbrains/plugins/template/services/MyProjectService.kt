package org.jetbrains.plugins.template.services

import com.intellij.AppTopics
import com.intellij.ProjectTopics
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.util.messages.Topic
import org.jetbrains.plugins.template.MyBundle
import org.jetbrains.plugins.template.topics.ChangeActionNotifier
import org.jetbrains.plugins.template.topics.Topics

class MyProjectService(val project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }

    fun someServiceMethod(parameter: String?) : String{
        //定义消息
//        val CHANGE_ACTION_TOPIC: Topic<ChangeActionNotifier> =
//            Topic.create("custom name", ChangeActionNotifier::class.java)
        //region 发送消息
        val syncPublisher = project.messageBus.syncPublisher(Topics.CHANGE_ACTION_TOPIC)
        syncPublisher.beforeAction("")
        //do sth
        syncPublisher.afterAction("")
        //endregion

        //订阅消息
        project.messageBus.connect().subscribe(Topics.CHANGE_ACTION_TOPIC, object: ChangeActionNotifier{
            override fun beforeAction(string: String) {
                TODO("Not yet implemented")
            }

            override fun afterAction(string: String) {
                TODO("Not yet implemented")
            }
        })

        ProjectTopics.MODULES

        ApplicationManager.getApplication().runReadAction {
            //读取数据
        }
        ApplicationManager.getApplication().runWriteAction{
            //写入数据入口
        }
        ApplicationManager.getApplication().invokeLater {
            //从后台线程传递到事件线程 更新UI
        }

        val anotherService: MyProjectService = project.service<MyProjectService>()
        val result: String = anotherService.someServiceMethod(parameter)
        // do some more stuff

        //Android 的SP  project 级别
//        PropertiesComponent.getInstance(project).getValue()
        return ""
    }
}
