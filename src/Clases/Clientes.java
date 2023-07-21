package Clases;

/**
 *
 * @author Farg-
 */
public class Clientes {
    int id_cliente;
    String nombre;
    String descripcion;
    String telefono;
    String correo;

    public Clientes() {
    }
    

    public Clientes(int id_cliente, String nombre, String descripcion, String telefono, String correo) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Insertar_Clientes{" + "id_cliente=" + id_cliente + ", nombre=" + nombre + ", descripcion=" + descripcion + ", telefono=" + telefono + ", correo=" + correo + '}';
    }
    
    
   
}
