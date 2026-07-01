package com.grupo2.sistemadegestionveterinaria.modelo;

/**
 * Modelo que representa una cita médica veterinaria en el sistema.
 * Almacena información sobre el médico, la mascota, la fecha, hora y estado
 * de la cita.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class ModeloCita {
    private Integer id;
    private Integer medicoId;
    private Integer mascotaId;
    private String fecha;
    private String hora;
    private String estado;

    /**
     * Constructor por defecto de ModeloCita.
     * Crea una instancia vacía de la cita.
     */
    public ModeloCita() {}

    /**
     * Constructor parametrizado de ModeloCita.
     *
     * @param medicoId el identificador único del médico asignado.
     * @param mascotaId el identificador único de la mascota.
     * @param fecha la fecha de la cita (DD/MM/YYYY o formato compatible).
     * @param hora la hora programada para la cita (HH:MM).
     * @param estado el estado actual de la cita (e.g. PROGRAMADA).
     */
    public ModeloCita(Integer medicoId, Integer mascotaId, String fecha, String hora, String estado) {
        this.medicoId = medicoId;
        this.mascotaId = mascotaId;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador único de la cita.
     *
     * @return el identificador de la cita.
     */
    public Integer getId() { return id; }

    /**
     * Establece el identificador único de la cita.
     *
     * @param id el identificador a asignar.
     */
    public void setId(Integer id) { this.id = id; }

    /**
     * Obtiene el identificador del médico asignado.
     *
     * @return el identificador del médico.
     */
    public Integer getMedicoId() { return medicoId; }

    /**
     * Establece el identificador del médico asignado.
     *
     * @param medicoId el identificador del médico a asignar.
     */
    public void setMedicoId(Integer medicoId) { this.medicoId = medicoId; }

    /**
     * Obtiene el identificador de la mascota vinculada.
     *
     * @return el identificador de la mascota.
     */
    public Integer getMascotaId() { return mascotaId; }

    /**
     * Establece el identificador de la mascota vinculada.
     *
     * @param mascotaId el identificador de la mascota a asignar.
     */
    public void setMascotaId(Integer mascotaId) { this.mascotaId = mascotaId; }

    /**
     * Obtiene la fecha de la cita.
     *
     * @return la fecha programada.
     */
    public String getFecha() { return fecha; }

    /**
     * Establece la fecha de la cita.
     *
     * @param fecha la fecha programada a asignar.
     */
    public void setFecha(String fecha) { this.fecha = fecha; }

    /**
     * Obtiene la hora programada de la cita.
     *
     * @return la hora programada.
     */
    public String getHora() { return hora; }

    /**
     * Establece la hora programada de la cita.
     *
     * @param hora la hora programada a asignar.
     */
    public void setHora(String hora) { this.hora = hora; }

    /**
     * Obtiene el estado actual de la cita.
     *
     * @return el estado de la cita.
     */
    public String getEstado() { return estado; }

    /**
     * Establece el estado actual de la cita.
     *
     * @param estado el estado a asignar.
     */
    public void setEstado(String estado) { this.estado = estado; }
}