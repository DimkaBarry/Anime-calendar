package com.example.anime;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    Connection connection;
    AnimeTitleData animeTitleData;
    DbConnection dbConnection;

    public Database(AnimeTitleData animeTitleData){
        this.animeTitleData = animeTitleData;

        try{
            dbConnection = new DbConnection();
            this.connection = dbConnection.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //create tables for data in database
    public void create(){
        String dropStatement = "drop table if exists anime";

        String createStatement = "create table anime\n" +
                "(\n" +
                "   animeID int auto_increment, \n" +
                "   name varchar(255), \n" +
                "   path_to_img varchar(255), \n" +
                "   constraint anime_pk\n" +
                "       primary key (animeID)\n" +
                ");\n" +
                "\n" +
                "";

        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(dropStatement);
            statement.executeUpdate(createStatement);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.fillDb();
    }

    //fill database with data
    private void fillDb() {
        try {
            Statement statement = this.connection.createStatement();

            for(AnimeTitle animeTitle: this.animeTitleData.getAnimeTitles()) {
                //get data to fill
                String name = animeTitle.getName();
                String path_to_img = animeTitle.getPath_to_img();

                //fill database
                String insertQuery = "insert into anime\n" +
                        "    (name, path_to_img)\n" +
                        "    values\n" +
                        "    (\n" +
                        "        '" + name + "',\n" +
                        "        '" + path_to_img + "'\n" +
                        "    );";

                statement.executeUpdate(insertQuery);
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //get data from database
//    public AnimeTitle[] getDataFromDataBase() {
//        try {
//            String query = "select\n" +
//                    "    name, path_to_img\n" +
//                    "from anime;";
//
//            Statement statement;
//            statement = this.connection.createStatement();
//            ResultSet dataSet = statement.executeQuery(query);
//            ArrayList<AnimeTitle> animeTitles = new ArrayList<AnimeTitle>();
//
//            while (dataSet.next()) {
//                String name = dataSet.getString("name");
//                String path_to_img = dataSet.getString("path_to_img");
//
//                animeTitles.add(new AnimeTitle(name, path_to_img));
//            }
//
//            statement.close();
//
//            AnimeTitle[] data = new AnimeTitle[animeTitles.size()];
//            animeTitles.toArray(data);
//
//            return data;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        AnimeTitle[] data = {};
//        return data;
//    }

//    public void showGui() {
//        AnimeTitle[] data = this.getDataFromDataBase();
//
//        new GUI(data);
//    }

}
