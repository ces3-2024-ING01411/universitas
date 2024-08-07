package co.edu.poli.ces3.universitas.database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CountDownLatch;

public class ConexionFirebaseRealTime {
    private static FirebaseDatabase database;

    public ConexionFirebaseRealTime() {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("src/main/resources/service-account.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://ces3-2024-1-ing01411-univer-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            database = FirebaseDatabase.getInstance();
            System.out.println("Conexión exitosa");
        } catch (IOException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (serviceAccount != null) {
                try {
                    serviceAccount.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new ConexionFirebaseRealTime();
        try {
            insertData();
        } catch (Exception e) {
            System.err.println("Error en main: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void insertData() throws InterruptedException {
        if (database == null) {
            System.err.println("La base de datos no está inicializada");
            return;
        }

        DatabaseReference usersRef = database.getReference("users");

        Map<String, Object> user = new HashMap<>();
        user.put("name", "John Doe");
        user.put("email", "john@example.com");

        String userId = usersRef.push().getKey();
        System.out.println("Intentando insertar usuario con ID: " + userId);

        CountDownLatch latch = new CountDownLatch(1);

        ApiFuture<Void> future = usersRef.child(userId).setValueAsync(user);

        future.addListener(() -> {
            try {
                future.get();
                System.out.println("Datos insertados correctamente con ID: " + userId);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error al insertar datos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }, Runnable::run);

        System.out.println("Esperando a que se complete la inserción...");
        latch.await();
        System.out.println("Operación de inserción completada.");
    }
}