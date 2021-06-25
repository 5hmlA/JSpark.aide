package org.jetbrains.plugins.template.listeners

import com.intellij.openapi.application.ApplicationListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.ex.ToolWindowManagerListener

/**
 * The class implementing the listener interface can define a one-argument constructor accepting a Project,
 * and it will receive the instance of the project for which the listener is created:
 */
internal class MyToolwindowListener constructor(val project : Project): ToolWindowManagerListener {

    init {
        print(" MyToolwindowListener >> $project.name")
    }

    override fun toolWindowRegistered(id: String) {
        super.toolWindowRegistered(id)
    }

    override fun toolWindowsRegistered(ids: MutableList<String>) {
        super.toolWindowsRegistered(ids)
    }

    override fun toolWindowUnregistered(id: String, toolWindow: ToolWindow) {
        super.toolWindowUnregistered(id, toolWindow)
    }

    override fun stateChanged(toolWindowManager: ToolWindowManager) {
        super.stateChanged(toolWindowManager)
    }

    override fun stateChanged() {
        super.stateChanged()
    }

    override fun toolWindowShown(id: String, toolWindow: ToolWindow) {
        super.toolWindowShown(id, toolWindow)
    }
}