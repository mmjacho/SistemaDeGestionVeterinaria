/**
 *
 * @author Alonso Serrano
 */
package com.grupo2.sistemadegestionveterinaria.controlador;

import com.grupo2.sistemadegestionveterinaria.modelo.ModeloMedico;
import com.grupo2.sistemadegestionveterinaria.vista.VistaMedico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ControladorMedico implements ActionListener {

    //-----------------------------------------
    // OBJETOS
    //-----------------------------------------
    private ModeloMedico modelo;
    private VistaMedico vista;

    //-----------------------------------------
    // ID SELECCIONADO
    //-----------------------------------------
    private int idMedicoSeleccionado;

    //-----------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------
    public ControladorMedico(
            ModeloMedico modelo,
            VistaMedico vista
    ) {

        this.modelo = modelo;
        this.vista = vista;

        //-------------------------------------
        // EVENTOS BOTONES
        //-------------------------------------
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);

        //-------------------------------------
        // MOSTRAR TABLA
        //-------------------------------------
        listarMedicos();

        //-------------------------------------
        // CLICK EN TABLA
        //-------------------------------------
        vista.tablaMedicos.addMouseListener(
                new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                cargarDatosTabla();
            }
        });
    }

    //-----------------------------------------
    // EVENTOS
    //-----------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        //-------------------------------------
        // GUARDAR
        //-------------------------------------
        if (e.getSource() == vista.btnGuardar) {

            guardarMedico();
        }
        
        //-------------------------------------
    // ACTUALIZAR
//-------------------------------------
    if (e.getSource() == vista.btnActualizar) {

         actualizarMedico();
        }
    
    //-------------------------------------
// ELIMINAR
//-------------------------------------
if (e.getSource() == vista.btnEliminar) {

    eliminarMedico();
}
//-------------------------------------
// LIMPIAR
//-------------------------------------
if (e.getSource() == vista.btnLimpiar) {

    limpiarCampos();
}

//-------------------------------------
// BUSCAR
//-------------------------------------
if (e.getSource() == vista.btnBuscar) {

    buscarMedico();
}
    }
    

    //-----------------------------------------
    // MÉTODO GUARDAR
    //-----------------------------------------
    public void guardarMedico() {
        
        //-------------------------------------
// VALIDAR CAMPOS VACÍOS
//-------------------------------------
     if (vista.txtNombres.getText().trim().isEmpty()
        || vista.txtApellidos.getText().trim().isEmpty()
        || vista.txtEspecialidad.getText().trim().isEmpty()
        || vista.txtTelefono.getText().trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            null,
            "Todos los campos son obligatorios"
    );

    return;
}

        modelo.setNombres(
                vista.txtNombres.getText()
        );

        modelo.setApellidos(
                vista.txtApellidos.getText()
        );

        modelo.setEspecialidad(
                vista.txtEspecialidad.getText()
        );

        modelo.setTelefono(
                vista.txtTelefono.getText()
        );
        
        modelo.setEstado(
        vista.chkEstado.isSelected()
        );
        
        if (vista.txtTelefono.getText().length() != 10) {

        JOptionPane.showMessageDialog(
            null,
            "El teléfono debe tener 10 dígitos"
            );

            return;
        }

        boolean resultado =
                modelo.guardarMedico();

        if (resultado) {

            JOptionPane.showMessageDialog(
                    null,
                    "Médico guardado correctamente"
            );

            limpiarCampos();

            listarMedicos();

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar médico"
            );
        }
    }

    //-----------------------------------------
    // LISTAR MÉDICOS
    //-----------------------------------------
    public void listarMedicos() {

        String columnas[] = {
                "ID",
                "Nombres",
                "Apellidos",
                "Especialidad",
                "Teléfono",
                "Estado"
        };

        DefaultTableModel tabla =
                new DefaultTableModel(
                        null,
                        columnas
                );

        ArrayList<ModeloMedico> lista =
                modelo.listarMedicos();

    // =========================
// ORDENAMIENTO BURBUJA
// POR APELLIDOS
// =========================
for (int i = 0; i < lista.size() - 1; i++) {

    for (int j = 0; j < lista.size() - i - 1; j++) {

        String apellido1 =
                lista.get(j)
                     .getApellidos();

        String apellido2 =
                lista.get(j + 1)
                     .getApellidos();

        //---------------------------------
        // COMPARAR ALFABÉTICAMENTE
        //---------------------------------
        if (apellido1.compareToIgnoreCase(apellido2) > 0) {

            //---------------------------------
            // INTERCAMBIO
            //---------------------------------
            ModeloMedico temporal =
                    lista.get(j);

            lista.set(
                    j,
                    lista.get(j + 1)
            );

            lista.set(
                    j + 1,
                    temporal
            );
        }
    }
}    
          
        
        for (ModeloMedico m : lista) {

            Object fila[] = {

                    m.getIdMedico(),
                    m.getNombres(),
                    m.getApellidos(),
                    m.getEspecialidad(),
                    m.getTelefono(),
                    m.isEstado() ? "Activo" : "Inactivo"
            };

            tabla.addRow(fila);
        }

        vista.tablaMedicos.setModel(tabla);
    }

    //-----------------------------------------
    // LIMPIAR CAMPOS
    //-----------------------------------------
    public void limpiarCampos() {

        vista.txtNombres.setText("");
        vista.txtApellidos.setText("");
        vista.txtEspecialidad.setText("");
        vista.txtTelefono.setText("");
        
        vista.chkEstado.setSelected(true);
    }

    //-----------------------------------------
    // CARGAR DATOS DE TABLA
    //-----------------------------------------
    public void cargarDatosTabla() {

        int fila =
                vista.tablaMedicos.getSelectedRow();

        if (fila >= 0) {

            idMedicoSeleccionado =
                    Integer.parseInt(
                            vista.tablaMedicos
                                    .getValueAt(fila, 0)
                                    .toString()
                    );

            vista.txtNombres.setText(
                    vista.tablaMedicos
                            .getValueAt(fila, 1)
                            .toString()
            );

            vista.txtApellidos.setText(
                    vista.tablaMedicos
                            .getValueAt(fila, 2)
                            .toString()
            );

            vista.txtEspecialidad.setText(
                    vista.tablaMedicos
                            .getValueAt(fila, 3)
                            .toString()
            );

            vista.txtTelefono.setText(
                    vista.tablaMedicos
                            .getValueAt(fila, 4)
                            .toString()
            );
            
            String estado =
            vista.tablaMedicos
                .getValueAt(fila, 5)
                .toString();

            vista.chkEstado.setSelected(
            estado.equals("Activo")
            );
        }
    }
    //-----------------------------------------
