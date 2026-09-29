package main;

import controlador.PedidoControlador;
import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PedidoControlador controlador = new PedidoControlador();

            new VentanaPrincipal(controlador).setVisible(true);

        });
    }
}