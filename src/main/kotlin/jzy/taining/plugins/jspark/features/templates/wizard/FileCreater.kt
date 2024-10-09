package jzy.taining.plugins.jspark.features.templates.wizard;

import com.intellij.lang.Language.findLanguageByID
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.log.EventLogger

interface FileCreater {
    fun createFile(
        environment: Environment,
        rootDir: String?,
        fileName: String,
        fileExtension: String,
        content: String,
        reformat: Boolean = true
    )

    fun createLayoutFile(
        resDirectionality: PsiDirectory,
        rootDir: String?,
        fileName: String,
        fileExtension: String,
        content: String
    )
}

class FileCreaterImpl : FileCreater {
    override fun createFile(
        environment: Environment,
        rootDir: String?,
        name: String,
        fileExtension: String,
        content: String,
        reformat: Boolean
    ) {
        val fileName = "$name$fileExtension"
        val createFileFromText = PsiFileFactory.getInstance(environment.project)
            .createFileFromText(fileName, findLanguageByID("kotlin")!!, content)
//        rootDir?.apply {
//            environment.psiDirectory.findSubdirectory(rootDir!!)?.add(createFileFromText)
//                ?: environment.psiDirectory.createSubdirectory(rootDir).add(createFileFromText)
//        } ?: environment.psiDirectory.add(createFileFromText)
        if (reformat) {
            CodeStyleManager.getInstance(environment.project).reformat(createFileFromText)
        }
        println(environment.psiDirectory.findFile(fileName))
        try {
            if (rootDir.isNullOrEmpty()) {
                environment.psiDirectory.findFile(fileName)?.delete()
                environment.psiDirectory.add(createFileFromText)
            } else {
                environment.psiDirectory.findSubdirectory(rootDir)?.findFile(fileName)?.delete()
                environment.psiDirectory.findSubdirectory(rootDir)?.add(createFileFromText)
                    ?: environment.psiDirectory.createSubdirectory(rootDir).add(createFileFromText)
            }
        } catch (e: Exception) {
            EventLogger.log("$ error is ${e.stackTrace}")
        }
    }

    override fun createLayoutFile(
        resDirectionality: PsiDirectory,
        rootDir: String?,
        layoutName: String,
        fileExtension: String,
        content: String
    ) {
        val fileName = "$layoutName$fileExtension"
        val createFileFromText = PsiFileFactory.getInstance(resDirectionality.project)
            .createFileFromText(fileName, findLanguageByID("kotlin")!!, content)
        try {
            if (rootDir.isNullOrEmpty()) {
                resDirectionality.findFile(fileName)?.delete()
                resDirectionality.add(createFileFromText)
            } else {
                resDirectionality.findSubdirectory(rootDir)?.findFile(fileName)?.delete()
                resDirectionality.findSubdirectory(rootDir!!)?.add(createFileFromText)
                    ?: resDirectionality.createSubdirectory(rootDir).add(createFileFromText)
            }
        } catch (e: Exception) {
            EventLogger.log("$ error is ${e.stackTrace}")
        }
    }
}