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

    public ConexionMySql(){
        this.user = "root";
        password = "";
        port = 3306;
        host = "localhost";
        nameDatabase = "ces3-universitas";
    }

    private void createConexion(){
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

    public List<User> getUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        String[] nameColumns = {"id", "name", "lastName", "mail", "password", "createdAt", "updatedAt", "deletedAt"};
        List<User> list = new ArrayList<>();
        try {
            createConexion();
            Statement stmt = cnn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()){
                list.add(new User(result.getString("name"), result.getString("lastName")));
            }
            stmt.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        ConexionMySql conection = new ConexionMySql();
        try {
            conection.getUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
