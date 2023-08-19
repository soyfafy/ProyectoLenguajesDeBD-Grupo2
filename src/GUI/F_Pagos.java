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

public class F_Pagos extends javax.swing.JFrame {
    private Connection con;
    private DefaultTableModel tabla;
    int xMouse, yMouse;

    public F_Pagos() throws SQLException {
        initComponents();
        con = conexion.conectar();
        this.setLocationRelativeTo(null);
        MostrarRegistroPago();


        

    }

        //Progra para tabla de Pagos//////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarPago() throws SQLException {
        String IDpagoText = txtIDpago.getText();
        String IDdireCText = txtIDdireC.getText();
        String MontoText = txtMonto.getText();
        String PedidoPagoText = txtIDPedidoPago.getText();

        try {
            int IDpago = Integer.parseInt(IDpagoText);
            int IDdireC = Integer.parseInt(IDdireCText);
            int Monto = Integer.parseInt(MontoText);
            int PedidoPago = Integer.parseInt(PedidoPagoText);

            // Llamada al procedimiento almacenado para insertar pago
            insertarPagoEnBD(IDpago, IDdireC, Monto, PedidoPago);
            limpiarCamposPago();
            limpiar_tabla_Pago();
            MostrarRegistroPago();

            JOptionPane.showMessageDialog(this, "Pago insertado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarPago() throws SQLException {
        String IDpagoText = txtIDpago.getText();
        String IDdireCText = txtIDdireC.getText();
        String MontoText = txtMonto.getText();
        String PedidoPagoText = txtIDPedidoPago.getText();


        try {
            int IDpago = Integer.parseInt(IDpagoText);
            int IDdireC = Integer.parseInt(IDdireCText);
            int Monto = Integer.parseInt(MontoText);
            int PedidoPago = Integer.parseInt(PedidoPagoText);


            // Llamada al procedimiento almacenado para modificar pago
            modificarPagoEnBD(IDpago, IDdireC, Monto, PedidoPago);
            limpiarCamposPago();
            limpiar_tabla_Pago();
            MostrarRegistroPago();

            JOptionPane.showMessageDialog(this, "Pago modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPago() {
        String IDpagoText = txtIDpago.getText();

        try {
            int IDpago = Integer.parseInt(IDpagoText);

            // Llamada al procedimiento almacenado para eliminar pago
            eliminarPagoEnBD(IDpago);
            limpiarCamposPago();
            limpiar_tabla_Pago();
            MostrarRegistroPago();

            JOptionPane.showMessageDialog(this, "Pago eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarPagoEnBD(int IDpago, int IDdireC, int Monto ,int PedidoPago) throws SQLException {
        String sql = "{CALL PAGOS_DB.INSERTAR_PAGO(?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, IDpago);
            statement.setInt(2, IDdireC);
            statement.setInt(3, Monto);
            statement.setInt(4, PedidoPago);

            statement.execute();
            

        }
    }

    private void modificarPagoEnBD(int IDpago, int IDdireC, int Monto ,int PedidoPago) throws SQLException {
        String sql = "{CALL PAGOS_DB.MODIFICAR_PAGO(?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(4, PedidoPago);
            statement.setInt(3, Monto);
            statement.setInt(2, IDdireC);
            statement.setInt(1, IDpago);
            statement.execute();
            
          } catch (SQLException ex) {
        ex.printStackTrace();
         
        }
    }
    
    private void eliminarPagoEnBD(int IDpago) throws SQLException {
        String sql = "{CALL PAGOS_DB.ELIMINAR_PAGO(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, IDpago);
            statement.execute();
                     

        }
    }
    
        public void limpiarCamposPago() {
        txtIDpago.setText("");
        txtIDdireC.setText("");
        txtIDPedidoPago.setText("");
        txtMonto.setText("");
//        txtDistrito.setText("");
//        txtDetalleDireccion.setText("");
    }

        public void MostrarRegistroPago(){
        String sql = "SELECT * FROM PAGOS";
        
        DefaultTableModel tabla = (DefaultTableModel) this.TablaPagos.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[4];
                fila[0] = rs.getString("PAGO_ID");
                fila[1] = rs.getString("DIRECCION_ID");
                fila[2] = rs.getString("MONTO");
                fila[3] = rs.getString("ID_PEDIDO");
//                fila[4] = rs.getString("DISTRITO");
//                fila[5] = rs.getString("DETALLE_DIRECCION");

                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla_Pago(){
        DefaultTableModel modelo = (DefaultTableModel) this.TablaPagos.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItemPago(){
        int fila = this.TablaPagos.getSelectedRow();
        if (fila != -1){
            
            this.txtIDpago.setText(this.TablaPagos.getValueAt(fila, 0).toString().trim());
            this.txtIDdireC.setText(this.TablaPagos.getValueAt(fila, 1).toString().trim());
            this.txtMonto.setText(this.TablaPagos.getValueAt(fila, 2).toString().trim());
            this.txtIDPedidoPago.setText(this.TablaPagos.getValueAt(fila, 3).toString().trim());
//            this.txtDistrito.setText(this.TablaPagos.getValueAt(fila, 4).toString().trim());           
//            this.txtDetalleDireccion.setText(this.TablaPagos.getValueAt(fila, 5).toString().trim());

            
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCalcularPrecio = new javax.swing.JButton();
        btnInsertarPago = new javax.swing.JButton();
        btnModificarPago = new javax.swing.JButton();
        btnEliminarPago = new javax.swing.JButton();
        txtIDdireC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIDpago = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIDPedidoPago = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPagos = new javax.swing.JTable();
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

        txtCalcularPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/check.png"))); // NOI18N
        txtCalcularPrecio.setText("Calcular precio final");
        txtCalcularPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCalcularPrecioActionPerformed(evt);
            }
        });
        getContentPane().add(txtCalcularPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 260, -1, -1));

        btnInsertarPago.setBackground(new java.awt.Color(204, 255, 204));
        btnInsertarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/insert.png"))); // NOI18N
        btnInsertarPago.setText("Insertar");
        btnInsertarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarPagoActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertarPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, -1, -1));

