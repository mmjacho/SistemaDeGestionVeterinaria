/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo2.sistemadegestionveterinaria.data;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloCliente;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Acceso a Datos (DAO) para la gestión de clientes y mascotas.
 * Proporciona métodos para buscar, insertar, actualizar y listar clientes y
 * mascotas en la base de datos de manera transaccional.
 *
 * @author Galo Izquierdo
 * @version 1.0
 */
public class DbDAOMod1 {

    // =========================
    // 🔍 BUSCAR CLIENTE
    // =========================
    /**
     * Busca un cliente en la base de datos por su número de cédula.
     *
     * @param cedula la cédula del cliente a buscar.
     * @return el modelo del cliente si se encuentra; null de lo contrario.
     * @throws Exception si ocurre un error en la consulta a la base de datos.
     */
    public ModeloCliente buscarCliente(String cedula) throws Exception {

        String sql = "SELECT * FROM g2_vet_clientes WHERE cedula=? AND eliminado IS NULL";

        try (Connection con = CnnDB.getConeccion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    ModeloCliente c = new ModeloCliente();
                    c.setId(rs.getInt("id_cliente"));
                    c.setCedula(rs.getString("cedula"));
                    c.setNombres(rs.getString("nombres"));
                    c.setTelefono(rs.getString("telefono"));
                    return c;
                }
            }
        }

        return null;
    }

    // =========================
    // 🔍 BUSCAR MASCOTAS
    // =========================
    /**
     * Busca las mascotas activas asociadas a un cliente específico.
     *
     * @param clienteId el identificador único del cliente.
     * @return una lista de modelos de mascota asociados al cliente.
     * @throws Exception si ocurre un error en la consulta a la base de datos.
     */
    public List<ModeloMascota> buscarMascotas(int clienteId) throws Exception {

        List<ModeloMascota> lista = new ArrayList<>();

        String sql = "SELECT * FROM g2_vet_mascotas WHERE id_cliente=? and estado=0 AND eliminado IS NULL";

        try (Connection con = CnnDB.getConeccion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clienteId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ModeloMascota m = new ModeloMascota();
                    m.setId(rs.getInt("id_mascota"));
                    m.setNombre(rs.getString("nombre"));
                    m.setRaza(rs.getString("raza"));
                    m.setEspecie(rs.getString("especie"));
                    m.setClienteId(clienteId);

                    lista.add(m);
                }
            }
        }

        return lista;
    }

    // =========================
    // ➕ INSERTAR CLIENTE
    // (USA TRANSACCIÓN)
    // =========================
    /**
     * Inserta un nuevo cliente en la base de datos dentro de una transacción.
     *
     * @param c el modelo del cliente a insertar.
     * @param con la conexión de base de datos activa.
     * @return el identificador generado para el nuevo cliente.
     * @throws Exception si ocurre un error en la inserción de datos.
     */
    public int insertarCliente(ModeloCliente c, Connection con) throws Exception {

        String sql = "INSERT INTO g2_vet_clientes (cedula,nombres,telefono,creado) VALUES (?,?,?,NOW())";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getTelefono());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new Exception("No se pudo obtener ID del cliente");
    }

    // =========================
    // ✏️ ACTUALIZAR CLIENTE
    // =========================
    /**
     * Actualiza la información de un cliente en la base de datos.
     *
     * @param c el modelo del cliente con los nuevos datos.
     * @param con la conexión de base de datos activa.
     * @throws Exception si ocurre un error al actualizar los datos.
     */
    public void actualizarCliente(ModeloCliente c, Connection con) throws Exception {

        String sql = "UPDATE g2_vet_clientes SET nombres=?, telefono=?, actualizado=NOW() WHERE id_cliente=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombres());
            ps.setString(2, c.getTelefono());
            ps.setInt(3, c.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Obtiene una lista de todos los clientes registrados en el sistema.
     *
     * @return una lista de modelos de cliente.
     * @throws Exception si ocurre un error en la consulta a la base de datos.
     */
    public List<ModeloCliente> listarClientes() throws Exception {
        List<ModeloCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_clientes WHERE eliminado IS NULL";
        try (Connection con = CnnDB.getConeccion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ModeloCliente c = new ModeloCliente();
                c.setId(rs.getInt("id_cliente"));
                c.setCedula(rs.getString("cedula"));
                c.setNombres(rs.getString("nombres"));
                lista.add(c);
            }
        }
        return lista;
    }

    /**
     * Genera un reporte detallado con los datos de clientes y sus mascotas.
     *
     * @return una lista de arreglos de cadenas con los datos del reporte.
     * @throws Exception si ocurre un error al ejecutar la consulta.
     */
    public List<String[]> obtenerReporteCompleto() throws Exception {
        List<String[]> reporte = new ArrayList<>();
        String sql = "SELECT c.cedula, c.nombres, m.nombre as mascota, m.especie "
                + "FROM g2_vet_clientes c "
                + "LEFT JOIN g2_vet_mascotas m ON c.id_cliente = m.id_cliente AND m.eliminado IS NULL "
                + "WHERE c.eliminado IS NULL "
                + "ORDER BY c.nombres";

        try (Connection con = CnnDB.getConeccion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                reporte.add(new String[]{
                    rs.getString("cedula"),
                    rs.getString("nombres"),
                    rs.getString("mascota"),
                    rs.getString("especie")
                });
            }
        }
        return reporte;
    }
    // =========================
    // 💾 INSERT / UPDATE MASCOTAS
    // =========================

    /**
     * Registra o actualiza un listado de mascotas asociadas a un cliente.
     *
     * @param lista la lista de mascotas a procesar.
     * @param clienteId el identificador único del cliente propietario.
     * @param con la conexión de base de datos activa.
     * @throws Exception si ocurre un error al guardar o actualizar.
     */
    public void guardarMascotas(List<ModeloMascota> lista, int clienteId, Connection con) throws Exception {

        String sqlInsert = "INSERT INTO g2_vet_mascotas (nombre,raza,especie,id_cliente,creado) VALUES (?,?,?,?,NOW())";
        String sqlUpdate = "UPDATE g2_vet_mascotas SET nombre=?, raza=?, especie=?, actualizado=NOW() WHERE id_mascota=?";

        for (ModeloMascota m : lista) {

            if (m.getId() == 0) {
                // NUEVA
                try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {

                    ps.setString(1, m.getNombre());
                    ps.setString(2, m.getRaza());
                    ps.setString(3, m.getEspecie());
                    ps.setInt(4, clienteId);

                    ps.executeUpdate();
                }

            } else {
                // EXISTENTE
                try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {

                    ps.setString(1, m.getNombre());
                    ps.setString(2, m.getRaza());
                    ps.setString(3, m.getEspecie());
                    ps.setInt(4, m.getId());

                    ps.executeUpdate();
                }
            }
        }
    }

    // =========================
    // ❌ ELIMINAR MASCOTA (OPCIONAL)
    // =========================
    /**
     * Realiza la eliminación lógica de una mascota cambiando su estado.
     *
     * @param id el identificador único de la mascota.
     * @param con la conexión de base de datos activa.
     * @throws Exception si ocurre un error al modificar el estado.
     */
    public void eliminarMascota(int id, Connection con) throws Exception {

        String sql = "UPDATE g2_vet_mascotas SET estado = 9, eliminado=NOW() WHERE id_mascota=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // =========================
    // 🔎 BUSCAR MASCOTA ESPECÍFICA
    // =========================
    /**
     * Busca mascotas en la base de datos que coincidan con un nombre dado.
     *
     * @param nombre el nombre o patrón de búsqueda para la mascota.
     * @return una lista de modelos de mascotas que coinciden con el nombre.
     * @throws Exception si ocurre un error al ejecutar la consulta.
     */
    public List<ModeloMascota> buscarPorNombreMascota(String nombre) throws Exception {

        List<ModeloMascota> lista = new ArrayList<>();

        String sql = "SELECT * FROM g2_vet_mascotas WHERE nombre LIKE ? AND eliminado IS NULL";

        try (Connection con = CnnDB.getConeccion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ModeloMascota m = new ModeloMascota();
                    m.setId(rs.getInt("id_mascota"));
                    m.setNombre(rs.getString("nombre"));
                    m.setRaza(rs.getString("raza"));
                    m.setEspecie(rs.getString("especie"));
                    m.setClienteId(rs.getInt("id_cliente"));

                    lista.add(m);
                }
            }
        }

        return lista;
    }
}
