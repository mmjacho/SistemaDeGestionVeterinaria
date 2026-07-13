/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo2.sistemadegestionveterinaria.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión con la base de datos MySQL del sistema.
 * Proporciona el punto de acceso centralizado para establecer conexiones.
 *
 * @author Galo Izquierdo
 * @version 1.0
 */
public class CnnDB {

  /**
   * Constructor por defecto de CnnDB.
   */
  public CnnDB() {
  }

  // 1. Parámetros del servidor:
  private static final String HOST = "www.ecuinfo.net";
  private static final String PUERTO = "3306";
  private static final String BASE_DATOS = "ugproy1"; // Base de datos oficial asignada

  // 2. Credenciales de acceso:
  private static final String USER = "ugproy1";
  private static final String PASS = "UG2026proy1";

  // Construcción de la URL de conexión para MySQL
  private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BASE_DATOS;

  /**
   * Establece y devuelve una conexión activa con la base de datos.
   *
   * @return un objeto Connection a la base de datos MySQL.
   * @throws SQLException si ocurre un error al intentar establecer la
   *                      conexión.
   */
  public static Connection getConeccion() throws SQLException {
    Connection con = DriverManager.getConnection(URL, USER, PASS);
    System.out.println("¡Conexión exitosa al servidor y a la base de datos: " + BASE_DATOS + "!");
    return con;
  }
}
