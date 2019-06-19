package cz.muni.fi.pb138.videolibrary;

import javafx.application.Application;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class App {
    public static void main(String args[]) throws Exception {
        //Application.launch();
        /**
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

        db.close();*/
    }
/*
    @Override
    public void start(Stage primaryStage) throws Exception {*/
        /*Pane mainPane = (Pane) FXMLLoader.load(App.class.getResource("gui.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        stage.setScene(new Scene(mainPane));
        stage.show();*/

        //URL url = new File("cz/muni/fi/pb138/videolibrary/gui.fxml").toURL();
        //Parent root = FXMLLoader.load(url);
        /*
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/gui.fxml"));
        primaryStage.setTitle("Video Manager");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1288, 926));
        primaryStage.show();

        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/
}
