/**
 *
 * @author Galo Izquierdo
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

/**
 * Modelo que representa un cliente (propietario de mascotas) en el sistema.
 * Contiene información de contacto e identificación del cliente.
 *
 * @author Galo Izquierdo
 * @version 1.0
 */
public class ModeloCliente {

  /**
   * Constructor por defecto de ModeloCliente.
   */
  public ModeloCliente() {
  }

  private Integer id;
  private String cedula;
  private String nombres;
  private String telefono;

  // getters y setters

  /**
   * Obtiene el identificador único del cliente.
   *
   * @return el identificador del cliente.
   */
  public Integer getId() {
    return id;
  }

  /**
   * Establece el identificador único del cliente.
   *
   * @param id el identificador a asignar.
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Obtiene la cédula de identidad del cliente.
   *
   * @return la cédula del cliente.
   */
  public String getCedula() {
    return cedula;
  }

  /**
   * Establece la cédula de identidad del cliente.
   *
   * @param cedula la cédula a asignar.
   */
  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  /**
   * Obtiene los nombres del cliente.
   *
   * @return los nombres del cliente.
   */
  public String getNombres() {
    return nombres;
  }

  /**
   * Establece los nombres del cliente.
   *
   * @param nombres los nombres a asignar.
   */
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  /**
   * Obtiene el número telefónico de contacto del cliente.
   *
   * @return el teléfono de contacto.
   */
  public String getTelefono() {
    return telefono;
  }

  /**
   * Establece el número telefónico de contacto del cliente.
   *
   * @param telefono el teléfono a asignar.
   */
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
}
