package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoreInfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldGenres;
    private JTextField textFieldYear;
    private JTextField textFieldLength;
    private JTextField textFieldActors;
    private JTextField textFieldType;

    private Medium medium;

    public MoreInfoDialog(Medium medium) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

        this.medium = medium;

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        textFieldName.setText(medium.getName());
        textFieldLength.setText(Integer.toString(medium.getLength()));
        textFieldYear.setText(Integer.toString(medium.getReleaseYear()));
        textFieldType.setText(medium.getMediumType().name());
        for (String actor : medium.getActors()) {
            textFieldActors.setText(actor + ' ');
        }
        for (Genre genre : medium.getGenres()) {
            textFieldGenres.setText(genre.name() + ' ');
        }

    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
