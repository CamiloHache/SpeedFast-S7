package main;

import controlador.PedidoControlador;
import vista.VentanaPrincipal;
import javax.swing.*;
import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class Main {

    public static void main (String[] args) {
        try{
            Connection conexion = ConexionBD.obtenerConexion();

            System.out.println("=============================");
            System.out.println("CONEXIÓN EXITOSA A MYSQL");
            System.out.println("Base de datos: speedfast_db");
            System.out.println("=============================");

            conexion.close();
        } catch (SQLException e) {

            System.out.println("=============================");
            System.out.println("ERROR AL CONECTAR A MYSQL");
            System.out.println("=============================");

            e.printStackTrace();
        }
    }
}
