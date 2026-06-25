package com.grupo2.sistemadegestionveterinaria.vista;

import com.grupo2.sistemadegestionveterinaria.controlador.ControladorCita;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaCita extends JFrame {

    public JComboBox<String> cbxMedico, cbxMascota, cbxTipoReporte;
    public JTextField txtFecha, txtHora, txtFiltroFecha;
    public JButton btnAgendar, btnReprogramar, btnCancelar, btnFiltrar, btnLimpiarFiltro, btnReportes, btnExportarCSV, btnImprimir;
    public JTable tablaCitas;
    public JLabel lblResumen;

    public VistaCita() {
        setTitle("Módulo Integrado: Gestión e Historial de Citas Veterinarias");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- PANEL NORTE: FILTRADO Y BUSQUEDA ---
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelNorte.setBorder(BorderFactory.createTitledBorder("Filtrado Rápido"));
        panelNorte.add(new JLabel("Fecha Filtro (DD/MM/YYYY):"));
        txtFiltroFecha = new JTextField(10);
        panelNorte.add(txtFiltroFecha);
        btnFiltrar = new JButton("Filtrar por Fecha");
        btnLimpiarFiltro = new JButton("Limpiar Filtros");
        panelNorte.add(btnFiltrar);
        panelNorte.add(btnLimpiarFiltro);

        // --- PANEL OESTE: FORMULARIO CRUD ---
        JPanel panelOeste = new JPanel(new BorderLayout(5, 5));
        panelOeste.setBorder(BorderFactory.createTitledBorder("Datos de la Cita"));

        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 10));
        panelCampos.add(new JLabel("Médico:"));
        cbxMedico = new JComboBox<>(new String[]{"(Seleccione un médico)"});
        panelCampos.add(cbxMedico);

        panelCampos.add(new JLabel("Mascota:"));
        cbxMascota = new JComboBox<>(new String[]{"(Seleccione una mascota)"});
        panelCampos.add(cbxMascota);

        panelCampos.add(new JLabel("Fecha (DD/MM/YYYY):"));
        txtFecha = new JTextField();
        panelCampos.add(txtFecha);

        panelCampos.add(new JLabel("Hora (HH:MM):"));
        txtHora = new JTextField();
        panelCampos.add(txtHora);

        JPanel panelBotonesCRUD = new JPanel(new GridLayout(3, 1, 5, 5));
        btnAgendar = new JButton("Agendar Cita (Guardar)");
        btnReprogramar = new JButton("Reprogramar Seleccionada");
        btnCancelar = new JButton("Cancelar Cita (Eliminar)");
        panelBotonesCRUD.add(btnAgendar);
        panelBotonesCRUD.add(btnReprogramar);
        panelBotonesCRUD.add(btnCancelar);

        panelOeste.add(panelCampos, BorderLayout.NORTH);
        panelOeste.add(panelBotonesCRUD, BorderLayout.SOUTH);

        // --- PANEL CENTRAL: TABLA DE CITAS ---
        tablaCitas = new JTable();
        tablaCitas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Cita", "Médico ID", "Mascota ID", "Fecha", "Hora", "Estado"}
        ));
        JScrollPane scrollTabla = new JScrollPane(tablaCitas);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Listado General / Resultados del Reporte"));

        // --- PANEL SUR: REPORTES INTEGRADOS ---
        JPanel panelSur = new JPanel(new BorderLayout(5, 5));
        panelSur.setBorder(BorderFactory.createTitledBorder("Módulo de Reportes de Citas"));

        JPanel panelAccionesReporte = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelAccionesReporte.add(new JLabel("Tipo de Reporte:"));
        cbxTipoReporte = new JComboBox<>(new String[]{"Histórico General", "Citas del Día", "Citas por Veterinario"});
        panelAccionesReporte.add(cbxTipoReporte);

        btnReportes = new JButton("Generar Vista de Reporte");
        btnExportarCSV = new JButton("Guardar en CSV");
        btnImprimir = new JButton("Imprimir Reporte");

        panelAccionesReporte.add(btnReportes);
        panelAccionesReporte.add(btnExportarCSV); // <-- Corregido aquí (sin espacio)
        panelAccionesReporte.add(btnImprimir);

        lblResumen = new JLabel("Resumen de Reporte: Seleccione un criterio y presione generar.");
        lblResumen.setHorizontalAlignment(SwingConstants.CENTER);

        panelSur.add(panelAccionesReporte, BorderLayout.NORTH);
        panelSur.add(lblResumen, BorderLayout.SOUTH);

        // Ensamblado en el contenedor principal
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        panelPrincipal.add(panelOeste, BorderLayout.WEST);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaCita vista = new VistaCita();
            ControladorCita controlador = new ControladorCita(vista);
            vista.setVisible(true);
        });
    }
}
