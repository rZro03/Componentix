package WebScraping;

import java.io.IOException;

public interface ScrapingService {
    void scrape(String url) throws IOException;
}