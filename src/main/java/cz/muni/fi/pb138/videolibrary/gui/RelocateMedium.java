package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;

import javax.swing.*;
import java.awt.event.*;
import java.util.Set;

public class RelocateMedium extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxCategory;
    private JLabel mediumName;

    private CategoryManager categoryManager;
    private Medium medium;


    public RelocateMedium(CategoryManager categoryManager, Medium medium) {
        this.categoryManager = categoryManager;
        this.medium = medium;

        mediumName.setText(medium.getName());

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        categoryManager.moveMedium(medium, new Category(comboBoxCategory.getSelectedItem().toString()));

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void main(String[] args) {
        /*
        RelocateMedium dialog = new RelocateMedium(categoryManager, mediumManager);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);*/
    }

    private void createUIComponents() {
        comboBoxCategory = new JComboBox();

        setComboBox();
    }

    private void setComboBox() {
        comboBoxCategory.removeAllItems();
        Set<Category> categories = categoryManager.findAllCategories();

        for(Category category : categories) {
            comboBoxCategory.addItem(category.getName());
        }

    }
}
