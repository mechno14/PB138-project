package cz.muni.fi.pb138.videolibrary;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Main {
    public static void main(String args[]) throws Exception {
        NativeXMLDatabaseManager db = new NativeXMLDatabaseManager();
        db.deleteCategory("TEST");
        db.createCategory("TEST");
        db.createCategory("TEST2");
        db.deleteCategory("TEST2");
        List<String> categories = db.findAllCategories();
        for (String cat:
        categories) {
            System.out.println(cat);
        }
        System.out.println(db.findAllMediumsByCategory("Movies"));

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

        db.createCategory(category.getName());
        db.addMedium(medium);


        System.out.println(db.getFirstFreeId());

        System.out.println(db.findMediumById("1"));

        db.close();
    }
}
