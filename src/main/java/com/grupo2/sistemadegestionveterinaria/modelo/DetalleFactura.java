package com.grupo2.sistemadegestionveterinaria.modelo;

/**
 * Modelo que representa el detalle de una factura de servicios o medicinas.
 * Contiene información de los conceptos facturados, cantidades y precios.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class DetalleFactura {

  private int idDetalle;
  private int idFactura;
  private String concepto;
  private double precioUnitario;
  private int cantidad;
  private double precioFinal;

  /**
   * Constructor por defecto de DetalleFactura.
   * Crea una instancia vacía del detalle de factura.
   */
  public DetalleFactura() {
  }

  /**
   * Constructor parametrizado de DetalleFactura.
   *
   * @param concepto el servicio o medicamento facturado.
   * @param precioUnitario el costo individual del concepto.
   * @param cantidad la cantidad del concepto facturado.
   * @param precioFinal el valor acumulado (precioUnitario * cantidad).
   */
  public DetalleFactura(String concepto, double precioUnitario, int cantidad, double precioFinal) {
    this.concepto = concepto;
    this.precioUnitario = precioUnitario;
    this.cantidad = cantidad;
    this.precioFinal = precioFinal;
  }

  // Getters y Setters
  /**
   * Obtiene el identificador único del detalle.
   *
   * @return el identificador del detalle.
   */
  public int getIdDetalle() {
    return idDetalle;
  }

  /**
   * Establece el identificador único del detalle.
   *
   * @param idDetalle el identificador del detalle a asignar.
   */
  public void setIdDetalle(int idDetalle) {
    this.idDetalle = idDetalle;
  }

  /**
   * Obtiene el identificador único de la factura asociada.
   *
   * @return el identificador de la factura.
   */
  public int getIdFactura() {
    return idFactura;
  }

  /**
   * Establece el identificador único de la factura asociada.
   *
   * @param idFactura el identificador de la factura a asignar.
   */
  public void setIdFactura(int idFactura) {
    this.idFactura = idFactura;
  }

  /**
   * Obtiene el concepto facturado.
   *
   * @return la descripción o nombre del concepto.
   */
  public String getConcepto() {
    return concepto;
  }

  /**
   * Establece el concepto facturado.
   *
   * @param concepto la descripción del concepto a asignar.
   */
  public void setConcepto(String concepto) {
    this.concepto = concepto;
  }

  /**
   * Obtiene el precio unitario del concepto.
   *
   * @return el costo unitario del concepto.
   */
  public double getPrecioUnitario() {
    return precioUnitario;
  }

  /**
   * Establece el precio unitario del concepto.
   *
   * @param precioUnitario el costo unitario del concepto a asignar.
   */
  public void setPrecioUnitario(double precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  /**
   * Obtiene la cantidad de ítems del concepto.
   *
   * @return la cantidad facturada.
   */
  public int getCantidad() {
    return cantidad;
  }

  /**
   * Establece la cantidad de ítems del concepto.
   *
   * @param cantidad la cantidad a asignar.
   */
  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  /**
   * Obtiene el precio final acumulado de este detalle.
   *
   * @return el costo final del detalle.
   */
  public double getPrecioFinal() {
    return precioFinal;
  }

  /**
   * Establece el precio final acumulado de este detalle.
   *
   * @param precioFinal el costo final a asignar.
   */
  public void setPrecioFinal(double precioFinal) {
    this.precioFinal = precioFinal;
  }
}
