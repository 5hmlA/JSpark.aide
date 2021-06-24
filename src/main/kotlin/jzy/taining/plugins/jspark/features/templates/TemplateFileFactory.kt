package jzy.taining.plugins.jspark.features.templates

import com.intellij.psi.codeStyle.CodeStyleManager
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.data.TempConfig

interface TemplateFileFactory {
    fun doCreate(environment: Environment, tempConfig: TempConfig)
}