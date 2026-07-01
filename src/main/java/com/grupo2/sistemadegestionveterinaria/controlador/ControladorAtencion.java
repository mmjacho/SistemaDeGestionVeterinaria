package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.data.AtencionDAO;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;
import com.grupo2.sistemadegestionveterinaria.vista.VistaAtencion;
import com.grupo2.sistemadegestionveterinaria.vista.VistaMenuPrincipal;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.util.List;
import java.util.Map;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.print.PrinterException;

/**
 * Coordinador lógico y controlador para el módulo de Gestión de
 * Atención Veterinaria. Enlaza los eventos de la interfaz gráfica con
 * las transacciones de persistencia de datos.
 * Módulo 4: Registro de Atención Veterinaria.
 *
 * @author Mario Jacho
 * @version 1.1
 */
public class ControladorAtencion {

    // CONSTANTES PARA ELIMINAR NÚMEROS MÁGICOS
    /**
     * Índice de la columna ID Atención en el modelo de tabla.
     */
    private static final int COL_IDX_ID = 0;

    /**
     * Índice de la columna Diagnóstico en el modelo de tabla.
     */
    private static final int COL_IDX_DIAG = 1;

    /**
     * Índice de la columna Receta en el modelo de tabla.
     */
    private static final int COL_IDX_RECE = 2;

    /**
     * Índice de la columna Temperatura en el modelo de tabla.
     */
    private static final int COL_IDX_TEMP = 3;

    /**
     * Índice de la columna Peso en el modelo de tabla.
     */
    private static final int COL_IDX_PESO = 4;

    /**
     * Límite clínico mínimo permitido para la temperatura.
     */
    private static final double TEMP_MIN = 35.0;

    /**
     * Límite clínico máximo permitido para la temperatura.
     */
    private static final double TEMP_MAX = 42.0;

    /**
     * Dimensión de ancho predeterminada de la vista previa.
     */
    private static final int ANCHO_DIALOGO = 750;

    /**
     * Dimensión de alto predeterminada de la vista previa.
     */
    private static final int ALTO_DIALOGO = 650;

    /**
     * Tamaño estándar de fuente para el botón del reporte.
     */
    private static final int DIALOG_FONT_SIZE = 13;

    /**
     * Componente Rojo del botón del reporte.
     */
    private static final int BLUE_BTN_R = 52;

    /**
     * Componente Verde del botón del reporte.
     */
    private static final int BLUE_BTN_G = 152;

    /**
     * Componente Azul del botón del reporte.
     */
    private static final int BLUE_BTN_B = 219;

    /**
     * Instancia de la interfaz de usuario gestionada.
     */
    private final VistaAtencion vista;

    /**
     * Instancia del modelo de datos de atención clínica.
     */
    private final ModeloAtencion modelo;

    /**
     * Componente de acceso a datos para operaciones CRUD.
     */
    private final AtencionDAO dao;

    /**
     * Respaldo en memoria del nombre del paciente actual.
     */
    private String nombreMascotaActual = "--";

    /**
     * Respaldo en memoria del nombre del propietario actual.
     */
    private String nombreDuenoActual = "--";

    /**
     * Respaldo en memoria del nombre del veterinario actual.
     */
    private String nombreMedicoActual = "--";

    /**
     * Separación horizontal interna para los paneles del reporte.
     */
    private static final int GAP_H_REPORT_LAYOUT = 10;

    /**
     * Separación vertical interna para los paneles del reporte.
     */
    private static final int GAP_V_REPORT_LAYOUT = 10;

    /**
     * Constructor de la clase ControladorAtencion. Enlaza el flujo de
     * componentes visuales y suscribe los gestores de eventos del lienzo
     * clínico.
     *
     * @param pVista Lienzo gráfico de la interfaz de usuario.
     * @param pModelo Estructura de datos del dominio clínico.
     */
    public ControladorAtencion(final VistaAtencion pVista,
            final ModeloAtencion pModelo) {
        this.vista = pVista;
        this.modelo = pModelo;
        this.dao = new AtencionDAO();
        configurarListeners();
    }

