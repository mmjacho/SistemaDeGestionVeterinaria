/**
 *
 * @author Galo Izquierdo
 */
package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana gráfica para la gestión de clientes y sus mascotas.
 * Permite registrar la información del propietario y los datos clínicos
 * básicos de la mascota.
 *
 * @author Galo Izquierdo
 * @version 1.0
 */
public class VistaClienteMascota extends JFrame {
    
    // 1. Declaración de variables
    /**
     * Campos de texto para la cédula, nombres, teléfono, nombre de la
     * mascota y raza de la mascota.
     */
    public JTextField txtCedula, txtNombres, txtTelefono, txtNombreMascota, txtRaza;
    /**
     * Selector desplegable para definir la especie de la mascota.
     */
    public JComboBox<String> cbxEspecie;
    /**
     * Botones para guardar, buscar y actualizar registros en la vista.
     */
    public JButton btnGuardar, btnBuscar, btnActualizar;
    /**
     * Tabla para visualizar la lista de clientes y mascotas registrados.
     */
    public JTable tablaDatos;

    // 2. Constructor de la ventana
    /**
     * Constructor de VistaClienteMascota.
     * Configura el título, dimensiones, márgenes y la estructura interna
     * de paneles del formulario y la tabla.
     */
    public VistaClienteMascota() {
        setTitle("Módulo: Registro de Cliente y Mascota");
        setSize(800, 500);
        setLocationRelativeTo(null); // Esto hace que la ventana aparezca centrada en la pantalla
        
        // --- EL TRUCO ESTÉTICO ---
        // Creamos un panel principal con márgenes internos de 20 píxeles por cada lado (Arriba, Izquierda, Abajo, Derecha)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15)); // El 10, 15 separa los paneles internamente
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior (Formulario) con un poco más de separación entre filas y columnas (10, 10)
        JPanel panelCentro = new JPanel(new GridLayout(6, 2, 10, 10));
        panelCentro.add(new JLabel("Cédula Cliente:")); txtCedula = new JTextField(); panelCentro.add(txtCedula);
        panelCentro.add(new JLabel("Nombres Cliente:")); txtNombres = new JTextField(); panelCentro.add(txtNombres);
        panelCentro.add(new JLabel("Nombre Mascota:")); txtNombreMascota = new JTextField(); panelCentro.add(txtNombreMascota);
        panelCentro.add(new JLabel("Especie:")); 
        cbxEspecie = new JComboBox<>(new String[]{"Perro", "Gato", "Otro"}); panelCentro.add(cbxEspecie);
        panelCentro.add(new JLabel("Raza:")); txtRaza = new JTextField(); panelCentro.add(txtRaza);

        // Panel inferior (Botones) centrado y con separación
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnGuardar = new JButton("Guardar"); btnBuscar = new JButton("Buscar"); btnActualizar = new JButton("Actualizar");
        panelSur.add(btnGuardar); panelSur.add(btnBuscar); panelSur.add(btnActualizar);

        // Agregamos todo al Panel Principal envuelto
        panelPrincipal.add(panelCentro, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaDatos = new JTable(new Object[][]{}, new String[]{"Cédula", "Cliente", "Mascota", "Especie"})), BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        // Finalmente, agregamos el Panel Principal a la Ventana
        add(panelPrincipal);
    }

    // 3. Método para correr esta interfaz de forma independiente
    /**
     * Método principal para iniciar la interfaz gráfica de cliente y mascota.
     *
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaClienteMascota().setVisible(true);
        });
    }
}
