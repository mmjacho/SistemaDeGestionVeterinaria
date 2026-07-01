package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Diálogo modal que despliega el historial completo de facturas emitidas.
 * Ofrece controles visuales para realizar operaciones CRUD (visualización,
 * edición y eliminación) sobre las facturas registradas.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class VentanaHistorial extends javax.swing.JDialog {

  /**
   * Tabla gráfica para mostrar la lista de facturas.
   */
  public JTable tblHistorial;
  /**
   * Modelo de datos para estructurar las filas de la tabla de facturas.
   */
  public DefaultTableModel modeloHistorial;
  /**
   * Botón para modificar los datos de cabecera de la factura.
   */
  public JButton btnModificar;
  /**
   * Botón para eliminar físicamente la factura seleccionada.
   */
  public JButton btnEliminarFactura;
  /**
   * Botón para recargar y refrescar los datos desde la base de datos.
   */
  public JButton btnActualizarLista;

  /**
   * Constructor de VentanaHistorial.
   * Inicializa la interfaz de usuario, define el marco padre y establece
   * el comportamiento modal para el control del historial.
   *
   * @param padre la ventana Frame padre que invoca a este diálogo.
   */
  public VentanaHistorial(Frame padre) {
    super(padre, "Historial de Facturas Emitidas (CRUD)", true);
    initComponents();
    this.setLocationRelativeTo(padre);
    this.setSize(700, 400);
  }

  private void initComponents() {
    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Tabla
    String[] columnas = {"ID Factura", "Cliente", "Cédula/RUC", "Fecha", "Subtotal", "IVA", "Total"};
    modeloHistorial = new DefaultTableModel(columnas, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    tblHistorial = new JTable(modeloHistorial);
    panelPrincipal.add(new JScrollPane(tblHistorial), BorderLayout.CENTER);

    // Botones de acción laterales
    JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));

    btnActualizarLista = new JButton("🔄 Recargar");
    btnModificar = new JButton("✏️ Editar Datos");
    btnEliminarFactura = new JButton("❌ Borrar Factura");

    btnModificar.setBackground(new Color(255, 193, 7)); // Amarillo
    btnEliminarFactura.setBackground(new Color(220, 53, 69)); // Rojo
    btnEliminarFactura.setForeground(Color.BLACK);

    panelBotones.add(btnActualizarLista);
    panelBotones.add(btnModificar);
    panelBotones.add(btnEliminarFactura);

    panelPrincipal.add(panelBotones, BorderLayout.EAST);
    this.add(panelPrincipal);
  }
}
