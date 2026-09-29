package vista;

import controlador.PedidoControlador;
import modelo.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import dao.PedidoDAO;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private final PedidoControlador controlador;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    public VentanaListaPedidos(PedidoControlador controlador) {
        this.controlador = controlador;
        setTitle("Lista de Pedidos");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        construirVentana();
    }

    private void construirVentana() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10,10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JLabel titulo = new JLabel("Pedidos Registrados",SwingConstants.CENTER);
        titulo.setFont(new Font("Arial",Font.BOLD,20));
        panelPrincipal.add(titulo,BorderLayout.NORTH);

        String[] columnas = {"ID", "Dirección", "Tipo", "Estado", "Repartidor"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        panelPrincipal.add(scrollPane,BorderLayout.CENTER);
        JPanel panelInferior = new JPanel();
        JButton btnActulizar = new JButton("Actualizar");
        panelInferior.add(btnActulizar);
        panelPrincipal.add(panelInferior,BorderLayout.SOUTH);
        btnActulizar.addActionListener(e -> cargarTabla());
        setContentPane(panelPrincipal);
        cargarTabla();
    }

    private void cargarTabla() {

        modeloTabla.setRowCount(0);

        PedidoDAO pedidoDAO = new PedidoDAO();

        List<Pedido> pedidos = pedidoDAO.listarTodos();

        for (Pedido pedido : pedidos) {

            modeloTabla.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipo(),
                    pedido.getEstado(),
                    pedido.getRepartidor()
            });
        }
    }
}
