/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMedico;
import com.grupo2.sistemadegestionveterinaria.controlador.ControladorMedico;
import com.grupo2.sistemadegestionveterinaria.controlador.ControladorCita;

public class VistaMenuPrincipal extends JFrame {

  public JButton btnModuloMascota, btnModuloMedico, btnModuloCita, btnModuloAtencion, btnModuloFactura;

  public VistaMenuPrincipal() {
    setTitle("Sistema de Gestión Veterinaria - Menú Principal");
    setSize(550, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Panel Principal con márgenes (Padding)
    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 20));
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

    JLabel lblTitulo = new JLabel("Panel de Control Principal", SwingConstants.CENTER);
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
    panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

    // Panel Central con los botones separados
    JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 15));

    btnModuloMascota = new JButton("1. Gestión de Mascotas y Clientes");
    btnModuloMedico = new JButton("2. Gestión de Médicos");
    btnModuloCita = new JButton("3. Agendar Citas");
    btnModuloAtencion = new JButton("4. Atención Veterinaria");
    btnModuloFactura = new JButton("5. Facturación");

    panelBotones.add(btnModuloMascota);
    panelBotones.add(btnModuloMedico);
    panelBotones.add(btnModuloCita);
    panelBotones.add(btnModuloAtencion);
    panelBotones.add(btnModuloFactura);

    // Acciones de los botones para abrir las otras ventanas
    btnModuloMascota.addActionListener(e -> {

      setVisible(false);

      FrmMascota vista = new FrmMascota();

      vista.addWindowListener(new java.awt.event.WindowAdapter() {

        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {

          setVisible(true);
        }
      });

      vista.setVisible(true);
    });

    btnModuloMedico.addActionListener(e -> {

      setVisible(false);

      VistaMedico vista = new VistaMedico();

      ModeloMedico modelo = new ModeloMedico();

      ControladorMedico controlador
              = new ControladorMedico(
                      modelo,
                      vista
              );

      vista.addWindowListener(
              new java.awt.event.WindowAdapter() {

        @Override
        public void windowClosed(
                java.awt.event.WindowEvent e
        ) {

          setVisible(true);
        }
      });

      vista.setVisible(true);
    });

    btnModuloCita.addActionListener(e -> {

      setVisible(false);

      VistaCita vista = new VistaCita();

      vista.addWindowListener(new java.awt.event.WindowAdapter() {

        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {

          setVisible(true);
        }
      });

      vista.setVisible(true);
    });

    btnModuloAtencion.addActionListener(e -> new VistaAtencion().setVisible(true));
    //btnModuloFactura.addActionListener(e -> new VistaFacturacion().setVisible(true));

    panelPrincipal.add(panelBotones, BorderLayout.CENTER);
    add(panelPrincipal);
  }

  // Método para correr esta interfaz de forma independiente
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new VistaMenuPrincipal().setVisible(true);
    });
  }
}
