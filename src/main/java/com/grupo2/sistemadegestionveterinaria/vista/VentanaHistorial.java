package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaHistorial extends javax.swing.JDialog {

  public JTable tblHistorial;
  public DefaultTableModel modeloHistorial;
  public JButton btnModificar;
  public JButton btnEliminarFactura;
  public JButton btnActualizarLista;

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