// ACTUALIZAR MÉDICO
//-----------------------------------------
public void actualizarMedico() {

    modelo.setIdMedico(
            idMedicoSeleccionado
    );

    modelo.setNombres(
            vista.txtNombres.getText()
    );

    modelo.setApellidos(
            vista.txtApellidos.getText()
    );

    modelo.setEspecialidad(
            vista.txtEspecialidad.getText()
    );

    modelo.setTelefono(
            vista.txtTelefono.getText()
    );
    
    modelo.setEstado(
        vista.chkEstado.isSelected()
    );

    if (vista.txtTelefono.getText().length() != 10) {

    JOptionPane.showMessageDialog(
            null,
            "El teléfono debe tener 10 dígitos"
           );

        return;
    }
    
    
    
    boolean resultado =
            modelo.actualizarMedico();

    if (resultado) {

        JOptionPane.showMessageDialog(
                null,
                "Médico actualizado correctamente"
        );

        limpiarCampos();

        listarMedicos();

    } else {

        JOptionPane.showMessageDialog(
                null,
                "Error al actualizar médico"
        );
    }
}
//-----------------------------------------
// ELIMINAR MÉDICO
//-----------------------------------------
public void eliminarMedico() {

    //-------------------------------------
    // VALIDAR SELECCIÓN
    //-------------------------------------
    if (idMedicoSeleccionado == 0) {

        JOptionPane.showMessageDialog(
                null,
                "Seleccione un médico"
        );

        return;
    }

    //-------------------------------------
    // CONFIRMACIÓN
    //-------------------------------------
    int opcion =
            JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea eliminar este médico?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

    if (opcion == JOptionPane.YES_OPTION) {

        modelo.setIdMedico(
                idMedicoSeleccionado
        );

        boolean resultado =
                modelo.eliminarMedico();

        if (resultado) {

            JOptionPane.showMessageDialog(
                    null,
                    "Médico eliminado correctamente"
            );

            limpiarCampos();

            listarMedicos();

            idMedicoSeleccionado = 0;

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al eliminar médico"
            );
        }
    }
}
//-----------------------------------------
// BUSCAR MÉDICO
//-----------------------------------------
public void buscarMedico() {

    //-------------------------------------
    // TEXTO DE BÚSQUEDA
    //-------------------------------------
    String textoBuscar =
            vista.txtNombres.getText();

    //-------------------------------------
    // COLUMNAS TABLA
    //-------------------------------------
    String columnas[] = {
            "ID",
            "Nombres",
            "Apellidos",
            "Especialidad",
            "Teléfono",
            "Estado"
    };

    //-------------------------------------
    // MODELO TABLA
    //-------------------------------------
    DefaultTableModel tabla =
            new DefaultTableModel(
                    null,
                    columnas
            );

    //-------------------------------------
    // LISTA RESULTADOS
    //-------------------------------------
    ArrayList<ModeloMedico> lista =
            modelo.buscarMedico(textoBuscar);
    //-------------------------------------
// VALIDAR RESULTADOS
//-------------------------------------
if (lista.isEmpty()) {

    JOptionPane.showMessageDialog(
            null,
            "No existe ningún médico con ese criterio de búsqueda"
    );
}

    //-------------------------------------
    // RECORRER RESULTADOS
    //-------------------------------------
    for (ModeloMedico m : lista) {

        Object fila[] = {

                m.getIdMedico(),
                m.getNombres(),
                m.getApellidos(),
                m.getEspecialidad(),
                m.getTelefono(),
                m.isEstado() ? "Activo" : "Inactivo"
        };

        tabla.addRow(fila);
    }

    //-------------------------------------
    // MOSTRAR TABLA
    //-------------------------------------
    vista.tablaMedicos.setModel(tabla);
}
}