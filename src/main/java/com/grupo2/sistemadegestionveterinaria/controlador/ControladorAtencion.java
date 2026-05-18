/**
 *
 * @author Mario Jacho
 */
package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.data.AtencionDAO;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;
import com.grupo2.sistemadegestionveterinaria.vista.VistaAtencion;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorAtencion {

    private final VistaAtencion vista;
    private final ModeloAtencion modelo;
    private final AtencionDAO dao;

    public ControladorAtencion(VistaAtencion vista, ModeloAtencion modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.dao = new AtencionDAO();
        configurarListeners();
    }

    private void configurarListeners() {
        this.vista.btnRegistrarAtencion.addActionListener(e -> ejecutarRegistro());
        this.vista.btnBuscarHistorial.addActionListener(e -> ejecutarBusquedaHistorial());
        this.vista.btnActualizar.addActionListener(e -> ejecutarActualizacion());
    }

    private void ejecutarRegistro() {
        try {
            if (vista.txtIdCita.getText().trim().isEmpty() || vista.txtTemperatura.getText().trim().isEmpty() || vista.txtPeso.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos numéricos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String strIdCita = vista.txtIdCita.getText().replaceAll("\\s+", "");
            String strTemp = vista.txtTemperatura.getText().replaceAll("\\s+", "").replace(",", ".");
            String strPeso = vista.txtPeso.getText().replaceAll("\\s+", "").replace(",", ".");

            int idCita = Integer.parseInt(strIdCita);

            if (!dao.existeCita(idCita)) {
                JOptionPane.showMessageDialog(vista, "El ID de cita provisto no existe en la agenda global.", "Fallo de Integración", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double temperatura = Double.parseDouble(strTemp);
            if (temperatura < 35.0 || temperatura > 42.0) {
                JOptionPane.showMessageDialog(vista, "Temperatura fuera de rango clínico válido (35.0°C - 42.0°C).", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double peso = Double.parseDouble(strPeso);
            if (peso <= 0) {
                JOptionPane.showMessageDialog(vista, "El peso debe ser mayor a 0 Kg.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String diagnostico = vista.txtDiagnostico.getText().trim();
            String receta = vista.txtReceta.getText().trim();

            if (diagnostico.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El campo de diagnóstico no puede quedar vacío.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelo.setIdCita(idCita);
            modelo.setTemperatura(temperatura);
            modelo.setPesoActual(peso);
            modelo.setDiagnostico(diagnostico);
            modelo.setReceta(receta);

            if (dao.registrarAtencion(modelo)) {
                JOptionPane.showMessageDialog(vista, "Ficha de atención clínica almacenada correctamente.");
                ejecutarBusquedaHistorial(); // Refresca la tabla automáticamente
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar. Verifique si la cita ya posee una atención registrada.", "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Verifique los campos numéricos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarBusquedaHistorial() {
        try {
            if (vista.txtIdCita.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese el ID de la Cita para cargar el historial.");
                return;
            }

            int idCita = Integer.parseInt(vista.txtIdCita.getText().trim());
            int idMascota = dao.obtenerIdMascotaPorCita(idCita);

            if (idMascota == -1) {
                JOptionPane.showMessageDialog(vista, "No se encontró una mascota asociada a este ID de Cita.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<ModeloAtencion> historial = dao.obtenerHistorialPorMascota(idMascota);
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.tablaHistorial.getModel();
            modeloTabla.setRowCount(0); // Limpia la tabla

            for (ModeloAtencion atencion : historial) {
                // Adaptamos los datos a tus 4 columnas: {"Fecha(ID Atención)", "Diagnóstico", "Temp", "Peso"}
                Object[] fila = {
                    atencion.getIdAtencion(), // Lo ponemos en la primera columna para usarlo al actualizar
                    atencion.getDiagnostico(),
                    atencion.getTemperatura() + " °C",
                    atencion.getPesoActual() + " Kg"
                };
                modeloTabla.addRow(fila);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID de la Cita debe ser numérico.");
        }
    }

    private void ejecutarActualizacion() {
        int filaSeleccionada = vista.tablaHistorial.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un registro de la tabla para actualizar el diagnóstico.");
            return;
        }

        // Recuperamos el ID de Atención de la primera columna
        int idAtencion = (int) vista.tablaHistorial.getValueAt(filaSeleccionada, 0);
        String nuevoDiagnostico = vista.txtDiagnostico.getText().trim();
        String nuevaReceta = vista.txtReceta.getText().trim();

        if (nuevoDiagnostico.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Escriba el nuevo diagnóstico en el campo de texto antes de actualizar.");
            return;
        }

        if (dao.actualizarDiagnostico(idAtencion, nuevoDiagnostico, nuevaReceta)) {
            JOptionPane.showMessageDialog(vista, "Diagnóstico y Receta actualizados con éxito.");
            ejecutarBusquedaHistorial(); // Refresca la tabla
        } else {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error al intentar actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}