package co.edu.poli.ces3.universitas.repositories;

import co.edu.poli.ces3.universitas.dao.Task;
import co.edu.poli.ces3.universitas.dao.User;
import co.edu.poli.ces3.universitas.database.ConexionFirebase;
import co.edu.poli.ces3.universitas.database.Crud;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TaskRespository extends ConexionFirebase implements Crud<Task> {

    @Override
    public void insert(Task x) {
        try{
            Firestore db = FirestoreClient.getFirestore();

            DocumentReference document = db.collection("tasks").document();

            Map<String, Object> task = new HashMap<>();

            task.put("id", x.getId());
            task.put("nameTask", x.getNameTask());
            task.put("createdAt", new Date());
            task.put("updatedAt", new Date());

            ApiFuture<WriteResult> result = document.set(task);

            System.out.println(result.get().getUpdateTime());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Task x) {

    }

    @Override
    public List<Task> get() throws SQLException {
        ArrayList<Task> list = new ArrayList<>();
        try{
            Firestore db = FirestoreClient.getFirestore();

            CollectionReference collection = db.collection("tasks");

            ApiFuture<QuerySnapshot> querySnapshot = collection.get();

            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                list.add(new Task(
                        Integer.parseInt(document.getData().get("id").toString()),
                        document.getData().get("nameTask").toString()
                ));
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Task getOne(int id) throws SQLException {
        return null;
    }

    public static void main(String[] args) {
        try {
            new TaskRespository().get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
