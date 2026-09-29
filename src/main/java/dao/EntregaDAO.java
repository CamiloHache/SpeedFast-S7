package dao;

import controlador.ConexionBD;
import modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntregaDAO {

    public boolean guardar(Entrega entrega) {

        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, entrega.getPedidoId());
            stmt.setInt(2, entrega.getRepartidorId());
            stmt.setString(3, entrega.getFecha());
            stmt.setString(4, entrega.getHora());

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}