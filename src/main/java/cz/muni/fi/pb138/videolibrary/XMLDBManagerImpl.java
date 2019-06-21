package cz.muni.fi.pb138.videolibrary;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import org.exist.xmldb.EXistResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
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
    private final XPathQueryService xPathQueryService;


    public XMLDBManagerImpl() throws Exception {
        Class cl = Class.forName(DRIVER);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        collection = DatabaseManager.getCollection(URI + COLLECTION, USERNAME, PASSWORD);
        xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        xPathQueryService = (XPathQueryService)collection.getService("XPathQueryService", "1.0");
        xPathQueryService.setProperty("indent", "yes");
    }

    public void close() throws XMLDBException {
        collection.close();
    }

    public Long getMaxId() {
        String xquery = "let $max := data(doc('database.xml')/videoLibrary/categories/category/medium/@id) "
                + "return $max";

        String max = xQueryCaller(xquery);

        if (max.isEmpty()) {
            return (long)0;
        }

        return Long.valueOf(max);
    }

    public boolean createCategory(Category category) {
        if (!categoryExist(category))
        {
        try {
            CompiledExpression compiledExpression = xQueryService.compile("update insert <category name='" + category.getName() + "' /> into doc('database.xml')/videoLibrary/categories");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
        return true;
        }
        return false;
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
            medium.setId(getMaxId()+1);
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

    public void addMediumToCategory(String mediumQuery, String category) {
        try {
            CompiledExpression compiledExpression = xQueryService.compile("update insert " + mediumQuery +
                    " into doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']");
            xQueryService.execute(compiledExpression);
        } catch (XMLDBException ex) {
            ex.printStackTrace();
        }
    }


    public Medium findMediumById(String mediumId) {
        String xpath =
                "doc('database.xml')/videoLibrary/categories/category/medium[@id='"+mediumId+"']";
        String xmlString =  xPathCaller(xpath);
        JAXBContext jaxbContext;
        Medium medium;
        try
        {
            jaxbContext = JAXBContext.newInstance(Medium.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            medium = (Medium) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
            if (medium.getGenres() == null)
                medium.setGenres(new HashSet<>());
            if (medium.getActors() == null)
                medium.setActors(new HashSet<>());
            medium.setCategory(new Category(findCategoryByMediumId(medium.getId().toString())));
            return medium;
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Set<Medium> findMediumByName(String mediumName) {
        String xquery =
                "<mediums>" +
                "{for $medium in doc('database.xml')/videoLibrary/categories/category/medium[name = '"+mediumName+"'] " +
                        "return $medium}" +
                        "</mediums>";
        String xmlToParse = xQueryCaller(xquery);
        return parseXmlToObject(xmlToParse, null);
    }

    public String findCategoryByMediumId(String mediumId) {
        String xpathToCategoryName =
                "string(doc('database.xml')/videoLibrary/categories/category[medium[@id='"+mediumId+"']]/@name)";
        return xPathCaller(xpathToCategoryName);
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

    public Set<Medium> findAllMediumsByCategory(String category) {
        String xpath =
                "<mediums>" +
                        "{for $medium in doc('database.xml')/videoLibrary/categories/category[@name='" + category + "']/medium " +
                        "return $medium}" +
                        "</mediums>";
        return parseXmlToObject(xPathCaller(xpath),null);
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

    public Map<Category, Set<Medium>> exportQueryFromDatabase() {
        String xpath = "doc('database.xml')/videoLibrary/categories";
        String xml = xPathCaller(xpath);

        Set<Category> categories = findAllCategories();

        Set<Medium> mediums = parseXmlToObject(xml, null);

        Map<Category, Set<Medium>> export = new HashMap<>();

        for (Category cat:
             categories) {
            export.put(cat, new HashSet<>());
        }
        for (Medium medium:
             mediums) {
            if (export.containsKey(medium.getCategory())){
                export.get(medium.getCategory()).add(medium);
            }
            else {
                Set<Medium> meds = new HashSet<>();
                meds.add(medium);
                export.put(medium.getCategory(), meds);
            }
        }

        return export;
    }


    public boolean categoryExist(Category category) {
        String xquery =
                "boolean(doc('database.xml')/videoLibrary/categories/category[@name='" + category.getName() + "'])";
        String result = xQueryCaller(xquery);
        if (result.equals("true")) {
            return true;
        }
        return false;
    }

    public boolean mediumExist(Medium medium) {
        String xquery =
                "boolean(doc('database.xml')/videoLibrary/categories/category/medium[@id='"+medium.getId().toString()+"'])";
        String result = xQueryCaller(xquery);
        if (result.equals("true")) {
            return true;
        }
        return false;
    }

    private String xPathCaller(String xpath) {
        String output = "";
        try {
            ResourceSet result = xPathQueryService.query(xpath);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    output = ((String) res.getContent());
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
        return output;
    }

    private String xQueryCaller(String xQuery) {
        String output = "";
        try {
            CompiledExpression compiledExpression = xQueryService.compile(xQuery);
            ResourceSet result = xQueryService.execute(compiledExpression);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    output = (String) res.getContent();
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
        return output;
    }

    private Set<Medium> parseXmlToObject(String xmlString, Category category) {
            JAXBContext jaxbContext;
            Set<Medium> media = new HashSet<>();

            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlString));

                Document doc = db.parse(is);

                NodeList mediaList = doc.getElementsByTagName("medium");

                for (int i = 0; i < mediaList.getLength(); i++) {
                    Element mediumElement = (Element) mediaList.item(i);
                    String node = "<medium id=\"" + mediumElement.getAttribute("id") + "\">\n" +
                            "<mediumType>" +
                            mediumElement.getElementsByTagName("mediumType")
                                    .item(0).getTextContent() +
                            "</mediumType>\n" +
                            "<name>" +
                            mediumElement.getElementsByTagName("name")
                                    .item(0).getTextContent() +
                            "</name>\n" +
                            "<length>" +
                            mediumElement.getElementsByTagName("length")
                                    .item(0).getTextContent() +
                            "</length>\n";


                    NodeList actorList = mediumElement.getElementsByTagName("actor");
                    if (actorList.getLength() > 0) {
                        node += "<actors>\n";
                        for (int j = 0; j < actorList.getLength(); j++) {
                            Element actorElement = (Element) actorList.item(j);
                            node += "<actor>" +
                                    actorElement.getTextContent() +
                                    "</actor>\n";
                        }
                        node += "</actors>\n";
                    }

                    NodeList genresList = mediumElement.getElementsByTagName("genre");

                    if (genresList.getLength() > 0) {
                        node += "<genres>\n";
                        for (int j = 0; j < genresList.getLength(); j++) {
                            Element genreElement = (Element) genresList.item(j);
                            node += "<genre>" +
                                    genreElement.getTextContent() +
                                    "</genre>\n";
                        }
                        node += "</genres>\n";
                    }

                    node += "<releaseYear>" +
                            mediumElement.getElementsByTagName("releaseYear")
                                    .item(0).getTextContent() +
                            "</releaseYear>\n</medium>\n";

                    try
                    {
                        jaxbContext = JAXBContext.newInstance(Medium.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        Medium medium = (Medium) jaxbUnmarshaller.unmarshal(new StringReader(node));

                        if (medium.getGenres() == null)
                            medium.setGenres(new HashSet<>());
                        if (medium.getActors() == null)
                            medium.setActors(new HashSet<>());
                        if (category == null) {
                            medium.setCategory(new Category(findCategoryByMediumId(medium.getId().toString())));
                        } else medium.setCategory(category);
                        media.add(medium);
                    }
                    catch (JAXBException e)
                    {
                        e.printStackTrace();
                        return null;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return media;
        }
    }

