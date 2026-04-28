/**
 *
 * @author Alonso Serrano
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;

public class VistaMedico extends JFrame {
    
    public JTextField txtNombres, txtApellidos, txtEspecialidad, txtTelefono;
    public JButton btnGuardar, btnBuscar, btnActualizar;
    public JTable tablaMedicos;

    public VistaMedico() {
        setTitle("Módulo: Registro de Médicos Veterinarios");
        setSize(700, 450);
        setLocationRelativeTo(null); // Centrar en pantalla
        
        // Panel Principal con márgenes (Padding)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Formulario superior
        JPanel panelCentro = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCentro.add(new JLabel("Nombres:")); txtNombres = new JTextField(); panelCentro.add(txtNombres);
        panelCentro.add(new JLabel("Apellidos:")); txtApellidos = new JTextField(); panelCentro.add(txtApellidos);
        panelCentro.add(new JLabel("Especialidad:")); txtEspecialidad = new JTextField(); panelCentro.add(txtEspecialidad);
        panelCentro.add(new JLabel("Teléfono:")); txtTelefono = new JTextField(); panelCentro.add(txtTelefono);

        // Botones centrados y con separación
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnGuardar = new JButton("Guardar Médico"); btnBuscar = new JButton("Buscar"); btnActualizar = new JButton("Actualizar");
        panelSur.add(btnGuardar); panelSur.add(btnBuscar); panelSur.add(btnActualizar);

        // Armar el panel principal
        panelPrincipal.add(panelCentro, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaMedicos = new JTable(new Object[][]{}, new String[]{"Apellidos", "Nombres", "Especialidad", "Teléfono"})), BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // Método para correr esta interfaz de forma independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaMedico().setVisible(true);
        });
    }
}
