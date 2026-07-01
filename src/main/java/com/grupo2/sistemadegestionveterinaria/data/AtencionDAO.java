package com.grupo2.sistemadegestionveterinaria.data;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase de Acceso a Datos (DAO) para la entidad de Atención Veterinaria.
 * Centraliza las operaciones CRUD y las consultas relacionales directas
 * sobre el motor de base de datos para el historial clínico.
 * Módulo 4: Registro de Atención Veterinaria.
 *
 * @author Mario Jacho
 * @version 1.1
 */
public class AtencionDAO {

    // CONSTANTES PARA ELIMINAR NÚMEROS MÁGICOS

    /** Índice de parámetro para el campo ID Cita. */
    private static final int PARAM_REG_CITA = 1;

    /** Índice de parámetro para la Temperatura. */
    private static final int PARAM_REG_TEMP = 2;

    /** Índice de parámetro para el Peso Actual. */
    private static final int PARAM_REG_PESO = 3;

    /** Índice de parámetro para el Diagnóstico. */
    private static final int PARAM_REG_DIAG = 4;

    /** Índice de parámetro para la Receta Médica. */
    private static final int PARAM_REG_RECE = 5;

    /** Índice de parámetro único para filtros basados en ID de Cita. */
    private static final int PARAM_FILTRO_CITA = 1;

    /** Índice de parámetro único para filtros basados en ID de Mascota. */
    private static final int PARAM_FILTRO_MASC = 1;

    /** Índice de parámetro único para filtros basados en ID de Atención. */
    private static final int PARAM_FILTRO_ATEN = 3;

    /** Índice de parámetro de borrado basado en ID de Atención. */
    private static final int PARAM_DEL_ATEN = 1;

    /** Valor por defecto retornado cuando no se encuentra una mascota. */
    private static final int ID_NO_ENCONTRADO = -1;

    /**
     * Registra una nueva ficha de atención clínica en el sistema.
     *
     * @param pAtencion Instancia del modelo con los datos recolectados.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public final boolean registrarAtencion(final ModeloAtencion pAtencion) {
        String sql = "INSERT INTO g2_vet_atenciones (id_cita, temperatura, "
                + "peso_actual, diagnostico, receta) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = CnnDB.getConeccion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(PARAM_REG_CITA, pAtencion.getIdCita());
            ps.setDouble(PARAM_REG_TEMP, pAtencion.getTemperatura());
            ps.setDouble(PARAM_REG_PESO, pAtencion.getPesoActual());
            ps.setString(PARAM_REG_DIAG, pAtencion.getDiagnostico());
            ps.setString(PARAM_REG_RECE, pAtencion.getReceta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error SQL al guardar registro de atención: "
                    + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica la existencia de una cita médica en la tabla de agenda
     * global.
     *
     * @param pIdCita Identificador único de la cita.
     * @return true si el registro existe, false de lo contrario.
     */
    public final boolean existeCita(final int pIdCita) {
        String sql = "SELECT id_cita FROM g2_vet_citas WHERE id_cita = ?";

        try (Connection con = CnnDB.getConeccion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(PARAM_FILTRO_CITA, pIdCita);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al verificar la cita: "
                    + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera información cruzada (Mascota, Dueño, Médico) de una cita.
     *
     * @param pIdCita Identificador único de la consulta.
     * @return Un mapa asociativo con los nombres textuales recuperados.
     */
    public final Map<String, String> obtenerDatosCita(final int pIdCita) {
        Map<String, String> datos = new HashMap<>();
        String sql = "SELECT m.nombre AS mascota, cl.nombres AS dueno, "
                + "CONCAT(med.nombres, ' ', med.apellidos) AS medico "
                + "FROM g2_vet_citas c "
                + "JOIN g2_vet_mascotas m ON c.mascota_id = m.id_mascota "
                + "JOIN g2_vet_clientes cl ON m.id_cliente = cl.id_cliente "
                + "JOIN g2_vet_medicos med ON c.medico_id = med.id_medico "
                + "WHERE c.id_cita = ?";

        try (Connection con = CnnDB.getConeccion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(PARAM_FILTRO_CITA, pIdCita);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    datos.put("mascota", rs.getString("mascota"));
                    datos.put("dueno", rs.getString("dueno"));
                    datos.put("medico", rs.getString("medico"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener datos de cita: "
                    + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general de conexión en datos de cita: "
                    + e.getMessage());
        }
        return datos;
    }

    /**
     * Consulta la lista cronológica de atenciones clínicas de una mascota.
     *
     * @param pIdMascota Identificador único del paciente.
     * @return Lista estructurada con los modelos de atención localizados.
     */
    public final List<ModeloAtencion> obtenerHistorialPorMascota(
            final int pIdMascota) {
        List<ModeloAtencion> historial = new ArrayList<>();
        String sql = "SELECT a.* FROM g2_vet_atenciones a "
                + "JOIN g2_vet_citas c ON a.id_cita = c.id_cita "
                + "WHERE c.mascota_id = ?";

        try (Connection con = CnnDB.getConeccion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(PARAM_FILTRO_MASC, pIdMascota);
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
            System.err.println("Error SQL al recuperar el historial: "
                    + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return historial;
    }

    /**
     * Modifica el diagnóstico clínico y la prescripción farmacológica.
     *
     * @param pIdAtencion  Identificador único de la ficha clínica.
     * @param pDiagnostico Nueva descripción textual de la evaluación.
     * @param pReceta      Nuevo listado de medicamentos.
     * @return true si la actualización afectó filas válidas, false si falló.
     */
    public final boolean actualizarDiagnostico(final int pIdAtencion,
            final String pDiagnostico, final String pReceta) {
        String sql = "UPDATE g2_vet_atenciones SET diagnostico = ?, "
                + "receta = ? WHERE id_atencion = ?";

        try (Connection con = CnnDB.getConeccion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pDiagnostico);
            ps.setString(2, pReceta);
            ps.setInt(PARAM_FILTRO_ATEN, pIdAtencion);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error SQL al modificar el diagnóstico: "
                    + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Ejecuta una baja física (DELETE) sobre una ficha de atención médica.
     *
     * @param pIdAtencion Identificador único de la atención a remover.
     * @return true si se eliminó de forma efectiva, false en caso contrario.
     */
    public final boolean eliminarAtencion(final int pIdAtencion) {
        String sql = "DELETE FROM g2_vet_atenciones WHERE id_atencion = ?";

        try (Connection con = CnnDB.getConeccion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(PARAM_DEL_ATEN, pIdAtencion);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar el registro: "
                    + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método puente para recuperar el id_mascota a partir de una cita dada.
     *
     * @param pIdCita Identificador único de la cita médica agendada.
     * @return El ID de la mascota asociada o -1 si ocurre un error.
     */
    public final int obtenerIdMascotaPorCita(final int pIdCita) {
        String sql = "SELECT mascota_id FROM g2_vet_citas WHERE id_cita = ?";
        try (Connection con = CnnDB.getConeccion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(PARAM_FILTRO_CITA, pIdCita);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("mascota_id");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar mascota: " + e.getMessage());
        }
        return ID_NO_ENCONTRADO;
    }
}
