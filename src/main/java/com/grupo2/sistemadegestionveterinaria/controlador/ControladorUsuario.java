/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloUsuario;
import com.grupo2.sistemadegestionveterinaria.vista.VistaLogin;

// Módulo 0 - Gestión de Usuarios y Accesos
public class ControladorUsuario {
    
    private ModeloUsuario modelo;
    private VistaLogin vista;

    public ControladorUsuario(ModeloUsuario modelo, VistaLogin vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        // Aquí luego agregarán los Listeners para el botón "Ingresar"
    }
    
    // Método temporal de validación
    public boolean validarLogin(String user, String pass) {
        // A futuro esto hará una consulta SQL (SELECT * FROM usuarios WHERE...)
        return user.equals("admin") && pass.equals("123");
    }
}
