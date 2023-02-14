package jzy.taining.plugins.jspark.features.templates.data

import com.intellij.psi.PsiDirectory

//fun activityContent(applicationid: String, packName: String, actName: String, vmName: String, layoutName: String): String
//fun viewModelContent(packageName: String, vmName: String): String
//fun layoutContent(activityFullName: String): String
//fun jViewBeanContent(applicationid: String, packageName: String, vbName: String, layoutName: String): String

class TemplateValues {
    lateinit var applicationId: String
    var packageName: String = "packageName"
    var activityName: String = "applicationId"
    var viewBeanName: String = "viewBeanName"
    var viewModelName: String = "viewModelName"
    var activityLayoutName: String = "activityLayoutName"
    var viewBeanLayoutName: String = "viewBeanLayoutName"
    var subDirName: String = "subDirName"
    var extension: String = "extension"
    var layoutDirectory: PsiDirectory? = null

    val fullActivityName: String by lazy {
        "$packageName.$activityName"
    }
    val fullViewModelName: String by lazy {
        "$packageName.$viewModelName"
    }
    val fullViewBeanName: String by lazy {
        "$packageName.$viewBeanName"
    }
}

fun templateValues(init: TemplateValues.() -> Unit): TemplateValues {
    val templateValues = TemplateValues()
    templateValues.init()
    return templateValues
}

class Html(val font: String = "") {
    var head: String = ""
    fun body() {}
}

fun html(init: Html.() -> Unit): Html {
    val html = Html()
    html.init()
    return html;
}

