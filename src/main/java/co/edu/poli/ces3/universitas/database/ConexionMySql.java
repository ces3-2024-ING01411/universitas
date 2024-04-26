package co.edu.poli.ces3.universitas.database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ConexionMySql {

    private String user;
    private String password;
    private int port;
    private String host;
    private String nameDatabase;
    private Connection cnn;

    ConexionMySql(){
        this.user = "root";
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
        /*ConexionMySql conection = new ConexionMySql();
        try {
            conection.getUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        ConexionMySql.conectFirebaseDataBase();
    }

    public static void conectFirebaseDataBase(){
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("C:\\Users\\USER\\Downloads\\serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                //.setDatabaseUrl("https://omesaprueba1.firebaseio.com")
                .build();

            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            DocumentReference docRef = db.collection("users").document("alovelace");

            Map<String, Object> data = new HashMap<>();
            data.put("first", "Ada");
            data.put("last", "Lovelace");
            data.put("born", 1815);

            ApiFuture<WriteResult> result = docRef.set(data);

            System.out.println("Update time : " + result.get().getUpdateTime());


            System.out.println("Se conecto a firebase sin problemas");
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}

class User {

    public String date_of_birth;
    public String full_name;
    public String nickname;

    public User(String dateOfBirth, String fullName) {
        // ...
    }

    public User(String dateOfBirth, String fullName, String nickname) {
        // ...
    }

}


