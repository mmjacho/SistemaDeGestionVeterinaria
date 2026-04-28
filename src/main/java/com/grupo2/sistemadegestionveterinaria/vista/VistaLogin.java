/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame {
    
    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnIngresar, btnSalir;

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

    // Método para correr esta interfaz de forma independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaLogin().setVisible(true);
        });
    }
}
