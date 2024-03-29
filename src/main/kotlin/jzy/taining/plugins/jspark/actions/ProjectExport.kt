package jzy.taining.plugins.jspark.actions

import com.android.tools.idea.actions.ExportProject
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.io.File

class ProjectExport : AnAction() {

    var path = "/ipo2/0labb/app/src/main/assets"
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let {
            val file = File(path + File.separator + it.name.hashCode())
            if (file.exists()) {
                file.delete()
            }
            ExportProject.save(file, e.project!!)
        }
    }
}