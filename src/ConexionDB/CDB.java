package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grupo 2 - Lenguajes de Bases de datos
 */
public class CDB {
    
       public static  Connection getConnection()
            {
                try
                    {
                        Class.forName("oracle.jdbc.OracleDriver");
                        String myDB = "jdbc:oracle:thin:@localhost:1521:orcl";
                        String usuario = "C##ADMIN_DB";
                        String password = "admindb";
                        Connection cnx = DriverManager.getConnection(myDB,usuario,password);
                        System.out.println("Conexión exitosa!");
                        return cnx;
                        
                    }
                catch(SQLException ex)
                        {
                            System.out.println(ex.getMessage());
                            System.out.println("no hubo conexion!");
                        }
                catch (ClassNotFoundException ex) {
                     Logger.getLogger(CDB.class.getName()).log(Level.SEVERE, null, ex);
                      System.out.println("Lo sentimos hay errores de conexion, intente de nuevo.");
                     }

                    return null;
                
                
            }
    
}