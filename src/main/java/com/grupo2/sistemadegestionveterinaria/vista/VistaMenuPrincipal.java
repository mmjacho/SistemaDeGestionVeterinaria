/**
 *
 * @author Grupo 2
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMedico;
import com.grupo2.sistemadegestionveterinaria.controlador.ControladorMedico;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;       // Importado para el MVC
import com.grupo2.sistemadegestionveterinaria.controlador.ControladorAtencion; // Importado para el MVC
import com.grupo2.sistemadegestionveterinaria.controlador.ControladorCita;

/**
 * Ventana gráfica que actúa como menú principal y panel de control.
 * Facilita el acceso directo a los distintos módulos operativos del sistema,
 * como la gestión de mascotas, médicos, citas, atenciones y facturación.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class VistaMenuPrincipal extends JFrame {

  /**
   * Botones de navegación para ingresar a los módulos de mascotas, médicos,
   * citas, atenciones clínicas y facturación.
   */
  public JButton btnModuloMascota, btnModuloMedico, btnModuloCita, btnModuloAtencion, btnModuloFactura;

  /**
   * Constructor de VistaMenuPrincipal.
   * Diseña el contenedor principal, inicializa los botones de navegación
   * y configura los disparadores de eventos para abrir cada módulo.
   */
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
      ControladorMedico controlador = new ControladorMedico(modelo, vista);

      vista.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
          setVisible(true);
        }
      });
      vista.setVisible(true);
    });

    btnModuloCita.addActionListener(e -> {
      setVisible(false);
      VistaCita vista = new VistaCita();
      ControladorCita controlador = new ControladorCita(vista);
      vista.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
          setVisible(true);
        }
      });
      vista.setVisible(true);
    });

    // CORREGIDO: Inyección completa del circuito MVC para Atención Veterinaria
    btnModuloAtencion.addActionListener(e -> {
      setVisible(false); // Oculta el menú principal temporalmente

      VistaAtencion vistaAtencion = new VistaAtencion();
      ModeloAtencion modeloAtencion = new ModeloAtencion();
      
      // El controlador internamente se encarga de acoplar todo y manejar los eventos de la 'X'
      ControladorAtencion controlador = new ControladorAtencion(vistaAtencion, modeloAtencion);

      // Listener de seguridad por si el controlador usara windowClosed en lugar de windowClosing
      vistaAtencion.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
          setVisible(true);
        }
      });

      vistaAtencion.setVisible(true);
    });

    btnModuloFactura.addActionListener(e -> {
      setVisible(false);
      VentanaFacturacion vista = new VentanaFacturacion();
      vista.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
          setVisible(true);
        }
      });
      vista.setVisible(true);
    });

    panelPrincipal.add(panelBotones, BorderLayout.CENTER);
    add(panelPrincipal);
  }

  /**
   * Método principal para iniciar la interfaz gráfica del menú principal.
   *
   * @param args los argumentos de la línea de comandos.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new VistaMenuPrincipal().setVisible(true);
    });
  }
}
