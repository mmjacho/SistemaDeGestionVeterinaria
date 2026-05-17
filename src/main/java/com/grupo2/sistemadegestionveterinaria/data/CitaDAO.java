/**
 *
 * @author Ruben Quiroga
 */
package com.grupo2.sistemadegestionveterinaria.data;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloCita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CitaDAO {

    private Connection con;

    public boolean guardarCita(ModeloCita cita) {
        String sql = "INSERT INTO g2_vet_citas (medico_id, mascota_id, fecha, hora, estado) VALUES (?, ?, ?, ?, ?)";
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cita.getMedicoId());
            ps.setInt(2, cita.getMascotaId());
            ps.setString(3, cita.getFecha());
            ps.setString(4, cita.getHora());
            ps.setString(5, cita.getEstado());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar cita: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<ModeloCita> listarCitas() {
        ArrayList<ModeloCita> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_citas";
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModeloCita cita = new ModeloCita();
                cita.setId(rs.getInt("id_cita"));
                cita.setMedicoId(rs.getInt("medico_id"));
                cita.setMascotaId(rs.getInt("mascota_id"));
                cita.setFecha(rs.getString("fecha"));
                cita.setHora(rs.getString("hora"));
                cita.setEstado(rs.getString("estado"));
                lista.add(cita);
            }
        } catch (Exception e) {
            System.out.println("Error al listar citas: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarCita(ModeloCita cita) {
        String sql = "UPDATE g2_vet_citas SET medico_id=?, mascota_id=?, fecha=?, hora=?, estado=? WHERE id_cita=?";
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cita.getMedicoId());
            ps.setInt(2, cita.getMascotaId());
            ps.setString(3, cita.getFecha());
            ps.setString(4, cita.getHora());
            ps.setString(5, cita.getEstado());
            ps.setInt(6, cita.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCita(Integer id) {
        String sql = "UPDATE g2_vet_citas SET estado='CANCELADA' WHERE id_cita=?";
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }
}
