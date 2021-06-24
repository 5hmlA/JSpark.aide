package jzy.taining.plugins.jspark.features.templates

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.data.TempConfig
import jzy.taining.plugins.jspark.features.templates.docreate.CreateFilesByTemplate

class TemplatelRealize {
    fun generateFiles(environment: Environment, tempConfig: TempConfig) {
        CreateFilesByTemplate().doCreate(environment, tempConfig)
    }
}