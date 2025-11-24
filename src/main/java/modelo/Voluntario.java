package modelo;

import java.io.Serializable; // Añadir la importación
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Voluntario extends Usuario implements Serializable {
    
    
    // Relación 1:N (Un voluntario realiza muchas tareas)
    @OneToMany(mappedBy = "voluntarioQueRealiza")
    private List<Tarea> tareasRealizadas = new ArrayList<>();

    public Voluntario() {
        super();
        this.tareasRealizadas = new ArrayList<>();
    }
    
    public Voluntario(String nombre, String correo, String contrasena, double telefono, String rol,String direccion) {
        super(nombre, correo, contrasena, telefono, rol,direccion);
        this.tareasRealizadas = new ArrayList<>();
    }
    
    public List<Tarea> getTareasRealizadas() {
        return tareasRealizadas;
    }
    public void setTareasRealizadas(List<Tarea> tareasRealizadas) {
        this.tareasRealizadas = tareasRealizadas;
    }
}