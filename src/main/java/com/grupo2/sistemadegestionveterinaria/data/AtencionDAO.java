/**
 *
 * @author Mario
 */

package com.grupo2.sistemadegestionveterinaria.data;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtencionDAO {

    // RF-4.1: Registrar una nueva atención clínica
    public boolean registrarAtencion(ModeloAtencion atencion) {
        String sql = "INSERT INTO g2_vet_atenciones (id_cita, temperatura, peso_actual, diagnostico, receta) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, atencion.getIdCita());
            ps.setDouble(2, atencion.getTemperatura());
            ps.setDouble(3, atencion.getPesoActual());
            ps.setString(4, atencion.getDiagnostico());
            ps.setString(5, atencion.getReceta());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error SQL al guardar registro de atención: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    // Validación de Integridad: Verifica si la cita existe en el módulo 3
    public boolean existeCita(int idCita) {
        String sql = "SELECT id_cita FROM g2_vet_citas WHERE id_cita = ?";
        
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            System.err.println("Error SQL al verificar la cita: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    // RF-4.2: Consultar historial clínico por ID de Mascota
    public List<ModeloAtencion> obtenerHistorialPorMascota(int idMascota) {
        List<ModeloAtencion> historial = new ArrayList<>();
        String sql = "SELECT a.* FROM g2_vet_atenciones a " +
                     "JOIN g2_vet_citas c ON a.id_cita = c.id_cita " +
                     "WHERE c.mascota_id = ?";
                     
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModeloAtencion atencion = new ModeloAtencion();
                    atencion.setIdAtencion(rs.getInt("id_atencion"));
                    atencion.setIdCita(rs.getInt("id_cita"));
                    atencion.setTemperatura(rs.getDouble("temperatura"));
                    atencion.setPesoActual(rs.getDouble("peso_actual"));
                    atencion.setDiagnostico(rs.getString("diagnostico"));
                    atencion.setReceta(rs.getString("receta"));
                    historial.add(atencion);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error SQL al recuperar el historial: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return historial;
    }

    // RF-4.3: Actualizar texto de Diagnóstico y Receta
    public boolean actualizarDiagnostico(int idAtencion, String diagnostico, String receta) {
        String sql = "UPDATE g2_vet_atenciones SET diagnostico = ?, receta = ? WHERE id_atencion = ?";
        
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, diagnostico);
            ps.setString(2, receta);
            ps.setInt(3, idAtencion);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error SQL al modificar el diagnóstico: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
    
    // Método puente para la vista: Obtiene la mascota a partir de la cita
    public int obtenerIdMascotaPorCita(int idCita) {
        // CAMBIO: id_mascota -> mascota_id
        String sql = "SELECT mascota_id FROM g2_vet_citas WHERE id_cita = ?";
        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                // CAMBIO: id_mascota -> mascota_id
                if (rs.next()) return rs.getInt("mascota_id");
            }
        } catch (Exception e) {
            System.err.println("Error al buscar mascota: " + e.getMessage());
        }
        return -1; // Retorna -1 si no existe
    }
}