package org.jetbrains.plugins.template.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import org.jetbrains.plugins.template.icon.Icons
import java.lang.StringBuilder

//类这里表明 要用父类的空方法构造方法
class SecondAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        // Using the event, create and show a dialog
        val currentProject = event.project
        val dlgMsg = StringBuilder(event.presentation.text + " Selected!")
        val dlgTitle = event.presentation.description
        // If an element is selected in the editor, add info about it.
        val nav = event.getData(CommonDataKeys.NAVIGATABLE)
        if (nav != null) {
            dlgMsg.append(String.format("\nSelected Element: %s", nav.toString()))
        }
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon())
    }
}