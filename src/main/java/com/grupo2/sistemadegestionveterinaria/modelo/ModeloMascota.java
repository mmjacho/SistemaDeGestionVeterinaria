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
 *
 * @author Galo Izquierdo
 */
public class ModeloMascota {

  private Integer id;
  private String nombre;
  private String raza;
  private String especie;
  private int clienteId;
  private int estado;

  // getters y setters

  /**
   *
   * @return
   */
  public Integer getId() {
    return id;
  }

  /**
   *
   * @param id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   *
   * @return
   */
  public String getNombre() {
    return nombre;
  }

  /**
   *
   * @param nombre
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   *
   * @return
   */
  public String getRaza() {
    return raza;
  }

  /**
   *
   * @param raza
   */
  public void setRaza(String raza) {
    this.raza = raza;
  }

  /**
   *
   * @return
   */
  public String getEspecie() {
    return especie;
  }

  /**
   *
   * @param especie
   */
  public void setEspecie(String especie) {
    this.especie = especie;
  }

  /**
   *
   * @return
   */
  public int getClienteId() {
    return clienteId;
  }

  /**
   *
   * @param clienteId
   */
  public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
  }

  /**
   *
   * @return
   */
  public Integer getEstado() {
    return estado;
  }

  /**
   *
   * @param estado
   */
  public void setEstado(Integer estado) {
    this.estado = estado;
  }

  /**
   *
   * @return
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
