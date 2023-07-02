package Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConnection {

    private static Firestore dataBase;

    public static void connection() throws IOException {
        // Uso Obligatorio de metodo deprecado, Builder().

        FileInputStream serviceAccount = new FileInputStream("Proyecto-POO.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://sistema-de-inventario-4f7f9-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        dataBase = FirestoreClient.getFirestore();

        System.out.println("Se ha conectado con exito a la base de datos.");
    }

    public static Firestore getDataBase() {
        return dataBase;
    }
}