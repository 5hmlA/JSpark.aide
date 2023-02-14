package jzy.taining.plugins.jspark.actions

import com.android.tools.idea.actions.ExportProjectZip
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiDirectory
import jzy.taining.plugins.jspark.features.templates.TemplatelRealize
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.ui.AdvancedDialog
import jzy.taining.plugins.jspark.features.templates.wizard.CheckerImpl

class DartTempAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        val psiElement = e.getData(CommonDataKeys.PSI_ELEMENT)

        if (psiElement is PsiDirectory) {
            val dialog = AdvancedDialog()
            dialog.show()
            println(dialog.tempConfig)
            TemplatelRealize().generateFiles(Environment(e.project!!, psiElement), dialog.tempConfig)
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val element = e.getData(CommonDataKeys.PSI_ELEMENT)
        e.presentation.isEnabledAndVisible = if (element is PsiDirectory) {
            val str = element.virtualFile.path.removePrefix(e.project!!.basePath!!)
            println("DartTempAction >> update > $str")
            Logger.getInstance(ExportProjectZip::class.java).info("DartTempAction >> update > $str")
            !CheckerImpl.instance.isCodeScope(e.dataContext) && !str.contains("src/main/res")
        } else {
            false
        }
    }

}