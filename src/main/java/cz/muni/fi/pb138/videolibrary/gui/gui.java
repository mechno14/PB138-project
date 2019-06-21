package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.XMLDBManager;
import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;
import cz.muni.fi.pb138.videolibrary.manager.MediumManagerImpl;
import org.xmldb.api.base.XMLDBException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class gui {

    private JPanel panel;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton deleteButton;
    private JTextField textField1;
    private JButton addCategoryButton;
    private JButton addMediumButton;
    private JButton relocateButton;
    private JButton moreInfoButton;

    private CategoryManager categoryManager;
    private MediumManager mediumManager;


    public gui() {
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name of category cannot be empty.");
                    return;
                }
                Category category = new Category(textField1.getText());
                if (!categoryManager.createCategory(category)) {
                    JOptionPane.showMessageDialog(null, "Name of category already exist.");
                    return;
                }
                setComboBox();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem() != null) {
                    TableModel tableModel = (TableModel) table1.getModel();
                    tableModel.setCategory(new Category(comboBox1.getSelectedItem().toString()));
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "No medium selected.");
                    return;
                }
                TableModel tableModel = (TableModel) table1.getModel();
                tableModel.removeRow(table1.getSelectedRow());
            }
        });

        addMediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMediumDialog dialog = new AddMediumDialog(categoryManager, mediumManager);
                dialog.pack();
                dialog.setVisible(true);

                TableModel tableModel = (TableModel) table1.getModel();
                tableModel.setCategory(new Category(comboBox1.getSelectedItem().toString()));
            }
        });

        relocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "No medium selected.");
                    return;
                }

                TableModel tableModel = (TableModel) table1.getModel();
                RelocateMedium dialog = new RelocateMedium(categoryManager, tableModel.getMediumAtRow(table1.getSelectedRow()));
                dialog.pack();
                dialog.setVisible(true);

                tableModel.setCategory(new Category(comboBox1.getSelectedItem().toString()));
            }
        });
        moreInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "No medium selected.");
                    return;
                }

                TableModel tableModel = (TableModel) table1.getModel();
                MoreInfoDialog dialog = new MoreInfoDialog(tableModel.getMediumAtRow(table1.getSelectedRow()));
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Video Library");
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
    }

    private void setComboBox() {
        comboBox1.removeAllItems();
        Set<Category> categories = categoryManager.findAllCategories();
        for(Category category : categories) {
            comboBox1.addItem(category.getName());
            //System.out.println(category.getName());
        }

        /*
        try
        {
            XMLDBManagerImpl db = new XMLDBManagerImpl();
            Map<Category, Set<Medium>> test = db.exportQueryFromDatabase();
            for (Map.Entry<Category, Set<Medium>> entry : test.entrySet()) {
                System.out.println(entry.getKey().getName() + ":");
                for (Medium med:
                     entry.getValue()) {
                    System.out.println(med.getName());
                }
            }
        } catch (Exception ex) {ex.printStackTrace();}
         */
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

