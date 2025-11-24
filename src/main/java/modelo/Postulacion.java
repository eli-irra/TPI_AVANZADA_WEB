package modelo;
import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Postulacion implements Serializable {
    
    public enum TipoAdopcion {
        TEMPORAL,
        DEFINITIVA
    }
    public enum Estado {
        APROBADA,
        PENDIENTE,
        RECHAZADA
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPostulacion;
    private LocalDate fechaCreacion;
    private Estado estado;
    
    //  CAMPO CLAVE: Mapea el Enum a la BD como texto (TEMPORAL o DEFINITIVA)
    @Enumerated(EnumType.STRING)
    private TipoAdopcion tipoAdopcion;
    
    // RELACIÓN N:1 con Gato (A qué gato se postula)
    @ManyToOne
    @JoinColumn(name = "gato_id")
    private Gato gatoRelacionado;
    
    // RELACIÓN N:1 con FamiliaAdoptante (Quién postula)
    @ManyToOne
    @JoinColumn(name = "familia_adoptante_id")
    private FamiliaAdoptante familiaPostulante;
    
    public Postulacion() {
        this.fechaCreacion = LocalDate.now();
        this.estado = estado;
    }
    

    public Postulacion(long idPostulacion, LocalDate fechaCreacion, Gato gatoRelacionado) {
        this.familiaPostulante = familiaPostulante;
        this.gatoRelacionado = gatoRelacionado;
        this.tipoAdopcion = tipoAdopcion;
    }

    public long getIdPostulacion() {
        return idPostulacion;
    }

    public void setIdPostulacion(long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    public Gato getGatoRelacionado() {
        return gatoRelacionado;
    }

    public void setGatoRelacionado(Gato gatoRelacionado) {
        this.gatoRelacionado = gatoRelacionado;
    }
    
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
    public FamiliaAdoptante getFamiliaPostulante() { return familiaPostulante; }
    public void setFamiliaPostulante(FamiliaAdoptante familiaPostulante) { this.familiaPostulante = familiaPostulante; }
    
    public TipoAdopcion getTipoAdopcion() {
        return tipoAdopcion;
    }

    public void setTipoAdopcion(TipoAdopcion tipoAdopcion) {
        this.tipoAdopcion = tipoAdopcion;
    }
} 

