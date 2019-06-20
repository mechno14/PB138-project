package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;
import cz.muni.fi.pb138.videolibrary.manager.MediumManagerImpl;
import org.xmldb.api.base.XMLDBException;

import javax.swing.*;
import java.awt.event.*;

public class AddMediumDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    static private CategoryManager categoryManager;
    static private MediumManager mediumManager;

    public AddMediumDialog(CategoryManager categoryManager, MediumManager mediumManager) {
            this.categoryManager = categoryManager;
            this.mediumManager = mediumManager;

        //XMLDBManagerImpl nativeXMLDatabaseManager = new XMLDBManagerImpl();

        /*categoryManager = new CategoryManagerImpl(nativeXMLDatabaseManager);
        mediumManager = new MediumManagerImpl(nativeXMLDatabaseManager);*/

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
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) throws Exception {
        AddMediumDialog dialog = new AddMediumDialog(categoryManager, mediumManager);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
