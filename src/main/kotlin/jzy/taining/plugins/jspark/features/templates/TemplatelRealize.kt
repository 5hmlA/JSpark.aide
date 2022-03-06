package jzy.taining.plugins.jspark.features.templates

import jzy.taining.plugins.jspark.features.templates.data.Constants
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.data.TempConfig
import jzy.taining.plugins.jspark.features.templates.docreate.CreateDartsByTemplate
import jzy.taining.plugins.jspark.features.templates.docreate.CreateFilesByTemplate

class TemplatelRealize {
    fun generateFiles(environment: Environment, tempConfig: TempConfig) {
        if (Constants.dart.equals(tempConfig.language)) {
            CreateDartsByTemplate().doCreate(environment, tempConfig)
        } else {
            CreateFilesByTemplate().doCreate(environment, tempConfig)
        }
    }
}