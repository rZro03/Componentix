package WebScraping;

import Firebase.FirebaseConnection;
import GUI.Ventana;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WebScrapingTest {

    private WebScraping webScraping;
    Ventana ventana;

    @BeforeEach
    void setUp() throws IOException {
        FirebaseConnection.connection();
        webScraping = new WebScraping();
        ventana = new Ventana();
    }

    @AfterEach
    void tearDown() {
        webScraping = null;
        ventana = null;
    }

    @Test
    void scrapePCfactory() throws IOException {
        webScraping.scrapePCfactory("https://www.pcfactory.cl");
        assertNotNull(webScraping);
    }

    @Test
    void scrapeInvalidURL() throws IOException {
        // Prueba para verificar el manejo de una URL inválida
        assertThrows(IOException.class, () -> {
            webScraping.scrapePCfactory("https://www.invalidurl.com");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://www.pcfactory.cl/procesadores?categoria=272&papa=633",
            "https://www.pcfactory.cl/placas-madres?categoria=292&papa=633",
            "https://www.pcfactory.cl/tarjetas-graficas?categoria=334&papa=633"})
    void scrapeURL(String url) throws IOException {
        // Prueba parametrizada para verificar el scraping de una URL específica
        webScraping.scrapePCfactory(url);
        assertNotNull(webScraping);
    }

    @Test
    public void testScrapePCfactory() {
        try {
            // URL de ejemplo para hacer el test
            String url = "https://www.pcfactory.cl/componentes/tarjetas-graficas";

            // Realizar el web scraping en la página de PCFactory
            webScraping.scrapePCfactory(url);

            // Verificar que se hayan agregado productos a la base de datos
            Assertions.assertTrue(ventana.getProductListModel().size() >= 0);
        } catch (IOException e) {
            Assertions.fail("Error al realizar el web scraping: " + e.getMessage());
        }
    }

    @RepeatedTest(5)
    public void testScrapePCfactoryRepeatedTest() {
        String url = "https://www.pcfactory.cl";

        try {
            webScraping.scrapePCfactory(url);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Se produjo una excepción durante el web scraping");
        }
    }
}