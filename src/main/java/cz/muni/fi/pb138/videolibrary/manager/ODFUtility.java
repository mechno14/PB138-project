package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import org.odftoolkit.simple.SpreadsheetDocument;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author Simona Bennárová
 */

public interface ODFUtility {

    /**
     * Reads .ods file by given path
     * @param path path to .ods document
     * @return content of file in form of SpreadsheetDocument
     * @throws IOException if something goes wrong with file
     */
    SpreadsheetDocument readFile(String path) throws IOException;

    /**
     * Reads .ods file by given path
     * @param file - .ods document
     * @return content of file in form of SpreadsheetDocument
     * @throws IOException if something goes wrong with file
     */
    SpreadsheetDocument readFile(File file) throws IOException;

    /**
     * Writes content of @param document into the .ods file specified in path
     * @param path path to .ods document, where is written
     * @param document SpreadsheetDocument, content is written to @path
     * @throws IOException when problems with files occurs
     */
    void writeFile(String path, SpreadsheetDocument document) throws IOException;

    /**
     * Writes content of @param document into the .ods file specified in path
     * @param file .ods document, where is written
     * @param document SpreadsheetDocument, content is written to @path
     * @throws IOException when problems with files occurs
     */
    void writeFile(File file, SpreadsheetDocument document) throws IOException;

    /**
     * Transform .ods document into list of categories
     * @param document document to transform
     * @return map of categories and mediums containing data from document
     */
    Map<Category,Set<Medium>> transformToSet(SpreadsheetDocument document);

    /**
     * Transform list of categories to SpreadsheetDocument format
     * @param categoryMap categories to transform
     * @return SpreadsheetDocument containing data from map
     */
    SpreadsheetDocument transformToDocument(Map<Category, Set<Medium>> categoryMap);


}

