/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria;

import com.grupo2.sistemadegestionveterinaria.controlador.ControladorUsuario;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloUsuario;
import com.grupo2.sistemadegestionveterinaria.vista.VistaLogin;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicializa y coordina la ejecución del sistema de gestión
 * veterinaria.
 *
 * @author Grupo 2
 * @version 1.0
 */
public final class SistemaDeGestionVeterinaria {

    /**
     * Constructor privado para evitar instanciación.
     */
    private SistemaDeGestionVeterinaria() {
        throw new UnsupportedOperationException(
                "Esta clase no debe ser instanciada.");
    }

    /**
     * Punto de entrada principal de la aplicación. Configura e inicia la
     * interfaz gráfica de usuario para el inicio de sesión.
     *
     * @param args los argumentos de la línea de comandos (no utilizados).
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaLogin vistaLogin = new VistaLogin();
            ModeloUsuario modeloUsuario = new ModeloUsuario();
            @SuppressWarnings("unused")
            ControladorUsuario controlador = new ControladorUsuario(
                    modeloUsuario, vistaLogin);
            vistaLogin.setVisible(true);
        });
    }
}
