package vista;

import controlador.PedidoControlador;
import modelo.Pedido;
import modelo.TipoPedido;
import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame{
    private final PedidoControlador controlador;
    private JTextField txtId;
    private JTextField txtDireccion;
    private JComboBox<TipoPedido> cmbTipo;

    public VentanaRegistroPedido(PedidoControlador controlador){
        this.controlador = controlador;
        setTitle("SpeedFast - Registro de Pedido");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        construirVentana();
    }

    public void construirVentana() {
        JPanel panelPrincipal1 = new JPanel(new BorderLayout(10,10));
        panelPrincipal1.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
        JLabel titulo = new JLabel("Registrar nuevo pedido",SwingConstants.CENTER);
        titulo.setFont(new Font("Arial",Font.BOLD,25));
        panelPrincipal1.add(titulo,BorderLayout.NORTH);
        JPanel formulario = new JPanel(new GridLayout(3,2,10,15));

        formulario.add(new JLabel("ID del pedido:"));
        txtId = new JTextField();
        formulario.add(txtId);

        formulario.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        formulario.add(txtDireccion);

        formulario.add(new JLabel("Tipo de pedido:"));
        cmbTipo = new JComboBox<>(TipoPedido.values());
        formulario.add(cmbTipo);

        panelPrincipal1.add(formulario,BorderLayout.CENTER);
        JPanel panelBotones = new JPanel();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);

        panelPrincipal1.add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarPedido());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        setContentPane(panelPrincipal1);
    }

    private void guardarPedido() {
        String idTexto = txtId.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if(idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Debe ingresaar un ID para el pedido.","Campo obligatorio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Debe ingresar la dirección","Campo obligatorio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"El ID debe ser númerico","Dato Invalido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(id<=0) {
            JOptionPane.showMessageDialog(this,"El ID debe ser mayor que 0","Dato Invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(controlador.existePedido(id)) {
            JOptionPane.showMessageDialog(this,"Ya existe un pedido con ese ID","Pedido duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TipoPedido tipo = (TipoPedido) cmbTipo.getSelectedItem();

        Pedido pedido = new Pedido (id, direccion, tipo);
        controlador.agregarPedido(pedido);
        JOptionPane.showMessageDialog(this,"Pedido registrado correctamente");
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtDireccion.setText("");
        cmbTipo.setSelectedIndex(0);
    }
}