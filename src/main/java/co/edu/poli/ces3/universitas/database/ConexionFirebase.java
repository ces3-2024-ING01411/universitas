package co.edu.poli.ces3.universitas.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConexionFirebase {

    public ConexionFirebase(){
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/service-account.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("Conexion to firebases!!!!!!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ConexionFirebase();
    }

}
