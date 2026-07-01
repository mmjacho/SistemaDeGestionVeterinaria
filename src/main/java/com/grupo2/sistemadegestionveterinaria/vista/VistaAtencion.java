package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;

/**
 * Interfaz gráfica de usuario para el módulo de Registro de
 * Atención Veterinaria. Proporciona los componentes visuales
 * necesarios para registrar, buscar, actualizar, eliminar y
 * generar reportes del historial clínico de los pacientes.
 * Módulo 4: Registro de Atención Veterinaria.
 *
 * @author Mario Jacho
 * @version 1.1
 */
public class VistaAtencion extends JFrame {

// CONSTANTES PARA ELIMINAR NÚMEROS MÁGICOS

    /** Ancho predeterminado en píxeles de la ventana principal. */
    private static final int ANCHO_VENTANA = 850;

    /** Alto predeterminado en píxeles de la ventana principal. */
    private static final int ALTO_VENTANA = 700;

    /** Margen interno de espaciado para el panel principal. */
    private static final int MARGEN_PANEL = 20;

    /** Separación horizontal interna en el diseño BorderLayoutPanel. */
    private static final int GAP_H_PRINCIPAL = 10;

    /** Separación vertical interna en el diseño BorderLayoutPanel. */
    private static final int GAP_V_PRINCIPAL = 15;

    /** Cantidad de columnas iniciales para el campo del ID de Cita. */
    private static final int COL_ID_CITA = 10;

    /** Cantidad de columnas iniciales para el campo de Filtro. */
    private static final int COL_FILTRO = 12;

    /** Separación horizontal para los elementos del panel Norte. */
    private static final int GAP_H_NORTE = 15;

    /** Número de filas para la rejilla de información de la cita. */
    private static final int FILAS_INFO_CITA = 1;

    /** Número de columnas para la rejilla de información de la cita. */
    private static final int COLS_INFO_CITA = 3;

    /** Separación horizontal interna para el panel de información. */
    private static final int GAP_H_INFO_CITA = 10;

    /** Margen de relleno vertical interno para el borde compuesto. */
    private static final int BORDE_PAD_INFO = 5;

    /** Margen de relleno horizontal interno para el borde compuesto. */
    private static final int BORDE_PAD_INFO_H = 10;

    /** Tamaño de fuente tipográfica para las etiquetas dinámicas. */
    private static final int FUENTE_SIZE = 12;

    /** Número de filas del formulario clínico central. */
    private static final int FILAS_CENTRO = 4;

    /** Número de columnas del formulario clínico central. */
    private static final int COLS_CENTRO = 2;

    /** Distancia de separación general en la rejilla central. */
    private static final int GAP_CENTRO = 10;

    /** Altura inicial en filas para las áreas de texto descriptivo. */
    private static final int AREA_TEXTO_FILAS = 3;

    /** Ancho inicial en columnas para las áreas de texto descriptivo. */
    private static final int AREA_TEXTO_COLS = 20;

    /** Espaciado vertical de separación en el panel superior. */
    private static final int GAP_V_SUPERIOR = 15;

    /** Separación horizontal entre los botones de acción del panel. */
    private static final int GAP_H_BOTONES = 20;

    /** Componente de color Rojo (R) para el fondo del botón eliminar. */
    private static final int ROJO_R = 255;

    /** Componente de color Verde (G) para el fondo del botón eliminar. */
    private static final int ROJO_G = 102;

    /** Componente de color Azul (B) para el fondo del botón eliminar. */
    private static final int ROJO_B = 102;

    /** Componente de color Rojo (R) para el botón generar reporte. */
    private static final int VERDE_R = 102;

    /** Componente de color Verde (G) para el botón generar reporte. */
    private static final int VERDE_G = 204;

    /** Componente de color Azul (B) para el botón generar reporte. */
    private static final int VERDE_B = 102;

    /**
     * Campo de texto para ingresar o mostrar el identificador
     * de la cita médica.
     */
    private final JTextField txtIdCita;

    /**
     * Campo de texto para registrar la temperatura corporal
     * del paciente.
     */
    private final JTextField txtTemperatura;

    /**
     * Campo de texto para registrar el peso actual del paciente.
     */
    private final JTextField txtPeso;

    /**
     * Área de texto estructurada para detallar el diagnóstico
     * clínico.
     */
    private final JTextArea txtDiagnostico;

    /**
     * Área de texto estructurada para detallar la receta o
     * prescripción médica.
     */
    private final JTextArea txtReceta;

    /**
     * Botón para procesar el almacenamiento de una nueva ficha
     * de atención.
     */
    private final JButton btnRegistrarAtencion;

    /**
     * Botón para consultar las citas globales y cargar el
     * historial clínico de la mascota.
     */
    private final JButton btnBuscarHistorial;

