package cz.muni.fi.pb138.videolibrary;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManager;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String args[]) throws Exception {
        XMLDBManagerImpl db = new XMLDBManagerImpl();

        db.addMediumToCategory("<medium id=\""+ db.getFirstFreeId() +"\"><mediumType>DVD</mediumType>\n" +
                "                <name>HERK</name>\n" +
                "                <length>90</length>\n" +
                "                <actors>\n" +
                "                    <actor>Mike Myers</actor>\n" +
                "                </actors>\n" +
                "                <status>available</status>\n" +
                "                <genres>\n" +
                "                    <genre>Animated</genre>\n" +
                "                </genres>\n" +
                "                <releaseYear>2001</releaseYear></medium>","Movies");
        db.addMediumToCategory("<medium id=\""+ db.getFirstFreeId() +"\"><mediumType>DVD</mediumType>\n" +
                "                <name>FER</name>\n" +
                "                <length>90</length>\n" +
                "                <actors>\n" +
                "                    <actor>Mike Myers</actor>\n" +
                "                </actors>\n" +
                "                <status>available</status>\n" +
                "                <genres>\n" +
                "                    <genre>Animated</genre>\n" +
                "                </genres>\n" +
                "                <releaseYear>2001</releaseYear></medium>","TEST");


        Category category = new Category("Awoj");
        Year year =  Year.now();
        Medium medium = new Medium("Adam", MediumType.DVD, 55, category,
                new HashSet<String>() {{add("peto"); add("eva");}},
                new HashSet<Genre>() {{add(Genre.Adventure);}},
                year);

        medium.setId(Long.parseLong(db.getFirstFreeId()));

        db.createCategory(category);
        db.createMedium(medium);


        System.out.println(db.getFirstFreeId());

        Category cat = new Category("Movies");

        System.out.println(db.findMediumById("1"));
        System.out.println(db.findMediumByName("HERK"));

        XmlMapper xm = new XmlMapper();
        //Medium med = xm.readValues(db.findMediumById("1"), Medium.class);

        db.close();

        /*
        XMLDBManagerImpl db = new XMLDBManagerImpl();
        CategoryManager categoryManager = new CategoryManagerImpl(db);
        Set<Category> categories = categoryManager.findAllCategories();
        for (Category category : categories) {
            System.out.println(category.getName());
        }*/
    }
}
