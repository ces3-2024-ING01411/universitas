package co.edu.poli.ces3.universitas.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import java.io.FileInputStream;
import java.io.IOException;

public class AuthExample {
    public static void main(String[] args) {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("C:\\Users\\USER\\Downloads\\serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                //.setDatabaseUrl("https://omesaprueba1.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Ejemplo de verificación de token de ID de Firebase
        String idToken = "USER_ID_TOKEN"; // Obtén este token desde el cliente, generalmente después de que el usuario haya iniciado sesión
            FirebaseToken decodedToken = auth.verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            System.out.println("Token de ID verificado con éxito. UID del usuario: " + uid);
        } catch (FirebaseAuthException | IOException e) {
            System.out.println("Error al verificar el token de ID: " + e.getMessage());
        }
    }
}
