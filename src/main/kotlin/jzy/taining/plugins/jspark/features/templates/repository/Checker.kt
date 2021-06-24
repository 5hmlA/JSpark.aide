package jzy.taining.plugins.jspark.features.templates.repository

import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import org.jetbrains.annotations.NotNull

interface Checker {

    fun isCodeScope(dataContext: DataContext): Boolean
}

class CheckerImpl private constructor() : Checker {
    companion object {
        val instance: Checker by lazy {
            CheckerImpl()
        }
    }

    override fun isCodeScope(dataContext: DataContext): Boolean {
        val element = CommonDataKeys.PSI_ELEMENT.getData(dataContext)
        val let = element?.let {
            val projectFileIndex = ProjectRootManager.getInstance(element.project).fileIndex
            if (it is PsiDirectory) {
                val virtualFile: VirtualFile = it.virtualFile
                val packageName = projectFileIndex.getPackageNameByDirectory(virtualFile)
                !packageName.isNullOrEmpty()
            } else {
                return false
            }
        }

        return let == true
    }
}