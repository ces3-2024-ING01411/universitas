package co.edu.poli.ces3.universitas.database;

import java.sql.*;

public class ConexionMySql {

    private String user;
    private String password;
    private int port;
    private String host;
    private String nameDatabase;
    private Connection cnn;

    ConexionMySql(){
        user = "root";
        password = "";
        port = 3306;
        host = "localhost";
        nameDatabase = "ces3-universitas";
    }

    public void createConexion(){
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

    public void getUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        String[] nameColumns = {"id", "name", "lastName", "mail", "password", "createdAt", "updatedAt", "deletedAt"};
        try {
            createConexion();
            Statement stmt = cnn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()){
                for (String field: nameColumns) {
                    System.out.println("************");
                    System.out.println("Name Column: " + result.getString(field));
                    System.out.println("*************");
                }
            }
            stmt.close();
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
