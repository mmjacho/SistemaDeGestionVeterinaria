package com.grupo2.sistemadegestionveterinaria.data;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloCita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase de Acceso a Datos (DAO) para gestionar las citas médicas
 * veterinarias en la base de datos. Permite realizar operaciones CRUD
 * y consultas especializadas de disponibilidad de médicos y mascotas.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class CitaDAO {

    /**
     * Registra una nueva cita médica en la base de datos.
     *
     * @param cita el objeto ModeloCita con la información de la cita.
     * @return true si la cita se guardó exitosamente; false de lo contrario.
     */
    public boolean guardarCita(ModeloCita cita) {
        String sql = "INSERT INTO g2_vet_citas (medico_id, mascota_id, fecha, hora, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cita.getMedicoId());
            ps.setInt(2, cita.getMascotaId());
            ps.setString(3, cita.getFecha());
            ps.setString(4, cita.getHora());
            ps.setString(5, cita.getEstado());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista con todas las citas registradas en la base de datos.
     *
     * @return una lista de objetos ModeloCita con todas las citas.
     */
    public ArrayList<ModeloCita> listarCitas() {
        ArrayList<ModeloCita> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_citas";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
        } catch (SQLException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza la información de una cita existente en la base de datos.
     *
     * @param cita el objeto ModeloCita con la información actualizada.
     * @return true si se actualizó con éxito; false en caso contrario.
     */
    public boolean actualizarCita(ModeloCita cita) {
        String sql = "UPDATE g2_vet_citas SET medico_id=?, mascota_id=?, fecha=?, hora=?, estado=? WHERE id_cita=?";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cita.getMedicoId());
            ps.setInt(2, cita.getMascotaId());
            ps.setString(3, cita.getFecha());
            ps.setString(4, cita.getHora());
            ps.setString(5, cita.getEstado());
            ps.setInt(6, cita.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cancela una cita médica cambiando su estado a 'CANCELADA'.
     *
     * @param id el identificador único de la cita a cancelar.
     * @return true si se canceló exitosamente; false en caso contrario.
     */
    public boolean eliminarCita(Integer id) {
        String sql = "UPDATE g2_vet_citas SET estado='CANCELADA' WHERE id_cita=?";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Filtra y obtiene una lista de citas para una fecha específica.
     *
     * @param fecha la fecha de la cita en formato de texto.
     * @return una lista de objetos ModeloCita programadas para esa fecha.
     */
    public ArrayList<ModeloCita> listarCitasPorFecha(String fecha) {
        ArrayList<ModeloCita> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_citas WHERE fecha = ?";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fecha);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar por fecha: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene todas las citas asignadas a un médico veterinario específico.
     *
     * @param medicoId el identificador del médico veterinario.
     * @return una lista de objetos ModeloCita asignadas a dicho médico.
     */
    public ArrayList<ModeloCita> obtenerCitasPorMedico(int medicoId) {
        ArrayList<ModeloCita> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_citas WHERE medico_id = ?";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, medicoId);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener por médico: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Cuenta el número total de citas según su estado actual.
     *
     * @param estado el estado de la cita por el cual filtrar.
     * @return la cantidad total de citas en el estado especificado.
     */
    public int contarCitasPorEstado(String estado) {
        String sql = "SELECT COUNT(*) FROM g2_vet_citas WHERE estado = ?";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar estados: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Verifica si un médico tiene disponibilidad en una fecha y hora dadas.
     *
     * @param medicoId el identificador del médico a verificar.
     * @param fecha la fecha de la cita a consultar.
     * @param hora la hora de la cita a consultar.
     * @param idCita el identificador de la cita (opcional, para exclusión).
     * @return true si el médico está disponible; false en caso contrario.
     */
    public boolean verificarDisponibilidadMedico(int medicoId, String fecha, String hora, Integer idCita) {
        String sql = "SELECT COUNT(*) FROM g2_vet_citas WHERE medico_id=? AND fecha=? AND hora=? AND estado!='CANCELADA'";
        if (idCita != null) sql += " AND id_cita != " + idCita;
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, medicoId);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar disponibilidad de médico: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifica si una mascota tiene disponibilidad en una fecha y hora dadas.
     *
     * @param mascotaId el identificador de la mascota a verificar.
     * @param fecha la fecha de la cita a consultar.
     * @param hora la hora de la cita a consultar.
     * @param idCita el identificador de la cita (opcional, para exclusión).
     * @return true si la mascota está disponible; false en caso contrario.
     */
    public boolean verificarDisponibilidadMascota(int mascotaId, String fecha, String hora, Integer idCita) {
        String sql = "SELECT COUNT(*) FROM g2_vet_citas WHERE mascota_id=? AND fecha=? AND hora=? AND estado!='CANCELADA'";
        if (idCita != null) sql += " AND id_cita != " + idCita;
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar disponibilidad de mascota: " + e.getMessage());
        }
        return false;
    }
}