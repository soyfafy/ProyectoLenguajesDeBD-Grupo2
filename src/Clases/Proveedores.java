package Clases;

/**
 *
 * @author Grupo 2 - Lenguajes de Bases de datos
 */

public class Proveedores {
    int id_proveedor;
    String nombre;
    String descripcion;
    String telefono;
    String correo;

    public Proveedores() {
    }

    public Proveedores(int id_proveedor, String nombre, String descripcion, String telefono, String correo) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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
        return "Proveedores{" + "id_proveedor=" + id_proveedor + ", nombre=" + nombre + ", descripcion=" + descripcion + ", telefono=" + telefono + ", correo=" + correo + '}';
    }

    
}
