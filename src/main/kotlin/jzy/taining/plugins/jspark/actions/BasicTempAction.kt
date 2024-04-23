package jzy.taining.plugins.jspark.actions

import com.intellij.openapi.actionSystem.*
import com.intellij.psi.PsiDirectory
import jzy.taining.jspark.gui.DefineFileDialog
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.wizard.CheckerImpl

//"Empty Activity"搜这个关键字看Android插件如何做的
//https://github.com/gmatyszczak/screen-generator-plugin
//https://github.com/takahirom/android-postfix-plugin
//https://github.com/balsikandar/Android-Studio-Plugins
//https://proandroiddev.com/how-i-automated-creating-files-for-a-new-screen-with-my-own-android-studio-plugin-5d54b14ba6fa
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