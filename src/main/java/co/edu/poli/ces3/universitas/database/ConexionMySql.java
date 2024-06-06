package co.edu.poli.ces3.universitas.database;

import co.edu.poli.ces3.universitas.dao.User;

import java.sql.*;
import java.util.*;

public class ConexionMySql {

    private String user;
    private String password;
    private int port;
    private String host;
    private String nameDatabase;
    private Connection cnn;

    public Connection getCnn(){
        return this.cnn;
    }

    public ConexionMySql(){
        this.user = "root";
        password = "";
        port = 3306;
        host = "localhost";
        nameDatabase = "ces3-universitas";
    }

    protected void createConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnn = DriverManager.getConnection(
                    "jdbc:mysql://" +host+":"+port+"/"+nameDatabase, user, password
            );
            System.out.println("Successful connection");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("An error occurred during the connection");
            throw new RuntimeException(e);
        }
    }
}
