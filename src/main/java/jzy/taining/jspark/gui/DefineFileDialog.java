package jzy.taining.jspark.gui;

import jzy.taining.plugins.jspark.features.templates.TemplatelRealize;
import jzy.taining.plugins.jspark.features.templates.data.Environment;
import jzy.taining.plugins.jspark.features.templates.data.TempConfig;
import org.apache.http.util.TextUtils;

import javax.swing.*;
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
                        activityName.setText("BusiRecvActivity");
                        viewmodelName.setText("BusiRecvViewModel");
                        jvbName.setText("BusiViewBean");
                        jvbLayoutName.setText("item_jvb_busi_layout");
                        layoutName.setText("");

                        rootDirName.setVisible(true);
                        rootDirName.setText("busi");
                        rootDirTile.setVisible(true);
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
                        jvbLayoutName.setText("item_jvb_busi_layout");
                        jvbName.setVisible(true);
                        jvbName.setText("BusiViewBean");
                        jvbLayoutTitle.setVisible(true);
                        jvbNameTitle.setVisible(true);
                        rootDirName.setVisible(false);
                        rootDirName.setText("");
                        activityName.setText("");
                        layoutName.setText("");
                        viewmodelName.setText("");
                        rootDirTile.setVisible(false);
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
                        activityName.setText("BusiActivity");
                        layoutName.setText("activity_busi");
                        viewmodelName.setText("BusiViewModel");
                        jvbName.setText("UiData");
                        jvbLayoutName.setVisible(false);
                        jvbLayoutName.setText("");

                        rootDirName.setVisible(true);
                        rootDirName.setText("busi");
                        rootDirTile.setVisible(true);
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

    private void onOK() {
        if (TextUtils.isEmpty(activityName.getText().trim()) && TextUtils.isEmpty(jvbName.getText().trim())) {
            return;
        }
        if (generateALayoutFileCheckBox.isSelected() && TextUtils.isEmpty(layoutName.getText().trim())) {

            return;
        }
        if (generageJViewBeanCheckBox.isSelected() && (TextUtils.isEmpty(jvbName.getText().trim()) || TextUtils.isEmpty(jvbLayoutName.getText().trim()))) {
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
                Objects.equals(tempTypeChoser.getSelectedItem().toString(),"BasicRecvActivity"));
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
