/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

// Módulo 0 - Gestión de Usuarios y Accesos
public class ModeloUsuario {
    private int idUsuario;
    private String usuario;
    private String password;
    private String rol;

    public ModeloUsuario() {}

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
