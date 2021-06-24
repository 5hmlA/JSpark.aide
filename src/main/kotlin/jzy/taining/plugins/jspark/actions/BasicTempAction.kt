package jzy.taining.plugins.jspark.actions

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import jzy.taining.jspark.gui.DefineFileDialog
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.repository.AndroidScopeProcessor
import jzy.taining.plugins.jspark.features.templates.repository.CheckerImpl
import org.jetbrains.annotations.NotNull

class BasicTempAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        val psiElement = e.getData(CommonDataKeys.PSI_ELEMENT)

        if (psiElement is PsiDirectory) {
            DefineFileDialog(Environment(e.project!!, psiElement)).isVisible = true
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabledAndVisible =
            e.getData(CommonDataKeys.PSI_ELEMENT)?.let { it is PsiDirectory && CheckerImpl.instance.isCodeScope(e.dataContext)} ?: false
    }

}