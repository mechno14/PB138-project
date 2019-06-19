package cz.muni.fi.pb138.videolibrary.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        System.out.println(this.getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Gui.fxml"));
        primaryStage.setTitle("Video Library");
        primaryStage.setScene(new Scene(root, 1080, 750));
        primaryStage.show();*/


        System.out.println(this.getClass().getClassLoader().getResource("Gui.fxml"));
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Gui.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300.0D, 275.0D));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
