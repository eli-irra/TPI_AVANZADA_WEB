package modelo;

import jakarta.persistence.Entity; 
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn; 

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Aptitud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) 
    private int idCertificado; // Declaración correcta
    
    private LocalDate fechaEmision;
    private String estadoGeneral; // Apto, No apto, Pendiente
    @ManyToOne 
    @JoinColumn(name = "veterinario_id") 
    private Veterinario veterinario;
    
    @ManyToOne 
    @JoinColumn(name = "gato_id") 
    private Gato gatoCertificado;
    
    public Aptitud() {
        this.fechaEmision = LocalDate.now(); 
    }
    
    public Aptitud(String estadoGeneral, Veterinario veterinario, Gato gatoCertificado) {
        this(); 
        this.estadoGeneral = estadoGeneral;
        this.veterinario = veterinario;
        this.gatoCertificado = gatoCertificado;
    }

    public int getIdCertificado() { return idCertificado; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public String getEstadoGeneral() { return estadoGeneral; }
    public Veterinario getVeterinario() { return veterinario; }
    public Gato getGatoCertificado() { return gatoCertificado; }

public void setEstadoGeneral(String estadoGeneral)
    {
        this.estadoGeneral = estadoGeneral; 
    }
    
    // Se recomienda añadir Setters para relaciones también.
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public void setGatoCertificado(Gato gatoCertificado) {
        this.gatoCertificado = gatoCertificado;
    }
    
    // El set para idCertificado no se suele crear en claves autogeneradas.
}
