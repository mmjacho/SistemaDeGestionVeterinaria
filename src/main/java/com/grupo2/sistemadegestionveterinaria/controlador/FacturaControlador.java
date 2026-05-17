package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.modelo.Factura;
import com.grupo2.sistemadegestionveterinaria.modelo.DetalleFactura;
import com.grupo2.sistemadegestionveterinaria.data.FacturaDAO;
import com.grupo2.sistemadegestionveterinaria.vista.VentanaFacturacion;
import com.grupo2.sistemadegestionveterinaria.vista.VentanaHistorial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacturaControlador {

  private VentanaFacturacion vista;
  private FacturaDAO dao;
  private List<DetalleFactura> listaDetalles;

  public FacturaControlador(VentanaFacturacion vista, FacturaDAO dao) {
    this.vista = vista;
    this.dao = dao;
    this.listaDetalles = new ArrayList<>();
    inicializarEventos();
  }

  private void inicializarEventos() {
    // Evento para agregar un ítem a la tabla
    vista.btnAgregar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        agregarItem();
      }
    });

    // Evento para eliminar un ítem seleccionado de la tabla de entrada
    vista.btnEliminar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        eliminarItem();
      }
    });

    // Evento para guardar la factura en la base de datos
    vista.btnFacturar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          procesarFactura();
        } catch (Exception ex) {
          Logger.getLogger(FacturaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });

    // Evento para abrir la ventana del historial completo (Lectura, Edición, Eliminación CRUD)
    vista.btnVerHistorial.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        abrirHistorial();
      }
    });
  }

  private void agregarItem() {
    try {
      String concepto = vista.cbConcepto.getSelectedItem().toString();
      double precioUnitario = Double.parseDouble(vista.txtPrecio.getText().trim());
      int cantidad = (int) vista.spnCantidad.getValue();

      // Validación de control básica
      if (precioUnitario <= 0) {
        JOptionPane.showMessageDialog(vista, "El precio debe ser un número mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      double precioFinal = precioUnitario * cantidad;

      // Guardar temporalmente en la lista del modelo
      DetalleFactura detalle = new DetalleFactura(concepto, precioUnitario, cantidad, precioFinal);
      listaDetalles.add(detalle);

      // Mostrar en la interfaz visual
      Object[] fila = {concepto, String.format("%.2f", precioUnitario), cantidad, String.format("%.2f", precioFinal)};
      vista.modeloTabla.addRow(fila);

      recalcularTotales();

    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(vista, "Por favor, ingresa un precio numérico válido (Ej: 15.50).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void eliminarItem() {
    int filaSeleccionada = vista.tblDetalles.getSelectedRow();
    if (filaSeleccionada >= 0) {
      listaDetalles.remove(filaSeleccionada);
      vista.modeloTabla.removeRow(filaSeleccionada);
      recalcularTotales();
    } else {
      JOptionPane.showMessageDialog(vista, "Selecciona un ítem de la tabla para poder eliminarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
  }

  // --- LÓGICA DE CÁLCULO EN CADENA PARA EL TESTING DU-CADENA ---
  public void recalcularTotales() {
    double acumSubtotal = 0.0;
    double calcIva = 0.0;
    double calcTotal = 0.0;

    // Se recorren los detalles calculando la suma inductiva
    for (DetalleFactura det : listaDetalles) {
      acumSubtotal += det.getPrecioFinal();
    }

    calcIva = acumSubtotal * 0.15; // IVA 15% vigente local
    calcTotal = acumSubtotal + calcIva;

    // Se actualizan las cajas de texto de la vista
    vista.txtSubtotal.setText(String.format("%.2f", acumSubtotal));
    vista.txtIva.setText(String.format("%.2f", calcIva));
    vista.txtTotal.setText(String.format("%.2f", calcTotal));
  }

  private void procesarFactura() throws Exception {
    String nombre = vista.txtNombre.getText().trim();
    String cedula = vista.txtCedula.getText().trim();

    if (nombre.isEmpty() || cedula.isEmpty()) {
      JOptionPane.showMessageDialog(vista, "Los campos del cliente son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
      return;
    }

    if (listaDetalles.isEmpty()) {
      JOptionPane.showMessageDialog(vista, "No puedes generar una factura sin agregar al menos un ítem.", "Sin Detalles", JOptionPane.WARNING_MESSAGE);
      return;
    }

    double subtotal = Double.parseDouble(vista.txtSubtotal.getText().replace(",", "."));
    double iva = Double.parseDouble(vista.txtIva.getText().replace(",", "."));
    double total = Double.parseDouble(vista.txtTotal.getText().replace(",", "."));

    // Crear objeto Factura para persistir
    Factura factura = new Factura(nombre, cedula, subtotal, iva, total);

    boolean exito = dao.registrarFacturaCompleta(factura, listaDetalles);

    if (exito) {
      JOptionPane.showMessageDialog(vista, "¡Factura guardada con éxito en la base de datos!", "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
      limpiarFormulario();
    } else {
      JOptionPane.showMessageDialog(vista, "Hubo un error interno al intentar guardar en la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
    }
  }

  // --- GESTIÓN INTERNA DEL HISTORIAL COMPLETO (READ, UPDATE, DELETE del CRUD) ---
  private void abrirHistorial() {
    VentanaHistorial vh = new VentanaHistorial(vista);

    // Expresión Lambda interna para refrescar los datos de la tabla desde MySQL
    Runnable cargarTabla;
    cargarTabla = () -> {
      vh.modeloHistorial.setRowCount(0);
      List<Factura> facturas = null;
      try {
        facturas = dao.obtenerTodasLasFacturas();
      } catch (Exception ex) {
        Logger.getLogger(FacturaControlador.class.getName()).log(Level.SEVERE, null, ex);
      }
      for (Factura f : facturas) {
        Object[] fila = {
          f.getIdFactura(),
          f.getNombreCliente(),
          f.getCedulaCliente(),
          f.getFechaEmision(),
          String.format("%.2f", f.getSubtotal()),
          String.format("%.2f", f.getIva()),
          String.format("%.2f", f.getTotal())
        };
        vh.modeloHistorial.addRow(fila);
      }
    };

    // Evento: Botón Recargar
    vh.btnActualizarLista.addActionListener(e -> cargarTabla.run());

    // Evento CRUD - ELIMINAR (Delete)
    vh.btnEliminarFactura.addActionListener(e -> {
      int fila = vh.tblHistorial.getSelectedRow();
      if (fila >= 0) {
        int id = (int) vh.tblHistorial.getValueAt(fila, 0);
        int seguro = JOptionPane.showConfirmDialog(vh, "¿Seguro que deseas eliminar permanentemente la factura N° " + id + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (seguro == JOptionPane.YES_OPTION) {
          try {
            if (dao.eliminarFactura(id)) {
              JOptionPane.showMessageDialog(vh, "La factura y sus detalles asociados fueron eliminados.", "Registro Borrado", JOptionPane.INFORMATION_MESSAGE);
              cargarTabla.run();
            } else {
              JOptionPane.showMessageDialog(vh, "No se pudo eliminar el registro de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
          } catch (Exception ex) {
            Logger.getLogger(FacturaControlador.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      } else {
        JOptionPane.showMessageDialog(vh, "Por favor, selecciona una factura de la lista para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
      }
    });

    // Evento CRUD - ACTUALIZAR (Update)
    vh.btnModificar.addActionListener(e -> {
      int fila = vh.tblHistorial.getSelectedRow();
      if (fila >= 0) {
        int id = (int) vh.tblHistorial.getValueAt(fila, 0);
        String nombreActual = vh.tblHistorial.getValueAt(fila, 1).toString();
        String cedulaActual = vh.tblHistorial.getValueAt(fila, 2).toString();

        String nuevoNombre = JOptionPane.showInputDialog(vh, "Modificar Nombre del Dueño:", nombreActual);
        String nuevaCedula = JOptionPane.showInputDialog(vh, "Modificar Cédula / RUC:", cedulaActual);

        // Validar que el usuario no haya cancelado o dejado vacío
        if (nuevoNombre != null && nuevaCedula != null && !nuevoNombre.trim().isEmpty() && !nuevaCedula.trim().isEmpty()) {
          try {
            if (dao.actualizarFactura(id, nuevoNombre.trim(), nuevaCedula.trim())) {
              JOptionPane.showMessageDialog(vh, "Datos de la cabecera actualizados con éxito.", "Registro Modificado", JOptionPane.INFORMATION_MESSAGE);
              cargarTabla.run();
            } else {
              JOptionPane.showMessageDialog(vh, "Error interno al intentar actualizar los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
          } catch (Exception ex) {
            Logger.getLogger(FacturaControlador.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      } else {
        JOptionPane.showMessageDialog(vh, "Por favor, selecciona una factura para poder modificarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
      }
    });

    // Cargar los datos la primera vez que se renderiza el diálogo modal
    cargarTabla.run();
    vh.setVisible(true);
  }

  private void limpiarFormulario() {
    vista.txtNombre.setText("");
    vista.txtCedula.setText("");
    vista.txtPrecio.setText("0.00");
    vista.spnCantidad.setValue(1);
    listaDetalles.clear();

    DefaultTableModel model = (DefaultTableModel) vista.tblDetalles.getModel();
    model.setRowCount(0);

    vista.txtSubtotal.setText("0.00");
    vista.txtIva.setText("0.00");
    vista.txtTotal.setText("0.00");
  }
}
