/**
 *
 * @author Ruben Quiroga
 */
package com.grupo2.sistemadegestionveterinaria.controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.grupo2.sistemadegestionveterinaria.data.ReporteCitaDAO;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloCita;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMedico;
import com.grupo2.sistemadegestionveterinaria.vista.VistaReporteCitas;

public class ControladorReporteCitas {

    private VistaReporteCitas vista;
    private ReporteCitaDAO dao;
    private ArrayList<ModeloCita> citasActuales;

    public ControladorReporteCitas(VistaReporteCitas vista) {
        this.vista = vista;
        this.dao = new ReporteCitaDAO();
        this.citasActuales = new ArrayList<>();
        iniciarEventos();
        cargarMedicos();
        generarHistoricoEstados();
    }

    private void iniciarEventos() {
        vista.btnGenerar.addActionListener(e -> generarReporte());
        vista.btnExportarCSV.addActionListener(e -> exportarCSV());
        vista.btnCerrar.addActionListener(e -> vista.dispose());
    }

    private void cargarMedicos() {
        try {
            ModeloMedico medicoModelo = new ModeloMedico();
            ArrayList<ModeloMedico> medicos = medicoModelo.listarMedicos();
            vista.cbxMedico.removeAllItems();
            vista.cbxMedico.addItem("(Todos)");
            for (ModeloMedico medico : medicos) {
                String item = medico.getIdMedico() + " - " + medico.getNombres() + " " + medico.getApellidos();
                vista.cbxMedico.addItem(item);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
    }

    private void generarReporte() {
        try {
            String tipoReporte = (String) vista.cbxTipoReporte.getSelectedItem();
            citasActuales.clear();

            switch (tipoReporte) {
                case "Citas del Día":
                    generarReporteDelDia();
                    break;
                case "Citas por Veterinario":
                    generarReportePorVeterinario();
                    break;
                case "Histórico de Estados":
                    generarHistoricoEstados();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al generar reporte: " + e.getMessage());
        }
    }

    private void generarReporteDelDia() {
        String fecha = vista.txtFecha.getText().trim();
        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese una fecha para el reporte del día");
            return;
        }

        try {
            String[] partesFecha = fecha.split("/");
            String fechaMySQL = partesFecha[2] + "-" + partesFecha[1] + "-" + partesFecha[0];
            citasActuales = dao.obtenerCitasDelDia(fechaMySQL);
            actualizarTabla(citasActuales);
            vista.lblResumen.setText("Resumen: " + citasActuales.size() + " citas para el día " + fecha);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error en formato de fecha. Use DD/MM/YYYY");
        }
    }

    private void generarReportePorVeterinario() {
        String medicoSeleccionado = (String) vista.cbxMedico.getSelectedItem();
        if (medicoSeleccionado.equals("(Todos)")) {
            JOptionPane.showMessageDialog(vista, "Seleccione un veterinario específico");
            return;
        }

        try {
            int medicoId = Integer.parseInt(medicoSeleccionado.split(" - ")[0]);
            citasActuales = dao.obtenerCitasPorMedico(medicoId);
            actualizarTabla(citasActuales);
            vista.lblResumen.setText("Resumen: " + citasActuales.size() + " citas para el veterinario " + medicoSeleccionado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al generar reporte por veterinario: " + e.getMessage());
        }
    }

    private void generarHistoricoEstados() {
        citasActuales = dao.obtenerHistoricoEstados();
        actualizarTabla(citasActuales);
        
        int programadas = dao.contarCitasPorEstado("PROGRAMADA");
        int reprogramadas = dao.contarCitasPorEstado("REPROGRAMADA");
        int canceladas = dao.contarCitasPorEstado("CANCELADA");
        
        vista.lblResumen.setText("Resumen: Programadas=" + programadas + 
            ", Reprogramadas=" + reprogramadas + ", Canceladas=" + canceladas);
    }

    private void actualizarTabla(ArrayList<ModeloCita> citas) {
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
        vista.tablaReporte.setModel(new DefaultTableModel(
            datos,
            new String[]{"ID Cita", "Médico", "Mascota", "Fecha", "Hora", "Estado"}
        ));
    }

    private void exportarCSV() {
        if (citasActuales.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay datos para exportar. Genere un reporte primero.");
            return;
        }

        try {
            String tipoReporte = (String) vista.cbxTipoReporte.getSelectedItem();
            String nombreArchivo = "reporte_" + tipoReporte.replace(" ", "_").toLowerCase() + ".csv";
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo));
            
            writer.write("ID Cita,Médico,Mascota,Fecha,Hora,Estado");
            writer.newLine();
            
            for (ModeloCita cita : citasActuales) {
                writer.write(cita.getId() + "," + 
                            cita.getMedicoId() + "," + 
                            cita.getMascotaId() + "," + 
                            cita.getFecha() + "," + 
                            cita.getHora() + "," + 
                            cita.getEstado());
                writer.newLine();
            }
            
            writer.close();
            JOptionPane.showMessageDialog(vista, "Reporte exportado exitosamente: " + nombreArchivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al exportar CSV: " + e.getMessage());
        }
    }
}
