/**
 *
 * @author Steven Armijos
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;

public class VistaFacturacion extends JFrame {

    public JTextField txtIdAtencion, txtSubtotal, txtIva, txtTotal;
    public JButton btnCalcular, btnGenerarFactura, btnBuscar, btnAnular;
    public JTable tablaFacturas;

    public VistaFacturacion() {
        setTitle("Módulo: Facturación de Servicios");
        setSize(750, 500);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelCentro = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCentro.add(new JLabel("ID Atención (Para extraer datos):"));
        txtIdAtencion = new JTextField();
        panelCentro.add(txtIdAtencion);
        panelCentro.add(new JLabel("Subtotal ($):"));
        txtSubtotal = new JTextField();
        txtSubtotal.setEditable(false);
        panelCentro.add(txtSubtotal);
        panelCentro.add(new JLabel("IVA 15% ($):"));
        txtIva = new JTextField();
        txtIva.setEditable(false);
        panelCentro.add(txtIva);
        panelCentro.add(new JLabel("Total a Pagar ($):"));
        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        panelCentro.add(txtTotal);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnCalcular = new JButton("Calcular Totales");
        btnGenerarFactura = new JButton("Generar Factura");
        btnBuscar = new JButton("Buscar");
        btnAnular = new JButton("Anular");

        panelSur.add(btnCalcular);
        panelSur.add(btnGenerarFactura);
        panelSur.add(btnBuscar);
        panelSur.add(btnAnular);

        panelPrincipal.add(panelCentro, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaFacturas = new JTable(new Object[][]{}, new String[]{"ID Fac", "ID Atención", "Cliente", "Subtotal", "Total", "Estado"})), BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaFacturacion().setVisible(true);
        });
    }
}
