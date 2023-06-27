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
        setSize(550, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        add(mainPanel);

        // Crear la tabla de productos
        tableModel = new DefaultTableModel(new Object[]{"NOMBRE", "PRECIO"}, 0);
        tablaProductos = new JXTable(tableModel);
        tablaProductos.setBackground(backgroundColor);
        tablaProductos.setForeground(fontColor);
        tablaProductos.setFont(font);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Crear el panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar los productos a la tabla
        for (String producto : productos) {
            String precio = obtenerPrecioProducto(producto);
            tableModel.addRow(new Object[]{producto, precio});
        }

        // Calcular el total de la compra
        BigDecimal total = calcularTotal();
        lblTotal = new JXLabel("Total: $" + total);
        lblTotal.setForeground(Color.WHITE);
        lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(lblTotal, BorderLayout.WEST);

        // Crear el botón de comprar
        JButton comprarButton = new JButton("Comprar");
        comprarButton.setBackground(backgroundColor);
        comprarButton.setForeground(fontColor);
        comprarButton.setFont(font);
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(PanelCarroCompra.this, "¿Estás seguro de que deseas finalizar la compra y salir?", "Confirmar Compra", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(PanelCarroCompra.this, "Compra realizada exitosamente.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); // Finalizar el programa
                }
            }
        });
        buttonPanel.add(comprarButton);
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

    private BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String precioStr = (String) tableModel.getValueAt(i, 1);
            BigDecimal precio = new BigDecimal(precioStr);
            total = total.add(precio);
        }
        return total;
    }
}