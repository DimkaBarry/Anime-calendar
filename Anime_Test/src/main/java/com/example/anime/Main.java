package com.example.anime;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application {
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Anime Calendar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        File dataFile = new File("src/main/resources/txtData/titles.tsv");
        AnimeTitleData animeData = new AnimeTitleData(dataFile);
        Database db = new Database(animeData);
        db.create();
        launch();
    }

    public FXMLLoader getFxmlLoader(){
        return this.fxmlLoader;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader){
        this.fxmlLoader = fxmlLoader;
    }

}