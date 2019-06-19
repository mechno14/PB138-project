package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.NativeXMLDatabaseManager;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;
import cz.muni.fi.pb138.videolibrary.manager.MediumManagerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui {

    private JPanel panel;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton deleteButton;
    private JTextField textField1;
    private JButton addCategoryButton;

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

        String column_names[]= {"Name","Genre","Year","Type"};
        TableModel table_model=new DefaultTableModel(column_names,4);
        table1 = new JTable(table_model);
        NativeXMLDatabaseManager nativeXMLDatabaseManager = new NativeXMLDatabaseManager();
        categoryManager = new CategoryManagerImpl(nativeXMLDatabaseManager);
        mediumManager = new MediumManagerImpl(nativeXMLDatabaseManager);
        System.out.println("ssssssssssssssss");
        setComboBox();

    }

    private void setComboBox() {
        comboBox1.removeAllItems();
        for(Category category : categoryManager.findAllCategories()) {
            comboBox1.addItem(category.getName());
        }

        mediumManager.findMediumByName("Shrek");
    }
}
