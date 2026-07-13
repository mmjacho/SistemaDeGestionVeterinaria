package com.grupo2.sistemadegestionveterinaria.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.grupo2.sistemadegestionveterinaria.modelo.Factura;
import com.grupo2.sistemadegestionveterinaria.modelo.DetalleFactura;

/**
 * Clase de Acceso a Datos (DAO) para la gestión de facturas y sus detalles
 * en la base de datos. Permite realizar operaciones transaccionales para
 * el registro completo de facturas.
 *
 * @author Grupo 2
 * @version 1.0
 */
public class FacturaDAO {

  /**
   * Constructor por defecto de FacturaDAO.
   */
  public FacturaDAO() {
  }

  /**
   * Registra una factura completa y sus detalles de manera transaccional.
   *
   * @param factura la cabecera de la factura con los totales.
   * @param detalles la lista de detalles asociados a la factura.
   * @return true si el registro fue exitoso; false de lo contrario.
   * @throws Exception si ocurre algún error durante la conexión o inserción.
   */
  public boolean registrarFacturaCompleta(Factura factura, List<DetalleFactura> detalles) throws Exception {
    String sqlFactura = "INSERT INTO g2_vet_factura (nombre_cliente, cedula_cliente, subtotal, iva, total, creado) VALUES (?, ?, ?, ?, ?, NOW())";
    String sqlDetalle = "INSERT INTO g2_vet_detalle_factura (id_factura, concepto, precio_unitario, cantidad, precio_final) VALUES (?, ?, ?, ?, ?)";

    try (Connection con = CnnDB.getConeccion()) {
      try {
        // Desactivamos el auto-commit para manejar la transacción de forma segura
        con.setAutoCommit(false);

        int idFacturaGenerado = 0;
        // 1. Insertar la Factura
        try (PreparedStatement psFactura = con.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
          psFactura.setString(1, factura.getNombreCliente());
          psFactura.setString(2, factura.getCedulaCliente());
          psFactura.setDouble(3, factura.getSubtotal());
          psFactura.setDouble(4, factura.getIva());
          psFactura.setDouble(5, factura.getTotal());
          psFactura.executeUpdate();

          // Obtener el ID de la factura recién creada
          try (ResultSet rs = psFactura.getGeneratedKeys()) {
            if (rs.next()) {
              idFacturaGenerado = rs.getInt(1);
            }
          }
        }

        // 2. Insertar cada Detalle ligado al ID de la Factura
        try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
          for (DetalleFactura detalle : detalles) {
            psDetalle.setInt(1, idFacturaGenerado);
            psDetalle.setString(2, detalle.getConcepto());
            psDetalle.setDouble(3, detalle.getPrecioUnitario());
            psDetalle.setInt(4, detalle.getCantidad());
            psDetalle.setDouble(5, detalle.getPrecioFinal());
            psDetalle.addBatch(); // Agrega a la cola
          }
          psDetalle.executeBatch(); // Ejecuta todas las inserciones de detalles juntas
        }

        // Confirmar transacción
        con.commit();
        return true;

      } catch (SQLException e) {
        System.err.println("Error al registrar factura: " + e.getMessage());
        try {
          con.rollback(); // Si algo falla, deshacer todo
        } catch (SQLException ex) {
          System.err.println("Error en rollback: " + ex.getMessage());
        }
        return false;
      } finally {
        try {
          con.setAutoCommit(true);
        } catch (SQLException e) {
          System.err.println("Error al restaurar autoCommit: " + e.getMessage());
        }
      }
    }
  }

  /**
   * Obtiene la lista completa de todas las facturas ordenadas por fecha.
   *
   * @return una lista de objetos Factura.
   * @throws Exception si ocurre algún error durante la consulta.
   */
  public List<Factura> obtenerTodasLasFacturas() throws Exception {
    List<Factura> lista = new ArrayList<>();
    String sql = "SELECT * FROM g2_vet_factura WHERE eliminado IS NULL ORDER BY id_factura DESC";
    try (Connection con = CnnDB.getConeccion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        Factura f = new Factura();
        f.setIdFactura(rs.getInt("id_factura"));
        f.setNombreCliente(rs.getString("nombre_cliente"));
        f.setCedulaCliente(rs.getString("cedula_cliente"));
        f.setFechaEmision(rs.getTimestamp("fecha_emision"));
        f.setSubtotal(rs.getDouble("subtotal"));
        f.setIva(rs.getDouble("iva"));
        f.setTotal(rs.getDouble("total"));
        lista.add(f);
      }
    } catch (SQLException e) {
      System.err.println("Error al listar facturas: " + e.getMessage());
    }
    return lista;
  }

  /**
   * Elimina una factura específica de la base de datos según su ID.
   *
   * @param idFactura el identificador único de la factura.
   * @return true si se eliminó correctamente; false de lo contrario.
   * @throws Exception si ocurre algún error durante la eliminación.
   */
  public boolean eliminarFactura(int idFactura) throws Exception {
    String sql = "UPDATE g2_vet_factura SET eliminado = NOW() WHERE id_factura = ?";
    try (Connection con = CnnDB.getConeccion();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, idFactura);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.err.println("Error al eliminar factura: " + e.getMessage());
      return false;
    }
  }

  /**
   * Actualiza el nombre y cédula del cliente en una factura existente.
   *
   * @param idFactura el identificador único de la factura.
   * @param nuevoNombre el nuevo nombre del cliente.
   * @param nuevaCedula la nueva cédula del cliente.
   * @return true si se actualizó correctamente; false de lo contrario.
   * @throws Exception si ocurre algún error durante la actualización.
   */
  public boolean actualizarFactura(int idFactura, String nuevoNombre, String nuevaCedula) throws Exception {
    String sql = "UPDATE g2_vet_factura SET nombre_cliente = ?, cedula_cliente = ?, actualizado = NOW() WHERE id_factura = ?";
    try (Connection con = CnnDB.getConeccion();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, nuevoNombre);
      ps.setString(2, nuevaCedula);
      ps.setInt(3, idFactura);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.err.println("Error al actualizar factura: " + e.getMessage());
      return false;
    }
  }
}
