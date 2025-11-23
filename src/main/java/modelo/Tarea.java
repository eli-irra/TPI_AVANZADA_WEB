package modelo;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tarea implements Serializable {

    // 🔹 Conviene que el nombre del enum empiece con mayúscula
    public enum TipoTarea {
        ALIMENTACION,
        CAPTURA_CASTRACION,
        CONTROL_VETERINARIO,
        TRANSPORTE_A_HOGAR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTarea;

    @Enumerated(EnumType.STRING)
    private TipoTarea tipoTarea; // ✅ ahora es del tipo del enum, no String

    private Date fecha;
    private String descripcion;
    private String ubicacion;
    
    @ManyToOne
    @JoinColumn(name = "gato_id")  // el nombre de la columna en la tabla Tarea
    private Gato gatoAsignado;
    
    @ManyToOne
    @JoinColumn(name = "voluntario_id")
    private Voluntario voluntarioQueRealiza;


    public Tarea() {
        // Constructor vacío obligatorio para JPA
    }

    public Tarea(long idTarea, TipoTarea tipoTarea, Date fecha, String descripcion, String ubicacion) {
        this.idTarea = idTarea;
        this.tipoTarea = tipoTarea;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(long idTarea) {
        this.idTarea = idTarea;
    }

    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public Gato getGatoAsignado() {
        return gatoAsignado;
    }

    public void setGatoAsignado(Gato gatoAsignado) {
        this.gatoAsignado = gatoAsignado;
    }
    
    public Voluntario getVoluntarioQueRealiza() {
    return voluntarioQueRealiza;
}

public void setVoluntarioQueRealiza(Voluntario voluntarioQueRealiza) {
    this.voluntarioQueRealiza = voluntarioQueRealiza;
}

}
