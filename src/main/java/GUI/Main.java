package GUI;

import Firebase.FirebaseConnection;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FirebaseConnection.connection();
                Ventana ventana = new Ventana();
            } catch (IOException e) {
                System.err.println("Error al conectar a la base de datos de Firebase: " + e.getMessage());
            }
        });
    }
}
