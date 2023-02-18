package com.example.anime;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseController {
    private DbConnection dbConnection;
    private ObservableList<AnimeTitle> dataList;
    private ObservableList<String> dropdownViewList;

    @FXML
    private ChoiceBox<String> dropdownView;
    @FXML
    public TableView tableView_0;
    @FXML
    public TableColumn columnPathToImg;
    @FXML
    public TableColumn columnName;

    @FXML
    public void initialize(){
        dbConnection = new DbConnection();
        dataList = FXCollections.observableArrayList();
        AnimeTitle[] data = getDataFromDatabase();

        //tableView
        for(int i = 1; i < data.length; i++){
            dataList.add(data[i]);
        }

        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnPathToImg.setCellValueFactory(new PropertyValueFactory<>("Path_to_img"));

        tableView_0.setItems(null);
        tableView_0.setItems(dataList);

        //dropdown to choose view
        dropdownViewList = FXCollections.observableArrayList(
                "Database",
                "Calendar"
        );

        dropdownView.getItems().addAll(dropdownViewList);
        dropdownView.setOnAction(this::switchView);

    }

    //method for dropdown
    public void switchView(ActionEvent event) {
        Parent root = null;
        try {
            String value = dropdownView.getValue();
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
