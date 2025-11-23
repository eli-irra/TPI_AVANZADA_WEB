package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "estudios")
public class Estudio implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEstudio;
    private String nombreEstudio;   // Nombre del análisis realizado (ej., "Análisis de Sangre", "Radiografía").
    private String descripcion;     // Resultados o conclusiones del estudio.
    
    @ManyToOne
    @JoinColumn(name = "historia_clinica_id") // Clave Foránea
    private HistoriaClinica historiaClinica;
    
    public Estudio() {}

    public Estudio(long IdEstudio, String nombreEstudio, String descripcion, HistoriaClinica historia) {
        this.nombreEstudio = nombreEstudio;
        this.descripcion = descripcion;
        this.historiaClinica = historia;
    }

    public long getIdEstudio() {
        return idEstudio;
    }
    
    public String getNombreEstudio() {
        return nombreEstudio;
    }

    public void setNombreEstudio(String nombreEstudio) {
        this.nombreEstudio = nombreEstudio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }
    public void setHistoriaClinica(HistoriaClinica historiaClinica) { this.historiaClinica = historiaClinica; }
}
