/**
 *
 * @author Alonso Serrano
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

import com.grupo2.sistemadegestionveterinaria.data.CnnDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Modelo que representa un médico veterinario en el sistema.
 * Contiene la información personal y profesional del médico, y proporciona
 * métodos de acceso (getters/setters) y de persistencia en la base de datos.
 *
 * @author Alonso Serrano
 * @version 1.0
 */
public class ModeloMedico {

    // ATRIBUTOS
    private int idMedico;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private String telefono;
    private boolean estado;

    //-----------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------

    /**
     * Constructor por defecto de la clase ModeloMedico.
     * Crea una instancia vacía del modelo de médico.
     */
    public ModeloMedico() {

    }

    //-----------------------------------------
    // GETTERS Y SETTERS
    //-----------------------------------------

    /**
     * Obtiene el identificador único del médico.
     *
     * @return el identificador único del médico.
     */
    public int getIdMedico() {
        return idMedico;
    }

    /**
     * Establece el identificador único del médico.
     *
     * @param idMedico el identificador único a asignar.
     */
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * Obtiene los nombres del médico.
     *
     * @return los nombres del médico.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres del médico.
     *
     * @param nombres los nombres a asignar.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos del médico.
     *
     * @return los apellidos del médico.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del médico.
     *
     * @param apellidos los apellidos a asignar.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene la especialidad profesional del médico.
     *
     * @return la especialidad del médico.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad profesional del médico.
     *
     * @param especialidad la especialidad a asignar.
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Obtiene el número telefónico de contacto del médico.
     *
     * @return el teléfono de contacto.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número telefónico de contacto del médico.
     *
     * @param telefono el teléfono a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el estado de actividad del médico.
     *
     * @return true si el médico está activo; false de lo contrario.
     */
   public boolean isEstado() {
        return estado;
    }

    /**
     * Establece el estado de actividad del médico.
     *
     * @param estado el estado de actividad a asignar.
     */
   public void setEstado(boolean estado) {
        this.estado = estado;
    }


    //-----------------------------------------
    // MÉTODO GUARDAR
    //-----------------------------------------

    /**
     * Registra un nuevo médico en la base de datos con los datos del modelo.
     *
     * @return true si el registro fue exitoso; false de lo contrario.
     */
    public boolean guardarMedico() {
        String sql = "INSERT INTO g2_vet_medicos "
        + "(nombres, apellidos, especialidad, telefono, estado) "
        + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setString(3, especialidad);
            ps.setString(4, telefono);
            ps.setBoolean(5, estado);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //-----------------------------------------
    // MÉTODO LISTAR
    //-----------------------------------------

    /**
     * Obtiene una lista de todos los médicos activos en el sistema.
     *
     * @return una lista de objetos ModeloMedico ordenados por apellidos.
     */
    public ArrayList<ModeloMedico> listarMedicos() {
        ArrayList<ModeloMedico> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_medicos WHERE estado = true ORDER BY apellidos";

        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ModeloMedico m = new ModeloMedico();
                m.setIdMedico(rs.getInt("id_medico"));
                m.setNombres(rs.getString("nombres"));
                m.setApellidos(rs.getString("apellidos"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setTelefono(rs.getString("telefono"));
                m.setEstado(rs.getBoolean("estado"));
                lista.add(m);
            }

        } catch (Exception e) {
            System.err.println("Error listar medicos: " + e.getMessage());
        }

        return lista;
    }

    //-----------------------------------------
    // MÉTODO ACTUALIZAR
    //-----------------------------------------

    /**
     * Actualiza la información de un médico existente en la base de datos.
     *
     * @return true si la actualización fue exitosa; false de lo contrario.
     */
    public boolean actualizarMedico() {
        String sql = "UPDATE g2_vet_medicos "
                + "SET nombres=?, "
                + "apellidos=?, "
                + "especialidad=?, "
                + "telefono=?, "
                + "estado=? "
                + "WHERE id_medico=?";

        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setString(3, especialidad);
            ps.setString(4, telefono);
            ps.setBoolean(5, estado);
            ps.setInt(6, idMedico);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //-----------------------------------------
    // MÉTODO ELIMINAR
    //-----------------------------------------

    /**
     * Desactiva lógicamente a un médico cambiando su estado a false.
     *
     * @return true si se desactivó correctamente; false de lo contrario.
     */
    public boolean eliminarMedico() {
        String sql = "UPDATE g2_vet_medicos SET estado=false WHERE id_medico=?";

        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //-----------------------------------------
    // MÉTODO BUSCAR
    //-----------------------------------------

    /**
     * Busca médicos que coincidan con un criterio en su nombre o apellido.
     *
     * @param textoBuscar el término o texto a buscar.
     * @return una lista de médicos que coinciden con la búsqueda.
     */
    public ArrayList<ModeloMedico> buscarMedico(String textoBuscar) {
        ArrayList<ModeloMedico> lista = new ArrayList<>();
        String sql = "SELECT * FROM g2_vet_medicos "
                + "WHERE nombres LIKE ? "
                + "OR apellidos LIKE ?";

        try (Connection con = CnnDB.getConeccion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + textoBuscar + "%");
            ps.setString(2, "%" + textoBuscar + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModeloMedico m = new ModeloMedico();
                    m.setIdMedico(rs.getInt("id_medico"));
                    m.setNombres(rs.getString("nombres"));
                    m.setApellidos(rs.getString("apellidos"));
                    m.setEspecialidad(rs.getString("especialidad"));
                    m.setTelefono(rs.getString("telefono"));
                    m.setEstado(rs.getBoolean("estado"));
                    lista.add(m);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
