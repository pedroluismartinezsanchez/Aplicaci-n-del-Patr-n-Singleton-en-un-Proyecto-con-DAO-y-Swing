/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;
import dao.MascotaDAO;
import dto.MascotaDTO;
import exepcion.DatoInvalidoException;
import javax.swing.JOptionPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
/**
 *
 * @author PEDRO LUIS MARTINEZ
 */
public class FormMascota extends javax.swing.JFrame {

     private JTextField txtNombre, txtEspecie, txtEdad;
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private MascotaDAO dao = new MascotaDAO();
    private int indiceSeleccionado = -1;

    public FormMascota() {
        setTitle("Gestión de Mascotas");
        setSize(800, 600);
        setLayout(new BorderLayout());
        

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2));
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Especie:"));
        txtEspecie = new JTextField();
        panelFormulario.add(txtEspecie);

        panelFormulario.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Especie", "Edad"}, 0);
        tablaMascotas = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaMascotas);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(this::guardarMascota);
        btnActualizar.addActionListener(this::actualizarMascota);
        btnEliminar.addActionListener(this::eliminarMascota);
        tablaMascotas.getSelectionModel().addListSelectionListener(e -> seleccionarMascota());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        listarMascotas();
    }

    private void guardarMascota(ActionEvent e) {
        try {
            MascotaDTO mascota = obtenerDatosFormulario();
            dao.guardar(mascota);
            JOptionPane.showMessageDialog(this, "✅ Mascota guardada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void actualizarMascota(ActionEvent e) {
        try {
            if (indiceSeleccionado < 0) {
                throw new Exception("Seleccione una mascota de la tabla");
            }
            MascotaDTO mascota = obtenerDatosFormulario();
            dao.actualizar(indiceSeleccionado, mascota);
            JOptionPane.showMessageDialog(this, "✏️ Mascota actualizada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void eliminarMascota(ActionEvent e) {
        try {
            if (indiceSeleccionado < 0) {
                throw new Exception("Seleccione una mascota para eliminar");
            }
            dao.eliminar(indiceSeleccionado);
            JOptionPane.showMessageDialog(this, "🗑️ Mascota eliminada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void seleccionarMascota() {
        indiceSeleccionado = tablaMascotas.getSelectedRow();
        if (indiceSeleccionado >= 0) {
            txtNombre.setText((String) modeloTabla.getValueAt(indiceSeleccionado, 0));
            txtEspecie.setText((String) modeloTabla.getValueAt(indiceSeleccionado, 1));
            txtEdad.setText(String.valueOf(modeloTabla.getValueAt(indiceSeleccionado, 2)));
        }
    }

    private void listarMascotas() {
        modeloTabla.setRowCount(0);
        List<MascotaDTO> lista = dao.listar();
        for (MascotaDTO m : lista) {
            modeloTabla.addRow(new Object[]{m.getNombre(), m.getEspecie(), m.getEdad()});
        }
        indiceSeleccionado = -1;
    }

    private MascotaDTO obtenerDatosFormulario() throws DatoInvalidoException {
        String nombre = txtNombre.getText().trim();
        String especie = txtEspecie.getText().trim();
        String edadStr = txtEdad.getText().trim();

        if (nombre.isEmpty() || especie.isEmpty() || edadStr.isEmpty()) {
            throw new DatoInvalidoException("Todos los campos son obligatorios");
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            throw new DatoInvalidoException("La edad debe ser un número válido");
        }

        if (edad < 0) {
            throw new DatoInvalidoException("La edad no puede ser negativa");
        }

        return new MascotaDTO(nombre, especie, edad);
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEspecie.setText("");
        txtEdad.setText("");
        tablaMascotas.clearSelection();
        indiceSeleccionado = -1;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMascota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMascota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMascota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMascota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMascota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
