package jzy.taining.plugins.jspark.actions

import com.android.tools.idea.actions.ExportProject
import com.android.tools.idea.model.AndroidModel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import org.jetbrains.android.facet.AndroidFacet
import java.io.File

class ProjectExport : AnAction() {

    var path = "/ipo/0labb/app/src/main/assets"
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let {
//            val module = PlatformCoreDataKeys.MODULE.getData(e.dataContext) ?: return
//            val facet = AndroidFacet.getInstance(module) ?: return
//            if (AndroidModel.get(facet) == null) {
//                return
//            }
            var targetDirectory = CommonDataKeys.VIRTUAL_FILE.getData(e.dataContext)
            // If the user selected a simulated folder entry (eg "Manifests"), there will be no target directory
            if (targetDirectory != null && !targetDirectory.isDirectory) {
                targetDirectory = targetDirectory.parent
                assert(targetDirectory != null)
            }

            val file = File(path + File.separator + it.name.hashCode())
            if (file.exists()) {
                file.delete()
            }
            ExportProject.save(file, e.project!!)
        }
    }
}