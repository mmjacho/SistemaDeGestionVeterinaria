package com.grupo2.sistemadegestionveterinaria.modelo;

import java.util.Date;

/**
 * Modelo que representa la cabecera de una factura en el sistema.
 * Almacena los datos del cliente, la fecha de emisión y los totales
 * calculados de la venta.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class Factura {

  private int idFactura;
  private String nombreCliente;
  private String cedulaCliente;
  private Date fechaEmision;
  private double subtotal;
  private double iva;
  private double total;

  /**
   * Constructor por defecto de Factura.
   * Crea una instancia vacía de la factura.
   */
  public Factura() {
  }

  /**
   * Constructor parametrizado de Factura (sin ID ni fecha).
   *
   * @param nombreCliente el nombre del cliente.
   * @param cedulaCliente la cédula o RUC del cliente.
   * @param subtotal el subtotal calculado antes de impuestos.
   * @param iva el valor calculado de IVA.
   * @param total el total acumulado a pagar.
   */
  public Factura(String nombreCliente, String cedulaCliente, double subtotal, double iva, double total) {
    this.nombreCliente = nombreCliente;
    this.cedulaCliente = cedulaCliente;
    this.subtotal = subtotal;
    this.iva = iva;
    this.total = total;
  }

  // Getters y Setters
  /**
   * Obtiene el identificador único de la factura.
   *
   * @return el identificador de la factura.
   */
  public int getIdFactura() {
    return idFactura;
  }

  /**
   * Establece el identificador único de la factura.
   *
   * @param idFactura el identificador a asignar.
   */
  public void setIdFactura(int idFactura) {
    this.idFactura = idFactura;
  }

  /**
   * Obtiene el nombre del cliente.
   *
   * @return el nombre del cliente.
   */
  public String getNombreCliente() {
    return nombreCliente;
  }

  /**
   * Establece el nombre del cliente.
   *
   * @param nombreCliente el nombre a asignar.
   */
  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  /**
   * Obtiene la cédula del cliente.
   *
   * @return la cédula del cliente.
   */
  public String getCedulaCliente() {
    return cedulaCliente;
  }

  /**
   * Establece la cédula del cliente.
   *
   * @param cedulaCliente la cédula a asignar.
   */
  public void setCedulaCliente(String cedulaCliente) {
    this.cedulaCliente = cedulaCliente;
  }

  /**
   * Obtiene la fecha de emisión de la factura.
   *
   * @return la fecha de emisión.
   */
  public Date getFechaEmision() {
    return fechaEmision;
  }

  /**
   * Establece la fecha de emisión de la factura.
   *
   * @param fechaEmision la fecha de emisión a asignar.
   */
  public void setFechaEmision(Date fechaEmision) {
    this.fechaEmision = fechaEmision;
  }

  /**
   * Obtiene el subtotal acumulado de la factura.
   *
   * @return el subtotal de la factura.
   */
  public double getSubtotal() {
    return subtotal;
  }

  /**
   * Establece el subtotal acumulado de la factura.
   *
   * @param subtotal el subtotal a asignar.
   */
  public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
  }

  /**
   * Obtiene el valor calculado de IVA.
   *
   * @return el valor del IVA.
   */
  public double getIva() {
    return iva;
  }

  /**
   * Establece el valor calculado de IVA.
   *
   * @param iva el valor de IVA a asignar.
   */
  public void setIva(double iva) {
    this.iva = iva;
  }

  /**
   * Obtiene el total final acumulado a pagar.
   *
   * @return el total a pagar.
   */
  public double getTotal() {
    return total;
  }

  /**
   * Establece el total final acumulado a pagar.
   *
   * @param total el total a pagar a asignar.
   */
  public void setTotal(double total) {
    this.total = total;
  }
}
