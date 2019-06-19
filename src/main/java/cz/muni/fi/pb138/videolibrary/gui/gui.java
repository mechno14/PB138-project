package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;
import cz.muni.fi.pb138.videolibrary.manager.MediumManagerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

public class gui {

    private JPanel panel;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton deleteButton;
    private JTextField textField1;
    private JButton addCategoryButton;
    private JButton addMediumButton;

    private CategoryManager categoryManager;
    private MediumManager mediumManager;


    public gui() {
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category category = new Category(textField1.getText());
                categoryManager.createCategory(category);
                setComboBox();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel tableModel = (TableModel) table1.getModel();
                tableModel.setCategory(new Category(comboBox1.getSelectedItem().toString()));
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel tableModel = (TableModel) table1.getModel();
                tableModel.removeRow(table1.getSelectedRow());
            }
        });
        addMediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Car Rental");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setContentPane(new gui().panel);
                frame.setPreferredSize(new Dimension(1000,600));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private void createUIComponents() throws Exception {
        panel = new JPanel();
        comboBox1 = new JComboBox();

        XMLDBManagerImpl nativeXMLDatabaseManager = new XMLDBManagerImpl();
        categoryManager = new CategoryManagerImpl(nativeXMLDatabaseManager);
        mediumManager = new MediumManagerImpl(nativeXMLDatabaseManager);
        setComboBox();


        cz.muni.fi.pb138.videolibrary.gui.TableModel tableModel =
                new cz.muni.fi.pb138.videolibrary.gui.TableModel
                        (mediumManager, new Category(comboBox1.getSelectedItem().toString()));
        table1 = new JTable(tableModel);

        setComboBox();

    }

    private void setComboBox() {
        comboBox1.removeAllItems();
        Category cat = new Category();
        Set<Category> categories = categoryManager.findAllCategories();
        for(Category category : categories) {
            comboBox1.addItem(category.getName());
        }
/*
        Iterator<Category> it = categories.iterator();
        while (it.hasNext()) {
            cat = it.next();
        }

        Set<Medium> media = mediumManager.findAllMediaByCategory
                (cat);

        for (Medium medium : media) {
            System.out.println(medium.getName());
        }*/

    }
}
