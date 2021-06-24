package jzy.taining.plugins.jspark.features.templates.docreate

import com.android.SdkConstants
import com.android.tools.idea.gradle.project.build.compiler.AndroidGradleBuildConfiguration
import com.android.tools.idea.wizard.template.template
import com.intellij.openapi.command.WriteCommandAction
import jzy.taining.plugins.jspark.features.templates.TemplateFileFactory
import jzy.taining.plugins.jspark.features.templates.data.*
import jzy.taining.plugins.jspark.features.templates.repository.AndroidScopeProcessor
import jzy.taining.plugins.jspark.features.templates.templates.TemplateInflate
import jzy.taining.plugins.jspark.features.templates.wizard.AndroidMainfestMagic
import jzy.taining.plugins.jspark.features.templates.wizard.FileCreaterImpl
import jzy.taining.plugins.jspark.log.EventLogger
import org.apache.http.util.TextUtils

val language = mapOf<String, String>(Constants.java to Constants.dot_java, Constants.kotlin to Constants.dot_kt)

class CreateFilesByTemplate : TemplateFileFactory {
    val tag = CreateFilesByTemplate::class.qualifiedName

    override fun doCreate(environment: Environment, tempConfig: TempConfig) {
        EventLogger.log("doCreate >> ${tempConfig.toString()}")
        println("doCreate >> ${tempConfig.toString()}")
        // 1, 根据类型 判断需要创建哪些文件
        // 2, 依次创建对应文件
        // 3, 结束
        WriteCommandAction.runWriteCommandAction(environment.project) {
            val androidScope = AndroidScopeProcessor(environment)
            val templateInflateImpl = TemplateInflate.getInstance(tempConfig.language)
            val fileCreater = FileCreaterImpl()

            val templateValues = templateValues {
                applicationId = androidScope.getApplicationId()
                packageName = androidScope.getPackageName(tempConfig.rootDir)!!
                activityName = tempConfig.activityName
                viewModelName = tempConfig.viewModuleName
                viewBeanName = tempConfig.jvbName
                activityLayoutName = tempConfig.layoutName
                viewBeanLayoutName = tempConfig.jvbLayoutName
                subDirName = tempConfig.rootDir
                extension = language[tempConfig.language.toLowerCase()]!!
                layoutDirectory = androidScope.findLayoutDir()
            }

            if (templateValues.activityName.isNotEmpty()) {

                if (!TextUtils.isEmpty(templateValues.viewBeanName)) {
                    createJViewbean(templateInflateImpl, templateValues, fileCreater, environment)
                } else if (tempConfig.isRecv) {
                    //列表页面 如果不需要viewbean需要一个默认的JviewBean
                    templateValues.viewBeanName = "JViewBean"
                }
                if (!TextUtils.isEmpty(templateValues.viewModelName)) {
                    EventLogger.log("$ create viewModel start")
                    //创建 viewModel
                    val viewModelContent = when(tempConfig.isRecv){
                        true -> templateInflateImpl.viewModelRecvContent(templateValues)
                        else -> templateInflateImpl.viewModelContent(templateValues)
                    }
                    fileCreater.createFile(
                        environment,
                        templateValues.subDirName,
                        templateValues.viewModelName,
                        templateValues.extension,
                        viewModelContent
                    )
                }

                // 创建activity 插入manifest 需要同时创建 layout? viewbean? viewmodel
                createActivity(templateInflateImpl, templateValues, fileCreater, environment, tempConfig.isRecv)

            } else if (!TextUtils.isEmpty(templateValues.viewBeanName)) {
                //只创建 viewbean 同时要有布局
                createJViewbean(templateInflateImpl, templateValues, fileCreater, environment)
            }

        }
    }

    /**
     * 创建Activity文件
     * 创建布局文件
     * 注册Activity到manifest
     */
    private fun createActivity(
        templateInflateImpl: TemplateInflate,
        templateValues: TemplateValues,
        fileCreater: FileCreaterImpl,
        environment: Environment,
        isRecv: Boolean
    ) {
        EventLogger.log("$ createActivity start")
        val activityContent = when (isRecv) {
            true -> templateInflateImpl.activityRecvContent(templateValues)
            else -> templateInflateImpl.activityContent(templateValues)
        }
        fileCreater.createFile(
            environment,
            templateValues.subDirName,
            templateValues.activityName,
            templateValues.extension,
            activityContent
        )
        if (templateValues.activityLayoutName.isNotEmpty()) {
            EventLogger.log("$ create Activity layout start")
            //创建布局
            val layoutContent = templateInflateImpl.layoutContent(templateValues)
            fileCreater.createLayoutFile(
                templateValues.layoutDirectory!!,
                Constants.layout_folder,
                templateValues.activityLayoutName,
                SdkConstants.DOT_XML,
                layoutContent
            )
        }
        //添加清单文件
        AndroidMainfestMagic(environment).addActivitysTag(templateValues.fullActivityName)
        EventLogger.log("$ create Activity layout finish")
    }

    private fun createJViewbean(
        templateInflateImpl: TemplateInflate,
        templateValues: TemplateValues,
        fileCreater: FileCreaterImpl,
        environment: Environment
    ) {

        if (templateValues.viewBeanLayoutName.isNotEmpty()) {
            EventLogger.log("$ create JViewbean with layout start")
            //创建jviewbean
            val jvbContent = templateInflateImpl.jViewBeanContent(templateValues)
            fileCreater.createFile(
                environment,
                templateValues.subDirName,
                templateValues.viewBeanName,
                templateValues.extension,
                jvbContent
            )
            //创建jviewbean layout
            val layoutContent = templateInflateImpl.layoutContent(templateValues)
            fileCreater.createLayoutFile(
                templateValues.layoutDirectory!!,
                Constants.layout_folder,
                templateValues.viewBeanLayoutName,
                SdkConstants.DOT_XML,
                layoutContent
            )
        } else {
            EventLogger.log("$ create JViewbean without layout start")
            val jvbContent = templateInflateImpl.dataStructure(templateValues)
            fileCreater.createFile(
                environment,
                templateValues.subDirName,
                templateValues.viewBeanName,
                templateValues.extension,
                jvbContent
            )
        }
    }
}