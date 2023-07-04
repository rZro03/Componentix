package Firebase;

import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class FirebaseConnectionTest {

    private Firestore database1;
    private Firestore database2;

    @BeforeEach
    void setUp() throws IOException {
        FirebaseConnection.connection();
        database1 = FirebaseConnection.getDataBase();
        database2 = FirebaseConnection.getDataBase();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testGetDatabaseInstance() throws ExecutionException, InterruptedException {
        Firestore database = FirebaseConnection.getDataBase();
        assertNotNull(database, "La instancia de la base de datos es nula");
        assertTrue(database.collectionGroup("example").get().get().isEmpty(), "La base de datos no está vacía");
    }

    @Test
    public void testGetSameDatabaseInstance() {
        Firestore database1 = FirebaseConnection.getDataBase();
        Firestore database2 = FirebaseConnection.getDataBase();
        assertSame(database1, database2);
    }

    @Test
    public void testGetDatabaseNotNull() {
        Firestore database = FirebaseConnection.getDataBase();
        assertNotNull(database);
    }

    @Test
    public void testConnectionFailure() {
        // Probar conexión fallida proporcionando un archivo JSON de configuración inválido
        assertThrows(IOException.class, () -> {
            FileInputStream serviceAccount = new FileInputStream("Invalid-Config.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://sistema-de-inventario-4f7f9-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            FirebaseConnection.connection();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"collection1", "collection2", "collection3"})
    public void testCollectionIsEmpty(String collectionName) throws InterruptedException, ExecutionException {
        Firestore database = FirebaseConnection.getDataBase();
        Assertions.assertNotNull(database, "La instancia de la base de datos es nula");

        boolean isCollectionEmpty = database.collection(collectionName).get().get().isEmpty();
        Assertions.assertTrue(isCollectionEmpty, "La colección no está vacía: " + collectionName);
    }

}