package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class FamiliaAdoptante extends Usuario implements Serializable{
    
    @OneToMany(mappedBy = "familiaAdoptante")
    private List<Gato> gatosAdoptados = new ArrayList<>();
    
    // Relación 1:N (Una familia recibe muchas visitas de seguimiento)
    @OneToMany(mappedBy = "familia")
    private List<Visita> historialVisitas = new ArrayList<>();

    public FamiliaAdoptante() {
        super();
        this.gatosAdoptados = new ArrayList<>();
        this.historialVisitas = new ArrayList<>();
    }
    
    public FamiliaAdoptante(String direccion, String nombre, String correo, String contrasena, double telefono, String rol) {
    super(nombre, correo, contrasena, telefono, rol, direccion);
    this.gatosAdoptados = new ArrayList<>();
    this.historialVisitas = new ArrayList<>();
    }

    public List<Gato> getGatosAdoptados() {
        return gatosAdoptados;
    }
    public void setGatosAdoptados(List<Gato> gatosAdoptados) {
        this.gatosAdoptados = gatosAdoptados;
    }

    public List<Visita> getHistorialVisitas() {
        return historialVisitas;
    }
    public void setHistorialVisitas(List<Visita> historialVisitas) {
        this.historialVisitas = historialVisitas;
    }
}