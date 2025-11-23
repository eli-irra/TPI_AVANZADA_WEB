package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Zona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idZona;
    private String nombreZona;      
    private String ubicacionGPS;
    
    @OneToMany(mappedBy = "zona")
    private List<Gato> gatosEnZona;
    
    public Zona() {
        this.gatosEnZona = new ArrayList<>();
    }

    public Zona(long idZona, String nombreZona, String ubicacionGPS) {
        this.idZona = idZona;
        this.nombreZona = nombreZona;
        this.ubicacionGPS = ubicacionGPS;
        this.gatosEnZona = new ArrayList<>();
    }

    public long getIdZona() {
        return idZona;
    }

    public void setIdZona(long idZona) {
        this.idZona = idZona;
    }
    
    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getUbicacionGPS() {
        return ubicacionGPS;
    }

    public void setUbicacionGPS(String ubicacionGPS) {
        this.ubicacionGPS = ubicacionGPS;
    }
    public List<Gato> getGatosEnZona() { return gatosEnZona; }
    public void setGatosEnZona(List<Gato> gatosEnZona) { this.gatosEnZona = gatosEnZona; }
}
