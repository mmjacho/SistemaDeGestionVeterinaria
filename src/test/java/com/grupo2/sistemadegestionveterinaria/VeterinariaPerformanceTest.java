package com.grupo2.sistemadegestionveterinaria;

import static us.abstracta.jmeter.javadsl.JmeterDsl.testPlan;
import static us.abstracta.jmeter.javadsl.JmeterDsl.threadGroup;
import static us.abstracta.jmeter.javadsl.JmeterDsl.htmlReporter;
import static us.abstracta.jmeter.javadsl.jdbc.JdbcJmeterDsl.jdbcConnectionPool;
import static us.abstracta.jmeter.javadsl.jdbc.JdbcJmeterDsl.jdbcSampler;

import org.junit.jupiter.api.Test;
import java.io.IOException;

/**
 * Pruebas de rendimiento para el sistema de gestión veterinaria usando JMeter
 * DSL bajo el enfoque Performance-as-Code.
 */
public class VeterinariaPerformanceTest {

    /**
     * Número de usuarios virtuales simulados en la prueba.
     */
    private static final int USUARIOS_VIRTUALES = 50;

    /**
     * Cantidad de iteraciones ejecutadas por cada usuario virtual.
     */
    private static final int ITERACIONES_POR_USUARIO = 5;

    /**
     * Ejecuta un plan de pruebas de rendimiento contra la base de datos del
     * sistema veterinario.
     *
     * @throws IOException si ocurre un error al ejecutar el plan o generar el
     * reporte HTML.
     */
    @Test
    public final void testRendimientoBaseDatos() throws IOException {

        testPlan(
                threadGroup(
                        "Carga Concurrente - Base de Datos",
                        USUARIOS_VIRTUALES,
                        ITERACIONES_POR_USUARIO,
                        jdbcConnectionPool(
                                "MiConexionVeterinaria",
                                com.mysql.cj.jdbc.Driver.class,
                                "jdbc:mysql://www.ecuinfo.net:3306/ugproy1"
                                + "?user=ugproy1&password=UG2026proy1"
                        ),
                        jdbcSampler(
                                "Módulo Mascotas",
                                "MiConexionVeterinaria",
                                "SELECT * FROM g2_vet_mascotas m "
                                + "INNER JOIN g2_vet_clientes c "
                                + "ON m.id_cliente = c.id_cliente;"
                        ),
                        jdbcSampler(
                                "Módulo Citas",
                                "MiConexionVeterinaria",
                                "SELECT * FROM g2_vet_citas;"
                        ),
                        jdbcSampler(
                                "Módulo Médicos",
                                "MiConexionVeterinaria",
                                "SELECT * FROM g2_vet_medicos;"
                        ),
                        htmlReporter("target/jmeter-report")
                )
        ).run();
    }
}
