package modelo;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sesion implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSesion;
    private LocalDate fechaHoraIncio;

    public Sesion() {
        this.fechaHoraIncio = LocalDate.now();
    }

    public long getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(long idSesion) {
        this.idSesion = idSesion;
    }

    public LocalDate getFechaHoraIncio() {
        return fechaHoraIncio;
    }

    public void setFechaHoraIncio(LocalDate fechaHoraIncio) {
        this.fechaHoraIncio = fechaHoraIncio;
    }
    
}
