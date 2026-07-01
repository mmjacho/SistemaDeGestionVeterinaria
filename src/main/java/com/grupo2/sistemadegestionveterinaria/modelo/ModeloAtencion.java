package com.grupo2.sistemadegestionveterinaria.modelo;

/**
 * Representa el modelo de datos para la entidad de Atención Veterinaria.
 * Esta clase almacena la información de los parámetros clínicos
 * recopilados durante la consulta médica de un paciente.
 * Módulo 4: Registro de Atención Veterinaria.
 *
 * @author Mario Jacho
 * @version 1.1
 */
public class ModeloAtencion {

    /**
     * ID único autoincremental de la ficha de atención.
     */
    private int idAtencion;

    /**
     * ID de la cita médica programada asociada.
     */
    private int idCita;

    /**
     * Temperatura corporal medida en grados Celsius (°C).
     */
    private double temperatura;

    /**
     * Peso actual del paciente medido en kilogramos (Kg).
     */
    private double pesoActual;

    /**
     * Descripción detallada del diagnóstico clínico emitido.
     */
    private String diagnostico;

    /**
     * Detalle de los medicamentos y dosis recetadas.
     */
    private String receta;

    /**
     * Constructor por defecto de la clase ModeloAtencion. Crea una instancia
     * vacía sin inicializar sus atributos.
     */
    public ModeloAtencion() {
    }

    /**
     * Obtiene el identificador único de la atención veterinaria.
     *
     * @return El ID de la atención.
     */
    public final int getIdAtencion() {
        return idAtencion;
    }

    /**
     * Establece el identificador único de la atención veterinaria.
     *
     * @param nuevoId El nuevo ID de la atención.
     */
    public final void setIdAtencion(final int nuevoId) {
        this.idAtencion = nuevoId;
    }

    /**
     * Obtiene el identificador de la cita médica vinculada.
     *
     * @return El ID de la cita médica.
     */
    public final int getIdCita() {
        return idCita;
    }

    /**
     * Establece el identificador de la cita médica para asociar la atención.
     *
     * @param nuevaCita El nuevo ID de la cita médica.
     */
    public final void setIdCita(final int nuevaCita) {
        this.idCita = nuevaCita;
    }

    /**
     * Obtiene la temperatura corporal registrada del paciente.
     *
     * @return La temperatura en grados Celsius (°C).
     */
    public final double getTemperatura() {
        return temperatura;
    }

    /**
     * Establece la temperatura corporal registrada del paciente.
     *
     * @param nuevaTemperatura la temperatura en grados Celsius (°C).
     */
    public final void setTemperatura(final double nuevaTemperatura) {
        this.temperatura = nuevaTemperatura;
    }

    /**
     * Obtiene el peso actual registrado del paciente.
     *
     * @return El peso en kilogramos (Kg).
     */
    public final double getPesoActual() {
        return pesoActual;
    }

    /**
     * Establece el peso actual registrado del paciente durante la consulta.
     *
     * @param nuevoPeso El peso medido en kilogramos (Kg).
     */
    public final void setPesoActual(final double nuevoPeso) {
        this.pesoActual = nuevoPeso;
    }

    /**
     * Obtiene el diagnóstico clínico de la consulta médica.
     *
     * @return Una cadena de texto con la descripción del diagnóstico.
     */
    public final String getDiagnostico() {
        return diagnostico;
    }

    /**
     * Establece el diagnóstico clínico de la consulta médica.
     *
     * @param nuevoDiagnostico el diagnóstico emitido en texto.
     */
    public final void setDiagnostico(final String nuevoDiagnostico) {
        this.diagnostico = nuevoDiagnostico;
    }

    /**
     * Obtiene la receta médica expedida para la atención actual.
     *
     * @return el tratamiento o medicamentos prescritos.
     */
    public final String getReceta() {
        return receta;
    }

    /**
     * Establece la receta médica con las indicaciones del tratamiento.
     *
     * @param nuevaReceta la prescripción médica o tratamiento.
     */
    public final void setReceta(final String nuevaReceta) {
        this.receta = nuevaReceta;
    }
}
