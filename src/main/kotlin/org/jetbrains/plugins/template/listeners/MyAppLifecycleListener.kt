package org.jetbrains.plugins.template.listeners

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref

class MyAppLifecycleListener constructor(val project: Project): AppLifecycleListener {
//    override fun appFrameCreated(commandLineArgs: MutableList<String>, willOpenProject: Ref<in Boolean>) {
//        super.appFrameCreated(commandLineArgs, willOpenProject)
//    }

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        super.appFrameCreated(commandLineArgs)
    }

    override fun welcomeScreenDisplayed() {
        super.welcomeScreenDisplayed()
    }

    override fun appStarting(projectFromCommandLine: Project?) {
        super.appStarting(projectFromCommandLine)
    }

    override fun appStarted() {
        super.appStarted()
    }

    override fun projectFrameClosed() {
        super.projectFrameClosed()
    }

    override fun projectOpenFailed() {
        super.projectOpenFailed()
    }

    override fun appClosing() {
        super.appClosing()
    }

    override fun appWillBeClosed(isRestart: Boolean) {
        super.appWillBeClosed(isRestart)
    }
}