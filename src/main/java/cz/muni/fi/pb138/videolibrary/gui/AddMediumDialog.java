package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;
import cz.muni.fi.pb138.videolibrary.manager.MediumManagerImpl;
import tests.cz.muni.fi.pb138.videolibary.classes.MediumManagerImplTest;


import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class AddMediumDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JComboBox comboBoxCategory;
    private JComboBox comboBoxType;
    private JTextField textFieldYear;
    private JComboBox comboBoxGenre;

    private CategoryManager categoryManager;
    private MediumManager mediumManager;

    public AddMediumDialog(CategoryManager categoryManager, MediumManager mediumManager) {
        this.categoryManager = categoryManager;
        this.mediumManager = mediumManager;

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
        if (textFieldName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name of medium cannot be empty.");
            return;
        }
        if (textFieldYear.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Year of medium cannot be empty.");
            return;
        }

        Category category = new Category(comboBoxCategory.getSelectedItem().toString());
        MediumType mediumType = MediumType.valueOf(comboBoxType.getSelectedItem().toString());
        Genre genre = Genre.valueOf(comboBoxGenre.getSelectedItem().toString());

        Medium medium = new Medium(textFieldName.getText().trim(),
                mediumType, category, genre, Integer.valueOf(textFieldYear.getText().trim()));
        mediumManager.createMedium(medium);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) throws Exception {
        /*
        AddMediumDialog dialog = new AddMediumDialog(categoryManager, mediumManager);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);*/
    }

    private void createUIComponents() {
        comboBoxCategory = new JComboBox();
        comboBoxGenre = new JComboBox(Genre.values());
        comboBoxType = new JComboBox(MediumType.values());

        setComboBox();
        System.out.println(comboBoxCategory.getSelectedItem());
    }

    private void setComboBox() {
        comboBoxCategory.removeAllItems();
        Set<Category> categories = categoryManager.findAllCategories();

        for(Category category : categories) {
            comboBoxCategory.addItem(category.getName());
        }

    }
}