package GUI;

/**
 *
 * @author Grupo 2 - Lenguajes de Bases de datos
 */

import java.util.Date;
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

public class V_Clientes_Productos extends javax.swing.JFrame {
    private Connection con;
    private DefaultTableModel tabla;
    int xMouse, yMouse;





    public V_Clientes_Productos() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        con = conexion.conectar();
//          MostrarVistClientesPedidos();
//        MostrarVistClientes();

        

    }
           
//public void MostrarVistClientesPedidos() {
//    DefaultTableModel tabla = (DefaultTableModel) this.TablaVistaClienteP.getModel();
//
//    try {
//        String procedimiento = "{CALL VER_VISTA_CLIENTE_PEDIDOS(?)}";
//        CallableStatement llamada = con.prepareCall(procedimiento);
//        llamada.registerOutParameter(1, OracleTypes.CURSOR);
//        llamada.execute();
//
//        ResultSet resultado = ((OracleCallableStatement) llamada).getCursor(1);
//
//        while (resultado.next()) {
//            Object[] fila = new Object[7];
//            fila[0] = resultado.getString("ID_CLIENTE");
//            fila[1] = resultado.getString("NOMBRE");
//            fila[2] = resultado.getString("ID_PRODUCTO");
//            fila[3] = resultado.getString("ID_PEDIDO");
//            fila[4] = resultado.getString("FECHA");
//            fila[5] = resultado.getString("TOTAL_FACTURA");
//            fila[6] = resultado.getString("ESTADO_PRODUCTO");
//            tabla.addRow(fila);
//        }
//
//        resultado.close();
//        llamada.close();
//        con.close();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}


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
        TablaVistaClienteP = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBusquedaPedidoC = new javax.swing.JTextField();
        btnBuscarPedidosC = new javax.swing.JButton();
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

        TablaVistaClienteP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_CLIENTE", "NOMBRE", "ID_PRODUCTO", "ID_PEDIDO", "FECHA", "TOTAL_FACTURA", "ESTADO_PRODUCTO"
            }
        ));
        jScrollPane1.setViewportView(TablaVistaClienteP);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 700, 200));

        jLabel2.setText("Buscar por ID:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Pedidos de los clientes");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));
        getContentPane().add(txtBusquedaPedidoC, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 200, -1));

        btnBuscarPedidosC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/search.png"))); // NOI18N
        btnBuscarPedidosC.setText("Buscar");
        btnBuscarPedidosC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPedidosCActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscarPedidosC, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 140, -1));

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

    private void btnBuscarPedidosCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPedidosCActionPerformed
        int idbusqueda = Integer.parseInt(txtBusquedaPedidoC.getText());

        String sql = "{ ? = call buscar_productos_por_cliente(?) }";

        try {
            CallableStatement statement = con.prepareCall(sql);
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setInt(2, idbusqueda);

            statement.execute();

            ResultSet resultSet = (ResultSet) statement.getObject(1);
            DefaultTableModel tableModel = (DefaultTableModel) TablaVistaClienteP.getModel();
            tableModel.setRowCount(0); // Limpiar la tabla antes de mostrar nuevos datos

            while (resultSet.next()) {
                int idCliente = resultSet.getInt("ID_CLIENTE");
                String nombre = resultSet.getString("NOMBRE");
                int idProducto = resultSet.getInt("ID_PRODUCTO");
                int idPedido = resultSet.getInt("ID_PEDIDO");
                java.util.Date fecha = resultSet.getDate("FECHA");
                BigDecimal totalFactura = resultSet.getBigDecimal("TOTAL_FACTURA");
                String estadoProducto = resultSet.getString("ESTADO_PRODUCTO");
                

                Object[] rowData = {idCliente, nombre ,idProducto, idPedido, fecha, totalFactura, estadoProducto};
                tableModel.addRow(rowData);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnBuscarPedidosCActionPerformed

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
            java.util.logging.Logger.getLogger(V_Clientes_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(V_Clientes_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(V_Clientes_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(V_Clientes_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new V_Clientes_Productos().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(V_Clientes_Productos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FONDO;
    private javax.swing.JTable TablaVistaClienteP;
    private javax.swing.JButton btnBuscarPedidosC;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBusquedaPedidoC;
    // End of variables declaration//GEN-END:variables
}
