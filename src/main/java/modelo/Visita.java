
package modelo;
import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "visitas_seguimiento")
public class Visita implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVisita;       
    private LocalDate fecha;    
    private int horaVisita;     // Hora de la visita (se asume un formato de hora entero, ej. 15 para 3 PM).
    private String descripcion; // Notas o detalles sobre la visita y sus resultados.
    private boolean realizada;  // Indica si la visita se llevó a cabo (true) o si está pendiente (false).
    
    @ManyToOne
    @JoinColumn(name = "familia_adoptante_id")
    private FamiliaAdoptante familia;
    
    // RELACIÓN N:1 con Voluntario (Quién realiza la visita)
    @ManyToOne
    @JoinColumn(name = "voluntario_id", nullable = true)
    private Voluntario voluntarioEncargado;
    
    public Visita() {}
    
    public Visita(long idVisita, LocalDate fecha, int horaVisita, String descripcion, boolean realizada, Voluntario voluntario, FamiliaAdoptante familiadop) {
        this.idVisita = idVisita;
        this.fecha = fecha;
        this.horaVisita = horaVisita;
        this.descripcion = descripcion;
        this.realizada = realizada;
        this.familia = familiadop;
        this.voluntarioEncargado = voluntario;
    }
    
    public long getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getHoraVisita() {
        return horaVisita;
    }

    public void setHoraVisita(int horaVisita) {
        this.horaVisita = horaVisita;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }
    
    public FamiliaAdoptante getFamilia() { return familia; }
    public void setFamilia(FamiliaAdoptante familia) { this.familia = familia; }
    
    public Voluntario getVoluntarioEncargado() { return voluntarioEncargado; }
    public void setVoluntarioEncargado(Voluntario voluntarioEncargado) { this.voluntarioEncargado = voluntarioEncargado; }
}

