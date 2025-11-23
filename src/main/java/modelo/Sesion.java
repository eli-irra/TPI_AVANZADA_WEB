package modelo;
import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
