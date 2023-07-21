package GUI;

import Conexion.conexion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Grupo 2 - Lenguajes de Bases de datos
 */

public class Clientes extends javax.swing.JFrame {
    private Connection con;
    int id;
    private DefaultTableModel tabla;

    
    public Clientes() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        con = conexion.conectar();
        Clases.Clientes objeto = new Clases.Clientes();
        MostrarRegistro();
    }

    public void insertar (Clases.Clientes objeto) throws SQLException{
     CallableStatement sql = con.prepareCall("{call CLIENTES_DB.INSERTAR_CLIENTE(?,?,?,?,?)}");
     try{
         sql.setInt(1,objeto.getId_cliente());
         sql.setString(2,objeto.getNombre());
         sql.setString(3,objeto.getDescripcion());
         sql.setString(4,objeto.getTelefono());
         sql.setString(5,objeto.getCorreo());
         
         ResultSet rs = sql.executeQuery();
         MostrarRegistro();
         rs.close();
         }catch(Exception ex){
         JOptionPane.showMessageDialog(rootPane,"ERROR AL INSERTAR DATOS"+ex);
        
     
     }
    }
    
    public void modificar (Clases.Clientes objeto) throws SQLException{
     CallableStatement sql = con.prepareCall("{call CLIENTES_DB.MODIFICAR_CLIENTE(?,?,?,?,?)}");
     try{
         
         sql.setString(2,objeto.getNombre());
         sql.setString(3,objeto.getDescripcion());
         sql.setString(4,objeto.getTelefono());
         sql.setString(5,objeto.getCorreo());
         sql.setInt(1,objeto.getId_cliente());
         
         ResultSet rs = sql.executeQuery();
         rs.close();
         
         }catch(Exception ex){
         JOptionPane.showMessageDialog(rootPane,"ERROR AL INSERTAR DATOS"+ex);
     
     }
    }
    
    public void eliminar (int id) throws SQLException{
     CallableStatement sql = con.prepareCall("{call CLIENTES_DB.ELIMINAR_CLIENTE(?)}");

         sql.setInt(1,id);
         sql.execute();
         sql.close();

    }
    
    public void MostrarRegistro(){
        String sql = "SELECT * FROM CLIENTES";
        
        DefaultTableModel tabla = (DefaultTableModel) this.Tabla_Clientes.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[5];
                fila[0] = rs.getString("ID_Cliente");
                fila[1] = rs.getString("NOMBRE");
                fila[2] = rs.getString("DESCRIPCION");
                fila[3] = rs.getString("TELEFONO");
                fila[4] = rs.getString("CORREO");
                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla(){
        DefaultTableModel modelo = (DefaultTableModel) this.Tabla_Clientes.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItem(){
        int fila = this.Tabla_Clientes.getSelectedRow();
        if (fila != -1){
            this.txtID.setText(this.Tabla_Clientes.getValueAt(fila, 0).toString().trim());
            this.txtNombre.setText(this.Tabla_Clientes.getValueAt(fila, 1).toString().trim());
            this.txtDescripcion.setText(this.Tabla_Clientes.getValueAt(fila, 2).toString().trim());
            this.txtTelefono.setText(this.Tabla_Clientes.getValueAt(fila, 3).toString().trim());
            this.txtCorreo.setText(this.Tabla_Clientes.getValueAt(fila, 4).toString().trim());
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

        btnDirecciones = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnIngresar = new javax.swing.JButton();
        txtCorreo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Clientes = new javax.swing.JTable();
        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDirecciones.setBackground(new java.awt.Color(0, 0, 0));
        btnDirecciones.setForeground(new java.awt.Color(255, 255, 255));
        btnDirecciones.setText("Direccion de pedidos");
        btnDirecciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDireccionesActionPerformed(evt);
            }
        });
        getContentPane().add(btnDirecciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 560, -1, -1));

        btnReportes.setBackground(new java.awt.Color(0, 0, 0));
        btnReportes.setForeground(new java.awt.Color(255, 255, 255));
        btnReportes.setText("Mas informacion");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        getContentPane().add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 560, -1, -1));

        btnEliminar.setBackground(new java.awt.Color(255, 102, 102));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 560, -1, -1));

        btnModificar.setBackground(new java.awt.Color(255, 153, 102));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, -1, -1));

        btnIngresar.setBackground(new java.awt.Color(204, 255, 204));
        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, -1, -1));
        getContentPane().add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 510, 420, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Correo:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 490, -1, -1));
        getContentPane().add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 450, 420, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Telefono:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, -1, -1));
        getContentPane().add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 390, 420, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Descripcion:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, -1, -1));
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 330, 420, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nombre:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 310, -1, -1));
        getContentPane().add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 420, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 250, -1, -1));

        Tabla_Clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "DESCRIPCION", "TELEFONO", "CORREO"
            }
        ));
        Tabla_Clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_ClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Clientes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 480, 290));

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

        getContentPane().add(exitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/CLIENTES.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        // Boton Ingresar
        if(this.txtID.getText().trim()==""){
            JOptionPane.showMessageDialog(rootPane,"Ingresar ID CLIENTE");
            return; 
        }
        if(this.txtNombre.getText().trim()==""){
            JOptionPane.showMessageDialog(rootPane,"Ingresar NOMBRE");
            return; 
        }
        if(this.txtDescripcion.getText().trim()==""){
                JOptionPane.showMessageDialog(rootPane,"Ingresar DESCRIPCION");
            return; 
        }
        if(this.txtTelefono.getText().trim()==""){
            JOptionPane.showMessageDialog(rootPane,"Ingresar TELEFONO");
            return; 
        }
        if(this.txtCorreo.getText().trim()==""){
            JOptionPane.showMessageDialog(rootPane,"Ingresar CORREO");
            return; 
        }else{
            {
            int id_cliente = Integer.parseInt(txtID.getText());
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            
            try{
                Clases.Clientes objetos = new Clases.Clientes(id_cliente,nombre,descripcion,telefono,correo);
                insertar(objetos);
                
                JOptionPane.showMessageDialog(rootPane,"DATOS GUARDADOS CON EXITO!");
                txtID.setText(null);
                txtNombre.setText(null);
                txtDescripcion.setText(null);
                txtTelefono.setText(null);
                txtCorreo.setText(null);
                
                limpiar_tabla();
                MostrarRegistro();  
            }catch(Exception ex){
                JOptionPane.showMessageDialog(rootPane,"ERROR!"+ex);
            }  
          }
        }
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Boton Eliminar
        try{
            JOptionPane.showMessageDialog(null,"REGISTRO ELIMINADO CON EXITO");
            id = Integer.parseInt(String.valueOf(this.Tabla_Clientes.getValueAt(this.Tabla_Clientes.getSelectedRow(),0)));
            eliminar(id);
            Clases.Clientes en = new Clases.Clientes();
            limpiar_tabla();
            MostrarRegistro();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"SELECCIONE EL REGISTRO QUE DESEA ELIMINAR"+e);
            
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // Boton Modificar
        try{
            Connection cn = conexion.conectar();
            String sql = ("{call CLIENTES_DB.MODIFICAR_CLIENTE(?,?,?,?,?)}");
            CallableStatement ps = cn.prepareCall(sql);
            ps.setInt(1, Integer.valueOf(txtID.getText()));
            ps.setString(2, txtNombre.getText());
            ps.setString(3, txtDescripcion.getText());
            ps.setString(4, txtTelefono.getText());
            ps.setString(5, txtCorreo.getText());

            ps.execute();
            ps.close();
            cn.close();
            JOptionPane.showMessageDialog(rootPane,"REGISTRO MODIFICADO CON EXITO");
            
            limpiar_tabla();
            MostrarRegistro();
            
        }catch(Exception e){
         JOptionPane.showMessageDialog(rootPane,"ERROR AL INSERTAR DATOS"+e);
         }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void Tabla_ClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_ClientesMouseClicked
        SeleccionarItem();
    }//GEN-LAST:event_Tabla_ClientesMouseClicked

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
            ReporteClientes v1 = null;
        try {
            v1 = new ReporteClientes();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
            v1.show();
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnDireccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDireccionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDireccionesActionPerformed

    private void exitTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseClicked
        Inicio v1 = new Inicio();
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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Clientes().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_Clientes;
    private javax.swing.JButton btnDirecciones;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReportes;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
