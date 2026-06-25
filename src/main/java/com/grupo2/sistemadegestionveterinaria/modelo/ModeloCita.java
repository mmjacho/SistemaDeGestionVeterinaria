package com.grupo2.sistemadegestionveterinaria.modelo;

public class ModeloCita {
    private Integer id;
    private Integer medicoId;
    private Integer mascotaId;
    private String fecha;
    private String hora;
    private String estado;

    public ModeloCita() {}

    public ModeloCita(Integer medicoId, Integer mascotaId, String fecha, String hora, String estado) {
        this.medicoId = medicoId;
        this.mascotaId = mascotaId;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getMedicoId() { return medicoId; }
    public void setMedicoId(Integer medicoId) { this.medicoId = medicoId; }

    public Integer getMascotaId() { return mascotaId; }
    public void setMascotaId(Integer mascotaId) { this.mascotaId = mascotaId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}