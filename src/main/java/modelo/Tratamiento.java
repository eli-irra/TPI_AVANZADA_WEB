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
@Table(name = "tratamientos")
public class Tratamiento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTratamiento;
    private String diagnostico;
    private String tratamiento;
    
    // RELACIÓN N:1 con HistoriaClinica
    @ManyToOne
    @JoinColumn(name = "historia_clinica_id") // Clave Foránea
    private HistoriaClinica historiaClinica;

    public Tratamiento() {}

    public Tratamiento(String diagnostico, String tratamiento, HistoriaClinica historiaClinica) {
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.historiaClinica = historiaClinica;
    }
    public long getidTratamiento() {
        return idTratamiento;
    }

    
    public String getDiagostico() {
        return diagnostico;
    }

    public void setDiagostico(String diagostico) {
        this.diagnostico = diagostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
    
    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }
    public void setHistoriaClinica(HistoriaClinica historiaClinica) { this.historiaClinica = historiaClinica; }
    
    
}
