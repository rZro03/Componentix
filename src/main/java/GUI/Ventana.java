package GUI;

import Firebase.FirebaseConnection;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Ventana extends JFrame {

    private JList<String> productList;
    private final DefaultListModel<String> productListModel;
    private final JPanel categoryPanel;
    private final JButton btnComprar;
    private final DefaultListModel<String> selectedProductsListModel;

    public Ventana() {
        // Configurar la ventana
        setTitle("Componentix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Crear el modelo de lista y la lista
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);

        // Crear el panel de categorías y agregarlo a la ventana
        categoryPanel = new JPanel();
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(categoryPanel, BorderLayout.NORTH);

        // Agregar las categorías al panel
        agregarCategoria("Procesadores");
        agregarCategoria("Tarjetas Gráficas");
        agregarCategoria("Placas Madres");
        agregarCategoria("Almacenamiento");

        // Agregar el botón de compra
        btnComprar = new JButton("Comprar");
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductosSeleccionados();
            }
        });
        categoryPanel.add(btnComprar);

        // Agregar la lista de productos
        add(new JScrollPane(productList), BorderLayout.CENTER);

        // Crear el modelo de lista para los productos seleccionados
        selectedProductsListModel = new DefaultListModel<>();

        // Crear la lista de productos seleccionados y agregarla a la ventana
        JList<String> selectedProductsList = new JList<>(selectedProductsListModel);
        add(new JScrollPane(selectedProductsList), BorderLayout.EAST);

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
                    .stream().map(document -> document.getString("name"))
                    .toList();

            products.forEach(productListModel::addElement);
        } catch (Exception e) {
            System.err.println("Error al cargar los productos: " + e.getMessage());
        }
    }

    private void agregarCategoria(String categoria) {
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem(categoria);
        categoryComboBox.addActionListener(e -> cargarProductosPorCategoria((String) categoryComboBox.getSelectedItem()));
        categoryPanel.add(categoryComboBox);
    }

    private void agregarProductosSeleccionados() {
        List<String> selectedProducts = productList.getSelectedValuesList();
        for (String product : selectedProducts) {
            selectedProductsListModel.addElement(product);
        }
    }

    private void removerProductoSeleccionados() {
        int selectedIndex = selectedProductsListModel.getSize() - 1;

        if (selectedIndex >= 0) {
            selectedProductsListModel.remove(selectedIndex);
        }
    }
}
