package com.example.anime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public Connection connect(){
        try{
            String url = "jdbc:mysql://root:password@localhost/anime";/*<-- tu należy wpisać URL bazy danych */
            Connection connection = DriverManager.getConnection(url);

            return connection;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
