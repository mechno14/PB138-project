package cz.muni.fi.pb138.videolibrary;

import cz.muni.fi.pb138.videolibrary.entity.Medium;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

import java.util.*;

public class NativeXMLDatabaseManager {

    private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
    private final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private final static String COLLECTION = "/db/test";
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "admin";

    private final Collection collection;
    private final XQueryService xQueryService;


    NativeXMLDatabaseManager() throws Exception {

        Class cl = Class.forName(DRIVER);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        collection = DatabaseManager.getCollection(URI + COLLECTION, USERNAME, PASSWORD);
        xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
    }

    public void close() throws XMLDBException {
        collection.close();
    }

    public String getFirstFreeId() throws XMLDBException
    {
        String xpath =
                        "for $category in doc('database.xml')/videoLibrary/categories/category " +
                        "for $medium in $category/medium " +
                "return number($medium/@id)";
        CompiledExpression compiledExpression = xQueryService.compile(xpath);
        ResourceSet result = xQueryService.execute(compiledExpression);
        ResourceIterator i = result.getIterator();
        Set<Integer> ids = new HashSet<>();
        Resource res = null;
        while (i.hasMoreResources()) {
            try {
                res = i.nextResource();
                ids.add(Integer.valueOf((String) res.getContent()));
            } finally {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        int counter = 1;
        for (Integer id:
             ids) {
            if (id != counter) {
                return Integer.toString(counter);
            }
            counter++;
        }
        return Integer.toString(counter);
    }

    public void createCategory(String category) throws XMLDBException {
        CompiledExpression compiledExpression = xQueryService.compile("update insert <category name='" + category + "' /> into doc('database.xml')/videoLibrary/categories");
        xQueryService.execute(compiledExpression);
    }

    public void deleteCategory(String category) throws XMLDBException {
        CompiledExpression compiledExpression = xQueryService.compile("update delete doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']");
        xQueryService.execute(compiledExpression);
    }

    public void addMediumToCategory(String mediumQuery, String category) throws XMLDBException {
        CompiledExpression compiledExpression = xQueryService.compile("update insert " + mediumQuery +
                " into doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']");
        xQueryService.execute(compiledExpression);
    }

    public void deleteMediumFromCategory(String mediumId, String category) throws XMLDBException {
        CompiledExpression compiledExpression = xQueryService.compile(
                "update delete doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']/medium[@id='" + mediumId + "']");
        xQueryService.execute(compiledExpression);
    }

    public String findMediumById(String mediumId) throws XMLDBException {
        String xpath =
                "for $category in doc('database.xml')/videoLibrary/categories/category " +
                        "return data($category/medium[id='"+mediumId+"'])";
        CompiledExpression compiledExpression = xQueryService.compile(xpath);
        ResourceSet result = xQueryService.execute(compiledExpression);
        ResourceIterator i = result.getIterator();
        String medium = "";
        Resource res = null;
        if (i.hasMoreResources()) {
            try {
                res = i.nextResource();
                medium = (String)res.getContent();
            } finally {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return medium;
    }

    public String findMediumByName(String mediumName) throws XMLDBException {
        String xpath =
                "for $category in doc('database.xml')/videoLibrary/categories/category " +
                        "return data($category/medium/name/text()='"+mediumName+"')";
        CompiledExpression compiledExpression = xQueryService.compile(xpath);
        ResourceSet result = xQueryService.execute(compiledExpression);
        ResourceIterator i = result.getIterator();
        String medium = "";
        Resource res = null;
        if (i.hasMoreResources()) {
            try {
                res = i.nextResource();
                medium = (String)res.getContent();
            } finally {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return medium;
    }

    public void moveMediumToDifferentCategory(String mediumId, String category) throws XMLDBException {
        String medium = findMediumById(mediumId);
        deleteMedium(mediumId);
        addMediumToCategory(medium, category);
    }

    public List<String> findAllCategories() throws XMLDBException {
        String xpath =
                "for $category in doc('database.xml')/videoLibrary/categories/category " +
                        "return data($category/@name)";
        CompiledExpression compiledExpression = xQueryService.compile(xpath);
        ResourceSet result = xQueryService.execute(compiledExpression);
        ResourceIterator i = result.getIterator();
        List<String> categories = new ArrayList<>();
        Resource res = null;
        while (i.hasMoreResources()) {
            try {
                res = i.nextResource();
                categories.add((String) res.getContent());
            } finally {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return categories;
    }

    public void deleteMedium(String mediumId) throws XMLDBException {
        CompiledExpression compiledExpression = xQueryService.compile("update delete doc('database.xml')/videoLibrary/categories/category");
        xQueryService.execute(compiledExpression);
    }

    public String findAllMediumsByCategory(String category) throws XMLDBException {
        String xpath =
                "<mediums>" +
                        "{for $medium in doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']/medium " +
                        "return $medium}" +
                        "</mediums>";
        CompiledExpression compiledExpression = xQueryService.compile(xpath);
        ResourceSet result = xQueryService.execute(compiledExpression);
        ResourceIterator i = result.getIterator();
        String mediums = "";
        Resource res = null;
        while (i.hasMoreResources()) {
            try {
                res = i.nextResource();
                mediums = ((String) res.getContent());
            } finally {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return mediums;
    }

}
