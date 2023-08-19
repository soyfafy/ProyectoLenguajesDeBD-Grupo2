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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;


public class Inicio extends javax.swing.JFrame {
    private Connection con;
    int xMouse, yMouse;

    public Inicio() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        con = conexion.conectar();
        MostrarRegistro();
        MostrarRegistroPedidos();
        MostrarRegistroDP();

    }

    //Progra para tabla de Facturas/////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarFactura() throws SQLException {
        String facturaIdText = txtFacturaId.getText();
        String totalText = txtTotal.getText();
        String empleadoIdText = txtEmpleadoId.getText();
        String idClienteText = txtIdCliente.getText();
        String pagoIdText = txtPagoId.getText();

        try {
            int facturaId = Integer.parseInt(facturaIdText);
            BigDecimal total = new BigDecimal(totalText);
            int empleadoId = Integer.parseInt(empleadoIdText);
            int idCliente = Integer.parseInt(idClienteText);
            int pagoId = Integer.parseInt(pagoIdText);

            // Llamada al procedimiento almacenado para insertar la factura
            insertarFacturaEnBD(facturaId, total, empleadoId, idCliente, pagoId);
            limpiarCamposFacturas();
            limpiar_tabla();
            MostrarRegistro();

            JOptionPane.showMessageDialog(this, "Factura insertada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarFactura() throws SQLException {
        String facturaIdText = txtFacturaId.getText();
        String totalText = txtTotal.getText();

        try {
            int facturaId = Integer.parseInt(facturaIdText);
            BigDecimal total = new BigDecimal(totalText);

            // Llamada al procedimiento almacenado para modificar la factura
            modificarFacturaEnBD(facturaId, total);
            limpiarCamposFacturas();
            limpiar_tabla();
            MostrarRegistro();

            JOptionPane.showMessageDialog(this, "Factura modificada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFactura() {
        String facturaIdText = txtFacturaId.getText();

        try {
            int facturaId = Integer.parseInt(facturaIdText);

            // Llamada al procedimiento almacenado para eliminar la factura
            eliminarFacturaEnBD(facturaId);
            limpiarCamposFacturas();
            limpiar_tabla();
            MostrarRegistro();

            JOptionPane.showMessageDialog(this, "Factura eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarFacturaEnBD(int facturaId, BigDecimal total, int empleadoId, int idCliente, int pagoId) throws SQLException {
        String sql = "{CALL FACTURAS_DB.INSERTAR_FACTURA(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, facturaId);
            statement.setBigDecimal(2, total);
            statement.setInt(3, empleadoId);
            statement.setInt(4, idCliente);
            statement.setInt(5, pagoId);
            statement.execute();
            statement.close();
            

        }
    }

    private void modificarFacturaEnBD(int facturaId, BigDecimal total) throws SQLException {
        String sql = "{CALL FACTURAS_DB.modificar_factura(?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setBigDecimal(2, total);
            statement.setInt(1, facturaId);
            statement.execute();
            statement.close();

        }
    }
    


    private void eliminarFacturaEnBD(int facturaId) throws SQLException {
        String sql = "{CALL FACTURAS_DB.eliminar_factura(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, facturaId);
            statement.execute();
            statement.close();
            
            

        }
    }
    
        public void limpiarCamposFacturas() {
        txtFacturaId.setText("");
        txtTotal.setText("");
        txtEmpleadoId.setText("");
        txtIdCliente.setText("");
        txtPagoId.setText("");
    }

        public void MostrarRegistro(){
        String sql = "SELECT * FROM FACTURAS";
        
        DefaultTableModel tabla = (DefaultTableModel) this.tblFacturas.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[7];
                fila[0] = rs.getString("FACTURA_ID");
                fila[1] = rs.getString("FECHA");
                fila[2] = rs.getString("TOTAL");
                fila[3] = rs.getString("EMPLEADO_ID");
                fila[4] = rs.getString("DESCRIPCION");
                fila[5] = rs.getString("ID_CLIENTE");
                fila[6] = rs.getString("PAGO_ID");
                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla(){
        DefaultTableModel modelo = (DefaultTableModel) this.tblFacturas.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItem(){
        int fila = this.tblFacturas.getSelectedRow();
        if (fila != -1){
            this.txtFacturaId.setText(this.tblFacturas.getValueAt(fila, 0).toString().trim());
            this.txtTotal.setText(this.tblFacturas.getValueAt(fila, 2).toString().trim());
//            this.txtEmpleadoId.setText(this.tblFacturas.getValueAt(fila, 3).toString().trim());
//            this.txtIdCliente.setText(this.tblFacturas.getValueAt(fila, 4).toString().trim());
//            this.txtPagoId.setText(this.tblFacturas.getValueAt(fila, 5).toString().trim());

            
        }
    }
    
    //Progra para tabla de Pedidos//////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarPedido() throws SQLException {
        String pedidoIdText = txtPedidoID.getText();
        String IDclientePText = txtIDclienteP.getText();
        String IDproductoPText = txtIDproductoP.getText();
        String EstadoPPText = txtEstadoP.getText();
        String CantidadPedidoText = txtCantidadPedido.getText();

        try {
            int pedidoID = Integer.parseInt(pedidoIdText);
            int IDclienteP = Integer.parseInt(IDclientePText);
            int IDproductoP = Integer.parseInt(IDproductoPText);
            String estadoP = EstadoPPText;
            int cantidadP = Integer.parseInt(CantidadPedidoText);

            // Llamada al procedimiento almacenado para insertar la factura
            insertarPedidoEnBD(pedidoID, IDclienteP, IDproductoP, estadoP, cantidadP);
            limpiarCamposPedidos();
            limpiar_tabla_Pedidos();
            MostrarRegistroPedidos();

            JOptionPane.showMessageDialog(this, "Pedido insertado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarPedido() throws SQLException {
        String pedidoIdText = txtPedidoID.getText();
        String IDproductoPText = txtIDproductoP.getText();
        String CantidadPedidoText = txtCantidadPedido.getText();

        try {
            int pedidoID = Integer.parseInt(pedidoIdText);
            int IDproductoP = Integer.parseInt(IDproductoPText);
            int cantidadP = Integer.parseInt(CantidadPedidoText);

            // Llamada al procedimiento almacenado para modificar el pedido
            modificarPedidoEnBD(pedidoID, IDproductoP,cantidadP);
            limpiarCamposPedidos();
            limpiar_tabla_Pedidos();
            MostrarRegistroPedidos();

            JOptionPane.showMessageDialog(this, "Factura modificada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPedido() {
        String pedidoIdText = txtPedidoID.getText();

        try {
            int pedidoID = Integer.parseInt(pedidoIdText);

            // Llamada al procedimiento almacenado para eliminar el pedido
            eliminarPedidoEnBD(pedidoID);
            limpiarCamposPedidos();
            limpiar_tabla_Pedidos();
            MostrarRegistroPedidos();

            JOptionPane.showMessageDialog(this, "Pedido eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarPedidoEnBD(int pedidoID, int IDclienteP, int IDproductoP, String estadoP, int cantidadP) throws SQLException {
        String sql = "{CALL PEDIDOS_DB.INSERTAR_PEDIDO(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, pedidoID);
            statement.setInt(2, IDclienteP);
            statement.setInt(3, IDproductoP);
            statement.setString(4, estadoP);
            statement.setInt(5, cantidadP);
            statement.execute();
            

        }
    }

    private void modificarPedidoEnBD(int pedidoID, int IDproductoP, int cantidadP) throws SQLException {
        String sql = "{CALL PEDIDOS_DB.MODIFICAR_PEDIDO(?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(3, cantidadP);
            statement.setInt(2, IDproductoP);
            statement.setInt(1, pedidoID);
            statement.execute();
            
           

        }
    }
    
    private void eliminarPedidoEnBD(int pedidoID) throws SQLException {
        String sql = "{CALL PEDIDOS_DB.ELIMINAR_PEDIDO(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, pedidoID);
            statement.execute();
                     

        }
    }
    
        public void limpiarCamposPedidos() {
        txtPedidoID.setText("");
        txtIDclienteP.setText("");
        txtIDproductoP.setText("");
        txtEstadoP.setText("");
        txtCantidadPedido.setText("");
    }

        public void MostrarRegistroPedidos(){
        String sql = "SELECT * FROM PEDIDOS";
        
        DefaultTableModel tabla = (DefaultTableModel) this.tabla_pedidos.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[7];
                fila[0] = rs.getString("ID_PEDIDO");
                fila[1] = rs.getString("ID_CLIENTE");
                fila[2] = rs.getString("ID_PRODUCTO");
                fila[3] = rs.getString("FECHA");
                fila[4] = rs.getString("TOTAL_FACTURA");
                fila[5] = rs.getString("ESTADO_PRODUCTO");
                fila[6] = rs.getString("CANTIDAD");
                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla_Pedidos(){
        DefaultTableModel modelo = (DefaultTableModel) this.tabla_pedidos.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItemPedidos(){
        int fila = this.tabla_pedidos.getSelectedRow();
        if (fila != -1){
            this.txtPedidoID.setText(this.tabla_pedidos.getValueAt(fila, 0).toString().trim());
            this.txtIDproductoP.setText(this.tabla_pedidos.getValueAt(fila, 2).toString().trim());
            this.txtCantidadPedido.setText(this.tabla_pedidos.getValueAt(fila, 6).toString().trim());
//            this.txtEmpleadoId.setText(this.tblFacturas.getValueAt(fila, 3).toString().trim());
//            this.txtIdCliente.setText(this.tblFacturas.getValueAt(fila, 4).toString().trim());
//            this.txtPagoId.setText(this.tblFacturas.getValueAt(fila, 5).toString().trim());

            
        }
    }
    
    //Progra para tabla de Detalles Pedidos//////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void insertarDetallePedido() throws SQLException {
        String DpedidoIdText = txtIDpedidoD.getText();
        String IDdetalleText = txtIDdetalle.getText();
        String IDdireccionText = txtIDdireccionD.getText();
        String cantidadDPText = txtCantidadD.getText();
        String precioUnitarioText = txtPrecioUnitario.getText();

        try {
            int DpedidoId = Integer.parseInt(DpedidoIdText);
            int IDdetalle = Integer.parseInt(IDdetalleText);
            int IDdireccion = Integer.parseInt(IDdireccionText);
            int cantidadDP = Integer.parseInt(cantidadDPText);
            BigDecimal precioUnitario = new BigDecimal(precioUnitarioText);

            // Llamada al procedimiento almacenado para insertar el detalle pedido
            insertarDPEnBD(IDdetalle, cantidadDP, precioUnitario, DpedidoId , IDdireccion);
            limpiarCamposDP();
            limpiar_tabla_DP();
            MostrarRegistroDP();

            JOptionPane.showMessageDialog(this, "Detalles del pedido insertado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar los detalles del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarDetallePedido() throws SQLException {
        String DpedidoIdText = txtIDpedidoD.getText();
        String IDdetalleText = txtIDdetalle.getText();
        String IDdireccionText = txtIDdireccionD.getText();
        String cantidadDPText = txtCantidadD.getText();
        String precioUnitarioText = txtPrecioUnitario.getText();

        try {
            int DpedidoId = Integer.parseInt(DpedidoIdText);
            int IDdetalle = Integer.parseInt(IDdetalleText);
            int IDdireccion = Integer.parseInt(IDdireccionText);
            int cantidadDP = Integer.parseInt(cantidadDPText);
            BigDecimal precioUnitario = new BigDecimal(precioUnitarioText);

            // Llamada al procedimiento almacenado para modificar los detalles del pedido
            modificarDPEnBD(DpedidoId, IDdetalle, IDdireccion, cantidadDP, precioUnitario);
            limpiarCamposDP();
            limpiar_tabla_DP();
            MostrarRegistroDP();

            JOptionPane.showMessageDialog(this, "Detalle del pedido modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el detalle del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDetallePedido() {
        String IDdetalleText = txtIDdetalle.getText();

        try {
            int IDdetalle = Integer.parseInt(IDdetalleText);

            // Llamada al procedimiento almacenado para eliminar el detalle del pedido
            eliminarDPEnBD(IDdetalle);
            limpiarCamposDP();
            limpiar_tabla_DP();
            MostrarRegistroDP();

            JOptionPane.showMessageDialog(this, "Detalle del pedido eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el detalle del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarDPEnBD(int IDdetalle,  int cantidadDP,  BigDecimal precioUnitario, int DpedidoId ,int IDdireccion) throws SQLException {
        String sql = "{CALL DETALLES_PEDIDO_DB.INSERTAR_DETALLE_PEDIDO(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, IDdetalle);
            statement.setInt(2, cantidadDP);
            statement.setBigDecimal(3, precioUnitario);
            statement.setInt(4, DpedidoId);
            statement.setInt(5, IDdireccion);
            statement.execute();
        }
    }

    private void modificarDPEnBD(int DpedidoId, int IDdetalle, int IDdireccion, int cantidadDP, BigDecimal precioUnitario) throws SQLException {
        String sql = "{CALL DETALLES_PEDIDO_DB.MODIFICAR_DETALLE_PEDIDO(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(5, IDdireccion);
            statement.setInt(4, DpedidoId);
            statement.setBigDecimal(3, precioUnitario);
            statement.setInt(2, cantidadDP);
            statement.setInt(1, IDdetalle);
            statement.execute();
            
           

        }
    }
    
    private void eliminarDPEnBD(int IDdetalle) throws SQLException {
        String sql = "{CALL DETALLES_PEDIDO_DB.ELIMINAR_DETALLE_PEDIDO(?)}";
        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setInt(1, IDdetalle);
            statement.execute();
                     

        }
    }
    
        public void limpiarCamposDP() {
        txtIDpedidoD.setText("");
        txtIDdetalle.setText("");
        txtIDdireccionD.setText("");
        txtCantidadD.setText("");
        txtPrecioUnitario.setText("");
    }

        public void MostrarRegistroDP(){
        String sql = "SELECT * FROM DETALLES_PEDIDO";
        
        DefaultTableModel tabla = (DefaultTableModel) this.tabla_DP.getModel();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = new Object[5];
                fila[0] = rs.getString("ID_DETALLE");
                fila[1] = rs.getString("CANTIDAD");
                fila[2] = rs.getString("PRECIO_UNITARIO");
                fila[3] = rs.getString("ID_PEDIDO");
                fila[4] = rs.getString("DIRECCION_ID");

                
                tabla.addRow(fila); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"ERROR AL MOSTRAR REGISTRO"+ex);
            
        }  
    }
    
    public void limpiar_tabla_DP(){
        DefaultTableModel modelo = (DefaultTableModel) this.tabla_DP.getModel();
        modelo.setRowCount(0);   
    }
    
    public void SeleccionarItemDP(){
        int fila = this.tabla_DP.getSelectedRow();
        if (fila != -1){
            
            this.txtIDdetalle.setText(this.tabla_DP.getValueAt(fila, 0).toString().trim());
            this.txtCantidadD.setText(this.tabla_DP.getValueAt(fila, 1).toString().trim());
            this.txtPrecioUnitario.setText(this.tabla_DP.getValueAt(fila, 2).toString().trim());
            this.txtIDpedidoD.setText(this.tabla_DP.getValueAt(fila, 3).toString().trim());
            this.txtIDdireccionD.setText(this.tabla_DP.getValueAt(fila, 4).toString().trim());           
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

        btnVerificarDetalles = new javax.swing.JButton();
        btnInsertarDetallesP = new javax.swing.JButton();
        btnActualizarDetallesP = new javax.swing.JButton();
        btnEliminarDetallesP = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtPrecioUnitario = new javax.swing.JTextField();
        txtIDdetalle = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtIDdireccionD = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtCantidadD = new javax.swing.JTextField();
        txtIDpedidoD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnDisponibilidad = new javax.swing.JButton();
        eliminarP = new javax.swing.JButton();
        modificarP = new javax.swing.JButton();
        btnInsertarP = new javax.swing.JButton();
        txtCantidadPedido = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtEstadoP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtIDproductoP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtIDclienteP = new javax.swing.JTextField();
        txtPedidoID = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnF_Pagos = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla_pedidos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_DP = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFacturas = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtPagoId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEmpleadoId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFacturaId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnDepartamentos = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        btnEliminarF = new javax.swing.JButton();
        btnModificarF = new javax.swing.JButton();
        btnInsertarF = new javax.swing.JButton();
        btnBuscarDC = new javax.swing.JButton();
        btnBuscarPrecioP1 = new javax.swing.JButton();
        btnBuscarPP = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        btnVerificarDetalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/check.png"))); // NOI18N
        btnVerificarDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarDetallesActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerificarDetalles, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 490, 50, -1));

        btnInsertarDetallesP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/insert.png"))); // NOI18N
        btnInsertarDetallesP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarDetallesPActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertarDetallesP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 30, -1));

        btnActualizarDetallesP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/update.png"))); // NOI18N
        btnActualizarDetallesP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarDetallesPActionPerformed(evt);
            }
        });
        getContentPane().add(btnActualizarDetallesP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 520, 30, -1));

        btnEliminarDetallesP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/delete  (1).png"))); // NOI18N
        btnEliminarDetallesP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDetallesPActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarDetallesP, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 30, -1));

        jLabel7.setText("ID Detalle:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, -1, -1));

        jLabel20.setText("Precio Unitario:");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, -1, -1));
        getContentPane().add(txtPrecioUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 490, 80, -1));
        getContentPane().add(txtIDdetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 440, 60, -1));

        jLabel18.setText("ID Direccion:");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, -1, -1));
        getContentPane().add(txtIDdireccionD, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 70, -1));

        jLabel19.setText("Cantidad:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));
        getContentPane().add(txtCantidadD, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 60, -1));
        getContentPane().add(txtIDpedidoD, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 60, -1));

        jLabel6.setText("ID Pedido:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, -1));

        btnDisponibilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/check.png"))); // NOI18N
        btnDisponibilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisponibilidadActionPerformed(evt);
            }
        });
        getContentPane().add(btnDisponibilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 340, 30, -1));

        eliminarP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/delete  (1).png"))); // NOI18N
        eliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarPActionPerformed(evt);
            }
        });
        getContentPane().add(eliminarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 370, 40, -1));

        modificarP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/update.png"))); // NOI18N
        modificarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarPActionPerformed(evt);
            }
        });
        getContentPane().add(modificarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, 40, -1));

        btnInsertarP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/insert.png"))); // NOI18N
        btnInsertarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarPActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 40, -1));
        getContentPane().add(txtCantidadPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, 50, -1));

        jLabel17.setText("Cantidad:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, -1, -1));

        jLabel16.setText("Estado Producto:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));
        getContentPane().add(txtEstadoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 100, -1));

        jLabel15.setText("ID Producto:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, -1, -1));
        getContentPane().add(txtIDproductoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 60, -1));

        jLabel14.setText("ID Cliente:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, -1, -1));
        getContentPane().add(txtIDclienteP, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 60, -1));
        getContentPane().add(txtPedidoID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 60, -1));

        jLabel10.setText("ID Pedido:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        btnF_Pagos.setBackground(new java.awt.Color(51, 102, 255));
        btnF_Pagos.setForeground(new java.awt.Color(255, 255, 255));
        btnF_Pagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/credit-card-blanco.png"))); // NOI18N
        btnF_Pagos.setText("Realizar pago factura");
        btnF_Pagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnF_PagosActionPerformed(evt);
            }
        });
        getContentPane().add(btnF_Pagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 560, 180, 30));

        tabla_pedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_PEDIDO", "ID_CLIENTE", "ID_PRODUCTO", "FECHA", "TOTAL_FACTURA", "ESTADO PRODUCTO", "CANTIDAD"
            }
        ));
        tabla_pedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_pedidosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabla_pedidos);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 690, 130));

        tabla_DP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_DETALLE", "CANTIDAD", "PRECIO_UNITARIO", "ID_PEDIDO", "DIRECCION_ID"
            }
        ));
        tabla_DP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_DPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabla_DP);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 690, 130));

        tblFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_FACTURA", "FECHA/HORA", "TOTAL", "ID_EMPLEADO", "DESCRIPCION", "ID_CLIENTE", "ID_PAGO"
            }
        ));
        tblFacturas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblFacturas);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 690, 130));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Tabla Detalles del pedido");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, -1, -1));

        jLabel12.setText("ID Pagos:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));
        getContentPane().add(txtPagoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 100, 30));

        jLabel8.setText("Total:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));
        getContentPane().add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 90, 30));

        jLabel5.setText("ID Empleado:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));
        getContentPane().add(txtEmpleadoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 90, 30));

        jLabel4.setText("ID Cliente:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));
        getContentPane().add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 100, 30));

        jLabel3.setText("ID Factura:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));
        getContentPane().add(txtFacturaId, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 90, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 92, 195));
        jLabel2.setText("Facturacion");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 90, 150, 30));

        btnDepartamentos.setText("Departamentos");
        btnDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartamentosActionPerformed(evt);
            }
        });
        getContentPane().add(btnDepartamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 120, 50));

        btnProveedores.setText("Proveedores");
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });
        getContentPane().add(btnProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 100, 50));

        btnProductos.setText("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        getContentPane().add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(464, 30, 90, 50));

        btnEmpleados.setText("Empleados");
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });
        getContentPane().add(btnEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, 50));

        btnCliente.setText("Clientes");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        getContentPane().add(btnCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 80, 50));

        exitBtn.setBackground(new java.awt.Color(255, 255, 255));
        exitBtn.setForeground(new java.awt.Color(255, 255, 255));

        exitTxt.setBackground(new java.awt.Color(0, 0, 0));
        exitTxt.setFont(new java.awt.Font("Roboto Light", 1, 24)); // NOI18N
        exitTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitTxt.setText("X");
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

        getContentPane().add(exitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, -1, -1));

        btnEliminarF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/delete  (1).png"))); // NOI18N
        btnEliminarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarFActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarF, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 30, 30));

        btnModificarF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/update.png"))); // NOI18N
        btnModificarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarFActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarF, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 30, 30));

        btnInsertarF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/insert.png"))); // NOI18N
        btnInsertarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarFActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertarF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 30, 30));

        btnBuscarDC.setBackground(new java.awt.Color(51, 102, 255));
        btnBuscarDC.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarDC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/search-blanco.png"))); // NOI18N
        btnBuscarDC.setText("Direcciones de clientes");
        btnBuscarDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarDCActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscarDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 560, 180, 30));

        btnBuscarPrecioP1.setBackground(new java.awt.Color(51, 102, 255));
        btnBuscarPrecioP1.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPrecioP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/search-blanco.png"))); // NOI18N
        btnBuscarPrecioP1.setText("Precio de productos");
        btnBuscarPrecioP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPrecioP1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscarPrecioP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 560, 180, 30));

        btnBuscarPP.setBackground(new java.awt.Color(51, 102, 255));
        btnBuscarPP.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/search-blanco.png"))); // NOI18N
        btnBuscarPP.setText("Productos por proveedor");
        btnBuscarPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPPActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscarPP, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, 190, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Tabla de Pedidos");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Tabla de Facturas");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/INICIO.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartamentosActionPerformed
        try {
            Departamentos v5 = new Departamentos();
            dispose();
            v5.show();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDepartamentosActionPerformed

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

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        try {
            Clientes v1 = new Clientes();
            this.dispose();
            v1.show();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        try {
            Empleados v2 = new Empleados();
            dispose();
            v2.show();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        try {
            Productos v3 = new Productos();
            dispose();
            v3.show();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        try {
            Proveedores v4 = new Proveedores();
            dispose();
            v4.show();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnInsertarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarFActionPerformed
        try {
            insertarFactura();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsertarFActionPerformed

    private void btnModificarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarFActionPerformed
        try {
            modificarFactura();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModificarFActionPerformed

    private void btnEliminarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarFActionPerformed
        eliminarFactura();
    }//GEN-LAST:event_btnEliminarFActionPerformed

    private void tblFacturasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturasMouseClicked
        SeleccionarItem();
    }//GEN-LAST:event_tblFacturasMouseClicked

    private void modificarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarPActionPerformed
        try {
            modificarPedido();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_modificarPActionPerformed

    private void btnInsertarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarPActionPerformed
        try {
            insertarPedido();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsertarPActionPerformed

    private void eliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarPActionPerformed
        eliminarPedido();
    }//GEN-LAST:event_eliminarPActionPerformed

    private void tabla_pedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_pedidosMouseClicked
        SeleccionarItemPedidos();
    }//GEN-LAST:event_tabla_pedidosMouseClicked

    private void btnDisponibilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisponibilidadActionPerformed
        int idProducto = Integer.parseInt(txtIDproductoP.getText());
        int cantidadDeseada = Integer.parseInt(txtCantidadPedido.getText());
        int numRepeticiones = 1;

        try {
            String call = "{ ? = call verificar_disponibilidad(?, ?, ?) }";
            CallableStatement stmt = con.prepareCall(call);

            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidadDeseada);
            stmt.setInt(4, numRepeticiones);
            stmt.registerOutParameter(1, Types.NUMERIC);

            for (int i = 0; i < numRepeticiones; i++) {
                stmt.execute();
                int resultado = stmt.getInt(1);

                if (resultado == 1) {
                    txtEstadoP.setText("En stock");
                } else {
                    txtEstadoP.setText("Sin stock");
                }

                Thread.sleep(100);
            }

            stmt.close();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            }
    }//GEN-LAST:event_btnDisponibilidadActionPerformed

    private void btnVerificarDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarDetallesActionPerformed
        int idPedido = Integer.parseInt(txtIDpedidoD.getText());

        try {
            String query = "{ ? = call calcular_detalles(?) }";

            CallableStatement callableStatement = con.prepareCall(query);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setInt(2, idPedido);

            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                int cantidad = resultSet.getInt(1); 
                double precio = resultSet.getDouble(2); 

                txtCantidadD.setText(String.valueOf(cantidad));
                txtPrecioUnitario.setText(String.valueOf(precio));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnVerificarDetallesActionPerformed

    private void btnInsertarDetallesPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarDetallesPActionPerformed
        try {
            insertarDetallePedido();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsertarDetallesPActionPerformed

    private void btnActualizarDetallesPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarDetallesPActionPerformed
        try {
            modificarDetallePedido();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarDetallesPActionPerformed

    private void btnEliminarDetallesPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDetallesPActionPerformed
        eliminarDetallePedido();
    }//GEN-LAST:event_btnEliminarDetallesPActionPerformed

    private void tabla_DPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_DPMouseClicked
        SeleccionarItemDP();
    }//GEN-LAST:event_tabla_DPMouseClicked

    private void btnF_PagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF_PagosActionPerformed
        F_Pagos v5 = null;
        try {
            v5 = new F_Pagos();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
            v5.show();
    }//GEN-LAST:event_btnF_PagosActionPerformed

    private void btnBuscarDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarDCActionPerformed
        V_Direcciones v6 = null;
        try {
            v6 = new V_Direcciones();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
            v6.show();
    }//GEN-LAST:event_btnBuscarDCActionPerformed

    private void btnBuscarPrecioP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPrecioP1ActionPerformed
        V_Productos v7 = null;
        try {
            v7 = new V_Productos();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
            v7.show();
    }//GEN-LAST:event_btnBuscarPrecioP1ActionPerformed

    private void btnBuscarPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPPActionPerformed
                V_Proveedores v8 = null;
        try {
            v8 = new V_Proveedores();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
            v8.show();
    }//GEN-LAST:event_btnBuscarPPActionPerformed

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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Inicio().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarDetallesP;
    private javax.swing.JButton btnBuscarDC;
    private javax.swing.JButton btnBuscarPP;
    private javax.swing.JButton btnBuscarPrecioP1;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnDepartamentos;
    private javax.swing.JButton btnDisponibilidad;
    private javax.swing.JButton btnEliminarDetallesP;
    private javax.swing.JButton btnEliminarF;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnF_Pagos;
    private javax.swing.JButton btnInsertarDetallesP;
    private javax.swing.JButton btnInsertarF;
    private javax.swing.JButton btnInsertarP;
    private javax.swing.JButton btnModificarF;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnVerificarDetalles;
    private javax.swing.JButton eliminarP;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton modificarP;
    private javax.swing.JTable tabla_DP;
    private javax.swing.JTable tabla_pedidos;
    private javax.swing.JTable tblFacturas;
    private javax.swing.JTextField txtCantidadD;
    private javax.swing.JTextField txtCantidadPedido;
    private javax.swing.JTextField txtEmpleadoId;
    private javax.swing.JTextField txtEstadoP;
    private javax.swing.JTextField txtFacturaId;
    private javax.swing.JTextField txtIDclienteP;
    private javax.swing.JTextField txtIDdetalle;
    private javax.swing.JTextField txtIDdireccionD;
    private javax.swing.JTextField txtIDpedidoD;
    private javax.swing.JTextField txtIDproductoP;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtPagoId;
    private javax.swing.JTextField txtPedidoID;
    private javax.swing.JTextField txtPrecioUnitario;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
