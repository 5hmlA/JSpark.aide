package jzy.taining.plugins.jspark.log

import com.intellij.notification.*
import com.intellij.openapi.project.Project

class EventLogger {
    companion object {
        private val TITLE = "JSpark"
        private val GROUP_ID = "JSpark"

        fun log(log: String) {
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

        fun loge(msg: String, project: Project) {
            val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("jspark")
            notificationGroup.createNotification(msg, NotificationType.INFORMATION)
                .notify(project)
        }
    }
}

inline fun NotificationGroup.notify(block: NotificationGroup.() -> Unit) {
    block()
}
