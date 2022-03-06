package jzy.taining.plugins.jspark.features.templates.docreate

import com.intellij.openapi.command.WriteCommandAction
import jzy.taining.plugins.jspark.features.templates.TemplateFileFactory
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.data.TempConfig
import jzy.taining.plugins.jspark.features.templates.templates.jsparkFCommander
import jzy.taining.plugins.jspark.features.templates.templates.jsparkFPage
import jzy.taining.plugins.jspark.features.templates.templates.jsparkFRepo
import jzy.taining.plugins.jspark.features.templates.wizard.FileCreaterImpl
import jzy.taining.plugins.jspark.log.EventLogger

class CreateDartsByTemplate : TemplateFileFactory {
    override fun doCreate(environment: Environment, tempConfig: TempConfig) {
        EventLogger.log("doCreate >> ${tempConfig.toString()}")
        println("doCreate >> ${tempConfig.toString()}")
        // 1, 根据类型 判断需要创建哪些文件
        // 2, 依次创建对应文件
        // 3, 结束
        WriteCommandAction.runWriteCommandAction(environment.project) {
            val fileCreater = FileCreaterImpl()
            val name = tempConfig.activityName
            val bean = tempConfig.jvbName
            //创建page  page_$name
            fileCreater.createFile(
                environment,
                tempConfig.rootDir,
                "page_${name.toLowerCase()}",
                ".dart",
                jsparkFPage(name, bean),
                reformat = false
            )
            //创建commander command_$name
            fileCreater.createFile(
                environment,
                tempConfig.rootDir,
                "command_${name.toLowerCase()}",
                ".dart",
                jsparkFCommander(name, bean), reformat = false
            )
            //创建repo  repo_$name
            fileCreater.createFile(
                environment,
                tempConfig.rootDir,
                "repo_${name.toLowerCase()}",
                ".dart",
                jsparkFRepo(name),
                reformat = false
            )
            //创建bean
        }
    }
}