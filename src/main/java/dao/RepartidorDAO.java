package dao;

import controlador.ConexionBD;
import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidor";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");

                repartidores.add(
                        new Repartidor(id, nombre)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return repartidores;
    }

    public boolean guardar(Repartidor repartidor) {

        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(
                        sql,
                        java.sql.Statement.RETURN_GENERATED_KEYS
                )
        ) {

            stmt.setString(1, repartidor.getNombre());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}