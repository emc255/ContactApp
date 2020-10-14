module JavaFx.ContactApp.Challenge {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;


    opens contactApp.dataModel;
    opens contactApp;

}