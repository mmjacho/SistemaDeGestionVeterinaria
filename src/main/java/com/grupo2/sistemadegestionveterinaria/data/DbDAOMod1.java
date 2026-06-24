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
 *
 * @author Galo Izquierdo
 */
public class DbDAOMod1 {

    // =========================
    // 🔍 BUSCAR CLIENTE
    // =========================
    /**
     *
     * @param cedula
     * @return
     * @throws Exception
     */
    public ModeloCliente buscarCliente(String cedula) throws Exception {

        String sql = "SELECT * FROM g2_vet_clientes WHERE cedula=?";

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
     *
     * @param clienteId
     * @return
     * @throws Exception
     */
    public List<ModeloMascota> buscarMascotas(int clienteId) throws Exception {

        List<ModeloMascota> lista = new ArrayList<>();

        String sql = "SELECT * FROM g2_vet_mascotas WHERE id_cliente=? and estado=0";

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
     *
     * @param c
     * @param con
     * @return
     * @throws Exception
     */
    public int insertarCliente(ModeloCliente c, Connection con) throws Exception {

        String sql = "INSERT INTO g2_vet_clientes (cedula,nombres,telefono) VALUES (?,?,?)";

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
     *
     * @param c
     * @param con
     * @throws Exception
     */
    public void actualizarCliente(ModeloCliente c, Connection con) throws Exception {

        String sql = "UPDATE g2_vet_clientes SET nombres=?, telefono=? WHERE id_cliente=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombres());
            ps.setString(2, c.getTelefono());
            ps.setInt(3, c.getId());

            ps.executeUpdate();
        }
    }

    public List<ModeloCliente> listarClientes() throws Exception {
        List<ModeloCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_clientes";
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

    public List<String[]> obtenerReporteCompleto() throws Exception {
        List<String[]> reporte = new ArrayList<>();
        String sql = "SELECT c.cedula, c.nombres, m.nombre as mascota, m.especie "
                + "FROM g2_vet_clientes c "
                + "LEFT JOIN g2_vet_mascotas m ON c.id_cliente = m.id_cliente "
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
     *
     * @param lista
     * @param clienteId
     * @param con
     * @throws Exception
     */
    public void guardarMascotas(List<ModeloMascota> lista, int clienteId, Connection con) throws Exception {

        String sqlInsert = "INSERT INTO g2_vet_mascotas (nombre,raza,especie,id_cliente) VALUES (?,?,?,?)";
        String sqlUpdate = "UPDATE g2_vet_mascotas SET nombre=?, raza=?, especie=? WHERE id_mascota=?";

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
     *
     * @param id
     * @param con
     * @throws Exception
     */
    public void eliminarMascota(int id, Connection con) throws Exception {

        String sql = "UPDATE g2_vet_mascotas SET estado = 9 WHERE id_mascota=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // =========================
    // 🔎 BUSCAR MASCOTA ESPECÍFICA
    // =========================
    /**
     *
     * @param nombre
     * @return
     * @throws Exception
     */
    public List<ModeloMascota> buscarPorNombreMascota(String nombre) throws Exception {

        List<ModeloMascota> lista = new ArrayList<>();

        String sql = "SELECT * FROM g2_vet_mascotas WHERE nombre LIKE ?";

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
