package org.jetbrains.plugins.template.topics

import com.intellij.util.messages.Topic

class Topics {
    companion object{
        val CHANGE_ACTION_TOPIC: Topic<ChangeActionNotifier> =
            Topic.create("custom name", ChangeActionNotifier::class.java)
    }

}