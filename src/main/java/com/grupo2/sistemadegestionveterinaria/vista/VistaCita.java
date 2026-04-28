/**
 *
 * @author Ruben Quiroga
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;

public class VistaCita extends JFrame {
    
    public JComboBox<String> cbxMedico, cbxMascota;
    public JTextField txtFecha, txtHora; 
    public JButton btnAgendar, btnBuscar, btnReprogramar;
    public JTable tablaCitas;

    public VistaCita() {
        setTitle("Módulo: Agenda de Citas");
        setSize(750, 450);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelCentro = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCentro.add(new JLabel("Seleccione Médico:")); cbxMedico = new JComboBox<>(new String[]{"(Seleccione un médico)"}); panelCentro.add(cbxMedico);
        panelCentro.add(new JLabel("Seleccione Mascota:")); cbxMascota = new JComboBox<>(new String[]{"(Seleccione una mascota)"}); panelCentro.add(cbxMascota);
        panelCentro.add(new JLabel("Fecha (DD/MM/YYYY):")); txtFecha = new JTextField(); panelCentro.add(txtFecha);
        panelCentro.add(new JLabel("Hora (HH:MM):")); txtHora = new JTextField(); panelCentro.add(txtHora);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnAgendar = new JButton("Agendar Cita"); btnBuscar = new JButton("Buscar Citas"); btnReprogramar = new JButton("Reprogramar");
        panelSur.add(btnAgendar); panelSur.add(btnBuscar); panelSur.add(btnReprogramar);

        panelPrincipal.add(panelCentro, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaCitas = new JTable(new Object[][]{}, new String[]{"ID Cita", "Médico", "Mascota", "Fecha", "Hora", "Estado"})), BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaCita().setVisible(true);
        });
    }
}
