package com.example.anime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUIController implements Initializable {
    //var
    DbConnection dbConnection;
    AnimeTitle[] animeData;
    ObservableList<String> dropdownList_switchView;
    ObservableList<String> dropdownList_chooseDay;
    ObservableList<String> dropdownList_chooseTitle;

    //choiseboxes
    @FXML
    private ChoiceBox<String> dropdown_switchView;
    @FXML
    private ChoiceBox<String> dropdown_chooseDay;
    @FXML
    private ChoiceBox<String> dropdown_chooseTitle;
    @FXML
    private Button btn_addTitle;
    //vboxes
    public VBox vbox_Monday;
    public VBox vbox_Tuesday;
    public VBox vbox_Wednesday;
    public VBox vbox_Thursday;
    public VBox vbox_Friday;
    public VBox vbox_Saturday;
    public VBox vbox_Sunday;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DbConnection();
        animeData = getDataFromDatabase();

        //dropdown to change view
        dropdownList_switchView = FXCollections.observableArrayList(
                "Database",
                "Calendar"
        );
        dropdown_switchView.getItems().addAll(dropdownList_switchView);
        dropdown_switchView.setOnAction(this::switchView);

        //dropdown to choose day
        dropdownList_chooseDay = FXCollections.observableArrayList(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
        dropdown_chooseDay.getItems().addAll(dropdownList_chooseDay);

        //dropdown to choose title
        dropdownList_chooseTitle = FXCollections.observableArrayList();
        for(int i = 1; i < animeData.length; i++) {
            dropdownList_chooseTitle.add(animeData[i].getName());
        }
        dropdown_chooseTitle.getItems().addAll(dropdownList_chooseTitle);
    }

    //method for btn_addTitle
    public void addTitle(ActionEvent event){
        //variables to set up vbox with label and image
        String day = dropdown_chooseDay.getValue();
        String title = dropdown_chooseTitle.getValue();
        Label lbl_title = new Label(title);
        ImageView imageView = new ImageView();

        for(int i = 1; i < animeData.length; i++){
            if(animeData[i].getName().equals(title)) {
                File file = new File(animeData[i].getPath_to_img());
                imageView.setImage(new Image(file.toURI().toString()));
            }
        }

        VBox vbox = new VBox();
        vbox.getChildren().addAll(lbl_title, imageView);

        //switch to add chosen title to chosen day
        switch (day){
            case "Monday": {
                vbox_Monday.getChildren().add(vbox);
                break;
            }
            case "Tuesday": {
                vbox_Tuesday.getChildren().add(lbl_title);
                break;
            }
            case "Wednesday": {
                vbox_Wednesday.getChildren().add(lbl_title);
                break;
            }
            case "Thursday": {
                vbox_Thursday.getChildren().add(lbl_title);
                break;
            }
            case "Friday": {
                vbox_Friday.getChildren().add(lbl_title);
                break;
            }
            case "Saturday": {
                vbox_Saturday.getChildren().add(lbl_title);
                break;
            }
            case "Sunday": {
                vbox_Sunday.getChildren().add(lbl_title);
                break;
            }
        }
    }

    //method for dropdown_switchView
    public void switchView(ActionEvent event) {
        Parent root = null;
        try {
            String value = dropdown_switchView.getValue();
            switch(value){
                case "Calendar":{
                   root  = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                   break;
                }
                case "Database":{
                    root = FXMLLoader.load(getClass().getResource("database.fxml"));
                    break;
                }
                default:{
                    System.out.println("Def");
                    break;
                }
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    //get data from database
    private AnimeTitle[] getDataFromDatabase() {
        try {
            Connection connection = dbConnection.connect();

            String query = "select\n" +
                    "    name, path_to_img\n" +
                    "from anime;";

            Statement statement;
            statement = connection.createStatement();
            ResultSet dataSet = statement.executeQuery(query);
            ArrayList<AnimeTitle> animeTitles = new ArrayList<AnimeTitle>();

            while (dataSet.next()) {
                String name = dataSet.getString("name");
                String path_to_img = dataSet.getString("path_to_img");

                animeTitles.add(new AnimeTitle(name, path_to_img));
            }

            statement.close();

            AnimeTitle[] data = new AnimeTitle[animeTitles.size()];
            animeTitles.toArray(data);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        AnimeTitle[] data = {};
        return data;
    }

}