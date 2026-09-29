package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import controlador.ConexionBD;
import modelo.Pedido;
import modelo.EstadoPedido;
import modelo.TipoPedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Todo el código DEBE estar dentro de un método como este:
    public boolean guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipo().toString());
            stmt.setString(3, pedido.getEstado().toString());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, estado FROM pedido";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipoTexto = rs.getString("tipo");
                String estadoTexto = rs.getString("estado");

                TipoPedido tipo = TipoPedido.valueOf(tipoTexto.toUpperCase());
                EstadoPedido estado = EstadoPedido.valueOf(estadoTexto.toUpperCase());

                Pedido pedido = new Pedido(id, direccion, tipo);
                pedido.setEstado(estado);

                pedidos.add(pedido);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return pedidos;
    }
}