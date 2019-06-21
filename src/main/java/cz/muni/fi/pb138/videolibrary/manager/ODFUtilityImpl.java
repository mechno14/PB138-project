package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class ODFUtilityImpl implements ODFUtility {

    @Override
    public SpreadsheetDocument readFile(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }

        return readFile(new File(path));
    }

    @Override
    public SpreadsheetDocument readFile(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        SpreadsheetDocument document;
        try {
            document = SpreadsheetDocument.loadDocument(file);
        } catch (Exception e) {
            throw new IOException("Failed to open document or the file is not in .ods format", e);
        }
        return document;
    }

    @Override
    public void writeFile(String path, SpreadsheetDocument document) throws IOException {
        if (path == null || document == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        writeFile(new File(path), document);
    }

    @Override
    public void writeFile(File file, SpreadsheetDocument document) throws IOException {
        if (file == null || document == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        try {
            document.save(file);
        } catch (Exception e) {
            throw new IOException("Failed to write to document", e);
        }
    }

    @Override
    public Map<Category,Set<Medium>> transformToSet(SpreadsheetDocument document) {
        if (document == null) {
            throw new IllegalArgumentException("document cannot be null");
        }

        Map<Category, Set<Medium>> categories = new HashMap<>();
        Set<Medium> mediums = new HashSet<>();
        document.getTableList()
                .forEach(table -> {
                    Category category = new Category();
                    category.setName(table.getTableName());
                    mediums.addAll(table
                            .getRowList()
                            .stream()
                            .skip(1)
                            .map(row -> parseRow(table.getTableName(), row))
                            .collect(Collectors.toSet())
                    );
                    categories.put(category, mediums);
                });

        return categories;
    }

    private static Medium parseRow(String categoryName, Row row) {
        Long id = Long.parseLong(row.getCellByIndex(0).getDisplayText());
        String name = row.getCellByIndex(1).getDisplayText();
        MediumType mediumType = toMediumType(row.getCellByIndex(2).getDisplayText());
        int length = row.getCellByIndex(3).getDoubleValue().intValue();

        //Set<String> actors = new HashSet<>(Arrays.asList(row.getCellByIndex(2).getDisplayText().split(";")));
        int releaseYear = Integer.parseInt(row.getCellByIndex(4).getDisplayText());
        Category category = new Category(categoryName);

        return mediumConstructor(id, name, mediumType, length, category, releaseYear);
    }

    private static Medium mediumConstructor(Long id, String name, MediumType mediumType, int length, Category category, int releaseYear) {
        Medium medium = new Medium();
        medium.setId(id);
        medium.setLength(length);
        medium.setCategory(category);
        medium.setName(name);
        medium.setMediumType(mediumType);
        medium.setReleaseYear(releaseYear);
        return medium;
    }

    private static MediumType toMediumType(String mediumType) {
        if (mediumType.equals("DVD")) {
            return MediumType.DVD;
        }
        if (mediumType.equals("VHS")) {
            return MediumType.VHS;
        }
        if (mediumType.equals("USB")) {
            return MediumType.USB;
        } else {
            throw new IllegalArgumentException("uknown medium type");}
    }


    @Override
    public SpreadsheetDocument transformToDocument(Map<Category, Set<Medium>> categoryMap) {
        if (categoryMap == null) {
            throw new IllegalArgumentException("categorySet cannot be null");
        }

        SpreadsheetDocument document = null;
        try {
            document = SpreadsheetDocument.newSpreadsheetDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }

        document.removeSheet(0);
        for (Category category : categoryMap.keySet()) {
            Table table = document.appendSheet(category.getName());
            table.appendRow();
            table.removeRowsByIndex(0, 3);
            System.out.println(table.getRowCount());
            table.appendColumns(5);
            writeFirstRow(table.appendRow());
            categoryMap.get(category).forEach(movie -> parseMovie(table.appendRow(), movie));
        }

        return document;
    }


    private void writeFirstRow(Row row) {
        row.getCellByIndex(0).setDisplayText("id");
        row.getCellByIndex(1).setDisplayText("name");
        row.getCellByIndex(2).setDisplayText("mediumType");
        row.getCellByIndex(3).setDisplayText("length");
        row.getCellByIndex(4).setDisplayText("releaseYear");
    }

    private static void parseMovie(Row row, Medium medium) {
        row.getCellByIndex(0).setFormatString("0");
        row.getCellByIndex(0).setDisplayText(medium.getId().toString());
        row.getCellByIndex(1).setDisplayText(medium.getName());
        row.getCellByIndex(2).setDisplayText(medium.getMediumType().toString());
        //row.getCellByIndex(2).setDisplayText(String.join(";", movie.getActors()));
        row.getCellByIndex(0).setFormatString("0");
        row.getCellByIndex(3).setDisplayText(Integer.toString(medium.getLength()));
        row.getCellByIndex(4).setFormatString("0");
        row.getCellByIndex(4).setDisplayText(Integer.toString(medium.getReleaseYear()));

    }
}
