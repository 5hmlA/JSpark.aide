package jzy.taining.plugins.jspark.services

import com.intellij.openapi.project.Project
import jzy.taining.plugins.jspark.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }


}
