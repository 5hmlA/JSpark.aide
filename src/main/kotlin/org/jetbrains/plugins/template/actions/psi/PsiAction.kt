package org.jetbrains.plugins.template.actions.psi

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.*
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.codeStyle.JavaCodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.idea.formatter.KtCodeStyleSettings
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtPsiUtil
import org.jetbrains.plugins.template.log.EventLogger


class PsiAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val psiElement = e.getData(CommonDataKeys.PSI_ELEMENT)
        psiElement?.apply {
            println("========= psiElement ====== ")
            println(psiElement::class)
            println(psiElement)
            val methods = PsiTreeUtil.getChildOfType(psiElement,PsiMethod::class.java)
            println(methods)
        }
        println("=====================")
        if (psiElement is PsiDirectory) {
            println("======== PsiDirectory =========")
            println(psiElement.virtualFile.path)//获取文件夹的具体路径 C:/Users/80256595.NA80256595/IdeaProjects/untitled1/src
            println(psiElement.parentDirectory)//获取的是 父文件夹对象  PsiDirectory:C:\Users\80256595.NA80256595\IdeaProjects\untitled1
            println("========  file list =======")
            for (file in psiElement.files) {
                println(file.containingDirectory)//文件所在的文件夹对象  = 文件的父文件夹  PsiDirectory:C:\Users\80256595.NA80256595\IdeaProjects\untitled1\src
                println(file.containingDirectory.virtualFile.path)//文件所在的文件夹的绝对路径  C:/Users/80256595.NA80256595/IdeaProjects/untitled1/src
                println(file.containingFile)//文件对象  KtFile: KClass.kt
                println(file.containingFile.containingFile.virtualFile.path)//文件的绝对路径  C:/Users/80256595.NA80256595/IdeaProjects/untitled1/src/KClass.kt
                if (file is PsiJavaFile) {
                    println(JavaPsiFacade.getInstance(project).findPackage(file.packageName))
                }
//                val findFileByPath = VcsFileSystem.getInstance().findFileByPath()
//                LocalFileSystem.getInstance().findFileByPath()
//                VfsUtilCore.iterateChildrenRecursively()
//                VirtualFileManager.getInstance().syncRefresh()//当访问新创建的文件的时候 很有必要先刷新vfs
            }
        }
        val psiFile = e.getData(CommonDataKeys.PSI_FILE) ?: return
        println("======== PSI_FILE =========")
        println(psiFile::class)
        println(psiFile)
        psiFile.accept(object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                super.visitElement(element)
                println("fond element  $element")
            }
        })
        if (psiFile is PsiJavaFile) {
            println("======== PsiJavaFile =========")
            psiFile.accept(object : JavaRecursiveElementVisitor() {
                override fun visitLocalVariable(variable: PsiLocalVariable) {
                    super.visitLocalVariable(variable)
                    println(variable.type)
                    println("Found a variable at offset " + variable.textRange.startOffset)
                }

                override fun visitMethod(method: PsiMethod?) {
                    super.visitMethod(method)
                    println("fond method $method")
                    method?.apply {
                        println(method.returnType)
                        for (parameter in method.parameters) {
                            println("method param $parameter type is ${parameter.type}")
                        }
                    }
                }
            })
            println(psiFile.packageName)//所在包名 src下的文件包名为空
            println(psiFile.name)//Test.java 文件名包括文件类型后缀
            println(psiFile.virtualFile.path)  //C:/Users/80256595.NA80256595/IdeaProjects/untitled1/src/Test.java
        } else if (psiFile is KtFile) {
            println("======== KtFile =========")
            println(psiFile.packageFqName)//所在包名 src下的文件包名为空
            println(psiFile.name)//Test.java 文件名包括文件类型后缀
            println(psiFile.virtualFile.path)  //C:/Users/80256595.NA80256595/IdeaProjects/untitled1/src/Test.java
        }
//        CodeStyleManager.getInstance(project!!).reformat(null)
        for (child in psiFile.children) {
            println(child::class)
            if (child is PsiClass) {
                println("======== PsiClass =========")
                println(child.name)//class名字  没有类型后缀  Test
                println(child.containingFile)//class对应的  文件的类对象 PsiJavaFile:Test.java
                println(child.containingFile)//class对应的  文件的类对象 PsiJavaFile:Test.java
                println(child.containingFile.virtualFile.path)//文件的具体路径  C:/Users/80256595.NA80256595/IdeaProjects/untitled1/src/Test.java
                println(child.classKind)
                println(child)
//                child.methods//返回当前类的所有方法
//                child.allMethods//返回当前类的所有方法 包括父类
                println("======== allMethods =========")
                for (allMethod in child.allMethods) {
                    println(allMethod.name)
                }
            } else if (child is KtClass) {
                println("======== KtClass =========")
                println(child.name)
                println(child)
                println(child.body)
                println(child.containingKtFile)
                println(child.containingKtFile.virtualFilePath)
                println(child.containingFile)
            }
        }
//        PsiFileFactory.getInstance(project).c
        EventLogger.log("完成卡啦啦啦啦啦啦啦啦")
    }
}