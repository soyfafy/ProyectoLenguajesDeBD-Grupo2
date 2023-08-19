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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Departamentos extends javax.swing.JFrame {
    private Connection con;
    int xMouse, yMouse;

    public Departamentos() throws SQLException {
        initComponents();
        con = conexion.conectar();
        MostrarRegistroDepartamento();

    }
    
        //Progra para tabla de departamentos//////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarDepartamento() throws SQLException, ParseException {
        String depaIdText = txtIDdepartamento.getText();
        String nombreDepaText = txtNombreDepa.getText();
        String ubiDepaText = txtUbicacionDepa.getText();
        String idProveedorDepaText = txtIDproveedorDepa.getText();
        String idEmpleadoDepaText = txtIDempleadoDepa.getText();

        try {
            int depaId = Integer.parseInt(depaIdText);
            String nombreDepa = nombreDepaText;
            String ubiDepa = ubiDepaText;
            int idProveedorDepa = Integer.parseInt(idProveedorDepaText);
            int idEmpleadoDepa = Integer.parseInt(idEmpleadoDepaText);

            // Llamada al procedimiento almacenado para insertar departamento
            insertarDepartamentoEnBD(depaId, nombreDepa, ubiDepa, idProveedorDepa, idEmpleadoDepa);
            limpiarCamposDepartamentos();
            limpiar_tabla_Departamento();
            MostrarRegistroDepartamento();

            JOptionPane.showMessageDialog(this, "Departamento insertado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el departamento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarDepartamento() throws SQLException {
        String depaIdText = txtIDdepartamento.getText();
        String nombreDepaText = txtNombreDepa.getText();
        String ubiDepaText = txtUbicacionDepa.getText();
        String idProveedorDepaText = txtIDproveedorDepa.getText();
        String idEmpleadoDepaText = txtIDempleadoDepa.getText();

        try {
            int depaId = Integer.parseInt(depaIdText);
            String nombreDepa = nombreDepaText;
            String ubiDepa = ubiDepaText;
            int idProveedorDepa = Integer.parseInt(idProveedorDepaText);
            int idEmpleadoDepa = Integer.parseInt(idEmpleadoDepaText);

            // Llamada al procedimiento almacenado para modificar el departamento
            modificarDepartamentoEnBD(depaId, nombreDepa, ubiDepa, idProveedorDepa, idEmpleadoDepa);
            limpiarCamposDepartamentos();
            limpiar_tabla_Departamento();
            MostrarRegistroDepartamento();

            JOptionPane.showMessageDialog(this, "Departamento modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el departamento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDepartamento() throws SQLException {
        String depaIdText = txtIDdepartamento.getText();

        try {
            int depaId = Integer.parseInt(depaIdText);

            // Llamada al procedimiento almacenado para eliminar el departamento
            eliminarDepartamentoEnBD(depaId);
            limpiarCamposDepartamentos();
            limpiar_tabla_Departamento();
            MostrarRegistroDepartamento();

            JOptionPane.showMessageDialog(this, "Departamento eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el departamento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarDepartamentoEnBD(int depaId, String nombreDepa, String ubiDepa, int idProveedorDepa, int idEmpleadoDepa) throws SQLException {
        String sql = "{CALL DEPARTAMENTOS_DB.INSERTAR_DEPARTAMENTO(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, depaId);
            statement.setString(2, nombreDepa);
            statement.setString(3, ubiDepa);
            statement.setInt(4, idProveedorDepa);
            statement.setInt(5, idEmpleadoDepa);
            statement.execute();

        }
    }

    private void modificarDepartamentoEnBD(int depaId, String nombreDepa, String ubiDepa, int idProveedorDepa, int idEmpleadoDepa) throws SQLException {
        String sql = "{CALL DEPARTAMENTOS_DB.MODIFICAR_DEPARTAMENTO(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(5, idEmpleadoDepa);
            statement.setInt(4, idProveedorDepa);
            statement.setString(3, ubiDepa);
            statement.setString(2, nombreDepa);
            statement.setInt(1, depaId);
            statement.execute();

        }
    }
    
    private void eliminarDepartamentoEnBD(int depaId) throws SQLException {
        String sql = "{CALL DEPARTAMENTOS_DB.ELIMINAR_DEPARTAMENTO(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, depaId);
            statement.execute();

        }
    }
    
        public void limpiarCamposDepartamentos() {
        txtIDdepartamento.setText("");
        txtNombreDepa.setText("");
        txtUbicacionDepa.setText("");
        txtIDproveedorDepa.setText("");
        txtIDempleadoDepa.setText("");

    }

        public void MostrarRegistroDepartamento(){
        String sql = "SELECT * FROM DEPARTAMENTOS";
        
        DefaultTableModel tabla = (DefaultTableModel) this.Tabla_Departamentos.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[5];
                fila[0] = rs.getString("DEPARTAMENTOS_ID");
                fila[1] = rs.getString("NOMBRE");
                fila[2] = rs.getString("UBICACION");
                fila[3] = rs.getString("ID_PROVEEDOR");
                fila[4] = rs.getString("EMPLEADO_ID");

                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla_Departamento(){
        DefaultTableModel modelo = (DefaultTableModel) this.Tabla_Departamentos.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItemDepartamentos(){
        int fila = this.Tabla_Departamentos.getSelectedRow();
        if (fila != -1){
            this.txtIDdepartamento.setText(this.Tabla_Departamentos.getValueAt(fila, 0).toString().trim());
            this.txtNombreDepa.setText(this.Tabla_Departamentos.getValueAt(fila, 1).toString().trim());
            this.txtUbicacionDepa.setText(this.Tabla_Departamentos.getValueAt(fila, 2).toString().trim());
            this.txtIDproveedorDepa.setText(this.Tabla_Departamentos.getValueAt(fila, 3).toString().trim());
            this.txtIDempleadoDepa.setText(this.Tabla_Departamentos.getValueAt(fila, 4).toString().trim());
//            this.txtEmpleadoId.setText(this.tblFacturas.getValueAt(fila, 3).toString().trim());
//            this.txtIdCliente.setText(this.tblFacturas.getValueAt(fila, 4).toString().trim());
//            this.txtPagoId.setText(this.tblFacturas.getValueAt(fila, 5).toString().trim());

            
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Departamentos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtIDdepartamento = new javax.swing.JTextField();
        txtNombreDepa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUbicacionDepa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIDproveedorDepa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtIDempleadoDepa = new javax.swing.JTextField();
        btnIngresarE = new javax.swing.JButton();
        btnModificarE = new javax.swing.JButton();
        btnEliminarE = new javax.swing.JButton();
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
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(exitBtnLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(exitTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        exitBtnLayout.setVerticalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(exitBtnLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(exitTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(exitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, -1, -1));

        Tabla_Departamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DEPARTAMENTOS_ID", "NOMBRE", "UBICACION", "ID_PROVEEDOR", "EMPLEADO_ID"
            }
        ));
        Tabla_Departamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_DepartamentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Departamentos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 480, 290));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID Departamento:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 250, -1, -1));
        getContentPane().add(txtIDdepartamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 420, 30));
        getContentPane().add(txtNombreDepa, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, 420, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nombre:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 300, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Ubicacion:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, -1, -1));
        getContentPane().add(txtUbicacionDepa, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 420, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("ID Proveedor:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, -1, -1));
        getContentPane().add(txtIDproveedorDepa, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 420, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("ID Empleado:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 460, -1, -1));
        getContentPane().add(txtIDempleadoDepa, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, 420, 30));

        btnIngresarE.setBackground(new java.awt.Color(204, 255, 204));
        btnIngresarE.setText("Ingresar");
        btnIngresarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarEActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresarE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 530, -1, -1));

        btnModificarE.setBackground(new java.awt.Color(255, 153, 102));
        btnModificarE.setText("Modificar");
        btnModificarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarE, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 530, -1, -1));

        btnEliminarE.setBackground(new java.awt.Color(255, 102, 102));
        btnEliminarE.setText("Eliminar");
        btnEliminarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarE, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 530, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/DEPARTAMENTOS.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseClicked
        Inicio v1 = null;
        try {
            v1 = new Inicio();
        } catch (SQLException ex) {
            Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
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

    private void Tabla_DepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_DepartamentosMouseClicked
        SeleccionarItemDepartamentos();
    }//GEN-LAST:event_Tabla_DepartamentosMouseClicked

    private void btnIngresarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarEActionPerformed
        try {
            insertarDepartamento();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIngresarEActionPerformed

    private void btnModificarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEActionPerformed
        try {
            modificarDepartamento();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModificarEActionPerformed

    private void btnEliminarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEActionPerformed
        try {
            eliminarDepartamento();
        } catch (SQLException ex) {
            Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnEliminarEActionPerformed

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
            java.util.logging.Logger.getLogger(Departamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Departamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Departamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Departamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new Departamentos().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_Departamentos;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtIDdepartamento;
    private javax.swing.JTextField txtIDempleadoDepa;
    private javax.swing.JTextField txtIDproveedorDepa;
    private javax.swing.JTextField txtNombreDepa;
    private javax.swing.JTextField txtUbicacionDepa;
    // End of variables declaration//GEN-END:variables
}
