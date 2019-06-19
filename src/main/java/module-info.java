module PB138.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires exist.core;
    requires org.xmldb.api;

    opens cz.muni.fi.pb138.videolibrary.gui to javafx.fxml;
    exports cz.muni.fi.pb138.videolibrary.gui;
}