/**
 *
 * @author Galo Izquierdo
 */
package com.grupo2.sistemadegestionveterinaria.modelo;


public class ModeloCliente {
    
  private Integer id;
  private String cedula;
  private String nombres;
  private String telefono;

  // getters y setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCedula() {
    return cedula;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
}
