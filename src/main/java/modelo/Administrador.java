 package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Administrador extends Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    // RELACIÓN 1:N con Reporte (Un administrador gestiona muchos reportes)
    @OneToMany(mappedBy = "administrador")
    private List<Reporte> reportesGenerados = new ArrayList<>();

    public Administrador() {
        super();
        this.reportesGenerados = new ArrayList<>();
    }

    public Administrador(String nombre, String correo, String contrasena, double telefono, String rol, String direccion) {
        super(nombre, correo, contrasena, telefono, rol, direccion);
        this.reportesGenerados = new ArrayList<>();
    }
    
    public List<Reporte> getReportesGenerados() {
        return reportesGenerados;
    }

    public void setReportesGenerados(List<Reporte> reportesGenerados) {
        this.reportesGenerados = reportesGenerados;
    }
    
}