/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloUsuario;
import com.grupo2.sistemadegestionveterinaria.vista.VistaLogin;

/**
 * Controlador para la gestión de acceso de usuarios en el sistema.
 * Acopla el modelo de datos de usuario con la interfaz gráfica de inicio
 * de sesión (login) para coordinar las validaciones de seguridad.
 *
 * @author Grupo 2
 * @version 1.0
 */
// Módulo 0 - Gestión de Usuarios y Accesos
public class ControladorUsuario {
    
    private ModeloUsuario modelo;
    private VistaLogin vista;

    /**
     * Constructor del controlador de usuario. Vincula la vista de acceso
     * con el modelo de datos de usuario correspondiente.
     *
     * @param modelo el modelo que representa los datos de usuario.
     * @param vista la ventana VistaLogin para el inicio de sesión.
     */
    public ControladorUsuario(ModeloUsuario modelo, VistaLogin vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        // Aquí luego agregarán los Listeners para el botón "Ingresar"
    }
    
    /**
     * Valida de forma temporal las credenciales de inicio de sesión.
     *
     * @param user el nombre de usuario ingresado.
     * @param pass la contraseña ingresada.
     * @return true si las credenciales coinciden; false de lo contrario.
     */
    public boolean validarLogin(String user, String pass) {
        // A futuro esto hará una consulta SQL (SELECT * FROM usuarios WHERE...)
        return user.equals("admin") && pass.equals("123");
    }
}