    /**
     * Suscribe los disparadores de eventos sobre el conjunto de componentes
     * encapsulados de la ventana gráfica.
     */
    private void configurarListeners() {
        this.vista.getBtnRegistrarAtencion().addActionListener(e
                -> ejecutarRegistro());
        this.vista.getBtnBuscarHistorial().addActionListener(e
                -> ejecutarBusquedaHistorial());
        this.vista.getBtnActualizar().addActionListener(e
                -> ejecutarActualizacion());
        this.vista.getBtnEliminar().addActionListener(e
                -> ejecutarEliminacion());
        this.vista.getBtnGenerarReporte().addActionListener(e
                -> ejecutarReporte());

        this.vista.getTablaHistorial().getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        cargarCamposDesdeTabla();
                    }
                });

        this.vista.getTxtFiltroDiagnostico().addKeyListener(
                new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent evt) {
                filtrarTabla();
            }
        });

        this.vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                vista.dispose();
                new VistaMenuPrincipal().setVisible(true);
            }
        });
    }

    /**
     * Procesa la lectura, validación e inserción de una nueva ficha clínica de
     * atención médica.
     */
    private void ejecutarRegistro() {
        try {
            if (vista.getTxtIdCita().getText().trim().isEmpty()
                    || vista.getTxtTemperatura().getText().trim().isEmpty()
                    || vista.getTxtPeso().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "Todos los campos numéricos son obligatorios.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCita = Integer.parseInt(vista.getTxtIdCita().getText()
                    .replaceAll("\\s+", ""));

            if (!dao.existeCita(idCita)) {
                JOptionPane.showMessageDialog(vista,
                        "El ID de cita provisto no existe en la agenda.",
                        "Fallo de Integración", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double temperatura = Double.parseDouble(vista.getTxtTemperatura()
                    .getText().replaceAll("\\s+", "").replace(",", "."));
            if (temperatura < TEMP_MIN || temperatura > TEMP_MAX) {
                JOptionPane.showMessageDialog(vista,
                        "Temperatura fuera de rango clínico válido "
                        + "(35.0°C - 42.0°C).", "Validación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            double peso = Double.parseDouble(vista.getTxtPeso().getText()
                    .replaceAll("\\s+", "").replace(",", "."));
            if (peso <= 0) {
                JOptionPane.showMessageDialog(vista,
                        "El peso debe ser mayor a 0 Kg.", "Validación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String diagnostico = vista.getTxtDiagnostico().getText().trim();
            String receta = vista.getTxtReceta().getText().trim();

            if (diagnostico.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "El campo de diagnóstico no puede quedar vacío.",
                        "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelo.setIdCita(idCita);
            modelo.setTemperatura(temperatura);
            modelo.setPesoActual(peso);
            modelo.setDiagnostico(diagnostico);
            modelo.setReceta(receta);

            if (dao.registrarAtencion(modelo)) {
                JOptionPane.showMessageDialog(vista,
                        "Ficha de atención clínica almacenada correctamente.");
                ejecutarBusquedaHistorial();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Error al guardar. Verifique si la cita ya posee "
                        + "una atención registrada.", "Error de Persistencia",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Verifique los campos numéricos.", "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Ejecuta la consulta relacional del historial clínico asociado a la
     * mascota vinculada con la cita provista.
     */
    private void ejecutarBusquedaHistorial() {
        try {
            if (vista.getTxtIdCita().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "Ingrese el ID de la Cita para cargar el historial.");
                return;
            }

            int idCita = Integer.parseInt(
                    vista.getTxtIdCita().getText().trim());
            Map<String, String> datosCita = dao.obtenerDatosCita(idCita);

            if (datosCita.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "No se encontró la cita en el sistema.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                vista.getLblNombreMascota().setText("Mascota: --");
                vista.getLblNombreDueno().setText("Dueño: --");
                vista.getLblNombreMedico().setText("Médico: --");
                return;
            }

            nombreMascotaActual = datosCita.get("mascota");
            nombreDuenoActual = datosCita.get("dueno");
            nombreMedicoActual = datosCita.get("medico");

            vista.getLblNombreMascota().setText("Mascota: "
                    + nombreMascotaActual);
            vista.getLblNombreDueno().setText("Dueño: " + nombreDuenoActual);
            vista.getLblNombreMedico().setText("Médico: " + nombreMedicoActual);

            int idMascota = dao.obtenerIdMascotaPorCita(idCita);
            if (idMascota == -1) {
                JOptionPane.showMessageDialog(vista,
                        "No se encontró una mascota asociada a la Cita.",
                        "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<ModeloAtencion> historial = dao
                    .obtenerHistorialPorMascota(idMascota);
            DefaultTableModel modeloTabla = vista.getModeloTablaDef();
            modeloTabla.setRowCount(0);

            for (ModeloAtencion atencion : historial) {
                Object[] fila = {
                    atencion.getIdAtencion(),
                    atencion.getDiagnostico(),
                    atencion.getReceta(),
                    atencion.getTemperatura(),
                    atencion.getPesoActual()
                };
                modeloTabla.addRow(fila);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                    "El ID de la Cita debe ser numérico.");
        }
    }

    /**
     * Actualiza la descripción diagnóstica y receta de una atención
     * seleccionada desde el grid de datos.
     */
    private void ejecutarActualizacion() {
        int filaSeleccionada = vista.getTablaHistorial().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione un registro de la tabla para actualizar.");
            return;
        }

        int filaModelo = vista.getTablaHistorial()
                .convertRowIndexToModel(filaSeleccionada);
        int idAtencion = (int) vista.getModeloTablaDef()
                .getValueAt(filaModelo, COL_IDX_ID);
        String nuevoDiagnostico = vista.getTxtDiagnostico().getText().trim();
        String nuevaReceta = vista.getTxtReceta().getText().trim();

        if (nuevoDiagnostico.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Escriba el nuevo diagnóstico antes de actualizar.");
            return;
        }

        if (dao.actualizarDiagnostico(idAtencion, nuevoDiagnostico,
                nuevaReceta)) {
            JOptionPane.showMessageDialog(vista,
                    "Diagnóstico y Receta actualizados con éxito.");
            ejecutarBusquedaHistorial();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vista,
                    "Ocurrió un error al intentar actualizar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Remueve de forma lógica o física una ficha de atención médica previa tras
     * la confirmación explícita del operador.
     */
    private void ejecutarEliminacion() {
        int filaSeleccionada = vista.getTablaHistorial().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione una atención de la tabla para eliminar.");
            return;
        }

        int filaModelo = vista.getTablaHistorial()
                .convertRowIndexToModel(filaSeleccionada);
        int idAtencion = (int) vista.getModeloTablaDef()
                .getValueAt(filaModelo, COL_IDX_ID);

        int confirmar = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro de eliminar esta ficha médica?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (dao.eliminarAtencion(idAtencion)) {
                JOptionPane.showMessageDialog(vista,
                        "Registro eliminado exitosamente.");
                ejecutarBusquedaHistorial();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Error al intentar eliminar el registro.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Controla el filtrado adaptativo de registros de la tabla basados en
     * expresiones regulares evaluadas sobre el diagnóstico.
     */
    private void filtrarTabla() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(
                vista.getModeloTablaDef());
        vista.getTablaHistorial().setRowSorter(sorter);
        String texto = vista.getTxtFiltroDiagnostico().getText().trim();
        if (texto.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto,
                    COL_IDX_DIAG));
        }
    }

    /**
     * Procesa la construcción y maquetación de la vista previa del reporte
     * clínico formateado en HTML para su impresión física o exportación a PDF.
     */
    private void ejecutarReporte() {
        if (vista.getTablaHistorial().getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista,
                    "No hay datos en la tabla para generar el reporte.",
                    "Reporte Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idCitaTxt = vista.getTxtIdCita().getText().trim();
        StringBuilder html = new StringBuilder();

        html.append("<html><body style='font-family:Arial; margin:20px;'>");
        html.append("<h2 style='text-align:center; color:#2C3E50;'>")
                .append("REPORTE DE ATENCIÓN VETERINARIA</h2>");
        html.append("<p style='text-align:center; font-size:12px;'>")
                .append("<b>Módulo de Atención Clínica - Grupo 2</b></p>");
        html.append("<hr style='border:1px solid #BDC3C7;'>");

        html.append("<table style='width:100%; font-size:13px;' ")
                .append("cellpadding='4'>");
        html.append("<tr><td><b>ID Cita:</b> ").append(idCitaTxt)
                .append("</td><td><b>Paciente (Mascota):</b> ")
                .append(nombreMascotaActual).append("</td></tr>");
        html.append("<tr><td><b>Propietario:</b> ").append(nombreDuenoActual)
                .append("</td><td><b>Médico Veterinario:</b> ")
                .append(nombreMedicoActual).append("</td></tr></table><br>");

        html.append("<table border='1' cellspacing='0' cellpadding='6' ")
                .append("style='width:100%; border-collapse:collapse;'>");
        html.append("<tr style='background-color:#ECF0F1;'>")
                .append("<th>ID Atenc.</th><th>Diagnóstico</th>")
                .append("<th>Receta</th><th>Temp.</th><th>Peso</th></tr>");

        for (int i = 0; i < vista.getTablaHistorial().getRowCount(); i++) {
            String valId = vista.getTablaHistorial()
                    .getValueAt(i, COL_IDX_ID).toString();
            String valDiag = vista.getTablaHistorial()
                    .getValueAt(i, COL_IDX_DIAG).toString();
            String valRece = vista.getTablaHistorial()
                    .getValueAt(i, COL_IDX_RECE).toString();
            String valTemp = vista.getTablaHistorial()
                    .getValueAt(i, COL_IDX_TEMP).toString();
            String valPeso = vista.getTablaHistorial()
                    .getValueAt(i, COL_IDX_PESO).toString();

            html.append("<tr><td>").append(valId)
                    .append("</td><td>").append(valDiag)
                    .append("</td><td>").append(valRece)
                    .append("</td><td>").append(valTemp)
                    .append(" °C</td><td>").append(valPeso)
                    .append(" Kg</td></tr>");
        }

        html.append("</table><br><br><br><br>");
        html.append("<p style='text-align:right; font-size:12px;'><b>Dr(a). ")
                .append(nombreMedicoActual).append("</b><br>")
                .append("Firma Autorizada</p></body></html>");

        JDialog ventanaReporte = new JDialog(vista,
                "Vista Previa del Reporte Estructurado", true);
        ventanaReporte.setSize(ANCHO_DIALOGO, ALTO_DIALOGO);
        ventanaReporte.setLocationRelativeTo(vista);
        ventanaReporte.setLayout(new BorderLayout(
                GAP_H_REPORT_LAYOUT, GAP_V_REPORT_LAYOUT));

        JEditorPane vistaPrevia = new JEditorPane();
        vistaPrevia.setContentType("text/html");
        vistaPrevia.setText(html.toString());
        vistaPrevia.setEditable(false);

        ventanaReporte.add(new JScrollPane(vistaPrevia), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnImprimir = new JButton("Imprimir / Guardar como PDF");
        btnImprimir.setBackground(new Color(
                BLUE_BTN_R, BLUE_BTN_G, BLUE_BTN_B));
        btnImprimir.setForeground(Color.WHITE);
        btnImprimir.setFont(new Font("Arial", Font.BOLD, DIALOG_FONT_SIZE));

        btnImprimir.addActionListener(e -> {
            try {
                boolean completado = vistaPrevia.print(null, null,
                        true, null, null, true);
                if (completado) {
                    JOptionPane.showMessageDialog(ventanaReporte,
                            "Operación realizada con éxito.", "Reporte",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(ventanaReporte,
                        "Error al gestionar la impresión: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelInferior.add(btnImprimir);
        ventanaReporte.add(panelInferior, BorderLayout.SOUTH);
        ventanaReporte.setVisible(true);
    }

    /**
     * Lee y traslada las propiedades de la fila de atención seleccionada hacia
     * los campos de edición de texto correspondientes de la interfaz.
     */
    private void cargarCamposDesdeTabla() {
        int filaSeleccionada = vista.getTablaHistorial().getSelectedRow();
        if (filaSeleccionada != -1) {
            int filaModelo = vista.getTablaHistorial()
                    .convertRowIndexToModel(filaSeleccionada);

            String diagnostico = vista.getModeloTablaDef()
                    .getValueAt(filaModelo, COL_IDX_DIAG).toString();
            String receta = vista.getModeloTablaDef()
                    .getValueAt(filaModelo, COL_IDX_RECE).toString();
            String temperatura = vista.getModeloTablaDef()
                    .getValueAt(filaModelo, COL_IDX_TEMP).toString();
            String peso = vista.getModeloTablaDef()
                    .getValueAt(filaModelo, COL_IDX_PESO).toString();

            vista.getTxtDiagnostico().setText(diagnostico);
            vista.getTxtReceta().setText(receta);
            vista.getTxtTemperatura().setText(temperatura);
            vista.getTxtPeso().setText(peso);
        }
    }

    /**
     * Vacía la entrada de texto de los cuadros clínicos del formulario.
     */
    private void limpiarCampos() {
        vista.getTxtDiagnostico().setText("");
        vista.getTxtReceta().setText("");
        vista.getTxtTemperatura().setText("");
        vista.getTxtPeso().setText("");
    }
}