    /**
     * Botón para modificar el diagnóstico o receta de una
     * atención seleccionada.
     */
    private final JButton btnActualizar;

    /**
     * Tabla visual para el despliegue del historial clínico
     * del paciente.
     */
    private final JTable tablaHistorial;

    /**
     * Modelo explícito de datos para el control estructurado
     * de las filas de la tabla.
     */
    private final DefaultTableModel modeloTablaDef;

    /**
     * Botón para eliminar permanentemente una ficha de atención
     * médica.
     */
    private final JButton btnEliminar;

    /**
     * Botón para disparar la ventana emergente de vista previa
     * del reporte imprimible.
     */
    private final JButton btnGenerarReporte;

    /**
     * Campo de texto para aplicar filtros dinámicos en tiempo
     * real sobre los diagnósticos.
     */
    private final JTextField txtFiltroDiagnostico;

    /**
     * Etiqueta dinámica para renderizar el nombre de la
     * mascota vinculada.
     */
    private final JLabel lblNombreMascota;

    /**
     * Etiqueta dinámica para renderizar el nombre del
     * propietario de la mascota.
     */
    private final JLabel lblNombreDueno;

    /**
     * Etiqueta dinámica para renderizar el nombre del médico
     * veterinario asignado.
     */
    private final JLabel lblNombreMedico;

    /**
     * Constructor de la clase VistaAtencion. Inicializa las
     * propiedades de la ventana, configura el lienzo principal,
     * maqueta la distribución de paneles mediante layouts y
     * construye los componentes gráficos.
     */
    public VistaAtencion() {
        setTitle("Módulo: Registro de Atención Veterinaria");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(
                new BorderLayout(GAP_H_PRINCIPAL, GAP_V_PRINCIPAL)
        );
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(MARGEN_PANEL,
                        MARGEN_PANEL, MARGEN_PANEL, MARGEN_PANEL)
        );

        // Búsqueda de Cita y Filtro
        JPanel panelNorte = new JPanel(
                new FlowLayout(FlowLayout.LEFT, GAP_H_NORTE, 0)
        );
        panelNorte.add(new JLabel("ID de Cita Médica:"));
        txtIdCita = new JTextField(COL_ID_CITA);
        panelNorte.add(txtIdCita);
        btnBuscarHistorial = new JButton("Cargar Cita / Historial");
        panelNorte.add(btnBuscarHistorial);

        panelNorte.add(new JLabel("Filtrar por Diagnóstico:"));
        txtFiltroDiagnostico = new JTextField(COL_FILTRO);
        panelNorte.add(txtFiltroDiagnostico);

