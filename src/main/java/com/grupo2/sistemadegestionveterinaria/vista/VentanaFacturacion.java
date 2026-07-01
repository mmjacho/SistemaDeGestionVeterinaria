package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana de la interfaz gráfica (JFrame) para el módulo de facturación
 * del sistema de gestión veterinaria.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class VentanaFacturacion extends javax.swing.JFrame {

  // Componentes que usará el Controlador (públicos para facilitar el acceso)
  /** Botón para visualizar el historial y realizar operaciones CRUD. */
  public JButton btnVerHistorial;
  /** Campo de texto para ingresar el nombre del cliente. */
  public JTextField txtNombre;
  /** Campo de texto para ingresar la cédula o RUC del cliente. */
  public JTextField txtCedula;
  /** Selector desplegable con los conceptos o servicios a facturar. */
  public JComboBox<String> cbConcepto;
  /** Campo de texto para ingresar el precio unitario del concepto. */
  public JTextField txtPrecio;
  /** Selector numérico para definir la cantidad a facturar. */
  public JSpinner spnCantidad;
  /** Botón para agregar un concepto a la lista de detalles. */
  public JButton btnAgregar;
  /** Botón para eliminar un ítem seleccionado de los detalles. */
  public JButton btnEliminar;
  /** Tabla que muestra los detalles actuales de la facturación. */
  public JTable tblDetalles;
  /** Modelo de datos para la estructura y contenido de la tabla. */
  public DefaultTableModel modeloTabla;
  /** Campo de texto no editable que muestra el subtotal calculado. */
  public JTextField txtSubtotal;
  /** Campo de texto no editable que muestra el IVA calculado. */
  public JTextField txtIva;
  /** Campo de texto no editable que muestra el total final a pagar. */
  public JTextField txtTotal;
  /** Botón para procesar, guardar y finalizar la factura actual. */
  public JButton btnFacturar;

  /**
   * Constructor de VentanaFacturacion. Inicializa la interfaz gráfica
   * manual de facturación y configura las propiedades del JFrame.
   */
  public VentanaFacturacion() {
    initComponentsManual();
    this.setTitle("Sistema de Gestión Veterinaria - Módulo de Facturación");
    this.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
  }

  private void initComponentsManual() {
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);

    // Panel Principal con Margen
    JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    panelPrincipal.setBackground(new Color(245, 245, 245));

    // --- 1. Panel Superior: Datos del Cliente ---
    JPanel panelCliente = new JPanel(new GridLayout(2, 2, 10, 10));
    panelCliente.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
    panelCliente.setBackground(Color.WHITE);

    panelCliente.add(new JLabel(" Nombre del Dueño:"));
    txtNombre = new JTextField();
    panelCliente.add(txtNombre);

    panelCliente.add(new JLabel(" Cédula / RUC:"));
    txtCedula = new JTextField();
    panelCliente.add(txtCedula);

    // --- 2. Panel Central-Superior: Ingreso de Servicios/Medicinas ---
    JPanel panelIngreso = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
    panelIngreso.setBorder(BorderFactory.createTitledBorder("Agregar Concepto a Facturar"));
    panelIngreso.setBackground(Color.WHITE);

    panelIngreso.add(new JLabel("Concepto:"));
    cbConcepto = new JComboBox<>(new String[]{
      "Consulta Veterinaria General",
      "Vacunación / Desparasitación",
      "Medicamento - Antibiótico",
      "Medicamento - Vitaminas",
      "Servicio de Peluquería Canina"
    });
    panelIngreso.add(cbConcepto);

    panelIngreso.add(new JLabel("Precio ($):"));
    txtPrecio = new JTextField("0.00", 5);
    panelIngreso.add(txtPrecio);

    panelIngreso.add(new JLabel("Cant:"));
    spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    panelIngreso.add(spnCantidad);

    btnAgregar = new JButton("Agregar");
    btnAgregar.setBackground(new Color(40, 167, 69));
    btnAgregar.setForeground(Color.BLACK);
    btnAgregar.setFont(new Font("SansSerif", Font.BOLD, 12));
    panelIngreso.add(btnAgregar);

    btnEliminar = new JButton("Eliminar Ítem");
    btnEliminar.setBackground(new Color(220, 53, 69));
    btnEliminar.setForeground(Color.BLACK);
    btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 12));
    panelIngreso.add(btnEliminar);

    // Contenedor para juntar los dos paneles de arriba
    JPanel contenedorTop = new JPanel(new BorderLayout(10, 10));
    contenedorTop.add(panelCliente, BorderLayout.NORTH);
    contenedorTop.add(panelIngreso, BorderLayout.CENTER);
    panelPrincipal.add(contenedorTop, BorderLayout.NORTH);

    // --- 3. Panel Central: Tabla de Detalles ---
    String[] columnas = {"Concepto", "Precio Unitario", "Cantidad", "Precio Final"};
    modeloTabla = new DefaultTableModel(columnas, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      } // Celdas no editables
    };
    tblDetalles = new JTable(modeloTabla);
    JScrollPane scrollTabla = new JScrollPane(tblDetalles);
    panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

    // --- 4. Panel Inferior: Totales y Botón de Acción ---
    JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
    panelInferior.setBackground(Color.WHITE);
    panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Subpanel Izquierdo para el botón de Ver Historial
    JPanel panelAccionesIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelAccionesIzquierda.setBackground(Color.WHITE);
    btnVerHistorial = new JButton("📋 Ver Historial (CRUD)");
    btnVerHistorial.setBackground(new Color(108, 117, 125));
    btnVerHistorial.setForeground(Color.BLACK);
    panelAccionesIzquierda.add(btnVerHistorial);

    // Subpanel Derecho para los campos matemáticos
    JPanel panelTotales = new JPanel(new GridLayout(3, 2, 5, 5));
    panelTotales.setBackground(Color.WHITE);

    panelTotales.add(new JLabel("Subtotal: $", SwingConstants.RIGHT));
    txtSubtotal = new JTextField("0.00", 8);
    txtSubtotal.setEditable(false);
    txtSubtotal.setHorizontalAlignment(JTextField.RIGHT);
    panelTotales.add(txtSubtotal);

    // Tasa local vigente ajustada a los lineamientos tributarios
    panelTotales.add(new JLabel("IVA (15%): $", SwingConstants.RIGHT));
    txtIva = new JTextField("0.00", 8);
    txtIva.setEditable(false);
    txtIva.setHorizontalAlignment(JTextField.RIGHT);
    panelTotales.add(txtIva);

    panelTotales.add(new JLabel("Total a Pagar: $", SwingConstants.RIGHT));
    txtTotal = new JTextField("0.00", 8);
    txtTotal.setEditable(false);
    txtTotal.setFont(new Font("SansSerif", Font.BOLD, 12));
    txtTotal.setHorizontalAlignment(JTextField.RIGHT);
    panelTotales.add(txtTotal);

    btnFacturar = new JButton("Procesar y Guardar Factura");
    btnFacturar.setFont(new Font("SansSerif", Font.BOLD, 14));
    btnFacturar.setBackground(new Color(0, 123, 255));
    btnFacturar.setForeground(Color.BLACK);

    // Agregando subpaneles al panel inferior
    panelInferior.add(panelAccionesIzquierda, BorderLayout.WEST);
    panelInferior.add(btnFacturar, BorderLayout.CENTER);
    panelInferior.add(panelTotales, BorderLayout.EAST);

    panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

    // Agregar todo al marco
    getContentPane().add(panelPrincipal);
    pack();
    setSize(850, 530); // Tamaño ampliado para asegurar visualización de botones
  }
}
