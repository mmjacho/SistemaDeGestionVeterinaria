/**
 *
 * @author Mario Jacho
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

public class ModeloAtencion {
    private int idAtencion;
    private int idCita;
    private double temperatura;
    private double pesoActual;
    private String diagnostico;
    private String receta;

    public ModeloAtencion() {}

    // Getters y Setters
    public int getIdAtencion() { return idAtencion; }
    public void setIdAtencion(int idAtencion) { this.idAtencion = idAtencion; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public double getTemperatura() { return temperatura; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }

    public double getPesoActual() { return pesoActual; }
    public void setPesoActual(double pesoActual) { this.pesoActual = pesoActual; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getReceta() { return receta; }
    public void setReceta(String receta) { this.receta = receta; }
}