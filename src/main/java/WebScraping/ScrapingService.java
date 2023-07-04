package WebScraping;

import java.io.IOException;

public interface ScrapingService {

    // Agregar mas metodos para agregar mas paginas.
    void scrapePCfactory(String url) throws IOException;
    void scrapePCplanet(String url) throws IOException;
}