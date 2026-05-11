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

public class ModeloMedico {

    // ATRIBUTOS
    private int idMedico;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private String telefono;
    private boolean estado;

    // CONEXIÓN
    Connection con;

    //-----------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------
    public ModeloMedico() {

    }

    //-----------------------------------------
    // GETTERS Y SETTERS
    //-----------------------------------------

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

   public boolean isEstado() {
        return estado;
    }

   public void setEstado(boolean estado) {
        this.estado = estado;
    }


    //-----------------------------------------
    // MÉTODO GUARDAR
    //-----------------------------------------
    public boolean guardarMedico() {

        String sql = "INSERT INTO g2_vet_medicos "
        + "(nombres, apellidos, especialidad, telefono, estado) "
        + "VALUES (?, ?, ?, ?, ?)";

        try {

    con = CnnDB.getConeccion();

    PreparedStatement ps =
            con.prepareStatement(sql);

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
    public ArrayList<ModeloMedico> listarMedicos() {

        ArrayList<ModeloMedico> lista =
                new ArrayList<>();

        String sql = "SELECT * FROM g2_vet_medicos";

        try {

            con = CnnDB.getConeccion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ModeloMedico m =
                        new ModeloMedico();

                m.setIdMedico(
                        rs.getInt("id_medico")
                );

                m.setNombres(
                        rs.getString("nombres")
                );

                m.setApellidos(
                        rs.getString("apellidos")
                );

                m.setEspecialidad(
                        rs.getString("especialidad")
                );

                m.setTelefono(
                        rs.getString("telefono")
                );

                m.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(m);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error listar medicos: "
                    + e.getMessage()
            );
        }

        return lista;
    }
    //-----------------------------------------
// MÉTODO ACTUALIZAR
//-----------------------------------------
public boolean actualizarMedico() {

    String sql =
            "UPDATE g2_vet_medicos "
            + "SET nombres=?, "
            + "apellidos=?, "
            + "especialidad=?, "
            + "telefono=?, "
            + "estado=? "
            + "WHERE id_medico=?";

    try {

        con = CnnDB.getConeccion();

        PreparedStatement ps =
                con.prepareStatement(sql);

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
public boolean eliminarMedico() {

    String sql =
            "UPDATE g2_vet_medicos "
            + "SET estado=false "
            + "WHERE id_medico=?";

    try {

        con = CnnDB.getConeccion();

        PreparedStatement ps =
                con.prepareStatement(sql);

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
public ArrayList<ModeloMedico> buscarMedico(
        String textoBuscar
) {

    ArrayList<ModeloMedico> lista =
            new ArrayList<>();

    String sql =
            "SELECT * FROM g2_vet_medicos "
            + "WHERE nombres LIKE ? "
            + "OR apellidos LIKE ?";

    try {

        con = CnnDB.getConeccion();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(
                1,
                "%" + textoBuscar + "%"
        );

        ps.setString(
                2,
                "%" + textoBuscar + "%"
        );

        ResultSet rs =
                ps.executeQuery();

        while (rs.next()) {

            ModeloMedico m =
                    new ModeloMedico();

            m.setIdMedico(
                    rs.getInt("id_medico")
            );

            m.setNombres(
                    rs.getString("nombres")
            );

            m.setApellidos(
                    rs.getString("apellidos")
            );

            m.setEspecialidad(
                    rs.getString("especialidad")
            );

            m.setTelefono(
                    rs.getString("telefono")
            );
            
            m.setEstado(
            rs.getBoolean("estado")
            );

            lista.add(m);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return lista;
}
}
