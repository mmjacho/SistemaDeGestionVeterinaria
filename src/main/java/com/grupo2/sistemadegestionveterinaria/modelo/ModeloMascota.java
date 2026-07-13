/**
 *
 * @author Galo Izquierdo
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.grupo2.sistemadegestionveterinaria.data.CnnDB;

/**
 * Modelo que representa una mascota en el sistema de gestión veterinaria.
 * Define sus propiedades y métodos de acceso, además de la recuperación
 * de registros desde la base de datos.
 *
 * @author Galo Izquierdo
 * @version 1.0
 */
public class ModeloMascota {

  /**
   * Constructor por defecto de ModeloMascota.
   */
  public ModeloMascota() {
  }

  private Integer id;
  private String nombre;
  private String raza;
  private String especie;
  private int clienteId;
  private int estado;

  // getters y setters

  /**
   * Obtiene el identificador único de la mascota.
   *
   * @return el identificador único de la mascota.
   */
  public Integer getId() {
    return id;
  }

  /**
   * Establece el identificador único de la mascota.
   *
   * @param id el identificador único a asignar.
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Obtiene el nombre de la mascota.
   *
   * @return el nombre de la mascota.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre de la mascota.
   *
   * @param nombre el nombre a asignar.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene la raza de la mascota.
   *
   * @return la raza de la mascota.
   */
  public String getRaza() {
    return raza;
  }

  /**
   * Establece la raza de la mascota.
   *
   * @param raza la raza a asignar.
   */
  public void setRaza(String raza) {
    this.raza = raza;
  }

  /**
   * Obtiene la especie de la mascota.
   *
   * @return la especie de la mascota.
   */
  public String getEspecie() {
    return especie;
  }

  /**
   * Establece la especie de la mascota.
   *
   * @param especie la especie a asignar.
   */
  public void setEspecie(String especie) {
    this.especie = especie;
  }

  /**
   * Obtiene el identificador del cliente propietario de la mascota.
   *
   * @return el identificador del cliente propietario.
   */
  public int getClienteId() {
    return clienteId;
  }

  /**
   * Establece el identificador del cliente propietario de la mascota.
   *
   * @param clienteId el identificador del cliente propietario a asignar.
   */
  public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
  }

  /**
   * Obtiene el estado de la mascota en el sistema.
   *
   * @return el estado de la mascota.
   */
  public Integer getEstado() {
    return estado;
  }

  /**
   * Establece el estado de la mascota en el sistema.
   *
   * @param estado el estado de la mascota a asignar.
   */
  public void setEstado(Integer estado) {
    this.estado = estado;
  }

  /**
   * Obtiene una lista de todas las mascotas registradas en la base de datos.
   *
   * @return una lista de objetos ModeloMascota.
   */
  public ArrayList<ModeloMascota> listarMascotas() {
    ArrayList<ModeloMascota> lista = new ArrayList<>();
    String sql = "SELECT * FROM g2_vet_mascotas";
    try (Connection con = CnnDB.getConeccion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        ModeloMascota mascota = new ModeloMascota();
        mascota.setId(rs.getInt("id_mascota"));
        mascota.setNombre(rs.getString("nombre"));
        mascota.setRaza(rs.getString("raza"));
        mascota.setEspecie(rs.getString("especie"));
        mascota.setClienteId(rs.getInt("id_cliente"));
        mascota.setEstado(rs.getInt("estado"));
        lista.add(mascota);
      }
    } catch (Exception e) {
      System.err.println("Error al listar mascotas: " + e.getMessage());
    }
    return lista;
  }
}
