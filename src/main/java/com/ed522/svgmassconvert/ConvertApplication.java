package com.ed522.svgmassconvert;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConvertApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConvertApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 320);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
