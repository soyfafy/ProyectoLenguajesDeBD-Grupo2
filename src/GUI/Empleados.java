package GUI;

/**
 *
 * @author Grupo 2 - Lenguajes de Bases de datos
 */

import Conexion.conexion;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Empleados extends javax.swing.JFrame {
    private Connection con;
    int xMouse, yMouse;

    public Empleados() throws SQLException {
        initComponents();
        con = conexion.conectar();
        MostrarRegistroEmpleados();
    }
    
    //Progra para tabla de Empleados//////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarEmpleado() throws SQLException, ParseException {
        String empleadoIdText = txtIDempleado.getText();
        String nombreEText = txtNombreE.getText();
        String apellidoEText = txtApellidoE.getText();
        String nacimientoEText = txtNacimientoE.getText();
        String puestoEText = txtPuestoE.getText();
        String idPedidoEText = txtIDpedidoE.getText();

        try {
            int empleadoId = Integer.parseInt(empleadoIdText);
            String nombreE = nombreEText;
            String apellidoE = apellidoEText;
            String nacimientoE = nacimientoEText;
            String puestoE = puestoEText;
            int idPedidoE = Integer.parseInt(idPedidoEText);

            // Llamada al procedimiento almacenado para insertar la Empleado
            insertarEmpleadoEnBD(empleadoId, nombreE, apellidoE, nacimientoE, puestoE, idPedidoE);
            limpiarCamposEmpleados();
            limpiar_tabla_Empleados();
            MostrarRegistroEmpleados();

            JOptionPane.showMessageDialog(this, "Empleado insertado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEmpleado() throws SQLException {
        String empleadoIdText = txtIDempleado.getText();
        String nombreEText = txtNombreE.getText();
        String apellidoEText = txtApellidoE.getText();
        String puestoEText = txtPuestoE.getText();

        try {
            int empleadoId = Integer.parseInt(empleadoIdText);
            String nombreE = nombreEText;
            String apellidoE = apellidoEText;
            String puestoE = puestoEText;

            // Llamada al procedimiento almacenado para modificar el pedido
            modificarEmpleadoEnBD(empleadoId, nombreE, apellidoE, puestoE);
            limpiarCamposEmpleados();
            limpiar_tabla_Empleados();
            MostrarRegistroEmpleados();

            JOptionPane.showMessageDialog(this, "Empleado modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPedido() throws SQLException {
        String empleadoIdText = txtIDempleado.getText();

        try {
            int empleadoId = Integer.parseInt(empleadoIdText);

            // Llamada al procedimiento almacenado para eliminar el pedido
            eliminarEmpleadoEnBD(empleadoId);
            limpiarCamposEmpleados();
            limpiar_tabla_Empleados();
            MostrarRegistroEmpleados();

            JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarEmpleadoEnBD(int empleadoId, String nombreE, String apellidoE, String nacimientoE, String puestoE, int idPedidoE) throws SQLException {
        String sql = "{CALL EMPLEADOS_DB.INSERTAR_EMPLEADO(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, empleadoId);
            statement.setString(2, nombreE);
            statement.setString(3, apellidoE);
            statement.setString(4, nacimientoE);
            statement.setString(5, puestoE);
            statement.setInt(6, idPedidoE);
            statement.execute();

        }
    }

    private void modificarEmpleadoEnBD(int empleadoId, String nombreE, String apellidoE, String puestoE) throws SQLException {
        String sql = "{CALL EMPLEADOS_DB.MODIFICAR_EMPLEADO(?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setString(4, puestoE);
            statement.setString(3, apellidoE);
            statement.setString(2, nombreE);
            statement.setInt(1, empleadoId);
            statement.execute();

        }
    }
    
    private void eliminarEmpleadoEnBD(int empleadoId) throws SQLException {
        String sql = "{CALL EMPLEADOS_DB.ELIMINAR_EMPLEADO(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, empleadoId);
            statement.execute();

        }
    }
    
        public void limpiarCamposEmpleados() {
        txtIDempleado.setText("");
        txtNombreE.setText("");
        txtApellidoE.setText("");
        txtNacimientoE.setText("");
        txtPuestoE.setText("");
        txtIDpedidoE.setText("");
    }

        public void MostrarRegistroEmpleados(){
        String sql = "SELECT * FROM EMPLEADO";
        
        DefaultTableModel tabla = (DefaultTableModel) this.Tabla_Empleados.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[6];
                fila[0] = rs.getString("EMPLEADO_ID");
                fila[1] = rs.getString("NOMBRE");
                fila[2] = rs.getString("APELLIDO");
                fila[3] = rs.getString("FECHA_NACIMIENTO");
                fila[4] = rs.getString("PUESTO");
                fila[5] = rs.getString("ID_PEDIDOS");

                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla_Empleados(){
        DefaultTableModel modelo = (DefaultTableModel) this.Tabla_Empleados.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItemPedidos(){
        int fila = this.Tabla_Empleados.getSelectedRow();
        if (fila != -1){
            this.txtIDempleado.setText(this.Tabla_Empleados.getValueAt(fila, 0).toString().trim());
            this.txtNombreE.setText(this.Tabla_Empleados.getValueAt(fila, 1).toString().trim());
            this.txtApellidoE.setText(this.Tabla_Empleados.getValueAt(fila, 2).toString().trim());
            this.txtPuestoE.setText(this.Tabla_Empleados.getValueAt(fila, 4).toString().trim());
//            this.txtIDpedidoE.setText(this.Tabla_Empleados.getValueAt(fila, 5).toString().trim());
//            this.txtEmpleadoId.setText(this.tblFacturas.getValueAt(fila, 3).toString().trim());
//            this.txtIdCliente.setText(this.tblFacturas.getValueAt(fila, 4).toString().trim());
//            this.txtPagoId.setText(this.tblFacturas.getValueAt(fila, 5).toString().trim());

            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtIDpedidoE = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtIDempleado = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreE = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellidoE = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNacimientoE = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPuestoE = new javax.swing.JTextField();
        btnIngresarE = new javax.swing.JButton();
        btnModificarE = new javax.swing.JButton();
        btnEliminarE = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Empleados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exitBtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        exitBtnLayout.setVerticalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exitBtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(exitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("ID Pedido:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 500, -1, -1));
        getContentPane().add(txtIDpedidoE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 420, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID Empleado:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 250, -1, -1));
        getContentPane().add(txtIDempleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 420, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nombre:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 300, -1, -1));
        getContentPane().add(txtNombreE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, 420, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Apellido:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, -1, -1));
        getContentPane().add(txtApellidoE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 420, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Fecha Nacimiento:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, -1, -1));
        getContentPane().add(txtNacimientoE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 420, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Puesto:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 450, -1, -1));
        getContentPane().add(txtPuestoE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 470, 420, 30));

        btnIngresarE.setBackground(new java.awt.Color(204, 255, 204));
        btnIngresarE.setText("Ingresar");
        btnIngresarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarEActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresarE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, -1, -1));

        btnModificarE.setBackground(new java.awt.Color(255, 153, 102));
        btnModificarE.setText("Modificar");
        btnModificarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarE, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, -1, -1));

        btnEliminarE.setBackground(new java.awt.Color(255, 102, 102));
        btnEliminarE.setText("Eliminar");
        btnEliminarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarE, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 560, -1, -1));

        Tabla_Empleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "APELLIDO", "FECHA NACIMIENTO", "PUESTO", "ID_PEDIDOS"
            }
        ));
        Tabla_Empleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_EmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Empleados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 480, 290));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/EMPLEADOS.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseClicked
        Inicio v1 = null;
        try {
            v1 = new Inicio();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
        v1.show();
    }//GEN-LAST:event_exitTxtMouseClicked

    private void exitTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseEntered
        exitBtn.setBackground(Color.red);
        exitTxt.setForeground(Color.white);
    }//GEN-LAST:event_exitTxtMouseEntered

    private void exitTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseExited
        exitBtn.setBackground(Color.white);
        exitTxt.setForeground(Color.black);
    }//GEN-LAST:event_exitTxtMouseExited

    private void btnIngresarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarEActionPerformed
        try {     
            insertarEmpleado();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIngresarEActionPerformed

    private void btnModificarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEActionPerformed
        try {       
            modificarEmpleado();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModificarEActionPerformed

    private void btnEliminarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEActionPerformed
        try {
            eliminarPedido();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEliminarEActionPerformed

    private void Tabla_EmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_EmpleadosMouseClicked
        SeleccionarItemPedidos();
    }//GEN-LAST:event_Tabla_EmpleadosMouseClicked

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
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Empleados().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_Empleados;
    private javax.swing.JButton btnEliminarE;
    private javax.swing.JButton btnIngresarE;
    private javax.swing.JButton btnModificarE;
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
    private javax.swing.JTextField txtApellidoE;
    private javax.swing.JTextField txtIDempleado;
    private javax.swing.JTextField txtIDpedidoE;
    private javax.swing.JTextField txtNacimientoE;
    private javax.swing.JTextField txtNombreE;
    private javax.swing.JTextField txtPuestoE;
    // End of variables declaration//GEN-END:variables
}
