package cz.muni.fi.pb138.videolibrary.gui;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.manager.MediumManager;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Set;

public class TableModel extends AbstractTableModel {

    private Set<Medium> media;
    private MediumManager mediumManager;
    private Category category;

    public TableModel(MediumManager mediumManager, Category category) {
        this.mediumManager = mediumManager;
        media = mediumManager.findAllMediaByCategory(category);
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
