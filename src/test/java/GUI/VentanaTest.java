package GUI;

import Firebase.FirebaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class VentanaTest {

    private Ventana ventana;

    @BeforeEach
    void setUp() throws IOException {
        FirebaseConnection.connection();
        ventana = new Ventana();
    }

    @AfterEach
    void tearDown() {
        ventana = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"Memoria Ram", "Procesadores", "Almacenamiento"})
    void cargarProductosPorCategoria_Parametrica(String categoria) {
        // Escenario de prueba
        ventana.cargarProductosPorCategoria(categoria);

        // Asegurar que se hayan cargado los productos correctamente
        assertTrue(ventana.getProductListModel().size() > 0, "Se esperaba que se cargaran productos para la categoría: " + categoria);
    }

    @Test
    public void testCargarProductosPorCategoria() {
        ventana.cargarProductosPorCategoria("Procesadores");
        assertFalse(ventana.getProductListModel().isEmpty());
    }

    @Test
    public void testAgregarAlCarro() {
        ventana.cargarProductosPorCategoria("Procesadores");
        ventana.getProductListModel().setElementAt("CPU A6-9500E - Gráficos Integrados (AM4)", 0);
        ventana.agregarAlcarro();
        assertFalse(ventana.getProductListModel().isEmpty());
    }

    @Test
    public void testAgregarAlCarro_SinSeleccion() {
        ventana.agregarAlcarro();
        assertTrue(ventana.getProductListModel().isEmpty());
    }

    @Test
    public void testCargarProductosPorCategoria2() {
        ventana.cargarProductosPorCategoria("Procesadores");
        DefaultListModel<String> productListModel = ventana.getProductListModel();
        assertNotEquals(1, productListModel.size());
    }
}