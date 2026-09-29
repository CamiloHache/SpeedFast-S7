package controlador;

import dao.PedidoDAO;
import modelo.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoControlador {

    private final List<Pedido> pedidos;

    public PedidoControlador() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        pedidos = new ArrayList<>(pedidoDAO.listarTodos());
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }

    public Pedido buscarPedidoPorId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    public boolean existePedido(int id) {
        return buscarPedidoPorId(id) != null;
    }
}