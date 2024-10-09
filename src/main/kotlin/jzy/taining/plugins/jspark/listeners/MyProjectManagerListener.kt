package jzy.taining.plugins.jspark.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import jzy.taining.plugins.jspark.services.MyProjectService
import java.lang.ref.WeakReference

internal class MyProjectManagerListener : ProjectManagerListener {


    lateinit var projectRef : WeakReference<Project>

//    override fun projectOpened(project: Project) {
//        projectRef = WeakReference(project)
//        project.service<MyProjectService>()
//    }

    override fun projectClosed(project: Project) {
        super.projectClosed(project)
    }
}
