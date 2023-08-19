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
import Conexion.conexion;
import java.awt.Color;
import java.math.BigDecimal;
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


public class Productos extends javax.swing.JFrame {
    private Connection con;
    int xMouse, yMouse;

    public Productos() throws SQLException {
        initComponents();
        con = conexion.conectar();
        MostrarRegistroProductos();
    }
        //Progra para tabla de Productos//////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarProducto() throws SQLException, ParseException {
        String IDproductoText = txtIDproducto.getText();
        String IDproveedorProText = txtIDproveedorPro.getText();
        String NombreProText = txtNombrePro.getText();
        String DescripcionProText = txtDescripcionPro.getText();
        String PrecioProText = txtPrecioPro.getText();
        String StockProText = txtStockPro.getText();

        try {
            int IDproducto = Integer.parseInt(IDproductoText);
            int IDproveedorPro = Integer.parseInt(IDproveedorProText);
            String NombrePro = NombreProText;
            String DescripcionPro = DescripcionProText;
            BigDecimal PrecioPro = new BigDecimal(PrecioProText);
            int StockPro = Integer.parseInt(StockProText);

            // Llamada al procedimiento almacenado para insertar producto
            insertarProductoEnBD(IDproducto, IDproveedorPro, NombrePro, DescripcionPro, PrecioPro, StockPro);
            limpiarCamposProductos();
            limpiar_tabla_Productos();
            MostrarRegistroProductos();

            JOptionPane.showMessageDialog(this, "Producto insertado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarProducto() throws SQLException {
        String IDproductoText = txtIDproducto.getText();
        String IDproveedorProText = txtIDproveedorPro.getText();
        String NombreProText = txtNombrePro.getText();
        String DescripcionProText = txtDescripcionPro.getText();
        String PrecioProText = txtPrecioPro.getText();
        String StockProText = txtStockPro.getText();

        try {
            int IDproducto = Integer.parseInt(IDproductoText);
            int IDproveedorPro = Integer.parseInt(IDproveedorProText);
            String NombrePro = NombreProText;
            String DescripcionPro = DescripcionProText;
            BigDecimal PrecioPro = new BigDecimal(PrecioProText);
            int StockPro = Integer.parseInt(StockProText);

            // Llamada al procedimiento almacenado para modificar el producto
            modificarProductoEnBD(IDproducto, IDproveedorPro, NombrePro, DescripcionPro, PrecioPro, StockPro);
            limpiarCamposProductos();
            limpiar_tabla_Productos();
            MostrarRegistroProductos();

            JOptionPane.showMessageDialog(this, "Producto modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el productoo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() throws SQLException {
        String IDproductoText = txtIDproducto.getText();

        try {
            int IDproducto = Integer.parseInt(IDproductoText);

            // Llamada al procedimiento almacenado para eliminar el producto
            eliminarProductoEnBD(IDproducto);
            limpiarCamposProductos();
            limpiar_tabla_Productos();
            MostrarRegistroProductos();

            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarProductoEnBD(int IDproducto, int IDproveedorPro, String NombrePro,  String DescripcionPro, BigDecimal PrecioPro, int StockPro) throws SQLException {
        String sql = "{CALL PRODUCTOS_DB.INSERTAR_PRODUCTO(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, IDproducto);
            statement.setInt(2, IDproveedorPro);
            statement.setString(3, NombrePro);
            statement.setString(4, DescripcionPro);
            statement.setBigDecimal(5, PrecioPro);
            statement.setInt(6, StockPro);
            statement.execute();

        }
    }

    private void modificarProductoEnBD(int IDproducto, int IDproveedorPro, String NombrePro,  String DescripcionPro, BigDecimal PrecioPro, int StockPro) throws SQLException {
        String sql = "{CALL PRODUCTOS_DB.MODIFICAR_PRODUCTO(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(6, StockPro);
            statement.setBigDecimal(5, PrecioPro);
            statement.setString(4, DescripcionPro);
            statement.setString(3, NombrePro);
            statement.setInt(2, IDproveedorPro);
            statement.setInt(1, IDproducto);
            statement.execute();

        }
    }
    
    private void eliminarProductoEnBD(int IDproducto) throws SQLException {
        String sql = "{CALL PRODUCTOS_DB.ELIMINAR_PRODUCTO(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, IDproducto);
            statement.execute();

        }
    }
    
        public void limpiarCamposProductos() {
        txtIDproducto.setText("");
        txtIDproveedorPro.setText("");
        txtNombrePro.setText("");
        txtDescripcionPro.setText("");
        txtPrecioPro.setText("");
        txtStockPro.setText("");
    }

        public void MostrarRegistroProductos(){
        String sql = "SELECT * FROM PRODUCTOS";
        
        DefaultTableModel tabla = (DefaultTableModel) this.Tabla_Productos.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[6];
                fila[0] = rs.getString("ID_PRODUCTO");
                fila[1] = rs.getString("ID_PROVEEDOR");
                fila[2] = rs.getString("NOMBRE");
                fila[3] = rs.getString("DESCRIPCION");
                fila[4] = rs.getString("PRECIO");
                fila[5] = rs.getString("STOCK");

                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla_Productos(){
        DefaultTableModel modelo = (DefaultTableModel) this.Tabla_Productos.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItemProductos(){
        int fila = this.Tabla_Productos.getSelectedRow();
        if (fila != -1){
            this.txtIDproducto.setText(this.Tabla_Productos.getValueAt(fila, 0).toString().trim());
            this.txtIDproveedorPro.setText(this.Tabla_Productos.getValueAt(fila, 1).toString().trim());
            this.txtNombrePro.setText(this.Tabla_Productos.getValueAt(fila, 2).toString().trim());
            this.txtDescripcionPro.setText(this.Tabla_Productos.getValueAt(fila, 3).toString().trim());
            this.txtPrecioPro.setText(this.Tabla_Productos.getValueAt(fila, 4).toString().trim());
            this.txtStockPro.setText(this.Tabla_Productos.getValueAt(fila, 5).toString().trim());
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
        Tabla_Productos = new javax.swing.JTable();
        txtIDproducto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIDproveedorPro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombrePro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDescripcionPro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPrecioPro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtStockPro = new javax.swing.JTextField();
        btnIngresarPro = new javax.swing.JButton();
        btnModificarPro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
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

        Tabla_Productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_PRODUCTO", "ID_PROVEEDOR", "NOMBRE", "DESCRIPCION", "PRECIO", "STOCK"
            }
        ));
        Tabla_Productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_ProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Productos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 480, 290));
        getContentPane().add(txtIDproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 420, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID Producto:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 250, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("ID Proveedor:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 300, -1, -1));
        getContentPane().add(txtIDproveedorPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, 420, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Nombre:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, -1, -1));
        getContentPane().add(txtNombrePro, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 420, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Descripcion:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, -1, -1));
        getContentPane().add(txtDescripcionPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 420, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Precio:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 450, -1, -1));
        getContentPane().add(txtPrecioPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 470, 420, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Stock:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 500, -1, -1));
        getContentPane().add(txtStockPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 420, 30));

        btnIngresarPro.setBackground(new java.awt.Color(204, 255, 204));
        btnIngresarPro.setText("Ingresar");
        btnIngresarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarProActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, -1, -1));

        btnModificarPro.setBackground(new java.awt.Color(255, 153, 102));
        btnModificarPro.setText("Modificar");
        btnModificarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, -1, -1));

        btnEliminarPro.setBackground(new java.awt.Color(255, 102, 102));
        btnEliminarPro.setText("Eliminar");
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 560, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/PRODUCTOS.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseClicked
        Inicio v1 = null;
        try {
            v1 = new Inicio();
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
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

    private void Tabla_ProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_ProductosMouseClicked
        SeleccionarItemProductos();
    }//GEN-LAST:event_Tabla_ProductosMouseClicked

    private void btnIngresarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarProActionPerformed
        try {
            insertarProducto();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIngresarProActionPerformed

    private void btnModificarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProActionPerformed
        try {
            modificarProducto();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModificarProActionPerformed

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        try {
            eliminarProducto();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

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
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new Productos().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_Productos;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnIngresarPro;
    private javax.swing.JButton btnModificarPro;
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
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtIDproducto;
    private javax.swing.JTextField txtIDproveedorPro;
    private javax.swing.JTextField txtNombrePro;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtStockPro;
    // End of variables declaration//GEN-END:variables
}
