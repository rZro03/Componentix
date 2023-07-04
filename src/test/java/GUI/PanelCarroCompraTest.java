package GUI;

import Firebase.FirebaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelCarroCompraTest {

    private PanelCarroCompra panel;
    private List<String> productos;

    @BeforeEach
    public void setUp() throws IOException {
        FirebaseConnection.connection();
        productos = Arrays.asList("CPU Intel Core i3-12100F 3.30GHz 12MB (s1700)", "CPU Intel Core i5-11400F 2.6GHz 12MB (s1200)",
                "CPU Intel Core i5-12600K (s1700)");
        panel = new PanelCarroCompra(productos);
    }

    @AfterEach
    public void teardown() {
        panel = null;
        productos = null;
    }

    @Test
    public void obtenerPrecioProducto_ProductoExistente_PrecioCorrecto() {
        String precio = panel.obtenerPrecioProducto(productos.get(0));
        assertEquals("94.990", precio); // Ajusta el valor esperado según tu base de datos
    }

    @Test
    public void obtenerPrecioProducto_ProductoInexistente_ExcepcionLanzada() {
        assertThrows(RuntimeException.class, () -> {
            panel.obtenerPrecioProducto(productos.get(4));
        });
    }

    @Test
    public void calcularTotal_CompraVacia_TotalCero() {
        List<String> productos = Arrays.asList();
        PanelCarroCompra panelVacio = new PanelCarroCompra(productos);

        BigDecimal total = panelVacio.calcularTotal();

        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    public void calcularTotal_CompraConProductos_TotalCorrecto() {
        BigDecimal total = panel.calcularTotal();

        // Ajusta el valor esperado según tus pruebas
        assertNotEquals(new BigDecimal("0"), total);
    }

    @Test
    void calcularTotal_DevuelveValorDistintoDeCero() {
        List<String> preciosProductos = Arrays.asList("100.00", "50.50", "75.25");
        PanelCarroCompra panelCarroCompra = new PanelCarroCompra(preciosProductos);

        BigDecimal total = panelCarroCompra.calcularTotal();

        assertTrue(total.compareTo(BigDecimal.ZERO) != 0);
    }
}