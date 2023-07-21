package GUI;

import Conexion.conexion;
import GUI.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class ReporteClientes extends javax.swing.JFrame {
    private Connection con;
    private DefaultTableModel tabla;



    int xMouse, yMouse;

    public ReporteClientes() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        con = conexion.conectar();
        MostrarVistClientesPedidos();

        

    }
    


        
public void MostrarVistClientesPedidos() {
    DefaultTableModel tabla = (DefaultTableModel) this.TablaVistaCliente.getModel();

    try {
        String procedimiento = "{CALL VER_VISTA_CLIENTE_PEDIDOS(?)}";
        CallableStatement llamada = con.prepareCall(procedimiento);
        llamada.registerOutParameter(1, OracleTypes.CURSOR);
        llamada.execute();

        ResultSet resultado = ((OracleCallableStatement) llamada).getCursor(1);

        while (resultado.next()) {
            Object[] fila = new Object[7];
            fila[0] = resultado.getString("ID_CLIENTE");
            fila[1] = resultado.getString("NOMBRE");
            fila[2] = resultado.getString("ID_PRODUCTO");
            fila[3] = resultado.getString("ID_PEDIDO");
            fila[4] = resultado.getString("FECHA");
            fila[5] = resultado.getString("TOTAL_FACTURA");
            fila[6] = resultado.getString("ESTADO_PRODUCTO");
            tabla.addRow(fila);
        }

        resultado.close();
        llamada.close();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


//    public void MostrarVistClientes() {
//        DefaultTableModel tabla = (DefaultTableModel) this.TablaVistaCliente.getModel();
//
//        try {
//            String procedimiento = "{CALL VER_VISTA_CLIENTES(?)}";
//            CallableStatement llamada = con.prepareCall(procedimiento);
//            llamada.registerOutParameter(1, OracleTypes.CURSOR);
//            llamada.execute();
//
//            ResultSet resultado = ((OracleCallableStatement) llamada).getCursor(1);
//
//            while (resultado.next()) {
//                Object[] fila = new Object[5];
//                fila[0] = resultado.getString("ID_CLIENTE");
//                fila[1] = resultado.getString("NOMBRE");
//                fila[2] = resultado.getString("DESCRIPCION");
//                fila[3] = resultado.getString("TELEFONO");
//                fila[4] = resultado.getString("CORREO");
//                tabla.addRow(fila);
//            }
//
//            resultado.close();
//            llamada.close();
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaVistaCliente = new javax.swing.JTable();
        FONDO = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        TablaVistaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "ID_PRODUCTO", "ID_PEDIDO", "FECHA", "TOTAL_FACTURA", "ESTADO_PRODUCTO"
            }
        ));
        jScrollPane1.setViewportView(TablaVistaCliente);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 700, 270));

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
            java.util.logging.Logger.getLogger(ReporteClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new ReporteClientes().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(ReporteClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FONDO;
    private javax.swing.JTable TablaVistaCliente;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
