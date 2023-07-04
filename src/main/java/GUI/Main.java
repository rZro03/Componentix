package GUI;

import Firebase.FirebaseConnection;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer la conexión con Firebase
                FirebaseConnection.connection();
                new Ventana();
            } catch (IOException e) {
                throw new RuntimeException("Error al conectar a la base de datos de Firebase: " + e.getMessage());
            }
        });
    }
}
