package org.jetbrains.plugins.template.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.lang.StringBuilder
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.components.serviceOrNull
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.util.PsiEditorUtil
import org.jetbrains.plugins.template.services.MyApplicationService
import org.jetbrains.plugins.template.services.MyProjectService
import javax.swing.Icon

/**
 * 必须是kotlin否则 报错class找不到
 */
//要实现多个父类的构造方法 类这里就不要指定具体构造方法
class FirstAction : AnAction {

    constructor()

    constructor(text: String?, description: String?, icon: Icon?) : super(text, description, icon)

    override fun actionPerformed(event: AnActionEvent) {
        val serviceOrNull = serviceOrNull<MyApplicationService>()
        println("================== ${serviceOrNull}")
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

    /**
     * Determines whether this menu item is available for the current context.
     * Requires a project to be open.
     *
     * @param e Event received when the associated group-id menu is chosen.
     */
    override fun update(e: AnActionEvent) {
        // Set the availability based on whether a project is open
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }
}