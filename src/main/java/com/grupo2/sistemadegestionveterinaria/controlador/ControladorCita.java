package com.grupo2.sistemadegestionveterinaria.controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.grupo2.sistemadegestionveterinaria.data.CitaDAO;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloCita;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMascota;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMedico;
import com.grupo2.sistemadegestionveterinaria.vista.VistaCita;

/**
 * Controlador para la gestión de citas médicas en el sistema. Conecta la
 * interfaz de usuario VistaCita con las operaciones de datos de CitaDAO,
 * gestionando el agendamiento, filtrado e impresión de reportes.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class ControladorCita {

    private final VistaCita vista;
    private final CitaDAO dao;
    private final ModeloCita modelo;
    private final ArrayList<ModeloCita> citasActuales;

    /**
     * Constructor del controlador. Vincula la vista de gestión de citas,
     * registra los eventos de interacción y carga los listados iniciales de
     * médicos, mascotas y citas.
     *
     * @param vista la ventana VistaCita que despliega los controles de la cita.
     */
    public ControladorCita(final VistaCita v) {
        this.vista = v; // Ya no hay conflicto de nombres
        this.dao = new CitaDAO();
        this.modelo = new ModeloCita();
        this.citasActuales = new ArrayList<>();

        iniciarEventos();
        cargarMedicos();
        cargarMascotas();
        cargarCitas();
        configurarSeleccionTabla();
    }

    private void iniciarEventos() {
        vista.btnAgendar.addActionListener(e -> agendarCita());
        vista.btnReprogramar.addActionListener(e -> reprogramarCita());
        vista.btnCancelar.addActionListener(e -> cancelarCita());
        vista.btnFiltrar.addActionListener(e -> filtrarCitas());
        vista.btnLimpiarFiltro.addActionListener(e -> limpiarFiltro());

        // Eventos de Reportes Unificados
        vista.btnReportes.addActionListener(e -> generarReporteAlterno());
        vista.btnExportarCSV.addActionListener(e -> exportarCSV());
        vista.btnImprimir.addActionListener(e -> imprimirReporte());
    }

    private void configurarSeleccionTabla() {
        vista.tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            int fila = vista.tablaCitas.getSelectedRow();
            if (fila != -1) {
                vista.txtFecha.setText(vista.tablaCitas.getValueAt(
                        fila, 3).toString());
                vista.txtHora.setText(vista.tablaCitas.getValueAt(
                        fila, 4).toString());
            }
        });
    }

    private void cargarMedicos() {
        try {
            ModeloMedico medicoModelo = new ModeloMedico();
            ArrayList<ModeloMedico> medicos = medicoModelo.listarMedicos();
            vista.cbxMedico.removeAllItems();
            vista.cbxMedico.addItem("(Seleccione un médico)");
            for (ModeloMedico medico : medicos) {
                String item = medico.getIdMedico() + " - " + medico.getNombres() + " " + medico.getApellidos();
                vista.cbxMedico.addItem(item);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
    }

    private void cargarMascotas() {
        try {
            ModeloMascota mascotaModelo = new ModeloMascota();
            ArrayList<ModeloMascota> mascotas = mascotaModelo.listarMascotas();
            vista.cbxMascota.removeAllItems();
            vista.cbxMascota.addItem("(Seleccione una mascota)");
            for (ModeloMascota mascota : mascotas) {
                String item = mascota.getId() + " - " + mascota.getNombre();
                vista.cbxMascota.addItem(item);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar mascotas: " + e.getMessage());
        }
    }

    private void cargarCitas() {
        citasActuales.clear();
        citasActuales.addAll(dao.listarCitas());
        actualizarTablaCitas(citasActuales);
    }

    private void actualizarTablaCitas(ArrayList<ModeloCita> citas) {
        Object[][] datos = new Object[citas.size()][6];
        for (int i = 0; i < citas.size(); i++) {
            ModeloCita cita = citas.get(i);
            datos[i][0] = cita.getId();
            datos[i][1] = cita.getMedicoId();
            datos[i][2] = cita.getMascotaId();
            datos[i][3] = cita.getFecha();
            datos[i][4] = cita.getHora();
            datos[i][5] = cita.getEstado();
        }
        vista.tablaCitas.setModel(new DefaultTableModel(
                datos,
                new String[]{"ID Cita", "Médico ID", "Mascota ID",
                    "Fecha", "Hora", "Estado"}
        ));
    }

    private void agendarCita() {
        try {
            String medicoSeleccionado = (String) vista.cbxMedico.getSelectedItem();
            String mascotaSeleccionada = (String) vista.cbxMascota.getSelectedItem();
            String fecha = vista.txtFecha.getText().trim();
            String hora = vista.txtHora.getText().trim();

            if (medicoSeleccionado.equals("(Seleccione un médico)")
                    || mascotaSeleccionada.equals(
                            "(Seleccione una mascota)") || fecha.isEmpty() || hora.isEmpty()) {
                JOptionPane.showMessageDialog(
                        vista, "Por favor complete todos los campos");
                return;
            }

            int medicoId = Integer.parseInt(medicoSeleccionado.split(" - ")[0]);
            int mascotaId = Integer.parseInt(mascotaSeleccionada.split(" - ")[0]);
            String fechaMySQL = convertirAFechaMySQL(fecha);

            if (!dao.verificarDisponibilidadMedico(
                    medicoId, fechaMySQL, hora, null)
                    || !dao.verificarDisponibilidadMascota(
                            mascotaId, fechaMySQL, hora, null)) {
                JOptionPane.showMessageDialog(
                        vista, "Conflicto de agenda: El médico o la mascota ya se encuentran ocupados.");
                return;
            }

            modelo.setMedicoId(medicoId);
            modelo.setMascotaId(mascotaId);
            modelo.setFecha(fechaMySQL);
            modelo.setHora(hora);
            modelo.setEstado("PROGRAMADA");

            if (dao.guardarCita(modelo)) {
                JOptionPane.showMessageDialog(
                        vista, "Cita agendada exitosamente");
                limpiarCampos();
                cargarCitas();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    vista, "Error: " + e.getMessage());
        }
    }

    private void reprogramarCita() {
        try {
            int fila = vista.tablaCitas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(
                        vista, "Seleccione una cita de la tabla para reprogramar");
                return;
            }

            String medicoSeleccionado = (String) vista.cbxMedico.getSelectedItem();
            String mascotaSeleccionada = (String) vista.cbxMascota.getSelectedItem();
            String fecha = vista.txtFecha.getText().trim();
            String hora = vista.txtHora.getText().trim();

            if (medicoSeleccionado.equals("(Seleccione un médico)")
                    || mascotaSeleccionada.equals("(Seleccione una mascota)") || fecha.isEmpty() || hora.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Faltan parámetros en los campos para procesar la reprogramación");
                return;
            }

            int idCita = Integer.parseInt(
                    vista.tablaCitas.getValueAt(fila, 0).toString());
            int medicoId = Integer.parseInt(medicoSeleccionado.split(" - ")[0]);
            int mascotaId = Integer.parseInt(
                    mascotaSeleccionada.split(" - ")[0]);
            String fechaMySQL = convertirAFechaMySQL(fecha);

            modelo.setId(idCita);
            modelo.setMedicoId(medicoId);
            modelo.setMascotaId(mascotaId);
            modelo.setFecha(fechaMySQL);
            modelo.setHora(hora);
            modelo.setEstado("REPROGRAMADA");

            if (dao.actualizarCita(modelo)) {
                JOptionPane.showMessageDialog(
                        vista, "Cita reprogramada exitosamente");
                limpiarCampos();
                cargarCitas();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    vista, "Error al reprogramar: " + e.getMessage());
        }
    }

    private void cancelarCita() {
        int fila = vista.tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    vista, "Seleccione la cita que desea dar de baja");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(
                vista, "¿Está seguro de cambiar el estado de la cita a CANCELADA?", "Confirmar Acción", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idCita = Integer.parseInt(
                    vista.tablaCitas.getValueAt(fila, 0).toString());
            if (dao.eliminarCita(idCita)) {
                JOptionPane.showMessageDialog(
                        vista, "Cita marcada como cancelada");
                cargarCitas();
            }
        }
    }

    private void filtrarCitas() {
        try {
            String fechaFiltro = vista.txtFiltroFecha.getText().trim();
            if (fechaFiltro.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese una fecha válida en el campo de filtrado");
                return;
            }
            String fechaMySQL = convertirAFechaMySQL(fechaFiltro);
            citasActuales.clear();
            citasActuales.addAll(dao.listarCitasPorFecha(fechaMySQL));
            actualizarTablaCitas(citasActuales);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error en filtrado: " + e.getMessage());
        }
    }

    private void limpiarFiltro() {
        vista.txtFiltroFecha.setText("");
        cargarCitas();
    }

    // --- SECCIÓN: REPORTES UNIFICADOS ---
    private void generarReporteAlterno() {
        try {
            String seleccion = (String) vista.cbxTipoReporte.getSelectedItem();
            switch (seleccion) {
                case "Histórico General":
                    cargarCitas();
                    int prog = dao.contarCitasPorEstado("PROGRAMADA");
                    int reprog = dao.contarCitasPorEstado("REPROGRAMADA");
                    int canc = dao.contarCitasPorEstado("CANCELADA");
                    vista.lblResumen.setText("Historial -> Totales Activos: Programadas: " + prog + " | Reprogramadas: " + reprog + " | Canceladas: " + canc);
                    break;

                case "Citas del Día":
                    String f = vista.txtFecha.getText().trim();
                    if (f.isEmpty()) {
                        JOptionPane.showMessageDialog(vista, "Escriba una fecha en el formulario principal (DD/MM/YYYY) para generar este reporte");
                        return;
                    }
                    citasActuales.clear();
                    citasActuales.addAll(dao.listarCitasPorFecha(convertirAFechaMySQL(f)));
                    actualizarTablaCitas(citasActuales);
                    vista.lblResumen.setText("Reporte Diario: " + citasActuales.size() + " registros hallados para " + f);
                    break;

                case "Citas por Veterinario":
                    String med = (String) vista.cbxMedico.getSelectedItem();
                    if (med.equals("(Seleccione un médico)")) {
                        JOptionPane.showMessageDialog(vista, "Seleccione un veterinario en el combo desplegable");
                        return;
                    }
                    int medicoId = Integer.parseInt(med.split(" - ")[0]);
                    citasActuales.clear();
                    citasActuales.addAll(dao.obtenerCitasPorMedico(medicoId));
                    actualizarTablaCitas(citasActuales);
                    vista.lblResumen.setText("Citas asignadas al Médico (" + med + "): " + citasActuales.size() + " registros.");
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al generar el reporte: Compruebe el formato de la fecha (DD/MM/YYYY). " + e.getMessage());
        }
    }

    private void exportarCSV() {
        if (citasActuales.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No existen registros en la visualización actual para guardar.");
            return;
        }
        try {
            String Archivo = "Reporte_Citas_Veterinaria.csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(Archivo));
            writer.write("ID Cita,Medico ID,Mascota ID,Fecha,Hora,Estado");
            writer.newLine();
            for (ModeloCita c : citasActuales) {
                writer.write(c.getId() + "," + c.getMedicoId() + "," + c.getMascotaId() + "," + c.getFecha() + "," + c.getHora() + "," + c.getEstado());
                writer.newLine();
            }
            writer.close();
            JOptionPane.showMessageDialog(vista, "¡Reporte guardado con éxito como CSV en la raíz del proyecto! Archivo: " + Archivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al escribir el archivo: " + e.getMessage());
        }
    }

    private void imprimirReporte() {
        try {
            MessageFormat header = new MessageFormat("Reporte del Sistema de Gestión Veterinaria - Citas");
            MessageFormat footer = new MessageFormat("Página {0} - Control de Reportes Clínicos");
            boolean completado = vista.tablaCitas.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            if (completado) {
                JOptionPane.showMessageDialog(vista, "Documento enviado con éxito a la cola de impresión / Guardado en PDF");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error de impresión: " + e.getMessage());
        }
    }

    // Auxiliar de conversión de formatos de fecha
    private String convertirAFechaMySQL(String fechaOrigen) throws Exception {
        String[] partes = fechaOrigen.split("/");
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }

    private void limpiarCampos() {
        vista.cbxMedico.setSelectedIndex(0);
        vista.cbxMascota.setSelectedIndex(0);
        vista.txtFecha.setText("");
        vista.txtHora.setText("");
    }
}
