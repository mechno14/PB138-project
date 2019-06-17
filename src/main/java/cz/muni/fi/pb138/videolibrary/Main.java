package cz.muni.fi.pb138.videolibrary;

import java.util.List;

public class Main {
    public static void main(String args[]) throws Exception {
        NativeXMLDatabaseManager db = new NativeXMLDatabaseManager();
        db.createCategory("TEST");
        db.createCategory("TEST2");
        db.deleteCategory("TEST2");
        List<String> categories = db.findAllCategories();
        for (String cat:
        categories) {
            System.out.println(cat);
        }
        System.out.println(db.findAllMediumsByCategory("Movies"));
        //db.deleteMediumFromCategory("7", "TEST");
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


        System.out.println(db.getFirstFreeId());

        db.close();
    }
}
