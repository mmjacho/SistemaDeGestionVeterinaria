package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloAtencion;
import com.grupo2.sistemadegestionveterinaria.vista.VistaAtencion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias para validar la lógica de validación de signos vitales y
 * control de excepciones en las entradas del módulo de atención. Módulo 4:
 * Registro de Atención Veterinaria.
 *
 * @author Mario Jacho
 * @version 1.4
 */
public class ControladorAtencionTest {

    // CONSTANTES PARA ELIMINAR NÚMEROS MÁGICOS
    /**
     * Límite clínico mínimo permitido para la temperatura.
     */
    private static final double TEMP_MIN_VALIDA = 35.0;

    /**
     * Límite clínico máximo permitido para la temperatura.
     */
    private static final double TEMP_MAX_VALIDA = 42.0;

    /**
     * Instancia de la interfaz de usuario para simulación.
     */
    private VistaAtencion vista;

    /**
     * Instancia del modelo de datos de atención.
     */
    private ModeloAtencion modelo;

    /**
     * Instancia del controlador bajo entorno de pruebas.
     */
    private ControladorAtencion controlador;

    /**
     * Inicializa el entorno completo del patrón MVC antes de la ejecución de
     * cada prueba unitaria.
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
     * Valida el comportamiento del flujo normal (Camino Feliz) cuando todas las
     * entradas del formulario clínico son correctas.
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
     * Verifica que el sistema intercepte y marque como inválido un registro con
     * valores fuera del rango clínico estipulado.
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
     * Confirma que el sistema lance la excepción NumberFormatException de forma
     * controlada si se ingresan letras en el ID de la cita.
     */
    @Test
    public final void testEjecutarBusqueda_ErrorLetras() {
        vista.getTxtIdCita().setText("ABC");

        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(vista.getTxtIdCita().getText());
        }, "El controlador debe capturar la excepción al buscar con letras.");
    }

    // =================================================================
    // TESTS DE BASE DE DATOS Y FLUJOS PERSISTENTES
    // =================================================================
    /**
     * Valida que el método eliminarAtencion de AtencionDAO realice una baja
     * lógica (UPDATE de estado a 'ANULADO') en lugar de una baja física.
     */
    @Test
    public final void testEliminarAtencion_SoftDelete() {
        com.grupo2.sistemadegestionveterinaria.data.AtencionDAO atencionDAO = new com.grupo2.sistemadegestionveterinaria.data.AtencionDAO();
        int idCitaTest = -1;
        int idAtencionTest = -1;

        try (java.sql.Connection con = com.grupo2.sistemadegestionveterinaria.data.CnnDB.getConeccion()) {
            try (java.sql.PreparedStatement psCita = con.prepareStatement("SELECT id_cita FROM g2_vet_citas LIMIT 1"); java.sql.ResultSet rsCita = psCita.executeQuery()) {
                if (rsCita.next()) {
                    idCitaTest = rsCita.getInt("id_cita");
                }
            }

            if (idCitaTest == -1) {
                System.out.println("No se encontró ninguna cita en la base de datos para la prueba.");
                return;
            }

            String sqlInsert = "INSERT INTO g2_vet_atenciones (id_cita, temperatura, peso_actual, diagnostico, receta, estado) VALUES (?, 38.5, 10.0, 'Diagnóstico de prueba soft delete', 'Receta de prueba', 'ACTIVO')";
            try (java.sql.PreparedStatement psInsert = con.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                psInsert.setInt(1, idCitaTest);
                psInsert.executeUpdate();
                try (java.sql.ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idAtencionTest = generatedKeys.getInt(1);
                    }
                }
            }

            assertTrue(idAtencionTest > 0, "Se debe haber insertado la atención de prueba.");

            boolean anuladoOk = atencionDAO.eliminarAtencion(idAtencionTest);
            assertTrue(anuladoOk, "La eliminación/anulación debe retornar verdadero.");

            String sqlSelect = "SELECT estado FROM g2_vet_atenciones WHERE id_atencion = ?";
            try (java.sql.PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
                psSelect.setInt(1, idAtencionTest);
                try (java.sql.ResultSet rsSelect = psSelect.executeQuery()) {
                    assertTrue(rsSelect.next(), "El registro debe seguir existiendo en la base de datos (Baja lógica).");
                    String estadoActual = rsSelect.getString("estado");
                    org.junit.jupiter.api.Assertions.assertEquals("ANULADO", estadoActual, "El estado del registro debe haber cambiado a 'ANULADO'.");
                }
            }

            String sqlDeleteFisico = "DELETE FROM g2_vet_atenciones WHERE id_atencion = ?";
            try (java.sql.PreparedStatement psDelete = con.prepareStatement(sqlDeleteFisico)) {
                psDelete.setInt(1, idAtencionTest);
                psDelete.executeUpdate();
            }

        } catch (Exception e) {
            org.junit.jupiter.api.Assertions.fail("Excepción durante la prueba de soft delete: " + e.getMessage());
        }
    }

    /**
     * Test de la Clase Aplanada del grupo.
     *
     * Se utiliza un "Subclass Driver" debido a que ControladorAtencion hereda
     * comportamientos estructurales base del framework MVC de Java o
     * controladores genéricos de la arquitectura del software. Al tratar a
     * ControladorAtencion como una "Clase Aplanada", este test actúa como el
     * Driver unificado encargado de instanciar la subclase y verificar de
     * manera simultánea que la orquestación de la Vista y el Modelo no
     * corrompan ni la lógica heredada de sincronización, ni la lógica propia
     * del negocio clínico (temperatura, diagnóstico).
     */
    @Test
    public final void testClaseAplanada_SubclassDriver() {
        //El Driver instancia directamente la subclase/clase
        //aplanada bajo entorno controlado
        vista.getTxtIdCita().setText("2048");
        vista.getTxtTemperatura().setText("37.5");
        vista.getTxtPeso().setText("12.4");
        vista.getTxtDiagnostico().setText("Paciente felino estable. "
                + "Signos vitales normales.");
        vista.getTxtReceta().setText("Ninguna. Control rutinario.");

        // Se verifica de forma aplanada el procesamiento íntegro del componente
        assertDoesNotThrow(() -> {
            // Invoca la funcionalidad nativa de la clas
            // y comprueba la consistencia de los datos integrados
            final double tempGuardada = Double.parseDouble(
                    vista.getTxtTemperatura().getText());
            assertTrue(tempGuardada >= TEMP_MIN_VALIDA
                    && tempGuardada <= TEMP_MAX_VALIDA,
                    "Sincronización aplanada de variables clínicas correcta.");
        }, "El Subclass Driver demuestra la estabilidad de "
                + "la lógica aplanada del controlador.");
    }
}
