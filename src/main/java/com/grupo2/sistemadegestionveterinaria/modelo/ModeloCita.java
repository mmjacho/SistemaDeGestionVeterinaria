/**
 * @author Ruben Quiroga
 */
package com.grupo2.sistemadegestionveterinaria.modelo; // Define el paquete al que pertenece esta clase organizada dentro del proyecto

public class ModeloCita {

    // --- ATRIBUTOS DE LA CLASE (Variables de instancia) ---
    // Encapsulan los datos que tendrá cada objeto de tipo "Cita"
    private Integer id;         // Identificador único de la cita (usualmente autoincremental en la base de datos)
    private Integer medicoId;   // ID del médico asignado a la cita (llave foránea)
    private Integer mascotaId;  // ID de la mascota que asistirá a la cita (llave foránea)
    private String fecha;       // Almacena la fecha programada para la cita
    private String hora;        // Almacena la hora programada para la cita
    private String estado;      // Estado actual de la cita (ej. "Pendiente", "Completada", "Cancelada")

    // --- CONSTRUCTORES ---

    /**
     * Constructor vacío (por defecto).
     * Permite crear un objeto de tipo ModeloCita sin asignarle datos iniciales.
     */
    public ModeloCita() {
    }

    /**
     * Constructor con parámetros (sin el ID).
     * Se usa para crear una nueva cita antes de guardarla en la base de datos, 
     * ya que el ID suele generarse automáticamente allí.
     */
    public ModeloCita(Integer medicoId, Integer mascotaId, String fecha, String hora, String estado) {
        this.medicoId = medicoId;   // Asigna el médico recibido al atributo de la clase
        this.mascotaId = mascotaId; // Asigna la mascota recibida al atributo de la clase
        this.fecha = fecha;         // Asigna la fecha recibida al atributo de la clase
        this.hora = hora;           // Asigna la hora recibida al atributo de la clase
        this.estado = estado;       // Asigna el estado recibido al atributo de la clase
    }

    // --- MÉTODOS GETTERS Y SETTERS ---
    // Permiten acceder (Get) y modificar (Set) los atributos privados desde fuera de esta clase.

    // Obtiene el ID de la cita
    public Integer getId() {
        return id;
    }

    // Modifica o establece el ID de la cita
    public void setId(Integer id) {
        this.id = id;
    }

    // Obtiene el ID del médico
    public Integer getMedicoId() {
        return medicoId;
    }

    // Modifica o establece el ID del médico
    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    // Obtiene el ID de la mascota
    public Integer getMascotaId() {
        return mascotaId;
    }

    // Modifica o establece el ID de la mascota
    public void setMascotaId(Integer mascotaId) {
        this.mascotaId = mascotaId;
    }

    // Obtiene la fecha de la cita
    public String getFecha() {
        return fecha;
    }

    // Modifica o establece la fecha de la cita
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // Obtiene la hora de la cita
    public String getHora() {
        return hora;
    }

    // Modifica o establece la hora de la cita
    public void setHora(String hora) {
        this.hora = hora;
    }

    // Obtiene el estado de la cita
    public String getEstado() {
        return estado;
    }

    // Modifica o establece el estado de la cita
    public void setEstado(String estado) {
        this.estado = estado;
    }
}