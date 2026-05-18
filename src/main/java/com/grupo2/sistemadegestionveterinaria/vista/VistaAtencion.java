/**
 *
 * @author Mario Jacho
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;

public class VistaAtencion extends JFrame {
    
    public JTextField txtIdCita, txtTemperatura, txtPeso;
    public JTextArea txtDiagnostico, txtReceta;
    public JButton btnRegistrarAtencion, btnBuscarHistorial, btnActualizar;
    public JTable tablaHistorial;

    public VistaAtencion() {
        setTitle("Módulo: Registro de Atención Veterinaria");
        setSize(850, 650);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Búsqueda de Cita
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelNorte.add(new JLabel("ID de Cita Médica:"));
        txtIdCita = new JTextField(15); panelNorte.add(txtIdCita);
        btnBuscarHistorial = new JButton("Cargar Cita / Historial"); panelNorte.add(btnBuscarHistorial);

        // Formulario Clínico
        JPanel panelCentro = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCentro.add(new JLabel("Temperatura (°C):")); txtTemperatura = new JTextField(); panelCentro.add(txtTemperatura);
        panelCentro.add(new JLabel("Peso Actual (Kg):")); txtPeso = new JTextField(); panelCentro.add(txtPeso);
        panelCentro.add(new JLabel("Diagnóstico Clínico:")); 
        txtDiagnostico = new JTextArea(3, 20); txtDiagnostico.setLineWrap(true); panelCentro.add(new JScrollPane(txtDiagnostico));
        panelCentro.add(new JLabel("Receta Médica:")); 
        txtReceta = new JTextArea(3, 20); txtReceta.setLineWrap(true); panelCentro.add(new JScrollPane(txtReceta));

        // Agrupamos el buscador y el formulario en un solo bloque superior
        JPanel panelSuperiorAgrupado = new JPanel(new BorderLayout(0, 15));
        panelSuperiorAgrupado.add(panelNorte, BorderLayout.NORTH);
        panelSuperiorAgrupado.add(panelCentro, BorderLayout.CENTER);

        // Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnRegistrarAtencion = new JButton("Registrar Atención"); btnActualizar = new JButton("Actualizar Diagnóstico");
        panelBotones.add(btnRegistrarAtencion); panelBotones.add(btnActualizar);

        panelPrincipal.add(panelSuperiorAgrupado, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaHistorial = new JTable(new Object[][]{}, new String[]{"Fecha", "Diagnóstico", "Temp", "Peso"})), BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaAtencion().setVisible(true);
        });
    }
}
