package cz.muni.fi.pb138.videolibrary;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

import java.util.*;

/**
 * @author Denis Hambalek
 */

public class XMLDBManagerImpl implements XMLDBManager{

    private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
    private final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private final static String COLLECTION = "/db/test";
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "admin";

    private final Collection collection;
    private final XQueryService xQueryService;


    public XMLDBManagerImpl() throws Exception {
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

    public Long getFirstFreeId() {
        String xpath =
                "for $category in doc('database.xml')/videoLibrary/categories/category " +
                        "for $medium in $category/medium " +
                        "return number($medium/@id)";
        Set<Integer> ids = new HashSet<>();
        try {
            CompiledExpression compiledExpression = xQueryService.compile(xpath);
            ResourceSet result = xQueryService.execute(compiledExpression);
            ResourceIterator i = result.getIterator();
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
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
        int counter = 1;
        for (Integer id :
                ids) {
            if (id != counter) {
                return (long)counter;
            }
            counter++;
        }
        return (long)counter;
    }

    public void createCategory(Category category) {
        try {
            CompiledExpression compiledExpression = xQueryService.compile("update insert <category name='" + category.getName() + "' /> into doc('database.xml')/videoLibrary/categories");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCategory(Category category) {
        try {
            CompiledExpression compiledExpression = xQueryService.compile("update delete doc('database.xml')/videoLibrary/categories/category[@name='" + category.getName() + "']");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
    }

    public void createMedium(Medium medium) {
        if (medium.getId() == null) {
            medium.setId(getFirstFreeId());
        }
        String mediumQuery = "<medium id='" + medium.getId() + "'>" +
                "<mediumType>" + medium.getMediumType().toString() + "</mediumType>" +
                "<name>" + medium.getName() + "</name>" +
                "<length>" + Integer.valueOf(medium.getLength()) + "</length>" +
                "<actors>";

        for (String actor :
                medium.getActors()) {
            mediumQuery += "<actor>" + actor + "</actor>";
        }

        mediumQuery += "</actors><genres>";

        for (Genre genre :
                medium.getGenres()) {
            mediumQuery += "<genre>" + genre.toString() + "</genre>";
        }

        mediumQuery += "</genres><releaseYear>" + medium.getReleaseYear()
                + "</releaseYear></medium>";

        try {
            CompiledExpression compiledExpression = xQueryService.compile("update insert " + mediumQuery +
                    " into doc('database.xml')/videoLibrary/categories/category[@name='" + medium.getCategory().getName() + "']");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteMedium(String mediumId) {
        try {
            CompiledExpression compiledExpression = xQueryService.compile("update delete doc('database.xml')/videoLibrary/categories/category/medium[@id='" + mediumId + "']");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
    }

    public void addMediumToCategory(String mediumQuery, String category) {
        try {
            CompiledExpression compiledExpression = xQueryService.compile("update insert " + mediumQuery +
                    " into doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteMedium(Medium medium) {
        try {
            CompiledExpression compiledExpression = xQueryService.compile(
                    "update delete doc('database.xml')/videoLibrary/categories/category[@name='"
                            + medium.getCategory().getName() + "']/medium[@id='" + medium.getId().toString() + "']");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
    }

    public String findMediumById(String mediumId) {
        String xquery =
        "for $medium in doc('database.xml')/videoLibrary/categories/category/medium[@id = '"+mediumId+"'] " +
        "return $medium";
        return findMedium(xquery);
    }

    public String findMediumByName(String mediumName) {
        String xquery =
                "for $medium in doc('database.xml')/videoLibrary/categories/category/medium[name = '"+mediumName+"'] " +
                        "return $medium";
        return findMedium(xquery);
    }

    private String findMedium(String xpathToMedium) {
        String medium = "";
        try {
            CompiledExpression compiledExpression = xQueryService.compile(xpathToMedium);
            ResourceSet result = xQueryService.execute(compiledExpression);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            if (i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    medium = (String) res.getContent();
                } finally {
                    try {
                        ((EXistResource) res).freeResources();
                    } catch (XMLDBException xe) {
                        xe.printStackTrace();
                    }
                }
            }
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
        return medium;
    }

    public Set<Category> findAllCategories() {
        String xpath =
                "for $category in doc('database.xml')/videoLibrary/categories/category " +
                        "return data($category/@name)";
        List<String> categories = new ArrayList<>();
        try {

            CompiledExpression compiledExpression = xQueryService.compile(xpath);
            ResourceSet result = xQueryService.execute(compiledExpression);
            ResourceIterator i = result.getIterator();
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
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }

        Set<Category> cats = new HashSet<>();

        for (String cat :
                categories) {
            cats.add(new Category(cat));
        }

        return cats;
    }

    public String findAllMediumsByCategory(String category) {
        String xpath =
                "<mediums><category>" + category + "</category>" +
                        "{for $medium in doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']/medium " +
                        "return $medium}" +
                        "</mediums>";
        String mediums = "";
        try {
            CompiledExpression compiledExpression = xQueryService.compile(xpath);
            ResourceSet result = xQueryService.execute(compiledExpression);
            ResourceIterator i = result.getIterator();
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
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
        return mediums;
    }

    public void importIntoDatabase(Map<Category, Set<Medium>> categoryMap) {
        for (Map.Entry<Category, Set<Medium>> entry : categoryMap.entrySet()) {
            if (!categoryExist(entry.getKey())){
                createCategory(entry.getKey());
            }
            for (Medium medium:
                    entry.getValue()) {
                if (!mediumExist(medium)) {
                    createMedium(medium);
                }
            }
        }
    }

    public boolean categoryExist(Category category) {
        String xquery =
                "boolean(doc('database.xml')/videoLibrary/categories/category[@name='" + category.getName() + "'])";
        return exist(xquery);
    }

    public boolean mediumExist(Medium medium) {
        String xquery =
                "boolean(doc('database.xml')/videoLibrary/categories/category/medium[@id='"+medium.getId().toString()+"'])";
        return exist(xquery);
    }

    private boolean exist(String xquery) {
        String contains = "false";
        try {
            CompiledExpression compiledExpression = xQueryService.compile(xquery);
            ResourceSet result = xQueryService.execute(compiledExpression);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            if (i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    contains = ((String) res.getContent());
                } finally {
                    try {
                        ((EXistResource) res).freeResources();
                    } catch (XMLDBException xe) {
                        xe.printStackTrace();
                    }
                }
            }
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }

        if (contains.equals("true")) {
            return true;
        }

        return false;
    }

}