        btnModificarPago.setBackground(new java.awt.Color(255, 153, 102));
        btnModificarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/update.png"))); // NOI18N
        btnModificarPago.setText("Modificar");
        btnModificarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPagoActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, -1, -1));

        btnEliminarPago.setBackground(new java.awt.Color(255, 102, 102));
        btnEliminarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/delete  (1).png"))); // NOI18N
        btnEliminarPago.setText("Eliminar");
        btnEliminarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPagoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 310, -1, -1));
        getContentPane().add(txtIDdireC, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 90, -1));

        jLabel3.setText("ID Direccion:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, -1, -1));
        getContentPane().add(txtIDpago, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 90, -1));

        jLabel4.setText("Monto:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, -1, -1));

        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        getContentPane().add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 260, 90, -1));

        jLabel5.setText("ID Pedido:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, -1, -1));
        getContentPane().add(txtIDPedidoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 90, -1));

        jLabel2.setText("ID Pago:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, -1, -1));

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

        TablaPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PAGO_ID", "DIRECCION_ID", "MONTO", "ID_PEDIDO"
            }
        ));
        TablaPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPagosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaPagos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 740, 180));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Pago de facturas");
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

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void btnInsertarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarPagoActionPerformed
        try {
            insertarPago();
        } catch (SQLException ex) {
            Logger.getLogger(F_Pagos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsertarPagoActionPerformed

    private void btnModificarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPagoActionPerformed
        try {
            modificarPago();
        } catch (SQLException ex) {
            Logger.getLogger(F_Pagos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModificarPagoActionPerformed

    private void btnEliminarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPagoActionPerformed
        eliminarPago();
    }//GEN-LAST:event_btnEliminarPagoActionPerformed

    private void TablaPagosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPagosMouseClicked
        SeleccionarItemPago();
    }//GEN-LAST:event_TablaPagosMouseClicked

    private void txtCalcularPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCalcularPrecioActionPerformed
        int idPedido = Integer.parseInt(txtIDPedidoPago.getText());

        try {
            String query = "{ ? = call calcular_precio_final(?) }";
            CallableStatement callableStatement = con.prepareCall(query);
            callableStatement.registerOutParameter(1, Types.DECIMAL);
            callableStatement.setInt(2, idPedido);

            callableStatement.execute();

            BigDecimal precioFinal = callableStatement.getBigDecimal(1);

            txtMonto.setText(precioFinal.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtCalcularPrecioActionPerformed

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
            java.util.logging.Logger.getLogger(F_Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new F_Pagos().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(F_Pagos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FONDO;
    private javax.swing.JTable TablaPagos;
    private javax.swing.JButton btnEliminarPago;
    private javax.swing.JButton btnInsertarPago;
    private javax.swing.JButton btnModificarPago;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton txtCalcularPrecio;
    private javax.swing.JTextField txtIDPedidoPago;
    private javax.swing.JTextField txtIDdireC;
    private javax.swing.JTextField txtIDpago;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
