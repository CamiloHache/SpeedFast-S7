package vista;

import controlador.PedidoControlador;
import modelo.EstadoPedido;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame{

    private final PedidoControlador controlador;

    public VentanaPrincipal(PedidoControlador controlador){
        this.controlador = controlador;
        setTitle("SpeedFast - Menú Principal");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        construirVentana();
    }

    private void construirVentana(){

        JPanel panelPrincipal = new JPanel(new BorderLayout(15,15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        JLabel titulo = new JLabel("SpeedFast - Gestión de Entregas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        JPanel panelBotones = new JPanel(new GridLayout(2,2,15,15));

        JButton btnRegistrar = new JButton("Registrar Pedido");
        JButton btnListar = new JButton("Listar Pedidos");
        JButton btnAsignar = new JButton("Asignar Repartidor");
        JButton btnIniciar = new JButton("Iniciar Entrega");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnIniciar);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e-> new VentanaRegistroPedido(controlador).setVisible(true));
        btnListar.addActionListener(e-> new VentanaListaPedidos(controlador).setVisible(true));
        btnAsignar.addActionListener(e-> asignarRepartidor());
        btnIniciar.addActionListener(e-> inicarEntrega());

        setContentPane(panelPrincipal);
    }

    private void asignarRepartidor(){
        String idTexto = JOptionPane.showInputDialog(this, "Ingrese el ID del pedido:");
        if(idTexto== null) {
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            PedidoControlador controladorLocal = controlador;
            if(!controladorLocal.existePedido(id)) {
                JOptionPane.showMessageDialog(this, "El pedido no existe","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String repartidor = JOptionPane.showInputDialog(this, "Ingrese el nombre del repartidor:");
            if(repartidor == null || repartidor.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Debe ingresar un nombre de repartidor","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controlador.buscarPedidoPorId(id).setRepartidor(repartidor.trim());
            JOptionPane.showMessageDialog(this, "Repartidor asignado correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"El ID debe ser numérico","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicarEntrega(){
        String idTexto = JOptionPane.showInputDialog(this, "Ingrese el ID del pedido:");
        if(idTexto== null) {
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            if(!controlador.existePedido(id)) {
                JOptionPane.showMessageDialog(this, "El pedido no existe","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controlador.buscarPedidoPorId(id).setEstado(EstadoPedido.EN_REPARTO);
            JOptionPane.showMessageDialog(this,"La entrega ha sido iniciada");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"El ID debe ser númerico","Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}