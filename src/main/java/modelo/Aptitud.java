package modelo;

import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn; 

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Aptitud implements Serializable {
    
    // =======================================================
    // === DECLARACIÓN DE ATRIBUTOS (CAMPOS DE CLASE) ===
    // =======================================================
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) 
    private int idCertificado; // Declaración correcta
    
    private LocalDate fechaEmision; // Declaración correcta
    private String estadoGeneral; // Apto, No apto, Pendiente
    @ManyToOne 
    @JoinColumn(name = "veterinario_id") 
    private Veterinario veterinario; // Declaración correcta y mapeo (ManyToOne)
    
    @ManyToOne 
    @JoinColumn(name = "gato_id") 
    private Gato gatoCertificado; // Declaración correcta y mapeo (ManyToOne)
    
    

    // =======================================================
    // === CONSTRUCTORES ===
    // =======================================================
    
    // 🛑 REQUISITO JPA: Constructor sin argumentos (Obligatorio para entidades)
    public Aptitud() {
        this.fechaEmision = LocalDate.now(); 
    }
    
    // Constructor con argumentos
    public Aptitud(String estadoGeneral, Veterinario veterinario, Gato gatoCertificado) {
        this(); 
        this.estadoGeneral = estadoGeneral;
        this.veterinario = veterinario; // Usando el nombre de campo minúsculo (buena práctica)
        this.gatoCertificado = gatoCertificado; // Usando el nombre de campo minúsculo (buena práctica)
    }

    // =======================================================
    // === Getters and Setters ===
    // =======================================================
    
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
