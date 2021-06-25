package org.jetbrains.plugins.template.actions.editor

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.util.withNotNullBackup

class ReplaceAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getRequiredData(CommonDataKeys.PROJECT)
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        WriteCommandAction.runWriteCommandAction(project) {
            //1, 通过 caretMode拿到Caret来获取 选中的文本
            //editor 获取 document(文件内容)
            //2, 通过document替换具体选中的位置的内容
            //3, 取消选择
            val primaryCaret = editor.caretModel.currentCaret
            editor.document.replaceString(primaryCaret.selectionStart, primaryCaret.selectionEnd, "替换的文字")
            editor.caretModel.primaryCaret.removeSelection()
        }
    }

    override fun update(e: AnActionEvent) {
        val ediror = e.getData(CommonDataKeys.EDITOR)
        //设置按钮是否可见
        e.presentation.isEnabledAndVisible = e.project != null && ediror?.selectionModel?.hasSelection() == true
    }
}