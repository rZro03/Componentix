import WebScraping.WebScraping;

import java.io.IOException;

public class Principal {

    public static void main(String[] args) throws IOException {
        // Inicio de la base de datos
        // FirebaseConnection.Connection();
        // Inicio del Scraping
        WebScraping.scrapingPlacaMadre();
        System.out.println("---------------------------------------------------------------------------");
        WebScraping.scrapingProcesador();
        System.out.println("---------------------------------------------------------------------------");
        WebScraping.scrapingTarjetaGrafica();
        System.out.println("---------------------------------------------------------------------------");
        WebScraping.scrapingAlmacenamiento();
    }
}
