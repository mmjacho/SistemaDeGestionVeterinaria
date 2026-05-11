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

   @Test
    public void testListarMedicos() {

    ModeloMedico medico = new ModeloMedico();

    assertNotNull(medico.listarMedicos());

    assertTrue(
        medico.listarMedicos().size() > 0
    );
    } 
    
    @Test
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
}
   @Test
   public void testEliminarMedico() {

    ModeloMedico medico = new ModeloMedico();

    // ID existente para eliminar
    medico.setIdMedico(10);

    boolean resultado = medico.eliminarMedico();

    assertTrue(resultado);
} 
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
    
    @Test
    public void testTelefonoExcedeLimite() {

    String telefono = "09999999999";

    assertFalse(
            telefono.length() <= 10
    );
    }
    
    @Test
    public void testTelefonoConLetras() {

    String telefono = "09999abcde";

    boolean soloNumeros =
            telefono.matches("\\d+");

    assertFalse(soloNumeros);
    }
    
    @Test
    public void testNombresConNumeros() {

    String nombre = "Juan123";

    boolean soloLetras =
            nombre.matches("[a-zA-Z ]+");

    assertFalse(soloLetras);
    }
    
    @Test
    public void testApellidosConNumeros() {

    String apellido = "Perez99";

    boolean soloLetras =
            apellido.matches("[a-zA-Z ]+");

    assertFalse(soloLetras);
    }
    
    @Test
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
}
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
}
   
}