package jzy.taining.plugins.jspark.features.templates.ui

import com.intellij.util.ui.JBDimension
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * others settings tab in config settings dialog
 * Created by Seal.Wu on 2018/2/6.
 */
class AdvancedOtherTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {
    init {
        jVerticalLinearLayout {

            alignLeftComponent {

//                jCheckBox("Enable Comment", ConfigManager.isCommentOff.not(), { isSelected -> ConfigManager.isCommentOff = isSelected.not() })

//                jCheckBox("Enable Order By Alphabetical", ConfigManager.isOrderByAlphabetical, { isSelected -> ConfigManager.isOrderByAlphabetical = isSelected })

//                jCheckBox("Enable Inner Class Model", ConfigManager.isInnerClassModel, { isSelected -> ConfigManager.isInnerClassModel = isSelected })

//                jCheckBox("Enable Map Type when JSON Field Key Is Primitive Type", ConfigManager.enableMapType, { isSelected -> ConfigManager.enableMapType = isSelected })

//                jCheckBox("Only create annotations when needed", ConfigManager.enableMinimalAnnotation, { isSelected -> ConfigManager.enableMinimalAnnotation = isSelected })

                jCheckBox("Auto detect JSON Scheme", false, { isSelected -> {} })

                jHorizontalLinearLayout {
                    jLabel("Indent (number of space): ")
                    jTextInput("ceshi") {
                        columns = 2
                        addFocusLostListener {
                            val str = try {
                                text.toInt()
                            } catch (e: Exception) {
                                text = "error"
                            }
                        }
                    }
                }
            }

            jHorizontalLinearLayout {
                jLabel("Parent Class Template: ")
                jTextInput("moren") {
                    addFocusLostListener {
                        val str = text
                    }
                    maximumSize = JBDimension(400, 30)
                }
            }
        }
    }
}
