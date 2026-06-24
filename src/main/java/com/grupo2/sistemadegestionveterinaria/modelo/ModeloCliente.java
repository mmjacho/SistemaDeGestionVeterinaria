/**
 *
 * @author Galo Izquierdo
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

/**
 *
 * @author Galo Izquierdo
 */
public class ModeloCliente {

  private Integer id;
  private String cedula;
  private String nombres;
  private String telefono;

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
  public String getCedula() {
    return cedula;
  }

  /**
   *
   * @param cedula
   */
  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  /**
   *
   * @return
   */
  public String getNombres() {
    return nombres;
  }

  /**
   *
   * @param nombres
   */
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  /**
   *
   * @return
   */
  public String getTelefono() {
    return telefono;
  }

  /**
   *
   * @param telefono
   */
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
}
