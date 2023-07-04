package WebScraping;

import Firebase.FirebaseConnection;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.common.util.concurrent.MoreExecutors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebScraping implements ScrapingService{

    private Firestore database;

    public WebScraping() {
        // Obtener la instancia de Firestore desde FirebaseConnection
        database = FirebaseConnection.getDataBase();
    }

    @Override
    public void scrapePCfactory(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        // Obtener el título de la página
        String title = document.title();
        System.out.println("Titulo: " + title);

        Elements elements = document.getElementsByClass("product");

        elements.stream().forEach(product -> {
            String name = product.getElementsByClass("price color-dark-2  product__card-title").text();
            String price = product.getElementsByClass("title-md color-primary-1 alineado-porcentaje-precio").text();

            // Remover el descuento y los caracteres no numéricos del precio
            price = price.replaceAll("[-+]?\\d+%\\s*", "").replaceAll("[^\\d.]", "");

            System.out.println(name);
            System.out.println(price);

            // Determinar la categoría del producto
            String category = determineCategory(url);

            // Insertar en la base de datos con la categoría correspondiente
            insertToFirebase(name, price, category);
        });
    }

    @Override
    public void scrapePCplanet(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        // Obtener el título de la página
        String title = document.title();
        System.out.println("Titulo: " + title);

        Elements elements = document.getElementsByClass("show-outimage-q-onimage-alt product-first pcols-lg-first" +
                " pcols-md-first pcols-xs-first post-1459 product type-product status-publish has-post-thumbnail " +
                "product_cat-componentes product_cat-tarjetas-graficas pa_cant-memoria-1gb first " +
                "outofstock taxable shipping-taxable purchasable product-type-simple");

        elements.stream().forEach(product -> {
            String name = product.getElementsByClass("product-loop-title").text();
            String price = product.getElementsByClass("woocommerce-Price-amount amount").text();

            // Remover el descuento y los caracteres no numéricos del precio
            price = price.replaceAll("[-+]?\\d+%\\s*", "").replaceAll("[^\\d.]", "");

            // Determinar la categoría del producto
            String category = determineCategory(url);

            System.out.println(name);
            System.out.println(price);

            // Insertar en la base de datos con la categoría correspondiente
            insertToFirebase(name, price, category);
        });
    }


    private void insertToFirebase(String name, String price, String category) {
        // Crear un nuevo documento en la colección "Productos"
        DocumentReference documentRef = database.collection("Productos").document();

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("price", price);
        data.put("category", category);

        ApiFuture<WriteResult> writeResult = documentRef.set(data);

        ApiFutures.addCallback(writeResult, new ApiFutureCallback<WriteResult>() {
            @Override
            public void onSuccess(WriteResult result) {
                System.out.println("Datos insertados en Firebase: " + name + " - " + price + " - " + category);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al insertar datos en Firebase: " + t.getMessage());
            }
        }, MoreExecutors.directExecutor());
    }

    private String determineCategory(String url) {
        if (url.contains("placas-madres") || url.contains("tarjetas-madres")) {
            return "Placas Madres";
        } else if (url.contains("procesadores")) {
            return "Procesadores";
        } else if (url.contains("tarjetas-graficas")) {
            return "Tarjetas Gráficas";
        } else if (url.contains("almacenamiento") || url.contains("discos-duros-externos")) {
            return "Almacenamiento";
        } else if (url.contains("memorias")) {
            return "Memoria Ram";
        }
        return "Categoría Desconocida";
    }
}