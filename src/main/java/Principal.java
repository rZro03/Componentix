import Firebase.Conexion;
import WebScraping.Scraping;

import java.io.IOException;

public class Principal {

    public static void main(String[] args) throws IOException {
        Conexion.conectarFirebase();
        Scraping.scrapingTarjetasGraficas();
    }
}
