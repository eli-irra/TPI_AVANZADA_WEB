
package modelo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Gato implements Serializable {
    
    public enum RespuestaBinaria{ 
        NO,  
        SI   
    }
    public enum EstadoSalud {
    SANO, ENFERMO, EN_TRATAMIENTO
}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGato;                     
    private LocalDate fecha;                
    private String nombre;                  
    private String raza;                    
    private String sexo;                    
    private RespuestaBinaria esterilizado; 
    private RespuestaBinaria disponible;   
    private String codigoQR;
    private EstadoSalud estadoFisico;
    private String color;
    private String caracteristicas;
    private String rutaFoto;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "historia_clinica_id") 
    private HistoriaClinica historiaClinica;
    
    @ManyToOne 
    @JoinColumn(name = "zona_id") 
    private Zona zona;
    
    @ManyToOne 
    @JoinColumn(name = "familia_adoptante_id")
    private FamiliaAdoptante familiaAdoptante;
    
    @OneToMany(mappedBy = "gatoAsignado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> historialTareas = new ArrayList<>();
    
    @OneToOne(mappedBy = "gatoRelacionado")
    private Postulacion postulacionActiva;

    // 🛑 REQUISITO JPA: Constructor sin argumentos OBLIGATORIO
    public Gato() {
        this.fecha = LocalDate.now();
        this.historialTareas = new ArrayList<>(); 
    }

    public Gato(long idGato, LocalDate fecha, String nombre, String raza, String sexo, RespuestaBinaria esterilizado, RespuestaBinaria disponible, String codigoQR, HistoriaClinica historiaClinica, Postulacion postulacion, FamiliaAdoptante familiaAdoptante) {
        this.idGato = idGato;
        this.fecha = LocalDate.now();
        this.nombre = nombre;
        this.raza = raza;
        this.sexo = sexo;
        this.esterilizado = esterilizado;
        this.disponible = disponible;
        this.codigoQR = codigoQR;
        this.estadoFisico = estadoFisico;
        this.historiaClinica = new HistoriaClinica();
        this.color = color;
        this.caracteristicas = caracteristicas;
        this.zona = zona;
        
        this.historiaClinica = new HistoriaClinica();
    }
    
    
    
    public long getIdGato() {
        return idGato;
    }

    public void setIdGato(int idGato) {
        this.idGato = idGato;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public RespuestaBinaria getEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(RespuestaBinaria esterilizado) {
        this.esterilizado = esterilizado;
    }

    public RespuestaBinaria getDisponible() {
        return disponible;
    }

    public void setDisponible(RespuestaBinaria disponible) {
        this.disponible = disponible;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }
    
    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public Postulacion getpostulacionActiva() {
        return postulacionActiva;
    }

    public void setpostulacionActiva(Postulacion postulacionActiva) {
        this.postulacionActiva = postulacionActiva;
    }

    public FamiliaAdoptante getFamiliaAdoptante() {
        return familiaAdoptante;
    }

    public void setFamiliaAdoptante(FamiliaAdoptante familiaAdoptante) {
        this.familiaAdoptante = familiaAdoptante;
    }
    
     public EstadoSalud getestadoFisico() {
        return estadoFisico;
    }

    public void setestadoFisico( EstadoSalud estadoFisico) {
        this.estadoFisico = estadoFisico;
    }
    public Zona getZona() { return zona; }
    public void setZona(Zona zona) { this.zona = zona; }
    
    public String getColor() { return color; }
    
    public void setColor(String color) { this.color = color; }
    
    public String getCaracteristicas() { return caracteristicas; }
    
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }
    
    public List<Tarea> getHistorialTareas() { return historialTareas; }
    public void setHistorialTareas(List<Tarea> historialTareas) { this.historialTareas = historialTareas; }
    public String getRutaFoto() { 
        return rutaFoto; 
    }
    
    public void setRutaFoto(String rutaFoto) { 
        this.rutaFoto = rutaFoto; 
    }

}

