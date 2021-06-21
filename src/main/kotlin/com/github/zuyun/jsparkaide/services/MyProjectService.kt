package com.github.zuyun.jsparkaide.services

import com.github.zuyun.jsparkaide.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
