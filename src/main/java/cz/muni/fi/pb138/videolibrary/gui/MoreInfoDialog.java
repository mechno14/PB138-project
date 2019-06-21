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
        StringBuilder actors = new StringBuilder();
        for (String actor : medium.getActors()) {
            actors.append(actor + ", ");
        }
        if (actors.length() > 2) {
            actors.setLength(actors.length() - 2);
        }
        textFieldActors.setText(actors.toString());

        StringBuilder genres = new StringBuilder();
        for (Genre genre : medium.getGenres()) {
            genres.append(genre.name() + ", ");
        }
        if (genres.length() > 2) {
            genres.setLength(genres.length() - 2);
        }
        textFieldGenres.setText(genres.toString());

    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
