package jzy.taining.plugins.jspark.log

import com.intellij.notification.*
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.diagnostic.Logger

class EventLogger {
    companion object {
        private val TITLE = "JSpark"
        private val GROUP_ID = "JSpark"
        private val logger = Logger.getInstance(EventLogger::class.java)


        fun notify(log:String){
            logger.info(log)

            //IDE 底部消息提示 EventLog页签也可以看到
            val notification = Notification(
                GROUP_ID,
                TITLE,
                log,
                NotificationType.INFORMATION
            ) //build a notification

            //notification.hideBalloon();//didn't work
            //notification.hideBalloon();//didn't work
            Notifications.Bus.notify(notification) //use the default bus to notify (application level)

            val balloon = notification.balloon
            balloon?.hide(true)
        }

        fun log(log: String) {
            logger.info(log)
        }

        fun loge(msg: String, project: Project) {
            NotificationGroup.create(
                "jspark",
                NotificationDisplayType.BALLOON,
                true,
                null,
                "spark",
                PluginId.getId("jzy.taining.plugins.JSpark")
            )
                .createNotification(msg, NotificationType.INFORMATION)
                .notify(project)
        }
    }
}

inline fun NotificationGroup.notify(block: NotificationGroup.() -> Unit) {
    block()
}
