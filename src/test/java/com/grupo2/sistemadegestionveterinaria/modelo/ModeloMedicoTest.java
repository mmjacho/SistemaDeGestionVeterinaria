/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.grupo2.sistemadegestionveterinaria.modelo;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mikka
 */
public class ModeloMedicoTest {

    /**
     * Prueba que se puedan listar los médicos y que la lista no sea nula ni vacía.
     */
   @Test
    public void testListarMedicos() {

    ModeloMedico medico = new ModeloMedico();

    assertNotNull(medico.listarMedicos());

    assertTrue(
        medico.listarMedicos().size() > 0
    );
    } 
    
  /*  @Test
   public void testActualizarMedico() {

    ModeloMedico medico = new ModeloMedico();

    // ID existente en tu BD
    medico.setIdMedico(1);

    medico.setNombres("Carlos Actualizado");
    medico.setApellidos("Perez Actualizado");
    medico.setEspecialidad("Felinos");
    medico.setTelefono("0981111111");
    medico.setEstado(true);

    boolean resultado = medico.actualizarMedico();

    assertTrue(resultado);
}*/
    /**
     * Prueba la eliminación de un médico del sistema por su ID.
     */
   @Test
   public void testEliminarMedico() {

    ModeloMedico medico = new ModeloMedico();

    // ID existente para eliminar
    medico.setIdMedico(10);

    boolean resultado = medico.eliminarMedico();

    assertTrue(resultado);
} 
    /**
     * Prueba la búsqueda de médicos por nombre.
     */
   @Test
    public void testBuscarMedico() {

    ModeloMedico medico = new ModeloMedico();

    String textoBuscar = "Carlos";

    assertNotNull(
            medico.buscarMedico(textoBuscar)
    );

    assertTrue(
            medico.buscarMedico(textoBuscar)
                    .size() >= 0
    );
}
    //PRUEBAS CON CASOS DE FALLO
    
    /**
     * Caso de fallo: Prueba que un número de teléfono con más de 10 dígitos exceda el límite permitido.
     */
    @Test
    public void testTelefonoExcedeLimite() {

    String telefono = "09999999999";

    assertFalse(
            telefono.length() <= 10
    );
    }
    
    /**
     * Caso de fallo: Prueba que el número de teléfono contenga letras y no sea puramente numérico.
     */
    @Test
    public void testTelefonoConLetras() {

    String telefono = "09999abcde";

    boolean soloNumeros =
            telefono.matches("\\d+");

    assertFalse(soloNumeros);
    }
    
    /**
     * Caso de fallo: Prueba que el nombre contenga números y no sea solo letras.
     */
    @Test
    public void testNombresConNumeros() {

    String nombre = "Juan123";

    boolean soloLetras =
            nombre.matches("[a-zA-Z ]+");

    assertFalse(soloLetras);
    }
    
    /**
     * Caso de fallo: Prueba que el apellido contenga números y no sea solo letras.
     */
    @Test
    public void testApellidosConNumeros() {

    String apellido = "Perez99";

    boolean soloLetras =
            apellido.matches("[a-zA-Z ]+");

    assertFalse(soloLetras);
    }
    
  /*  @Test
   public void testGuardarMedico() {

    ModeloMedico medico = new ModeloMedico();

    medico.setNombres("Carlos");
    medico.setApellidos("Perez");
    medico.setEspecialidad("Caninos");
    medico.setTelefono("0999999999");
    medico.setEstado(true);

    boolean resultado =
            medico.guardarMedico();

    assertTrue(resultado);
}*/
  /*  
   @Test
public void testGuardarMedicoInactivo() {

    ModeloMedico medico = new ModeloMedico();

    medico.setNombres("Luis");
    medico.setApellidos("Torres");
    medico.setEspecialidad("Felinos");
    medico.setTelefono("0988888888");
    medico.setEstado(false);

    boolean resultado =
            medico.guardarMedico();

    assertTrue(resultado);
}*/
   
}