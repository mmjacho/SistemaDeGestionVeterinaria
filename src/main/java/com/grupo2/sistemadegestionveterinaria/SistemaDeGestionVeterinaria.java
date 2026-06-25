/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria;

import com.grupo2.sistemadegestionveterinaria.controlador.ControladorUsuario;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloUsuario;
import com.grupo2.sistemadegestionveterinaria.vista.VistaLogin;
import javax.swing.SwingUtilities;

public class SistemaDeGestionVeterinaria {

    public static void main(String[] args) {
        // Ejecución segura de la interfaz gráfica apuntando al flujo del Login
        SwingUtilities.invokeLater(() -> {
            // 1. Instanciamos el Modelo y la Vista del Módulo 0
            VistaLogin vistaLogin = new VistaLogin();
            ModeloUsuario modeloUsuario = new ModeloUsuario();
            
            // 2. Acoplamos los componentes mediante el Controlador oficial
            ControladorUsuario controlador = new ControladorUsuario(modeloUsuario, vistaLogin);

            // 3. Desplegamos la ventana de acceso
            vistaLogin.setVisible(true);
        });
    }
}
