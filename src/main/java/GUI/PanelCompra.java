package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelCompra {

    private JFrame frame;
    private final JList<String> productList;
    private final DefaultListModel<String> productListModel;

    public PanelCompra(List<String> productos) {
        frame = new JFrame("Carrito de compras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);

        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);

        productos.forEach(productListModel::addElement);

        frame.add(new JScrollPane(productList), BorderLayout.CENTER);
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}