        // Panel de Información Dinámica Cruzada de la Cita
        JPanel panelInfoCita = new JPanel(
                new GridLayout(FILAS_INFO_CITA, COLS_INFO_CITA,
                        GAP_H_INFO_CITA, 0)
        );
        panelInfoCita.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Detalles de la Cita"),
                BorderFactory.createEmptyBorder(BORDE_PAD_INFO,
                        BORDE_PAD_INFO_H, BORDE_PAD_INFO, BORDE_PAD_INFO_H)
        ));

        lblNombreMascota = new JLabel("Mascota: --");
        lblNombreMascota.setFont(
                new Font("Arial", Font.BOLD, FUENTE_SIZE)
        );
        lblNombreDueno = new JLabel("Dueño: --");
        lblNombreDueno.setFont(
                new Font("Arial", Font.BOLD, FUENTE_SIZE)
        );
        lblNombreMedico = new JLabel("Médico: --");
        lblNombreMedico.setFont(
                new Font("Arial", Font.BOLD, FUENTE_SIZE)
        );

        panelInfoCita.add(lblNombreMascota);
        panelInfoCita.add(lblNombreDueno);
        panelInfoCita.add(lblNombreMedico);

        // Formulario Clínico
        JPanel panelCentro = new JPanel(
                new GridLayout(FILAS_CENTRO, COLS_CENTRO,
                        GAP_CENTRO, GAP_CENTRO)
        );
        panelCentro.add(new JLabel("Temperatura (°C):"));
        txtTemperatura = new JTextField();
        panelCentro.add(txtTemperatura);
        panelCentro.add(new JLabel("Peso Actual (Kg):"));
        txtPeso = new JTextField();
        panelCentro.add(txtPeso);
        panelCentro.add(new JLabel("Diagnóstico Clínico:"));
        txtDiagnostico = new JTextArea(AREA_TEXTO_FILAS,
                AREA_TEXTO_COLS);
        txtDiagnostico.setLineWrap(true);
        panelCentro.add(new JScrollPane(txtDiagnostico));
        panelCentro.add(new JLabel("Receta Médica:"));
        txtReceta = new JTextArea(AREA_TEXTO_FILAS,
                AREA_TEXTO_COLS);
        txtReceta.setLineWrap(true);
        panelCentro.add(new JScrollPane(txtReceta));

        // Agrupación Superior
        JPanel panelSuperiorAgrupado = new JPanel(
                new BorderLayout(0, GAP_V_SUPERIOR)
        );
        panelSuperiorAgrupado.add(panelNorte, BorderLayout.NORTH);
        panelSuperiorAgrupado.add(panelInfoCita, BorderLayout.CENTER);
        panelSuperiorAgrupado.add(panelCentro, BorderLayout.SOUTH);

        // Botones de acción
        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, GAP_H_BOTONES, 0)
        );
        btnRegistrarAtencion = new JButton("Registrar Atención");
        btnActualizar = new JButton("Actualizar Diagnóstico");

        btnEliminar = new JButton("Eliminar Registro");
        btnEliminar.setBackground(new Color(ROJO_R, ROJO_G, ROJO_B));

        btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.setBackground(
                new Color(VERDE_R, VERDE_G, VERDE_B)
        );

        panelBotones.add(btnRegistrarAtencion);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGenerarReporte);

        // Estructura limpia de 5 columnas para la tabla
        modeloTablaDef = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Atención", "Diagnóstico", "Receta",
                    "Temp", "Peso"}
        );
        tablaHistorial = new JTable(modeloTablaDef);

        panelPrincipal.add(panelSuperiorAgrupado, BorderLayout.NORTH);
        panelPrincipal.add(
                new JScrollPane(tablaHistorial), BorderLayout.CENTER
        );
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Obtiene el componente del ID de la cita.
     * @return El campo txtIdCita.
     */
    public final JTextField getTxtIdCita() {
        return txtIdCita;
    }

    /**
     * Obtiene el componente de la temperatura.
     * @return El campo txtTemperatura.
     */
    public final JTextField getTxtTemperatura() {
        return txtTemperatura;
    }

    /**
     * Obtiene el componente del peso.
     * @return El campo txtPeso.
     */
    public final JTextField getTxtPeso() {
        return txtPeso;
    }

    /**
     * Obtiene el componente del diagnóstico.
     * @return El área txtDiagnostico.
     */
    public final JTextArea getTxtDiagnostico() {
        return txtDiagnostico;
    }

    /**
     * Obtiene el componente de la receta.
     * @return El área txtReceta.
     */
    public final JTextArea getTxtReceta() {
        return txtReceta;
    }

    /**
     * Obtiene el botón de registrar atención.
     * @return El botón btnRegistrarAtencion.
     */
    public final JButton getBtnRegistrarAtencion() {
        return btnRegistrarAtencion;
    }

    /**
     * Obtiene el botón de buscar historial.
     * @return El botón btnBuscarHistorial.
     */
    public final JButton getBtnBuscarHistorial() {
        return btnBuscarHistorial;
    }

    /**
     * Obtiene el botón de actualizar.
     * @return El botón btnActualizar.
     */
    public final JButton getBtnActualizar() {
        return btnActualizar;
    }

    /**
     * Obtiene la tabla de historial clínico.
     * @return La tabla tablaHistorial.
     */
    public final JTable getTablaHistorial() {
        return tablaHistorial;
    }

    /**
     * Obtiene el modelo por defecto de la tabla.
     * @return El modelo modeloTablaDef.
     */
    public final DefaultTableModel getModeloTablaDef() {
        return modeloTablaDef;
    }

    /**
     * Obtiene el botón de eliminación.
     * @return El botón btnEliminar.
     */
    public final JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Obtiene el botón de generación de reportes.
     * @return El botón btnGenerarReporte.
     */
    public final JButton getBtnGenerarReporte() {
        return btnGenerarReporte;
    }

    /**
     * Obtiene el campo de filtro para el diagnóstico.
     * @return El campo txtFiltroDiagnostico.
     */
    public final JTextField getTxtFiltroDiagnostico() {
        return txtFiltroDiagnostico;
    }

    /**
     * Obtiene la etiqueta dinámica de la mascota.
     * @return La etiqueta lblNombreMascota.
     */
    public final JLabel getLblNombreMascota() {
        return lblNombreMascota;
    }

    /**
     * Obtiene la etiqueta dinámica del dueño.
     * @return La etiqueta lblNombreDueno.
     */
    public final JLabel getLblNombreDueno() {
        return lblNombreDueno;
    }

    /**
     * Obtiene la etiqueta dinámica del médico.
     * @return La etiqueta lblNombreMedico.
     */
    public final JLabel getLblNombreMedico() {
        return lblNombreMedico;
    }
}
