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
            vista.cbxMedico.removeAllItems();
            vista.cbxMedico.addItem("(Seleccione un médico)");
            for (ModeloMedico medico : medicos) {
                vista.cbxMedico.addItem(medico.getIdMedico() + " - " + medico.getNombres() + " " + medico.getApellidos());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
    }

    private void cargarMascotas() {
        try {
            ArrayList<ModeloMascota> mascotas = obtenerMascotas();
            vista.cbxMascota.removeAllItems();
            vista.cbxMascota.addItem("(Seleccione una mascota)");
            for (ModeloMascota mascota : mascotas) {
                vista.cbxMascota.addItem(mascota.getId() + " - " + mascota.getNombre());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar mascotas: " + e.getMessage());
        }
    }

    private ArrayList<ModeloMascota> obtenerMascotas() {
        ArrayList<ModeloMascota> lista = new ArrayList<>();
        try {
            java.sql.Connection con = com.grupo2.sistemadegestionveterinaria.data.CnnDB.getConeccion();
            String sql = "SELECT * FROM g2_vet_mascotas WHERE estado=0";
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModeloMascota mascota = new ModeloMascota();
                mascota.setId(rs.getInt("id_mascota"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setRaza(rs.getString("raza"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setClienteId(rs.getInt("id_cliente"));
                mascota.setEstado(rs.getInt("estado"));
                lista.add(mascota);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener mascotas: " + e.getMessage());
        }
        return lista;
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
