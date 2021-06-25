package org.jetbrains.plugins.template.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import org.jetbrains.plugins.template.actions.ui.FirstDialog

class UiAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        FirstDialog().isVisible = true

        Messages.showCheckboxMessageDialog("message", "title", arrayOf("option1", "option2"), "checkboxtext", true,0,1,AllIcons.Idea_logo_welcome,
            null)

    }
}