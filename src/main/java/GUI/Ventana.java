package GUI;

import Firebase.FirebaseConnection;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ventana extends JXFrame {
    private final JXList productList;
    private final DefaultListModel<String> productListModel;
    private final JPanel categoryPanel;
    private List<String> selectedProducts;
    private final Color backgroundColor = new Color(30, 30, 30); // Establece el color de fondo deseado
    private final Color fontColor = Color.WHITE; // Establece el color de fuente deseado
    private final Font font = new Font("Cascadia Code", Font.PLAIN, 14); // Establece la fuente deseada
    private PanelCarroCompra panelCarroCompra;

    public Ventana() {
        // Configurar la ventana
        setTitle("Componentix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        int ventanaVentanaX = this.getLocation().x - 300;
        int ventanaVentanaY = this.getLocation().y;
        setLocation(ventanaVentanaX, ventanaVentanaY);

        // Establecer el estilo del panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        setContentPane(mainPanel);

        // Crear el modelo de lista y la lista
        productListModel = new DefaultListModel<>();
        productList = new JXList(productListModel);
        productList.setBackground(backgroundColor);
        productList.setForeground(fontColor);
        productList.setFont(font);

        // Crear el panel de categorías y agregarlo al panel principal
        categoryPanel = new JPanel();
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.setBackground(backgroundColor);
        mainPanel.add(categoryPanel, BorderLayout.NORTH);

        // Agregar las categorías al panel
        agregarCategoria("Procesadores");
        agregarCategoria("Tarjetas Gráficas");
        agregarCategoria("Placas Madres");
        agregarCategoria("Almacenamiento");
        agregarCategoria("Memoria Ram");

        // Agregar la lista de productos
        JScrollPane scrollPane = new JScrollPane(productList);
        scrollPane.setBackground(backgroundColor);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Crear el botón de compra
        JButton compraButton = new JButton("Agregar Al Carro");
        compraButton.setBackground(backgroundColor);
        compraButton.setForeground(fontColor);
        compraButton.setFont(font);
        compraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAlcarro();
            }
        });

        // Crear el botón "Ir al Carro de Compras"
        JButton carroComprasButton = new JButton("Ir al Carro de Compras");
        carroComprasButton.setBackground(backgroundColor);
        carroComprasButton.setForeground(fontColor);
        carroComprasButton.setFont(font);
        carroComprasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCarroCompras();
            }
        });

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(compraButton);
        buttonPanel.add(carroComprasButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Inicializar la lista de productos seleccionados
        selectedProducts = new ArrayList<>();

        // Hacer visible la ventana
        setVisible(true);
    }

    public void cargarProductosPorCategoria(String categoria) {
        productListModel.clear();

        Firestore database = FirebaseConnection.getDataBase();
        Query query = database.collection("Productos").whereEqualTo("category", categoria);

        try {
            QuerySnapshot querySnapshot = query.get().get();
            List<String> products = querySnapshot.getDocuments()
                    .stream()
                    .sorted(Comparator.comparing(document -> document.getString("name")))
                    .map(document -> document.getString("name"))
                    .toList();

            products.forEach(productListModel::addElement);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar los productos: " + e.getMessage(), e);
        }
    }

    private void agregarCategoria(String categoria) {
        JXButton categoryButton = new JXButton(categoria);
        categoryButton.setBackground(backgroundColor);
        categoryButton.setForeground(fontColor);
        categoryButton.setFont(font);
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductosPorCategoria(categoria);
            }
        });
        categoryPanel.add(categoryButton);
    }

    void agregarAlcarro() {
        List<String> selectedProducts = productList.getSelectedValuesList();
        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se han seleccionado productos.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            this.selectedProducts.addAll(selectedProducts);
            JOptionPane.showMessageDialog(this, "Producto agregado al carro.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void abrirCarroCompras() {
        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se han agregado productos al carro.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            panelCarroCompra = new PanelCarroCompra(selectedProducts);
            int ventanaVentanaX = this.getLocation().x + this.getWidth();
            int ventanaVentanaY = this.getLocation().y;
            panelCarroCompra.setLocation(ventanaVentanaX, ventanaVentanaY);
            panelCarroCompra.setVisible(true);
        }
    }

    public DefaultListModel<String> getProductListModel() {
        return productListModel;
    }
}