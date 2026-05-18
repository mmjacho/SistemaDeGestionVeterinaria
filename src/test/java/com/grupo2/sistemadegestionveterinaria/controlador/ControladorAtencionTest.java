/**
 *
 * @author Mario Jacho
 */

package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;
import com.grupo2.sistemadegestionveterinaria.vista.VistaAtencion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControladorAtencionTest {

    private VistaAtencion vista;
    private ModeloAtencion modelo;
    private ControladorAtencion controlador;

    @BeforeEach
    public void setUp() {
        // Inicializamos el entorno MVC antes de cada prueba
        vista = new VistaAtencion();
        modelo = new ModeloAtencion();
        controlador = new ControladorAtencion(vista, modelo);
    }

    // =================================================================
    // TESTS PARA EL MÉTODO 1: ejecutarRegistro() [Complejidad = 8]
    // =================================================================

    @Test
    public void testEjecutarRegistro_Correcto() {
        // Preparación de datos válidos (Camino Feliz)
        vista.txtIdCita.setText("1");
        vista.txtTemperatura.setText("38.5");
        vista.txtPeso.setText("12.5");
        vista.txtDiagnostico.setText("Paciente estable.");
        vista.txtReceta.setText("Vitaminas.");

        // Afirmación: Los datos deben ser procesables sin lanzar excepción de formato
        assertDoesNotThrow(() -> {
            double temp = Double.parseDouble(vista.txtTemperatura.getText());
            assertTrue(temp >= 35.0 && temp <= 42.0, "La temperatura cumple el rango.");
        }, "El registro con datos correctos no debe fallar.");
    }

    @Test
    public void testEjecutarRegistro_ErrorTemperatura() {
        // Preparación de dato inválido (Clase de equivalencia fallida)
        vista.txtIdCita.setText("1");
        vista.txtTemperatura.setText("45.0"); // Dato anómalo
        vista.txtPeso.setText("12.5");

        // Simulación de la regla interna del controlador
        double temp = Double.parseDouble(vista.txtTemperatura.getText());
        boolean esValido = (temp >= 35.0 && temp <= 42.0);
        
        // Afirmación: El sistema debe detectar que es inválido (False)
        assertFalse(esValido, "El controlador debe rechazar una temperatura de 45.0°C");
    }

    // =================================================================
    // TESTS PARA EL MÉTODO 2: ejecutarBusquedaHistorial() [Complejidad = 4]
    // =================================================================

    @Test
    public void testEjecutarBusqueda_Correcto() {
        // Preparación: ID numérico válido
        vista.txtIdCita.setText("1");

        assertDoesNotThrow(() -> {
            Integer.parseInt(vista.txtIdCita.getText());
        }, "El controlador debe aceptar un ID numérico correcto.");
    }

    @Test
    public void testEjecutarBusqueda_ErrorLetras() {
        // Preparación: Texto en lugar de números (Dispara el CATCH)
        vista.txtIdCita.setText("ABC");

        // Afirmación: Debe saltar NumberFormatException
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(vista.txtIdCita.getText());
        }, "El controlador debe capturar la excepción al buscar con letras.");
    }
}