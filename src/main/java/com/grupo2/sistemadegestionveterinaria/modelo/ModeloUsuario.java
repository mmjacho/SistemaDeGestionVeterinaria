/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

/**
 * Modelo que representa un usuario con credenciales de acceso al sistema.
 * Almacena el identificador, nombre de usuario, contraseña y rol asignado.
 *
 * @author Grupo 2
 * @version 1.0
 */
// Módulo 0 - Gestión de Usuarios y Accesos
public class ModeloUsuario {
    private int idUsuario;
    private String usuario;
    private String password;
    private String rol;

    /**
     * Constructor por defecto de ModeloUsuario.
     * Crea una instancia vacía del usuario.
     */
    public ModeloUsuario() {}

    // Getters y Setters
    /**
     * Obtiene el identificador único del usuario.
     *
     * @return el identificador del usuario.
     */
    public int getIdUsuario() { return idUsuario; }

    /**
     * Establece el identificador único del usuario.
     *
     * @param idUsuario el identificador a asignar.
     */
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    /**
     * Obtiene el nombre de usuario de acceso.
     *
     * @return el nombre de usuario.
     */
    public String getUsuario() { return usuario; }

    /**
     * Establece el nombre de usuario de acceso.
     *
     * @param usuario el nombre de usuario a asignar.
     */
    public void setUsuario(String usuario) { this.usuario = usuario; }

    /**
     * Obtiene la contraseña de acceso del usuario.
     *
     * @return la contraseña del usuario.
     */
    public String getPassword() { return password; }

    /**
     * Establece la contraseña de acceso del usuario.
     *
     * @param password la contraseña a asignar.
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Obtiene el rol o nivel de privilegios del usuario.
     *
     * @return el rol del usuario.
     */
    public String getRol() { return rol; }

    /**
     * Establece el rol o nivel de privilegios del usuario.
     *
     * @param rol el rol a asignar.
     */
    public void setRol(String rol) { this.rol = rol; }
}
