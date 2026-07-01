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

/**
 * Controlador que coordina la gestión conjunta de clientes y mascotas.
 * Permite registrar, buscar y eliminar información relacionada aplicando
 * reglas de validación de negocio antes de la persistencia.
 *
 * @author Galo Izquierdo
 * @version 1.0
 */
public class ControladorClienteMascota {

    private final DbDAOMod1 dao = new DbDAOMod1();

    // =========================
    // 💾 GUARDAR (INSERT / UPDATE COMPLETO)
    // =========================
    /**
     * Guarda o actualiza un cliente y su lista de mascotas asociadas.
     * Realiza las validaciones de negocio antes de procesar la transacción.
     *
     * @param c el modelo del cliente a guardar o actualizar.
     * @param mascotas la lista de mascotas vinculadas al cliente.
     * @throws Exception si hay datos inválidos o falla la transacción en la
     *                   base de datos.
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
     * Busca un cliente por su cédula y carga todas sus mascotas asociadas.
     *
     * @param cedula la cédula del cliente a buscar.
     * @param lista la lista donde se agregarán las mascotas encontradas.
     * @return el modelo del cliente encontrado, o null si no existe.
     * @throws Exception si ocurre un error durante la búsqueda en la base
     *                   de datos.
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
     * Busca mascotas en el sistema que coincidan con el nombre especificado.
     *
     * @param nombre el nombre de la mascota o coincidencia a buscar.
     * @return una lista de mascotas con el nombre especificado.
     * @throws Exception si ocurre un error al realizar la consulta.
     */
    public List<ModeloMascota> buscarMascota(String nombre) throws Exception {
        return dao.buscarPorNombreMascota(nombre);
    }

    // =========================
    // ❌ ELIMINAR MASCOTA
    // =========================
    /**
     * Elimina lógicamente una mascota del sistema según su identificador.
     *
     * @param id el identificador único de la mascota a eliminar.
     * @throws Exception si ocurre un error al eliminar el registro.
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
        //     }
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
