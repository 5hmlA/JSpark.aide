package jzy.taining.jspark.gui;

import jzy.taining.plugins.jspark.features.templates.TemplatelRealize;
import jzy.taining.plugins.jspark.features.templates.data.Environment;
import jzy.taining.plugins.jspark.features.templates.data.TempConfig;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.Objects;

public class DefineFileDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox tempTypeChoser;
    private JTextField activityName;
    private JTextField viewmodelName;
    private JCheckBox generateALayoutFileCheckBox;
    private JTextField layoutName;
    private JCheckBox generageJViewBeanCheckBox;
    private JTextField jvbName;
    private JComboBox choseLanguage;
    private JTextField jvbLayoutName;
    private JLabel layoutnameTitle;
    private JLabel jvbNameTitle;
    private JLabel jvbLayoutTitle;
    private JLabel activityTitle;
    private JLabel viewmodelTitle;
    private JTextField rootDirName;
    private JLabel rootDirTile;
    private Environment environment;
    private String preFix = "Busi";
    private String TempateType = "BasicActivity";

    public DefineFileDialog(Environment environment) {
        this.environment = environment;
        setSize(444, 600);
        setLocation(666, 200);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("New JBasic Tempate");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        rootDirName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeAllText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeAllText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeAllText();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        choseLanguage.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(e.getItem());
                }
            }
        });
        tempTypeChoser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    TempateType = e.getItem().toString();
                    if ("BasicRecvActivity".equals(e.getItem())) {
                        generateALayoutFileCheckBox.setVisible(false);
                        generateALayoutFileCheckBox.setSelected(false);
                        layoutName.setVisible(false);
                        layoutnameTitle.setVisible(false);

                        activityName.setVisible(true);
                        activityTitle.setVisible(true);
                        viewmodelName.setVisible(true);
                        viewmodelTitle.setVisible(true);
                        generageJViewBeanCheckBox.setVisible(true);
                        generageJViewBeanCheckBox.setSelected(true);
                        rootDirName.setVisible(true);
                        rootDirTile.setVisible(true);
                        jvbLayoutName.setVisible(true);

                        rootDirName.setText(preFix);

                    } else if ("JViewBean".equals(e.getItem())) {
                        generateALayoutFileCheckBox.setVisible(false);
                        layoutName.setVisible(false);
                        layoutnameTitle.setVisible(false);
                        activityName.setVisible(false);
                        activityTitle.setVisible(false);
                        viewmodelName.setVisible(false);
                        viewmodelTitle.setVisible(false);
                        generageJViewBeanCheckBox.setVisible(false);
                        generateALayoutFileCheckBox.setSelected(false);
                        generageJViewBeanCheckBox.setSelected(false);

                        jvbLayoutName.setVisible(true);
                        jvbName.setVisible(true);
                        jvbLayoutTitle.setVisible(true);
                        jvbNameTitle.setVisible(true);
                        rootDirName.setVisible(false);
                        rootDirTile.setVisible(false);

                        rootDirName.setText("");
                    } else {
                        generateALayoutFileCheckBox.setSelected(true);
                        generateALayoutFileCheckBox.setVisible(true);
                        layoutName.setVisible(true);
                        layoutnameTitle.setVisible(true);

                        activityName.setVisible(true);
                        activityTitle.setVisible(true);
                        viewmodelName.setVisible(true);
                        viewmodelTitle.setVisible(true);
                        generageJViewBeanCheckBox.setVisible(false);
                        generageJViewBeanCheckBox.setSelected(false);
                        rootDirName.setVisible(true);
                        rootDirTile.setVisible(true);
                        layoutnameTitle.setVisible(false);
                        jvbLayoutName.setVisible(false);

                        rootDirName.setText(preFix);
                    }
                }
            }
        });

        generateALayoutFileCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    layoutName.setVisible(true);
                    layoutnameTitle.setVisible(true);
                } else {
                    layoutName.setVisible(false);
                    layoutName.setText("");
                    layoutnameTitle.setVisible(false);
                }
            }
        });

        generageJViewBeanCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    jvbNameTitle.setVisible(true);
                    jvbName.setVisible(true);
                    jvbLayoutTitle.setVisible(true);
                    jvbLayoutName.setVisible(true);
                } else {
                    jvbNameTitle.setVisible(false);
                    jvbName.setVisible(false);
                    jvbLayoutTitle.setVisible(false);
                    jvbLayoutName.setVisible(false);
                    jvbLayoutName.setText("");
                    jvbName.setText("");
                }
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void changeAllText() {
        String text = rootDirName.getText();
        if (!TextUtils.isEmpty(text)) {
            preFix = text;
        } else {
            preFix = "Busi";
        }

        updateText(jvbLayoutName, "item_recv_" + preFix.toLowerCase() + "_layout");
        if ("BasicActivity".equals(TempateType)) {
            updateText(layoutName, "act_" + preFix.toLowerCase() + "_jspark");
            updateText(jvbName, preFix + "Bean");
            updateText(activityName, preFix + "Activity");
            updateText(viewmodelName, preFix + "ViewModel");
        } else {
            updateText(jvbName, preFix + "ViewBean");
            updateText(layoutName, "item_recv_" + preFix.toLowerCase() + "_layout");
            updateText(activityName, preFix + "RecvActivity");
            updateText(viewmodelName, preFix + "RecvViewModel");
        }
    }

    private void updateText(JTextField textField, String text) {
        if (textField.isVisible()) {
            textField.setText(text);
        } else {
            textField.setText("");
        }
    }

    private void onOK() {
        if (TextUtils.isEmpty(activityName.getText().trim()) && TextUtils.isEmpty(jvbName.getText().trim())) {
            return;
        }
        if (generateALayoutFileCheckBox.isSelected() && TextUtils.isEmpty(layoutName.getText().trim())) {
            return;
        }
        if (generageJViewBeanCheckBox.isSelected() && (TextUtils.isEmpty(jvbName.getText().trim()))) {
            return;
        }
        TempConfig tempConfig = new TempConfig(
                rootDirName.getText().trim(),
                activityName.getText().trim(),
                viewmodelName.getText().trim(),
                layoutName.getText().trim(),
                jvbName.getText().trim(),
                jvbLayoutName.getText().trim(),
                choseLanguage.getSelectedItem().toString(),
                Objects.equals(tempTypeChoser.getSelectedItem().toString(), "BasicRecvActivity"));
        new TemplatelRealize().generateFiles(environment, tempConfig);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        DefineFileDialog dialog = new DefineFileDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
