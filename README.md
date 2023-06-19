# Componentix
Trabajo Proyecto Final POO

# Proyecto de Web Scraping
Este proyecto realiza web scraping en el sitio web PC Factory para extraer información de precios de diferentes categorías de productos(Tarjetas Graficas, Procesadores, Placas Madres y Almacenamiento). Utiliza la biblioteca Jsoup para realizar las solicitudes HTTP y analizar el contenido HTML de las páginas web.

## Clases
El proyecto consta de las siguientes clases:

### Clase FirebaseConnection
La clase `FirebaseConnection` se encarga de establecer la conexión con la base de datos Firebase. Utiliza el archivo `Firebase.json` para autenticarse y configura la URL de la base de datos. Asegúrate de proporcionar el archivo `Firebase.json` y la URL correcta de la base de datos.

### Clase WebScraping
La clase `WebScraping` contiene métodos para realizar web scraping en diferentes categorías de productos en el sitio web PC Factory. Cada método se conecta a una URL específica, extrae la información de los productos (nombre y precio) y la muestra por consola. Los métodos disponibles son:

- `scrapingPlacaMadre()`: Realiza web scraping en la categoría de placas madre.
- `scrapingProcesador()`: Realiza web scraping en la categoría de procesadores.
- `scrapingTarjetaGrafica()`: Realiza web scraping en la categoría de tarjetas gráficas.
- `scrapingAlmacenamiento()`: Realiza web scraping en la categoría de almacenamiento.

## Uso
Para utilizar este proyecto, sigue los siguientes pasos:

1. Asegúrate de tener Java instalado en tu sistema.
2. Descarga el proyecto y abre el código en tu entorno de desarrollo.
3. Configura el archivo `Firebase.json` con las credenciales correctas de tu proyecto Firebase.
4. En la clase `Principal`, descomenta la línea `// FirebaseConnection.Connection();` si deseas utilizar la conexión a la base de datos.
5. Ejecuta la clase `Principal` para iniciar el web scraping en las diferentes categorías de productos.

Ten en cuenta que los resultados del web scraping se mostrarán por consola.

## Dependencias
El proyecto utiliza la biblioteca Jsoup para el análisis de HTML. Asegúrate de tener la siguiente dependencia en tu archivo de configuración de dependencias:

```xml
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.14.3</version>
</dependency>
