package WebScraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraping {

    public static void scrapingPlacaMadre() throws IOException {
        Document document = Jsoup.connect("https://www.pcfactory.cl/placas-madres?categoria=292&papa=633").get();

        // Obtener el título de la página
        String title = document.title();
        System.out.println("Titulo: " + title);

        Elements elements = document.getElementsByClass("product");

        for (Element product : elements) {
            String name = product.getElementsByClass("price color-dark-2  product__card-title").text();
            String price = product
                    .getElementsByClass("title-md color-primary-1 alineado-porcentaje-precio")
                    .text();

            System.out.println("\nNombre: " + name);
            System.out.println("Precio: " + price);
        }
    }

    public static void scrapingProcesador() throws IOException {
        Document document = Jsoup.connect("https://www.pcfactory.cl/procesadores?categoria=272&papa=633").get();

        // Obtener el título de la página
        String title = document.title();
        System.out.println("Titulo: " + title);

        Elements elements = document.getElementsByClass("product");

        for (Element product : elements) {
            String name = product.getElementsByClass("price color-dark-2  product__card-title").text();
            String price = product
                    .getElementsByClass("title-md color-primary-1 alineado-porcentaje-precio")
                    .text();

            System.out.println("\nNombre: " + name);
            System.out.println("Precio: " + price);
        }
    }

    public static void scrapingTarjetaGrafica() throws IOException {
        Document document = Jsoup.connect("https://www.pcfactory.cl/tarjetas-graficas?categoria=334&papa=633").get();

        // Obtener el título de la página
        String title = document.title();
        System.out.println("Titulo: " + title);

        Elements elements = document.getElementsByClass("product");

        for (Element product : elements) {
            String name = product.getElementsByClass("price color-dark-2  product__card-title").text();
            String price = product
                    .getElementsByClass("title-md color-primary-1 alineado-porcentaje-precio")
                    .text();

            System.out.println("\nNombre: " + name);
            System.out.println("Precio: " + price);
        }
    }

    public static void scrapingAlmacenamiento() throws IOException {
        Document document = Jsoup.connect("https://www.pcfactory.cl/almacenamiento?categoria=312&papa=633").get();

        // Obtener el título de la página
        String title = document.title();
        System.out.println("Titulo: " + title);

        Elements elements = document.getElementsByClass("product");

        for (Element product : elements) {
            String name = product.getElementsByClass("price color-dark-2  product__card-title").text();
            String price = product
                    .getElementsByClass("title-md color-primary-1 alineado-porcentaje-precio")
                    .text();

            System.out.println("\nNombre: " + name);
            System.out.println("Precio: " + price);
        }
    }
}