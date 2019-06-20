package  cz.muni.fi.pb138.videolibrary.gui;
import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;


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
        if (textFieldName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty");
            return;
        }
        if (textFieldYear.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Year cannot be empty");
            return;
        }
        int year;
        try {
            year = Integer.valueOf(textFieldYear.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Incorrect year");
            return;
        }
        if (year < 1900) {
            JOptionPane.showMessageDialog(null, "Year cannot be sooner than 1900");
            return;
        }

        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.valueOf(comboBoxGenre.getSelectedItem().toString()));

        mediumManager.createMedium(new Medium(textFieldName.getText(), MediumType.valueOf(comboBoxType.getSelectedItem().toString()),
                0, new Category(comboBoxCategory.getSelectedItem().toString()), new HashSet<String>(), genres,
                year));

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
    }

    private void setComboBox() {
        comboBoxCategory.removeAllItems();
        Set<Category> categories = categoryManager.findAllCategories();

        for(Category category : categories) {
            comboBoxCategory.addItem(category.getName());
        }

    }
}

