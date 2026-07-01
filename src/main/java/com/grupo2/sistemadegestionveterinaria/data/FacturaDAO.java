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

public class FacturaDAO {

  public boolean registrarFacturaCompleta(Factura factura, List<DetalleFactura> detalles) throws Exception {
    String sqlFactura = "INSERT INTO factura (nombre_cliente, cedula_cliente, subtotal, iva, total) VALUES (?, ?, ?, ?, ?)";
    String sqlDetalle = "INSERT INTO detalle_factura (id_factura, concepto, precio_unitario, cantidad, precio_final) VALUES (?, ?, ?, ?, ?)";

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

  // 1. LEER (Read): Obtener todas las facturas de la base de datos
  public List<Factura> obtenerTodasLasFacturas() throws Exception {
    List<Factura> lista = new ArrayList<>();
    String sql = "SELECT * FROM factura ORDER BY id_factura DESC";
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

  // 2. ELIMINAR (Delete): Borrar una factura por su ID
  public boolean eliminarFactura(int idFactura) throws Exception {
    String sql = "DELETE FROM factura WHERE id_factura = ?";
    try (Connection con = CnnDB.getConeccion();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, idFactura);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.err.println("Error al eliminar factura: " + e.getMessage());
      return false;
    }
  }

  // 3. ACTUALIZAR (Update): Modificar los datos básicos del cliente en la factura
  public boolean actualizarFactura(int idFactura, String nuevoNombre, String nuevaCedula) throws Exception {
    String sql = "UPDATE factura SET nombre_cliente = ?, cedula_cliente = ? WHERE id_factura = ?";
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
