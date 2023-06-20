# Proyecto de Web Scraping y Firebase

Este proyecto consiste en un sistema de web scraping que extrae información de productos de diferentes categorías de un sitio web y la almacena en una base de datos Firebase. Utiliza la biblioteca Jsoup para realizar el web scraping y el SDK de Firebase para la conexión y escritura de datos en la base de datos.

## Estructura del proyecto

El proyecto está organizado en los siguientes paquetes y clases:

- `Firebase`
  - `FirebaseConnection`: Clase encargada de establecer la conexión con la base de datos de Firebase.

- `WebScraping`
  - `ScrapingService`: Interfaz que define el contrato para los servicios de web scraping.
  - `WebScraping`: Clase principal que realiza el web scraping de los productos.

- `Principal`: Clase principal del programa que contiene el método `main()` para ejecutar el web scraping.

## Configuración previa

Antes de ejecutar el programa, es necesario realizar algunas configuraciones:

1. Crea un proyecto en Firebase y habilita Firestore como base de datos.
2. Descarga el archivo de configuración `Firebase.json` desde Firebase y colócalo en la carpeta raíz del proyecto.

## Dependencias

El proyecto utiliza las siguientes dependencias:

- `com.google.firebase:firebase-admin:9.1.1`: SDK de Firebase para Java que proporciona acceso a los servicios de Firebase, incluyendo Firestore.
- `org.jsoup:jsoup:1.16.1`: Biblioteca Java para el manejo de HTML y web scraping.
- `org.junit.jupiter:junit-jupiter-api:5.9.2`: Dependencia opcional para pruebas unitarias.

## Ejecución

Sigue los siguientes pasos para ejecutar el programa:

1. Asegúrate de tener Java y Maven instalados en tu sistema.
2. Abre una terminal en la carpeta raíz del proyecto.
3. Ejecuta el siguiente comando para compilar el proyecto y descargar las dependencias: 'mvn clean install'
4. Una vez compilado, ejecuta el programa con el siguiente comando: java -cp target/classes:target/dependency/* Principal

Asegúrate de reemplazar `target` con el nombre de tu carpeta de destino si es diferente.

5. El programa realizará el web scraping de productos de diferentes categorías y mostrará los resultados por consola.

## Personalización

Puedes personalizar el proyecto de acuerdo a tus necesidades:

- Modifica las URLs de las categorías de productos en el archivo `Principal.java` para adaptarlo a tu sitio web.
- Agrega nuevas clases o métodos para realizar tareas adicionales de web scraping o manipulación de datos.
