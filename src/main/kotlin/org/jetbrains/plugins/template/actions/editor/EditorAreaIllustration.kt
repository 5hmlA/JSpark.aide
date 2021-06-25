package org.jetbrains.plugins.template.actions.editor

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiFile

class EditorAreaIllustration : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        // Get access to the editor and caret model. update() validated editor's existence.
        val editor: Editor = event.getRequiredData<Editor>(CommonDataKeys.EDITOR)
        //光标
        val caretModel = editor.caretModel
        // Getting the primary caret ensures we get the correct one of a possible many.
        val primaryCaret = caretModel.primaryCaret
        // Get the caret information
        val logicalPos = primaryCaret.logicalPosition//实际位置
        val visualPos = primaryCaret.visualPosition//视觉的位置 因为代码可以被折叠 这个时候 和实际位置不一样 看到的位置会小
        val caretOffset = primaryCaret.offset
        // Build and display the caret report.
        val report = """
            $logicalPos
            $visualPos
            Offset: $caretOffset
            """.trimIndent()
        Messages.showInfoMessage(report, "Caret Parameters Inside The Editor")
        val psiFile = event.getData(CommonDataKeys.PSI_FILE)
        psiFile?.let {
            val element = psiFile.findElementAt(caretOffset)
            println(element)
        }

    }
}