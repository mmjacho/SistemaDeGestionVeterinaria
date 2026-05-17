package com.grupo2.sistemadegestionveterinaria.modelo;

public class DetalleFactura {

  private int idDetalle;
  private int idFactura;
  private String concepto;
  private double precioUnitario;
  private int cantidad;
  private double precioFinal;

  public DetalleFactura() {
  }

  public DetalleFactura(String concepto, double precioUnitario, int cantidad, double precioFinal) {
    this.concepto = concepto;
    this.precioUnitario = precioUnitario;
    this.cantidad = cantidad;
    this.precioFinal = precioFinal;
  }

  // Getters y Setters
  public int getIdDetalle() {
    return idDetalle;
  }

  public void setIdDetalle(int idDetalle) {
    this.idDetalle = idDetalle;
  }

  public int getIdFactura() {
    return idFactura;
  }

  public void setIdFactura(int idFactura) {
    this.idFactura = idFactura;
  }

  public String getConcepto() {
    return concepto;
  }

  public void setConcepto(String concepto) {
    this.concepto = concepto;
  }

  public double getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(double precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public double getPrecioFinal() {
    return precioFinal;
  }

  public void setPrecioFinal(double precioFinal) {
    this.precioFinal = precioFinal;
  }
}
