package org.jetbrains.plugins.template.actions

import com.intellij.openapi.actionSystem.*
import org.jetbrains.plugins.template.icon.Icons

class FirstActionGroup : DefaultActionGroup() {

    //继承自 ActionGroup 需要自己实现 getChildren方法 动态添加action
//    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
//        return arrayOf(FirstAction())
//    }

    /**
     * Given [CustomDefaultActionGroup] is derived from [com.intellij.openapi.actionSystem.ActionGroup],
     * in this context `update()` determines whether the action group itself should be enabled or disabled.
     * Requires an editor to be active in order to enable the group functionality.
     *
     * @param event Event received when the associated group-id menu is chosen.
     * @see com.intellij.openapi.actionSystem.AnAction.update
     */
    override fun update(event: AnActionEvent) {
        // Enable/disable depending on whether user is editing
        val editor = event.getData(CommonDataKeys.EDITOR)//editor 表示编辑文件 如果光标没在文件编辑的时候 editor为空
        event.presentation.isEnabled = editor != null
        // Take this opportunity to set an icon for the group.
        event.presentation.icon = Icons.def_icon
    }
}