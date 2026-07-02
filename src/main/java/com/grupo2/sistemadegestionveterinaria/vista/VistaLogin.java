/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana gráfica para el inicio de sesión de los usuarios en el sistema.
 * Permite ingresar las credenciales de seguridad (usuario y contraseña)
 * para autorizar el acceso a las funciones del software.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class VistaLogin extends JFrame {
    
    /**
     * Campo de texto para ingresar el nombre de usuario.
     */
    public JTextField txtUsuario;
    /**
     * Campo de contraseña para ingresar la clave de seguridad.
     */
    public JPasswordField txtPassword;
    /**
     * Botón para ingresar al sistema y botón para salir de la aplicación.
     */
    public JButton btnIngresar, btnSalir;

    /**
     * Constructor de VistaLogin.
     * Diseña y posiciona todos los componentes visuales de la interfaz de
     * inicio de sesión e inicializa las acciones básicas.
     */
    public VistaLogin() {
        setTitle("Sistema de Gestión Veterinaria - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 
        
        // Panel Principal con márgenes (Padding)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título Superior
        JLabel lblTitulo = new JLabel("Clínica Veterinaria", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Formulario Central
        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 15));
        panelCentro.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelCentro.add(txtUsuario);
        
        panelCentro.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelCentro.add(txtPassword);

        // Botones Inferiores
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnIngresar = new JButton("Ingresar");
        btnSalir = new JButton("Salir");
        panelSur.add(btnIngresar);
        panelSur.add(btnSalir);

        // --- LÓGICA TEMPORAL PARA EL LOGIN ---
        btnIngresar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            if (usuario.equals("admin") && password.equals("123")) {
                com.grupo2.sistemadegestionveterinaria.modelo.ModeloUsuario.setUsuarioLogueado(usuario);
                JOptionPane.showMessageDialog(this, "¡Bienvenido al sistema!");
                new VistaMenuPrincipal().setVisible(true);
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));

        // Armar la ventana
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    /**
     * Método principal para iniciar la interfaz gráfica de inicio de sesión.
     *
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaLogin().setVisible(true);
        });
    }
}
