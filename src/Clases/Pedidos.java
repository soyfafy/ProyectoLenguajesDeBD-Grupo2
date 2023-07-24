package Clases;

import java.sql.Timestamp;

public class Pedidos {
    private int id;
    private double total;
    private Timestamp fechaUltimaModificacion;

    public Pedidos() {
    }

    public Pedidos(int id, double total, Timestamp fechaUltimaModificacion) {
        this.id = id;
        this.total = total;
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Timestamp getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Timestamp fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }
    
    
    
}


