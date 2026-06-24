/**
 *
 * @author Galo Izquierdo
 */
package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.data.DbDAOMod1;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloCliente;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMascota;
import com.grupo2.sistemadegestionveterinaria.data.CnnDB;
import java.sql.Connection;
import java.util.List;

public class ControladorClienteMascota {

    private final DbDAOMod1 dao = new DbDAOMod1();

    // =========================
    // 💾 GUARDAR (INSERT / UPDATE COMPLETO)
    // =========================
    /**
     *
     * @param c
     * @param mascotas
     * @throws Exception
     */
    public void guardar(ModeloCliente c, List<ModeloMascota> mascotas) throws Exception {

        validarCliente(c);

        if (mascotas == null || mascotas.isEmpty()) {
            throw new Exception("Debe ingresar al menos una mascota");
        }

        for (ModeloMascota m : mascotas) {
            validarMascota(m);
        }

        Connection con = CnnDB.getConeccion();

        try {
            con.setAutoCommit(false);

            // INSERT o UPDATE cliente
            if (c.getId() == null || c.getId() == 0) {
                int id = dao.insertarCliente(c, con);
                c.setId(id);
            } else {
                dao.actualizarCliente(c, con);
            }

            // Guardar mascotas (insert/update)
            dao.guardarMascotas(mascotas, c.getId(), con);

            con.commit();

        }
        catch (Exception e) {
            con.rollback();
            throw e;
        }
        finally {
            con.close();
        }
    }

    // =========================
    // 🔍 BUSCAR CLIENTE + MASCOTAS
    // =========================
    /**
     *
     * @param cedula
     * @param lista
     * @return
     * @throws Exception
     */
    public ModeloCliente buscar(String cedula, List<ModeloMascota> lista) throws Exception {

        ModeloCliente cA = dao.buscarCliente(cedula);

        if (cA != null) {
            lista.addAll(dao.buscarMascotas(cA.getId()));
        }

        return cA;
    }

    // =========================
    // 🔎 BUSCAR MASCOTA POR NOMBRE
    // =========================
    /**
     *
     * @param nombre
     * @return
     * @throws Exception
     */
    public List<ModeloMascota> buscarMascota(String nombre) throws Exception {
        return dao.buscarPorNombreMascota(nombre);
    }

    // =========================
    // ❌ ELIMINAR MASCOTA
    // =========================
    /**
     *
     * @param id
     * @throws Exception
     */
    public void eliminarMascota(int id) throws Exception {

        Connection con = CnnDB.getConeccion();

        try {
            con.setAutoCommit(false);

            dao.eliminarMascota(id, con);

            con.commit();

        }
        catch (Exception e) {
            con.rollback();
            throw e;
        }
        finally {
            con.close();
        }
    }

    // =========================
    // 🧪 VALIDACIONES
    // =========================
    private void validarCliente(ModeloCliente c) throws Exception {

        if (c.getCedula() == null || !c.getCedula().matches("\\d{10}")) {
            throw new Exception("Cédula inválida");
        }

        if (c.getNombres() == null || !c.getNombres().matches("[a-zA-Z ]+")) {
            throw new Exception("Nombre inválido");
        }

        String telefono = c.getTelefono();

        //  if (telefono != null && !telefono.isEmpty() && !telefono.matches("\\d+")) {
        //     throw new Exception("Teléfono inválido");
//        }
    }

    private void validarMascota(ModeloMascota m) throws Exception {

        if (m.getNombre() == null || m.getNombre().trim().isEmpty()) {
            throw new Exception("Nombre de mascota requerido");
        }

        if (m.getRaza() == null || m.getRaza().trim().isEmpty()) {
            throw new Exception("Raza requerida");
        }

        if (m.getEspecie() == null || m.getEspecie().trim().isEmpty()) {
            throw new Exception("Especie requerida");
        }
    }
}
