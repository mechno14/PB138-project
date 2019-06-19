package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;

import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;
import cz.muni.fi.pb138.videolibrary.exception.IllegalEntityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

public class MediumManagerImpl implements MediumManager {


    private XMLDBManagerImpl databaseManager;

    public MediumManagerImpl(XMLDBManagerImpl databaseManager) {
        this.databaseManager = databaseManager;
    }

    private void validate(Medium medium) {
        if (medium == null)
            throw new IllegalArgumentException("Medium cannot be null.");

        if (medium.getName() == null ||
                medium.getMediumType() == null ||
                medium.getCategory() == null ||
                medium.getActors() == null ||
                medium.getGenres() == null)
            throw new EntityValidationException("Entity has null attribute(s).");

        if (medium.getLength() < 0)
            throw new EntityValidationException("Length cannot be negative.");

        if (medium.getName().isEmpty())
            throw new EntityValidationException("Name cannot be empty.");

        if (medium.getReleaseYear() < 1900)
            throw new EntityValidationException("Wrong year.");
    }

    @Override
    public void createMedium(Medium medium) {
        validate(medium);

        if (medium.getId() != null)
            throw new IllegalEntityException("Entity has already id.");

        databaseManager.createMedium(medium);

    }

    @Override
    public void deleteMedium(Medium medium) {
        validate(medium);

        if (medium.getId() == null || findMediumById(medium.getId()) == null)
            throw new IllegalEntityException("Entity is not in db.");

        databaseManager.deleteMedium(medium);
    }

    @Override
    public Medium findMediumById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");

        String xmlString = databaseManager.findMediumById(id.toString());
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
            return medium;
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Medium findMediumByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty.");

        String xmlString = databaseManager.findMediumByName(name);
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
            return medium;
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Medium> findAllMediaByCategory(Category category) {

        String xmlString = databaseManager.findAllMediumsByCategory(category.getName());
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
                if (actorList.getLength() > 0) {
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
                    medium.setCategory
                            (new Category(doc.getElementsByTagName("category").item(0).getTextContent()));
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
