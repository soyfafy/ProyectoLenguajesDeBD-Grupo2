package GUI;

/**
 *
 * @author Grupo 2 - Lenguajes de Bases de datos
 */

import Conexion.conexion;
import GUI.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class C_Direcciones extends javax.swing.JFrame {
    private Connection con;
    private DefaultTableModel tabla;



    int xMouse, yMouse;

    public C_Direcciones() throws SQLException {
        initComponents();
        con = conexion.conectar();
        this.setLocationRelativeTo(null);
        MostrarRegistroDireccion();


        

    }

        //Progra para tabla de Direcciones//////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarDireccion() throws SQLException {
        String direccionIdText = txtIDdirecciones.getText();
        String IDclienteDireText = txtIDclienteD.getText();
        String provinciaText = txtProvincia.getText();
        String cantonText = txtCanton.getText();
        String distritoText = txtDistrito.getText();
        String DetalleDireccionText = txtDetalleDireccion.getText();

        try {
            int direccionId = Integer.parseInt(direccionIdText);
            int IDclienteDire = Integer.parseInt(IDclienteDireText);
            String provincia = provinciaText;
            String canton = cantonText;
            String distrito = distritoText;
            String DetalleDireccion = DetalleDireccionText;

            // Llamada al procedimiento almacenado para insertar direcciones
            insertarDireccionEnBD(direccionId, IDclienteDire, provincia, canton, distrito, DetalleDireccion);
            limpiarCamposDireccion();
            limpiar_tabla_Direccion();
            MostrarRegistroDireccion();

            JOptionPane.showMessageDialog(this, "Direccion insertada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar la direccion.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarDireccion() throws SQLException {
        String direccionIdText = txtIDdirecciones.getText();
        String IDclienteDireText = txtIDclienteD.getText();
        String provinciaText = txtProvincia.getText();
        String cantonText = txtCanton.getText();
        String distritoText = txtDistrito.getText();
        String DetalleDireccionText = txtDetalleDireccion.getText();

        try {
            int direccionId = Integer.parseInt(direccionIdText);
            int IDclienteDire = Integer.parseInt(IDclienteDireText);
            String provincia = provinciaText;
            String canton = cantonText;
            String distrito = distritoText;
            String DetalleDireccion = DetalleDireccionText;

            // Llamada al procedimiento almacenado para modificar la direccion
            modificarDireccionEnBD(direccionId, IDclienteDire, provincia, canton, distrito, DetalleDireccion);
            limpiarCamposDireccion();
            limpiar_tabla_Direccion();
            MostrarRegistroDireccion();

            JOptionPane.showMessageDialog(this, "Direccion modificada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar la direccion.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDireccion() {
        String direccionIdText = txtIDdirecciones.getText();

        try {
            int direccionId = Integer.parseInt(direccionIdText);

            // Llamada al procedimiento almacenado para eliminar la direccion
            eliminarDireccionEnBD(direccionId);
            limpiarCamposDireccion();
            limpiar_tabla_Direccion();
            MostrarRegistroDireccion();

            JOptionPane.showMessageDialog(this, "Direccion eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar direccion.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarDireccionEnBD(int direccionId, int IDclienteDire, String provincia, String canton, String distrito, String DetalleDireccion) throws SQLException {
        String sql = "{CALL DIRECCIONES_DB.INSERTAR_DIRECCION(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, direccionId);
            statement.setInt(2, IDclienteDire);
            statement.setString(3, provincia);
            statement.setString(4, canton);
            statement.setString(5, distrito);
            statement.setString(6, DetalleDireccion);
            statement.execute();
            

        }
    }

    private void modificarDireccionEnBD(int direccionId, int IDclienteDire, String provincia, String canton, String distrito, String DetalleDireccion) throws SQLException {
        String sql = "{CALL DIRECCIONES_DB.MODIFICAR_DIRECCION(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setString(6, DetalleDireccion);
            statement.setString(5, distrito);
            statement.setString(4, canton);
            statement.setString(3, provincia);
            statement.setInt(2, IDclienteDire);
            statement.setInt(1, direccionId);
            statement.execute();
            
           

        }
    }
    
    private void eliminarDireccionEnBD(int direccionId) throws SQLException {
        String sql = "{CALL DIRECCIONES_DB.ELIMINAR_DIRECCION(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, direccionId);
            statement.execute();
                     

        }
    }
    
        public void limpiarCamposDireccion() {
        txtIDdirecciones.setText("");
        txtIDclienteD.setText("");
        txtProvincia.setText("");
        txtCanton.setText("");
        txtDistrito.setText("");
        txtDetalleDireccion.setText("");
    }

        public void MostrarRegistroDireccion(){
        String sql = "SELECT * FROM DIRECCIONES";
        
        DefaultTableModel tabla = (DefaultTableModel) this.TablaDirecciones.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[6];
                fila[0] = rs.getString("DIRECCION_ID");
                fila[1] = rs.getString("ID_CLIENTE");
                fila[2] = rs.getString("PROVINCIA");
                fila[3] = rs.getString("CANTON");
                fila[4] = rs.getString("DISTRITO");
                fila[5] = rs.getString("DETALLE_DIRECCION");

                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla_Direccion(){
        DefaultTableModel modelo = (DefaultTableModel) this.TablaDirecciones.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItemDireccion(){
        int fila = this.TablaDirecciones.getSelectedRow();
        if (fila != -1){
            
            this.txtIDdirecciones.setText(this.TablaDirecciones.getValueAt(fila, 0).toString().trim());
            this.txtIDclienteD.setText(this.TablaDirecciones.getValueAt(fila, 1).toString().trim());
            this.txtProvincia.setText(this.TablaDirecciones.getValueAt(fila, 2).toString().trim());
            this.txtCanton.setText(this.TablaDirecciones.getValueAt(fila, 3).toString().trim());
            this.txtDistrito.setText(this.TablaDirecciones.getValueAt(fila, 4).toString().trim());           
            this.txtDetalleDireccion.setText(this.TablaDirecciones.getValueAt(fila, 5).toString().trim());

            
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDistrito = new javax.swing.JTextField();
        btnInsertarDire = new javax.swing.JButton();
        btnModificarDire = new javax.swing.JButton();
        btnEliminarDire = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtDetalleDireccion = new javax.swing.JTextField();
        txtIDclienteD = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIDdirecciones = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtProvincia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCanton = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDirecciones = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        FONDO = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(txtDistrito, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 90, -1));

        btnInsertarDire.setBackground(new java.awt.Color(204, 255, 204));
        btnInsertarDire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/insert.png"))); // NOI18N
        btnInsertarDire.setText("Insertar");
        btnInsertarDire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarDireActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertarDire, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, -1, -1));

        btnModificarDire.setBackground(new java.awt.Color(255, 153, 102));
        btnModificarDire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/update.png"))); // NOI18N
        btnModificarDire.setText("Modificar");
        btnModificarDire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDireActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarDire, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, -1, -1));

        btnEliminarDire.setBackground(new java.awt.Color(255, 102, 102));
        btnEliminarDire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/delete  (1).png"))); // NOI18N
        btnEliminarDire.setText("Eliminar");
        btnEliminarDire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDireActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarDire, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 310, -1, -1));

        jLabel7.setText("Detalle de la direccion:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 240, -1, -1));
        getContentPane().add(txtDetalleDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 260, 230, -1));
        getContentPane().add(txtIDclienteD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 90, -1));

        jLabel3.setText("ID Cliente");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, -1, -1));
        getContentPane().add(txtIDdirecciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 90, -1));

        jLabel4.setText("Provincia:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, -1, -1));

        txtProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProvinciaActionPerformed(evt);
            }
        });
        getContentPane().add(txtProvincia, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 90, -1));

        jLabel5.setText("Canton:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 240, -1, -1));
        getContentPane().add(txtCanton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, 90, -1));

        jLabel6.setText("Distrito:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, -1, -1));

        jLabel2.setText("ID Direccion:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        exitBtn.setBackground(new java.awt.Color(255, 255, 255));
        exitBtn.setForeground(new java.awt.Color(255, 255, 255));

        exitTxt.setBackground(new java.awt.Color(0, 0, 0));
        exitTxt.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        exitTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitTxt.setText("🢀");
        exitTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exitTxt.setPreferredSize(new java.awt.Dimension(40, 40));
        exitTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout exitBtnLayout = new javax.swing.GroupLayout(exitBtn);
        exitBtn.setLayout(exitBtnLayout);
        exitBtnLayout.setHorizontalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exitTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        exitBtnLayout.setVerticalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exitTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(exitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, -1, -1));

        TablaDirecciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DIRECCION_ID", "ID_CLIENTE", "PROVINCIA", "CANTON", "DISTRITO", "DETALLE_DIRECCION"
            }
        ));
        TablaDirecciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDireccionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaDirecciones);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 740, 180));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Direcciones de clientes");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        FONDO.setForeground(new java.awt.Color(255, 255, 255));
        FONDO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/FONDO_REPORTES.jpg"))); // NOI18N
        FONDO.setToolTipText("");
        getContentPane().add(FONDO, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 350));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void exitTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseClicked
        dispose();
    }//GEN-LAST:event_exitTxtMouseClicked

    private void exitTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseEntered
        exitBtn.setBackground(Color.red);
        exitTxt.setForeground(Color.white);
    }//GEN-LAST:event_exitTxtMouseEntered

    private void exitTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseExited
        exitBtn.setBackground(Color.white);
        exitTxt.setForeground(Color.black);
    }//GEN-LAST:event_exitTxtMouseExited

    private void txtProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProvinciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProvinciaActionPerformed

    private void btnInsertarDireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarDireActionPerformed
        try {
            insertarDireccion();
        } catch (SQLException ex) {
            Logger.getLogger(C_Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsertarDireActionPerformed

    private void btnModificarDireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDireActionPerformed
        try {
            modificarDireccion();
        } catch (SQLException ex) {
            Logger.getLogger(C_Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModificarDireActionPerformed

    private void btnEliminarDireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDireActionPerformed
        eliminarDireccion();
    }//GEN-LAST:event_btnEliminarDireActionPerformed

    private void TablaDireccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDireccionesMouseClicked
        SeleccionarItemDireccion();
    }//GEN-LAST:event_TablaDireccionesMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int Y = evt.getYOnScreen();
        int X = evt.getXOnScreen();
        setLocation(X - xMouse, Y - yMouse);
    }//GEN-LAST:event_formMouseDragged

    
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
            java.util.logging.Logger.getLogger(C_Direcciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(C_Direcciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(C_Direcciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(C_Direcciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new C_Direcciones().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(C_Direcciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FONDO;
    private javax.swing.JTable TablaDirecciones;
    private javax.swing.JButton btnEliminarDire;
    private javax.swing.JButton btnInsertarDire;
    private javax.swing.JButton btnModificarDire;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCanton;
    private javax.swing.JTextField txtDetalleDireccion;
    private javax.swing.JTextField txtDistrito;
    private javax.swing.JTextField txtIDclienteD;
    private javax.swing.JTextField txtIDdirecciones;
    private javax.swing.JTextField txtProvincia;
    // End of variables declaration//GEN-END:variables
}
