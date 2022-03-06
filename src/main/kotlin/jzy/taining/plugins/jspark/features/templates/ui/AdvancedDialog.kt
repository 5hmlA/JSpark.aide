package jzy.taining.plugins.jspark.features.templates.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.JBDimension
import jzy.taining.plugins.jspark.features.templates.TemplatelRealize
import jzy.taining.plugins.jspark.features.templates.data.Constants
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.features.templates.data.TempConfig
import java.awt.event.ActionListener
import javax.swing.Action
import javax.swing.JComponent

/**
 *
 * Created by Seal.Wu on 2017/9/13.
 */

class AdvancedDialog() : DialogWrapper(true) {
    val tempConfig = TempConfig(language = Constants.dart)

    init {
        init()
        title = "Advanced"
    }


    override fun createCenterPanel(): JComponent? {
        title = "创建Sparkj模板"
        okAction.isEnabled = false
        return jVerticalLinearLayout {

            alignLeftComponent {

                jCheckBox("generate bean", false, { isSelected -> {} })

                jHorizontalLinearLayout {
                    jLabel("page: ")
                    jTextInput {
                        columns = 2
                        addFocusLostListener {
                            tempConfig.activityName = (text.toString().trim())
                            okAction.isEnabled = tempConfig.activityName.isNotEmpty()&&tempConfig.jvbName.isNotEmpty()
                        }
                    }
                }
                jHorizontalLinearLayout {
                    jLabel("bean: ")
                    jTextInput {
                        columns = 2
                        addFocusLostListener {
                            tempConfig.jvbName = (text.toString().trim())
                            okAction.isEnabled = tempConfig.activityName.isNotEmpty()&&tempConfig.jvbName.isNotEmpty()
                        }
                    }
                }
            }
        }
    }

    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }

}
