package WebScraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scraping {

    //Metodo base de como hacer scraping con Jsoup
    public static void scrapingTarjetasGraficas() {

        String url = "https://www.pcfactory.cl/tarjetas-graficas-nvidia?categoria=378";

        try {
            // Realizar la solicitud HTTP y obtener el documento HTML
            Document document = Jsoup.connect(url).get();

            // Buscar el elemento span con la clase especificada
            Elements elements = document.select(
                    "span.Fractal-Typography--base.Fractal-Typography--typographyBestPrice.Fractal-Typography__typography--primary.Fractal-Typography--typographySemiBold.Fractal-Typography__typography--left.Fractal-Price--price");

            // Extraer y imprimir el contenido de texto de los elementos encontrados
            for (Element element : elements) {
                String texto = element.text();
                System.out.println(texto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
