package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.XMLDBManager;
import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;
import cz.muni.fi.pb138.videolibrary.manager.MediumManagerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.time.Year;

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
        XMLDBManagerImpl nativeXMLDatabaseManager = new XMLDBManagerImpl();
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


        String xmlString = "<medium id=\"1\">\n" +
                "                <mediumType>DVD</mediumType>\n" +
                "                <name>Shrek</name>\n" +
                "                <length>90</length>\n" +
                "                <actors>\n" +
                "                    <actor>Mike Myers</actor>\n" +
                "                    <actor>Eddie Murphy</actor>\n" +
                "                    <actor>Cameron Diaz</actor>\n" +
                "                </actors>\n" +
                "                <genres>\n" +
                "                    <genre>Animated</genre>\n" +
                "                    <genre>Adventure</genre>\n" +
                "                    <genre>Comedy</genre>\n" +
                "                </genres>\n" +
                "                <releaseYear>2001</releaseYear>\n" +
                "            </medium>";

        JAXBContext jaxbContext;
        try
        {
            jaxbContext = JAXBContext.newInstance(Medium.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Medium employee = (Medium) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));

            System.out.println(employee.getId() + employee.getName() + employee.getLength()
                + employee.getReleaseYear() + employee.getActors().size()
                    + employee.getGenres().iterator().next());
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }
}
