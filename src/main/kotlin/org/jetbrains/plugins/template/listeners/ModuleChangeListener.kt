package org.jetbrains.plugins.template.listeners

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.util.Function

internal class ModuleChangeListener : com.intellij.openapi.project.ModuleListener {
    override fun moduleAdded(project: Project, module: Module) {
        super.moduleAdded(project, module)
    }

    override fun beforeModuleRemoved(project: Project, module: Module) {
        super.beforeModuleRemoved(project, module)
    }

    override fun moduleRemoved(project: Project, module: Module) {
        super.moduleRemoved(project, module)
    }

    override fun modulesRenamed(
        project: Project,
        modules: MutableList<Module>,
        oldNameProvider: Function<Module, String>
    ) {
        super.modulesRenamed(project, modules, oldNameProvider)
    }
}