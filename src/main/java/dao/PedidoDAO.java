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

        String sql =
                "SELECT p.id, p.direccion, p.tipo, p.estado, " +
                        "r.nombre AS repartidor " +
                        "FROM pedido p " +
                        "LEFT JOIN entrega e ON p.id = e.id_pedido " +
                        "LEFT JOIN repartidor r ON e.id_repartidor = r.id " +
                        "ORDER BY p.id";

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
                String repartidor = rs.getString("repartidor");

                TipoPedido tipo =
                        TipoPedido.valueOf(tipoTexto.toUpperCase());

                EstadoPedido estado =
                        EstadoPedido.valueOf(estadoTexto.toUpperCase());

                Pedido pedido =
                        new Pedido(id, direccion, tipo);

                pedido.setEstado(estado);

                if (repartidor != null) {
                    pedido.setRepartidor(repartidor);
                }

                pedidos.add(pedido);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return pedidos;
    }

    public Pedido buscarPorId(int id) {

        String sql = "SELECT id, direccion, tipo, estado FROM pedido WHERE id = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    int idPedido = rs.getInt("id");
                    String direccion = rs.getString("direccion");
                    String tipoTexto = rs.getString("tipo");
                    String estadoTexto = rs.getString("estado");

                    TipoPedido tipo =
                            TipoPedido.valueOf(tipoTexto.toUpperCase());

                    EstadoPedido estado =
                            EstadoPedido.valueOf(estadoTexto.toUpperCase());

                    Pedido pedido =
                            new Pedido(idPedido, direccion, tipo);

                    pedido.setEstado(estado);

                    return pedido;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}