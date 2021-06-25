package org.jetbrains.plugins.template.actions.psi

import com.intellij.ide.actions.JavaCreateTemplateInPackageAction
import com.intellij.ide.impl.ProjectUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.impl.file.PsiDirectoryFactory
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.containingClass


class ModifyPsiAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        //选中目录的时候 拿到的是 PsiDirectory 选中java文件的时候拿到的是PsiClass  选中Kotlin文件的时候 拿到的是 KtFile: KKClass.kt
        val element = e.getData(CommonDataKeys.PSI_ELEMENT)
        println(element)
        element?.apply {
            if (element is PsiDirectory) {
                createCustFile(project, element)
//                WriteCommandAction.runWriteCommandAction(e.project) {
//                    val createSubdirectory = element.createSubdirectory("subcreate")
//                    println(createSubdirectory.virtualFile.path)
//                }
            } else if (element is PsiFile) {
                if (element is KtFile) {
                    println(element.containingClass()?.name)
                    return
                }
                if (element is PsiJavaFile) {
                }
//                WriteCommandAction.runWriteCommandAction(e.project) {
//                    println(element.delete())
//                }

            } else if (element is PsiClass) {
                WriteCommandAction.runWriteCommandAction(e.project) {
                    println(element.delete())
                }
            }
        }
    }

    fun createCustFile(project: Project, psiDirectory: PsiDirectory) {
//        JavaPsiFacade.getInstance(project).elementFactory.crea


        WriteCommandAction.runWriteCommandAction(project) {

            //Create file from PsiFile with path
            val createFileFromText = PsiFileFactory.getInstance(project)
                .createFileFromText("name.kt", KotlinLanguage.INSTANCE, "moren nei rong")
            PsiDirectoryFactory.getInstance(project).createDirectory(project.guessProjectDir()!!.findChild("src")!!).add(createFileFromText)

//            JavaCreateTemplateInPackageAction
            println(createFileFromText?.containingFile!!.virtualFile.path)
//            psiDirectory.virtualFile.findChild("sub")?.exists()
            val sub = psiDirectory.createSubdirectory("sub")
//            val customFileTemplate = CustomFileTemplate("KtName", KotlinFileType.INSTANCE.defaultExtension)
            sub.createFile("KtName.${KotlinFileType.INSTANCE.defaultExtension}")
            JavaDirectoryService.getInstance().createClass(sub, "name")

//            val findChild = project.guessProjectDir()!!.findChild("src")//?.findChild("main")?.findChild("java")
//            println(findChild)
//            val findDirectory = PsiManager.getInstance(project).findDirectory(findChild!!)
//            val createSubdirectory = findDirectory!!.createSubdirectory("cum").createSubdirectory("custom").createSubdirectory("pack")
//            createSubdirectory!!.createFile("KtName.${KotlinFileType.INSTANCE.defaultExtension}")

//            ProjectUtil.getBaseDir()
        }
    }

}