package vista;

import dao.PedidoDAO;
import dao.RepartidorDAO;
import dao.EntregaDAO;
import modelo.Pedido;
import modelo.Repartidor;
import modelo.Entrega;
import java.time.LocalDate;
import java.time.LocalTime;
import controlador.PedidoControlador;
import modelo.EstadoPedido;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final PedidoControlador controlador;

    public VentanaPrincipal(PedidoControlador controlador) {
        this.controlador = controlador;
        setTitle("SpeedFast - Menú Principal");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        construirVentana();
    }

    private void construirVentana() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel titulo = new JLabel(
                "SpeedFast - Gestión de Entregas",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 15, 15));

        JButton btnRegistrar = new JButton("Registrar Pedido");
        JButton btnListar = new JButton("Listar Pedidos");
        JButton btnAsignar = new JButton("Asignar Repartidor");
        JButton btnIniciar = new JButton("Iniciar Entrega");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnIniciar);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        btnRegistrar.addActionListener(
                e -> new VentanaRegistroPedido(controlador).setVisible(true)
        );

        btnListar.addActionListener(
                e -> new VentanaListaPedidos(controlador).setVisible(true)
        );

        btnAsignar.addActionListener(
                e -> asignarRepartidor()
        );

        btnIniciar.addActionListener(
                e -> inicarEntrega()
        );

        setContentPane(panelPrincipal);
    }

    private void asignarRepartidor() {

        String idTexto = JOptionPane.showInputDialog(
                this,
                "Ingrese el ID del pedido:"
        );

        if (idTexto == null) {
            return;
        }

        try {

            int idPedido = Integer.parseInt(idTexto);

            PedidoDAO pedidoDAO = new PedidoDAO();

            Pedido pedido = pedidoDAO.buscarPorId(idPedido);

            if (pedido == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "El pedido no existe en la base de datos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            RepartidorDAO repartidorDAO = new RepartidorDAO();

            java.util.List<Repartidor> repartidores =
                    repartidorDAO.listarTodos();

            if (repartidores.isEmpty()) {

                String nombre = JOptionPane.showInputDialog(
                        this,
                        "No existen repartidores registrados.\n" +
                                "Ingrese el nombre del repartidor:"
                );

                if (nombre == null || nombre.trim().isEmpty()) {
                    return;
                }

                Repartidor nuevoRepartidor =
                        new Repartidor(0, nombre.trim());

                if (!repartidorDAO.guardar(nuevoRepartidor)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "No fue posible registrar el repartidor",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                repartidores = repartidorDAO.listarTodos();
            }

            Repartidor repartidorSeleccionado =
                    (Repartidor) JOptionPane.showInputDialog(
                            this,
                            "Seleccione un repartidor:",
                            "Asignar Repartidor",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            repartidores.toArray(),
                            repartidores.get(0)
                    );

            if (repartidorSeleccionado == null) {
                return;
            }

            String fecha = LocalDate.now().toString();
            String hora = LocalTime.now().toString();

            Entrega entrega = new Entrega(
                    0,
                    idPedido,
                    repartidorSeleccionado.getId(),
                    fecha,
                    hora
            );

            EntregaDAO entregaDAO = new EntregaDAO();

            if (entregaDAO.guardar(entrega)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Repartidor asignado correctamente"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible guardar la asignación",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID del pedido debe ser numérico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void inicarEntrega() {

        String idTexto = JOptionPane.showInputDialog(
                this,
                "Ingrese el ID del pedido:"
        );

        if (idTexto == null) {
            return;
        }

        try {

            int idPedido = Integer.parseInt(idTexto);

            PedidoDAO pedidoDAO = new PedidoDAO();
            Pedido pedido = null;

            for (Pedido p : pedidoDAO.listarTodos()) {

                if (p.getId() == idPedido) {
                    pedido = p;
                    break;
                }
            }

            if (pedido == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "El pedido no existe en la base de datos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String idRepartidorTexto = JOptionPane.showInputDialog(
                    this,
                    "Ingrese el ID del repartidor:"
            );

            if (idRepartidorTexto == null) {
                return;
            }

            int idRepartidor = Integer.parseInt(idRepartidorTexto);

            String fecha = LocalDate.now().toString();
            String hora = LocalTime.now().toString();

            Entrega entrega = new Entrega(
                    0,
                    idPedido,
                    idRepartidor,
                    fecha,
                    hora
            );

            EntregaDAO entregaDAO = new EntregaDAO();

            if (entregaDAO.guardar(entrega)) {

                pedido.setEstado(EstadoPedido.EN_REPARTO);

                JOptionPane.showMessageDialog(
                        this,
                        "La entrega ha sido iniciada y registrada correctamente"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible registrar la entrega",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Los IDs deben ser numéricos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}