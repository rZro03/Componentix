import WebScraping.ScrapingService;
import WebScraping.WebScraping;

import java.io.IOException;

import Firebase.FirebaseConnection;

public class Principal {

    public static void main(String[] args) throws IOException {
        FirebaseConnection.connection();
        ScrapingService scrapingService = new WebScraping();

        String urlPlacasMadres = "https://www.pcfactory.cl/placas-madres?categoria=292&papa=633";
        String urlProcesadores = "https://www.pcfactory.cl/procesadores?categoria=272&papa=633";
        String urlTarjetasGraficas = "https://www.pcfactory.cl/tarjetas-graficas?categoria=334&papa=633";
        String urlAlmacenamiento = "https://www.pcfactory.cl/almacenamiento?categoria=312&papa=633";

        scrapingService.scrape(urlPlacasMadres);
        System.out.println("---------------------------------------------------------------------------");

        scrapingService.scrape(urlProcesadores);
        System.out.println("---------------------------------------------------------------------------");

        scrapingService.scrape(urlTarjetasGraficas);
        System.out.println("---------------------------------------------------------------------------");

        scrapingService.scrape(urlAlmacenamiento);
        System.out.println("---------------------------------------------------------------------------");
    }
}