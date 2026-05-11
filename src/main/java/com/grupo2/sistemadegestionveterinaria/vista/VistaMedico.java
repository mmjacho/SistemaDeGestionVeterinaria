package com.grupo2.sistemadegestionveterinaria.vista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import com.grupo2.sistemadegestionveterinaria.controlador.ControladorMedico;
import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMedico;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VistaMedico extends JFrame {

    // CAMPOS
    public JTextField txtNombres;
    public JTextField txtApellidos;
    public JTextField txtEspecialidad;
    public JTextField txtTelefono;
    public JCheckBox chkEstado;

    // BOTONES
    public JButton btnGuardar;
    public JButton btnBuscar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnLimpiar;

    // TABLA
    public JTable tablaMedicos;

    public VistaMedico() {

        iniciarComponentes();
    }

    private void iniciarComponentes() {

        setTitle("Módulo - Médicos Veterinarios");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //----------------------------------------
        // PANEL PRINCIPAL
        //----------------------------------------
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 247, 250));

        //----------------------------------------
        // HEADER
        //----------------------------------------
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(new EmptyBorder(25, 25, 15, 25));

        JLabel lblTitulo = new JLabel("REGISTRO DE MÉDICOS VETERINARIOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblTitulo.setForeground(new Color(15, 33, 65));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelHeader.add(lblTitulo, BorderLayout.CENTER);

        //----------------------------------------
        // PANEL CONTENIDO
        //----------------------------------------
        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(new Color(245, 247, 250));
        panelContenido.setLayout(new BorderLayout(20, 20));
        panelContenido.setBorder(new EmptyBorder(10, 25, 25, 25));

        //----------------------------------------
        // PANEL FORMULARIO
        //----------------------------------------
        JPanel panelFormulario = new JPanel(new GridLayout(2, 4, 20, 20));

        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        //----------------------------------------
// TEXTFIELDS
//----------------------------------------
txtNombres = crearTextField();
txtApellidos = crearTextField();
txtEspecialidad = crearTextField();
txtTelefono = crearTextField();

chkEstado = new JCheckBox("Médico Activo");

    chkEstado.setFont(
        new Font("Segoe UI", Font.BOLD, 15)
    );

    chkEstado.setBackground(Color.WHITE);

    chkEstado.setSelected(true);

//----------------------------------------
// VALIDACIÓN NOMBRES
//----------------------------------------
txtNombres.addKeyListener(
        new KeyAdapter() {

    @Override
    public void keyTyped(KeyEvent e) {

        char c = e.getKeyChar();

        if (!Character.isLetter(c)
                && c != ' '
                && !Character.isISOControl(c)) {

            e.consume();
        }
    }
});

txtNombres.addFocusListener(
        new java.awt.event.FocusAdapter() {

    @Override
    public void focusLost(
            java.awt.event.FocusEvent e
    ) {

        txtNombres.setText(
                convertirMayusculas(
                        txtNombres.getText()
                )
        );
    }
});

//----------------------------------------
// VALIDACIÓN APELLIDOS
//----------------------------------------
txtApellidos.addKeyListener(
        new KeyAdapter() {

    @Override
    public void keyTyped(KeyEvent e) {

        char c = e.getKeyChar();

        if (!Character.isLetter(c)
                && c != ' '
                && !Character.isISOControl(c)) {

            e.consume();
        }
    }
});

txtApellidos.addFocusListener(
        new java.awt.event.FocusAdapter() {

    @Override
    public void focusLost(
            java.awt.event.FocusEvent e
    ) {

        txtApellidos.setText(
                convertirMayusculas(
                        txtApellidos.getText()
                )
        );
    }
});

//----------------------------------------
// VALIDACIÓN ESPECIALIDAD
//----------------------------------------
txtEspecialidad.addKeyListener(
        new KeyAdapter() {

    @Override
    public void keyTyped(KeyEvent e) {

        char c = e.getKeyChar();

        if (!Character.isLetter(c)
                && c != ' '
                && !Character.isISOControl(c)) {

            e.consume();
        }
    }
});

txtEspecialidad.addFocusListener(
        new java.awt.event.FocusAdapter() {

    @Override
    public void focusLost(
            java.awt.event.FocusEvent e
    ) {

        txtEspecialidad.setText(
                convertirMayusculas(
                        txtEspecialidad.getText()
                )
        );
    }
});

//----------------------------------------
// VALIDACIÓN TELÉFONO
//----------------------------------------
txtTelefono.addKeyListener(
        new KeyAdapter() {

    @Override
    public void keyTyped(KeyEvent e) {

        char c = e.getKeyChar();

        //---------------------------------
        // SOLO NÚMEROS
        //---------------------------------
        if (!Character.isDigit(c)
                && !Character.isISOControl(c)) {

            e.consume();
        }

        //---------------------------------
        // MÁXIMO 10 DÍGITOS
        //---------------------------------
        if (txtTelefono.getText().length() >= 10
                && !Character.isISOControl(c)) {

            e.consume();
        }
    }
});

//----------------------------------------
// AGREGAR CAMPOS
//----------------------------------------
panelFormulario.add(
        crearCampo("Nombres:", txtNombres)
);

panelFormulario.add(
        crearCampo("Apellidos:", txtApellidos)
);

panelFormulario.add(
        crearCampo("Especialidad:", txtEspecialidad)
);

panelFormulario.add(
        crearCampo("Teléfono:", txtTelefono)
);

panelFormulario.add(chkEstado);

        //----------------------------------------
        // PANEL BOTONES
        //----------------------------------------
        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 15)
        );

        panelBotones.setBackground(new Color(245, 247, 250));

        btnGuardar = crearBoton("Guardar", new Color(0, 153, 76));
        btnBuscar = crearBoton("Buscar", new Color(0, 102, 204));
        btnActualizar = crearBoton("Actualizar", new Color(255, 153, 0));
        btnEliminar = crearBoton("Eliminar", new Color(220, 53, 69));
        btnLimpiar = crearBoton("Limpiar", new Color(108, 117, 125));

        
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        

        //----------------------------------------
        // TABLA
        //----------------------------------------
        String columnas[] = {
                "ID",
                "Nombres",
                "Apellidos",
                "Especialidad",
                "Teléfono",
                "Estado"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        tablaMedicos = new JTable(modelo);

        tablaMedicos.setRowHeight(35);
        tablaMedicos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaMedicos.setSelectionBackground(new Color(220, 235, 252));
        tablaMedicos.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tablaMedicos.getTableHeader();

        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(240, 244, 248));
        header.setForeground(new Color(20, 20, 20));
        header.setPreferredSize(new Dimension(100, 40));

        JScrollPane scroll = new JScrollPane(tablaMedicos);

        scroll.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(5, 5, 5, 5)
                )
        );

        //----------------------------------------
        // FOOTER
        //----------------------------------------
        JPanel panelFooter = new JPanel(new BorderLayout());

        panelFooter.setBackground(new Color(235, 240, 245));
        panelFooter.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblSistema = new JLabel("Sistema de Gestión Veterinaria");
        lblSistema.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblHora = new JLabel("SDK 20 - NetBeans");
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        panelFooter.add(lblSistema, BorderLayout.WEST);
        panelFooter.add(lblHora, BorderLayout.EAST);

        //----------------------------------------
        // AGREGAR COMPONENTES
        //----------------------------------------
        JPanel panelSuperior = new JPanel(new BorderLayout());

        panelSuperior.setBackground(new Color(245, 247, 250));

        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    //----------------------------------------
    // MÉTODOS AUXILIARES
    //----------------------------------------

    private JTextField crearTextField() {

        JTextField txt = new JTextField();

        txt.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txt.setPreferredSize(new Dimension(250, 40));

        return txt;
    }

    private JPanel crearCampo(String texto, JTextField txt) {

        JPanel panel = new JPanel(new BorderLayout(5, 8));

        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(texto);

        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(txt, BorderLayout.CENTER);

        return panel;
    }

    private JButton crearBoton(String texto, Color color) {

        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);

        boton.setPreferredSize(new Dimension(190, 45));

        boton.setBorder(new EmptyBorder(10, 15, 10, 15));

        return boton;
    }

    //----------------------------------------
    // MAIN
    //----------------------------------------
    public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {

        //---------------------------------
        // VISTA
        //---------------------------------
        VistaMedico vista =
                new VistaMedico();

        //---------------------------------
        // MODELO
        //---------------------------------
        ModeloMedico modelo =
                new ModeloMedico();

        //---------------------------------
        // CONTROLADOR
        //---------------------------------
        ControladorMedico controlador =
                new ControladorMedico(
                        modelo,
                        vista
                );

        //---------------------------------
        // MOSTRAR VENTANA
        //---------------------------------
        vista.setVisible(true);

    });
}
  //----------------------------------------
// CONVERTIR PRIMERAS LETRAS EN MAYÚSCULA
//----------------------------------------
private String convertirMayusculas(
        String texto
) {

    //------------------------------------
    // ELIMINAR ESPACIOS EXTRA
    //------------------------------------
    texto = texto.trim().replaceAll("\\s+", " ");

    //------------------------------------
    // DIVIDIR PALABRAS
    //------------------------------------
    String palabras[] =
            texto.toLowerCase().split(" ");

    //------------------------------------
    // CONSTRUIR RESULTADO
    //------------------------------------
    StringBuilder resultado =
            new StringBuilder();

    for (String palabra : palabras) {

        if (!palabra.isEmpty()) {

            resultado.append(
                    Character.toUpperCase(
                            palabra.charAt(0)
                    )
            );

            resultado.append(
                    palabra.substring(1)
            );

            resultado.append(" ");
        }
    }

    //------------------------------------
    // RETORNAR TEXTO LIMPIO
    //------------------------------------
    return resultado.toString().trim();
}
}