/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

  // 1. Parámetros del servidor:
  private static final String HOST = "www.ecuinfo.net";
  private static final String PUERTO = "3306";
  private static final String BASE_DATOS = "ugproy1"; // Base de datos oficial asignada

  // 2. Credenciales de acceso:
  private static final String USER = "ugproy1";
  private static final String PASSWORD = "UG2026proy1";

  // Construcción de la URL de conexión para MySQL
  private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BASE_DATOS;

  public static Connection getConexion() {
    Connection con = null;
    try {
      // Se establece la conexión con la base de datos
      con = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("¡Conexión exitosa al servidor y a la base de datos: " + BASE_DATOS + "!");
    } catch (SQLException e) {
      System.err.println("Error crítico de conexión a la BD: " + e.getMessage());
    }
    return con;
  }
}
