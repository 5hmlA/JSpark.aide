package jzy.taining.plugins.jspark.features.templates.templates

import jzy.taining.plugins.jspark.features.templates.data.Constants
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.data.TemplateValues

interface TemplateInflate {
    companion object {
        fun getInstance(language: String): TemplateInflate {
            return when (language.toLowerCase()) {
                Constants.java -> TemplateInflateJava()
                else -> TemplateInflateKotlin()
            }
        }
    }

    fun activityContent(templateValues: TemplateValues): String
    fun activityRecvContent(templateValues: TemplateValues): String
    fun viewModelRecvContent(templateValues: TemplateValues): String
    fun viewModelContent(templateValues: TemplateValues): String
    fun layoutContent(templateValues: TemplateValues): String
    fun jViewBeanContent(templateValues: TemplateValues): String
    fun dataStructure(templateValues: TemplateValues): String
}

class TemplateInflateKotlin : TemplateInflate {

    override fun activityContent(templateValues: TemplateValues) = jsparkActivity(templateValues)

    override fun activityRecvContent(templateValues: TemplateValues) = jsparkRecvActivity(templateValues)

    override fun viewModelRecvContent(templateValues: TemplateValues) = jsparkRecvViewmodel(templateValues)

    override fun viewModelContent(templateValues: TemplateValues) = jsparkViewmodel(templateValues)

    override fun layoutContent(templateValues: TemplateValues) = jsparkLayout(templateValues)

    override fun jViewBeanContent(templateValues: TemplateValues) = jsparkJViewBean(templateValues)

    override fun dataStructure(templateValues: TemplateValues) = jsparkDataStructure(templateValues)
}

class TemplateInflateJava : TemplateInflate {

    override fun activityContent(templateValues: TemplateValues) = jsparkActivity(templateValues)

    override fun activityRecvContent(templateValues: TemplateValues) = jsparkRecvActivity(templateValues)

    override fun viewModelContent(templateValues: TemplateValues) = jsparkViewmodel(templateValues)

    override fun viewModelRecvContent(templateValues: TemplateValues) = jsparkRecvViewmodel(templateValues)

    override fun layoutContent(templateValues: TemplateValues) = jsparkLayout(templateValues)

    override fun jViewBeanContent(templateValues: TemplateValues) = jsparkJViewBean(templateValues)

    override fun dataStructure(templateValues: TemplateValues) = jsparkDataStructure(templateValues)
}