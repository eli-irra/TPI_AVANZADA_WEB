package modelo;

import java.io.Serializable; // Añadir la importación
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Voluntario extends Usuario implements Serializable { // 🟢 CORRECCIÓN 1: Extender de Usuario e Implementar Serializable
    
    
    // Relación 1:N (Un voluntario realiza muchas tareas)
    @OneToMany(mappedBy = "voluntarioQueRealiza")
    private List<Tarea> tareasRealizadas = new ArrayList<>();

    // 🟢 CORRECCIÓN 2: Constructor por defecto (SIN ARGUMENTOS) - REQUERIDO por JPA
    public Voluntario() {
        super();
        this.tareasRealizadas = new ArrayList<>();
    }
    
    // 🟢 CORRECCIÓN 3: Ajustar el constructor de negocio para incluir los campos heredados
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