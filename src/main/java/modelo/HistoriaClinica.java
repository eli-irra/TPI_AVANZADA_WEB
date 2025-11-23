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
import jakarta.persistence.OneToMany;

@Entity
public class HistoriaClinica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idHistoria;
    private LocalDate fechaCreacion;
    //private List<Tratamiento> tratamientos = new ArrayList<>;
    //private List<Estudio> estudios = new ArrayList<>;
    private String descripcion;
    
    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tratamiento> tratamientos = new ArrayList<>();
    
    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estudio> estudios = new ArrayList<>();

   public HistoriaClinica() {
        this.fechaCreacion = LocalDate.now();
        this.tratamientos = new ArrayList<>();
        this.estudios = new ArrayList<>();
    }

    // Constructor de Negocio (solo atributos simples)
    public HistoriaClinica(String descripcion) {
        this(); // Llama al constructor sin argumentos para inicializar fecha y listas
        this.descripcion = descripcion;
        this.tratamientos = new ArrayList<>();
        this.estudios = new ArrayList<>();
    }

    
    public long getidHistoria() {
        return idHistoria;
    }
     public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    // 🟢 ================== INICIO CORRECCIÓN ==================
    // Agregamos el getter que faltaba
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    // 🟢 =================== FIN CORRECCIÓN ====================
    
    public List<Tratamiento> getTratamientos() { return tratamientos; }
    public void setTratamientos(List<Tratamiento> tratamientos) { this.tratamientos = tratamientos; }
    
    public List<Estudio> getEstudios() { return estudios; }
    public void setEstudios(List<Estudio> estudios) { this.estudios = estudios; }
    
    // Métodos de negocio útiles
    public void agregarTratamiento(Tratamiento t) {
        this.tratamientos.add(t);
        // Opcional: Establecer la relación inversa
        // t.setHistoriaClinica(this); 
    }
    
    public void agregarEstudio(Estudio e) {
        this.estudios.add(e);
        // Opcional: Establecer la relación inversa
        // e.setHistoriaClinica(this); 
    }
}