package com.grupo2.sistemadegestionveterinaria.modelo;

import java.util.Date;

public class Factura {

  private int idFactura;
  private String nombreCliente;
  private String cedulaCliente;
  private Date fechaEmision;
  private double subtotal;
  private double iva;
  private double total;

  // Constructor vacío
  public Factura() {
  }

  // Constructor con parámetros (sin ID ni fecha, ya que se generan en la BD)
  public Factura(String nombreCliente, String cedulaCliente, double subtotal, double iva, double total) {
    this.nombreCliente = nombreCliente;
    this.cedulaCliente = cedulaCliente;
    this.subtotal = subtotal;
    this.iva = iva;
    this.total = total;
  }

  // Getters y Setters
  public int getIdFactura() {
    return idFactura;
  }

  public void setIdFactura(int idFactura) {
    this.idFactura = idFactura;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public String getCedulaCliente() {
    return cedulaCliente;
  }

  public void setCedulaCliente(String cedulaCliente) {
    this.cedulaCliente = cedulaCliente;
  }

  public Date getFechaEmision() {
    return fechaEmision;
  }

  public void setFechaEmision(Date fechaEmision) {
    this.fechaEmision = fechaEmision;
  }

  public double getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
  }

  public double getIva() {
    return iva;
  }

  public void setIva(double iva) {
    this.iva = iva;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }
}
