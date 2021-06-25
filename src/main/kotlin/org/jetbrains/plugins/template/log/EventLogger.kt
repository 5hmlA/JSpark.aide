package org.jetbrains.plugins.template.log

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

class EventLogger {
    companion object{
        private val TITLE = "TITLE"
        private val GROUP_ID = "LOGGER"

        fun log(log: String) {
            //IDE 底部消息提示 EventLog页签也可以看到
            val notification = Notification(
                EventLogger.GROUP_ID,
                EventLogger.TITLE,
                log,
                NotificationType.INFORMATION
            ) //build a notification

            //notification.hideBalloon();//didn't work
            //notification.hideBalloon();//didn't work
            Notifications.Bus.notify(notification) //use the default bus to notify (application level)

            val balloon = notification.balloon
            balloon?.hide(true)
        }
    }
}