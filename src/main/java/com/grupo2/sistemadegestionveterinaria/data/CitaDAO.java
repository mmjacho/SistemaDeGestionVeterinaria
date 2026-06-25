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

    public ArrayList<ModeloCita> listarCitasPorFecha(String fecha) {
        ArrayList<ModeloCita> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_citas WHERE fecha = ?";
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
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
            System.out.println("Error al filtrar por fecha: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ModeloCita> obtenerCitasPorMedico(int medicoId) {
        ArrayList<ModeloCita> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_citas WHERE medico_id = ?";
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, medicoId);
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
            System.out.println("Error al obtener por médico: " + e.getMessage());
        }
        return lista;
    }

    public int contarCitasPorEstado(String estado) {
        String sql = "SELECT COUNT(*) FROM g2_vet_citas WHERE estado = ?";
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error al contar estados: " + e.getMessage());
        }
        return 0;
    }

    // Métodos de validación requeridos por el controlador
    public boolean verificarDisponibilidadMedico(int medicoId, String fecha, String hora, Integer idCita) {
        String sql = "SELECT COUNT(*) FROM g2_vet_citas WHERE medico_id=? AND fecha=? AND hora=? AND estado!='CANCELADA'";
        if (idCita != null) sql += " AND id_cita != " + idCita;
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, medicoId);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) == 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean verificarDisponibilidadMascota(int mascotaId, String fecha, String hora, Integer idCita) {
        String sql = "SELECT COUNT(*) FROM g2_vet_citas WHERE mascota_id=? AND fecha=? AND hora=? AND estado!='CANCELADA'";
        if (idCita != null) sql += " AND id_cita != " + idCita;
        try {
            con = CnnDB.getConeccion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, mascotaId);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) == 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}