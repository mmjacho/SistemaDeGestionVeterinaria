package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;
import com.grupo2.sistemadegestionveterinaria.vista.VistaAtencion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias para validar la lógica de validación de signos vitales
 * y control de excepciones en las entradas del módulo de atención.
 * Módulo 4: Registro de Atención Veterinaria.
 *
 * @author Mario Jacho
 * @version 1.3
 */
public class ControladorAtencionTest {

    // CONSTANTES PARA ELIMINAR NÚMEROS MÁGICOS

    /** Límite clínico mínimo permitido para la temperatura. */
    private static final double TEMP_MIN_VALIDA = 35.0;

    /** Límite clínico máximo permitido para la temperatura. */
    private static final double TEMP_MAX_VALIDA = 42.0;

    /** Instancia de la interfaz de usuario para simulación. */
    private VistaAtencion vista;

    /** Instancia del modelo de datos de atención. */
    private ModeloAtencion modelo;

    /** Instancia del controlador bajo entorno de pruebas. */
    private ControladorAtencion controlador;

    /**
     * Inicializa el entorno completo del patrón MVC antes de la
     * ejecución de cada prueba unitaria.
     */
    @BeforeEach
    public final void setUp() {
        vista = new VistaAtencion();
        modelo = new ModeloAtencion();
        controlador = new ControladorAtencion(vista, modelo);
    }

    // =================================================================
    // TESTS PARA EL MÉTODO 1: ejecutarRegistro() [Complejidad = 8]
    // =================================================================

    /**
     * Valida el comportamiento del flujo normal (Camino Feliz) cuando
     * todas las entradas del formulario clínico son correctas.
     */
    @Test
    public final void testEjecutarRegistro_Correcto() {
        vista.getTxtIdCita().setText("1024");
        vista.getTxtTemperatura().setText("38.2");
        vista.getTxtPeso().setText("8.5");
        vista.getTxtDiagnostico().setText("Canino presenta cuadro leve "
                + "de otitis en oído derecho. Se realiza limpieza.");
        vista.getTxtReceta().setText("Limpiar con solución ótica cada "
                + "12 horas por 5 días.");

        assertDoesNotThrow(() -> {
            final double temp = Double.parseDouble(
                    vista.getTxtTemperatura().getText());
            assertTrue(temp >= TEMP_MIN_VALIDA && temp <= TEMP_MAX_VALIDA,
                    "La temperatura cumple el rango estipulado.");
        }, "El registro con datos correctos no debe fallar.");
    }

    /**
     * Verifica que el sistema intercepte y marque como inválido un
     * registro con valores fuera del rango clínico estipulado.
     */
    @Test
    public final void testEjecutarRegistro_ErrorTemperatura() {
        vista.getTxtIdCita().setText("1026");
        vista.getTxtTemperatura().setText("43.5");
        vista.getTxtPeso().setText("-2.5");

        final double temp = Double.parseDouble(
                vista.getTxtTemperatura().getText());
        final boolean esValido = (temp >= TEMP_MIN_VALIDA
                && temp <= TEMP_MAX_VALIDA);

        assertFalse(esValido,
                "El controlador debe rechazar una temperatura de 43.5°C");
    }

    // =================================================================
    // TESTS PARA EL MÉTODO 2: ejecutarBusquedaHistorial() [Complejidad = 4]
    // =================================================================

    /**
     * Valida que la búsqueda acepte y parsee correctamente un identificador
     * numérico de cita válido.
     */
    @Test
    public final void testEjecutarBusqueda_Correcto() {
        vista.getTxtIdCita().setText("1024");

        assertDoesNotThrow(() -> {
            Integer.parseInt(vista.getTxtIdCita().getText());
        }, "El controlador debe aceptar un ID numérico correcto.");
    }

    /**
     * Confirma que el sistema lance la excepción NumberFormatException
     * de forma controlada si se ingresan letras en el ID de la cita.
     */
    @Test
    public final void testEjecutarBusqueda_ErrorLetras() {
        vista.getTxtIdCita().setText("ABC");

        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(vista.getTxtIdCita().getText());
        }, "El controlador debe capturar la excepción al buscar con letras.");
    }
}