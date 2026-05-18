/**
 *
 * @author Ruben Quiroga
 */
package com.grupo2.sistemadegestionveterinaria.controlador;

import java.util.ArrayList;

import com.grupo2.sistemadegestionveterinaria.data.CitaDAO;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloCita;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMascota;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMedico;
import com.grupo2.sistemadegestionveterinaria.vista.VistaCita;

public class ControladorCita {

    private VistaCita vista;
    private CitaDAO dao;
    private ModeloCita modelo;

    public ControladorCita(VistaCita vista) {
        this.vista = vista;
        this.dao = new CitaDAO();
        this.modelo = new ModeloCita();
        iniciarEventos();
        cargarMedicos();
        cargarMascotas();
        cargarCitas();
    }

    private void iniciarEventos() {
        vista.btnAgendar.addActionListener(e -> agendarCita());
        vista.btnBuscar.addActionListener(e -> cargarCitas());
        vista.btnReprogramar.addActionListener(e -> reprogramarCita());
    }

    private void cargarMedicos() {
        try {
            ModeloMedico medicoModelo = new ModeloMedico();
            ArrayList<ModeloMedico> medicos = medicoModelo.listarMedicos();
            System.out.println("Cantidad de médicos encontrados: " + medicos.size());
            vista.cbxMedico.removeAllItems();
            vista.cbxMedico.addItem("(Seleccione un médico)");
            for (ModeloMedico medico : medicos) {
                String item = medico.getIdMedico() + " - " + medico.getNombres() + " " + medico.getApellidos();
                System.out.println("Agregando médico: " + item);
                vista.cbxMedico.addItem(item);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarMascotas() {
        try {
            ModeloMascota mascotaModelo = new ModeloMascota();
            ArrayList<ModeloMascota> mascotas = mascotaModelo.listarMascotas();
            System.out.println("Cantidad de mascotas encontradas: " + mascotas.size());
            vista.cbxMascota.removeAllItems();
            vista.cbxMascota.addItem("(Seleccione una mascota)");
            for (ModeloMascota mascota : mascotas) {
                String item = mascota.getId() + " - " + mascota.getNombre();
                System.out.println("Agregando mascota: " + item);
                vista.cbxMascota.addItem(item);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar mascotas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void agendarCita() {
        try {
            String medicoSeleccionado = (String) vista.cbxMedico.getSelectedItem();
            String mascotaSeleccionada = (String) vista.cbxMascota.getSelectedItem();
            String fecha = vista.txtFecha.getText();
            String hora = vista.txtHora.getText();

            if (medicoSeleccionado.equals("(Seleccione un médico)") || 
                mascotaSeleccionada.equals("(Seleccione una mascota)") ||
                fecha.isEmpty() || hora.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos");
                return;
            }

            int medicoId = Integer.parseInt(medicoSeleccionado.split(" - ")[0]);
            int mascotaId = Integer.parseInt(mascotaSeleccionada.split(" - ")[0]);

            // Convertir fecha de DD/MM/YYYY a YYYY-MM-DD
            String[] partesFecha = fecha.split("/");
            String fechaMySQL = partesFecha[2] + "-" + partesFecha[1] + "-" + partesFecha[0];

            modelo.setMedicoId(medicoId);
            modelo.setMascotaId(mascotaId);
            modelo.setFecha(fechaMySQL);
            modelo.setHora(hora);
            modelo.setEstado("PROGRAMADA");

            if (dao.guardarCita(modelo)) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Cita agendada exitosamente");
                limpiarCampos();
                cargarCitas();
            } else {
                javax.swing.JOptionPane.showMessageDialog(vista, "Error al agendar cita");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
        }
    }

    private void reprogramarCita() {
        try {
            int fila = vista.tablaCitas.getSelectedRow();
            if (fila == -1) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Seleccione una cita para reprogramar");
                return;
            }

            String medicoSeleccionado = (String) vista.cbxMedico.getSelectedItem();
            String mascotaSeleccionada = (String) vista.cbxMascota.getSelectedItem();
            String fecha = vista.txtFecha.getText();
            String hora = vista.txtHora.getText();

            if (medicoSeleccionado.equals("(Seleccione un médico)") || 
                mascotaSeleccionada.equals("(Seleccione una mascota)") ||
                fecha.isEmpty() || hora.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos");
                return;
            }

            int idCita = Integer.parseInt(vista.tablaCitas.getValueAt(fila, 0).toString());
            int medicoId = Integer.parseInt(medicoSeleccionado.split(" - ")[0]);
            int mascotaId = Integer.parseInt(mascotaSeleccionada.split(" - ")[0]);

            // Convertir fecha de DD/MM/YYYY a YYYY-MM-DD
            String[] partesFecha = fecha.split("/");
            String fechaMySQL = partesFecha[2] + "-" + partesFecha[1] + "-" + partesFecha[0];

            modelo.setId(idCita);
            modelo.setMedicoId(medicoId);
            modelo.setMascotaId(mascotaId);
            modelo.setFecha(fechaMySQL);
            modelo.setHora(hora);
            modelo.setEstado("REPROGRAMADA");

            if (dao.actualizarCita(modelo)) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Cita reprogramada exitosamente");
                limpiarCampos();
                cargarCitas();
            } else {
                javax.swing.JOptionPane.showMessageDialog(vista, "Error al reprogramar cita");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
        }
    }

    private void cargarCitas() {
        try {
            ArrayList<ModeloCita> citas = dao.listarCitas();
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
            vista.tablaCitas.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{"ID Cita", "Médico", "Mascota", "Fecha", "Hora", "Estado"}
            ));
        } catch (Exception e) {
            System.out.println("Error al cargar citas: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        vista.cbxMedico.setSelectedIndex(0);
        vista.cbxMascota.setSelectedIndex(0);
        vista.txtFecha.setText("");
        vista.txtHora.setText("");
    }
}
