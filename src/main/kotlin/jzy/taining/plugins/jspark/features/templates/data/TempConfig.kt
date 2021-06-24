package jzy.taining.plugins.jspark.features.templates.data

data class TempConfig(
    val rootDir: String,
    val activityName: String,
    val viewModuleName: String,
    val layoutName: String,
    val jvbName: String,
    val jvbLayoutName: String,
    val language: String,
    val isRecv: Boolean
)
