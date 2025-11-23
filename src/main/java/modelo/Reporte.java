package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Reporte implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReporte;
    private LocalDate fechaReporte;
    private int cantidad;
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;
    
    public Reporte() { this.fechaReporte = LocalDate.now(); } // 🛑 Constructor JPA

    public Reporte(long idReporte, LocalDate fechaReporte, int cantidad, String descripcion) {
        this.idReporte = idReporte;
        this.fechaReporte = LocalDate.now();
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(long idReporte) {
        this.idReporte = idReporte;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Administrador getAdministrador() { return administrador; }
    public void setAdministrador(Administrador administrador) { this.administrador = administrador; }
    
    
}
