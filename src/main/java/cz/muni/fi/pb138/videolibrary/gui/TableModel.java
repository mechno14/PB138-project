package cz.muni.fi.pb138.videolibrary.gui;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TableModel extends AbstractTableModel {

    private List<Medium> media;
    private Category category;
    private MediumManager mediumManager;

    private String[] columnNames = {"Name","Genre","Year","Type"};

    public TableModel(MediumManager mediumManager, Category category) {
        this.mediumManager = mediumManager;
        this.category = category;
        media = new ArrayList<>(mediumManager.findAllMediaByCategory(category));
    }

    public void setCategory(Category category) {
        this.category = category;
        media = new ArrayList<>(mediumManager.findAllMediaByCategory(category));
        fireTableDataChanged();
    }

    public void removeRow(int i) {
        Medium medium = media.get(i);
        mediumManager.deleteMedium(medium);
        media = new ArrayList<>(mediumManager.findAllMediaByCategory(category));
        fireTableDataChanged();
    }

    public Medium getMediumAtRow(int i) {
        return media.get(i);
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    @Override
    public int getRowCount() {
        return media.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Medium medium = media.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return medium.getName();
            case 1:
                StringBuilder stringBuilder = new StringBuilder();
                Set<Genre> genres = medium.getGenres();
                for (Genre genre : genres) {
                    stringBuilder.append(genre);
                    stringBuilder.append(", ");
                }
                if (stringBuilder.length() > 2) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
                return stringBuilder.toString();
            case 2:
                return medium.getReleaseYear();
            case 3:
                return medium.getMediumType();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}
