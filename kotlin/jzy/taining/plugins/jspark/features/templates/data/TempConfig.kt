package jzy.taining.plugins.jspark.features.templates.data

data class TempConfig(
    var rootDir: String = "",
    var activityName: String = "",
    var viewModuleName: String = "",
    var layoutName: String = "",
    var jvbName: String = "",
    var jvbLayoutName: String = "",
    var language: String = "",
    var isRecv: Boolean = false
)
