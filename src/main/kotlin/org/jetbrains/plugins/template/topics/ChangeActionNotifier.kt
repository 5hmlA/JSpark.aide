package org.jetbrains.plugins.template.topics

interface ChangeActionNotifier : java.util.EventListener{

//    Topic<ChangeActionNotifier> CHANGE_ACTION_TOPIC = Topic.create("custom name", ChangeActionNotifier.class)

    fun beforeAction(string: String)
    fun afterAction(string: String)
}