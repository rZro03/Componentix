package GUI;

import Firebase.FirebaseConnection;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

public class PanelCarroCompra extends JXFrame {
    private JXTable tablaProductos;
    private DefaultTableModel tableModel;
    private JXLabel lblTotal;
    private final Firestore database = FirebaseConnection.getDataBase();
    private final Color backgroundColor = new Color(30, 30, 30);
    private final Color fontColor = Color.WHITE;
    private final Font font = new Font("Cascadia Code", Font.PLAIN, 14);

    public PanelCarroCompra(List<String> productos) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        add(mainPanel);


        // Crear el panel para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        buttonPanel.setBackground(backgroundColor);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Crear los botones
        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBackground(backgroundColor);
        actualizarButton.setForeground(fontColor);
        actualizarButton.setFont(font);
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción del botón "Actualizar"
                actualizarTabla(productos);
            }
        });
        buttonPanel.add(actualizarButton);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBackground(backgroundColor);
        eliminarButton.setForeground(fontColor);
        eliminarButton.setFont(font);
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción del botón "Eliminar"
                accionEliminar(productos);
            }
        });
        buttonPanel.add(eliminarButton);

        JButton comprarButton = new JButton("Comprar");
        comprarButton.setBackground(backgroundColor);
        comprarButton.setForeground(fontColor);
        comprarButton.setFont(font);
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PanelCarroCompra.this, "Compra realizada exitosamente por: $" + calcularTotal(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); // Finalizar el programa
            }
        });
        buttonPanel.add(comprarButton);

        // Crear la tabla de productos
        tableModel = new DefaultTableModel(new Object[]{"NOMBRE", "PRECIO"}, 0);
        tablaProductos = new JXTable(tableModel);
        tablaProductos.setBackground(backgroundColor);
        tablaProductos.setForeground(fontColor);
        tablaProductos.setFont(font);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar los productos a la tabla
        for (String producto : productos) {
            String precio = obtenerPrecioProducto(producto);
            tableModel.addRow(new Object[]{producto, precio});
        }

        // Calcular el total de la compra
        BigDecimal total = calcularTotal();
        lblTotal = new JXLabel("Total: $" + total);
        lblTotal.setFont(font);
        lblTotal.setForeground(Color.WHITE);
        lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(lblTotal, BorderLayout.SOUTH);
    }

    private String obtenerPrecioProducto(String producto) {
        Query query = database.collection("Productos").whereEqualTo("name", producto);

        try {
            QuerySnapshot querySnapshot = query.get().get();
            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
            return documentSnapshot.getString("price");
        } catch (Exception e) {
            System.err.println("Error al obtener el precio del producto '" + producto + "': " + e.getMessage());
        }

        return "";
    }

    public BigDecimal calcularTotal() {
        return IntStream.range(0, tableModel.getRowCount())
                .mapToObj(i -> (String) tableModel.getValueAt(i, 1))
                .map(precioProductoStr -> {
                    try {
                        BigDecimal precioProducto = new BigDecimal(precioProductoStr.replace(".", ""));
                        return precioProducto;
                    } catch (NumberFormatException e) {
                        System.err.println("Error al convertir el precio del producto: " + e.getMessage());
                        return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private void accionEliminar(List<String> productos) {
        int selectedRow = tablaProductos.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            productos.remove(selectedRow);
            recalcularTotal();
        }

        // Calcular el total después de eliminar el producto
        calcularTotal();
    }

    public void recalcularTotal() {
        BigDecimal total = calcularTotal();

        lblTotal.setText("Total: $" + total.toPlainString());
    }

    private void actualizarTabla(List<String> productos) {
        // Actualizar la tabla con los nuevos productos
        productos.stream()
                .filter(producto -> !existeProductoEnTabla(producto))
                .forEach(producto -> {
                    String precio = obtenerPrecioProducto(producto);
                    tableModel.addRow(new Object[]{producto, precio});
                });
        // Calcular el total después de actualizar la tabla
        recalcularTotal();
    }

    private boolean existeProductoEnTabla(String producto) {
        return IntStream.range(0, tableModel.getRowCount())
                .mapToObj(i -> (String) tableModel.getValueAt(i, 0))
                .anyMatch(nombreProducto -> nombreProducto.equals(producto));
    }
}