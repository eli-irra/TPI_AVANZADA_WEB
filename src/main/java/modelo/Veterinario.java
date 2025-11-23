package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
@Entity
public class Veterinario extends Usuario implements Serializable { 
    
    
    private int matricula; 
    @OneToMany(mappedBy = "veterinario")
    private List<Aptitud> certificadosEmitidos = new ArrayList<>();

   
    public Veterinario() {
        super();
        this.certificadosEmitidos = new ArrayList<>();
    }
    
   
    public Veterinario(int matricula, String nombre, String correo, String contrasena, double telefono, String rol, String direccion) {
        super(nombre, correo, contrasena, telefono, rol, direccion); 
        this.matricula = matricula;
        this.certificadosEmitidos = new ArrayList<>();
    }

    
    // --- Getters y Setters de la Matrícula ---

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public List<Aptitud> getCertificadosEmitidos() {
        return certificadosEmitidos;
    }
    public void setCertificadosEmitidos(List<Aptitud> certificadosEmitidos) {
        this.certificadosEmitidos = certificadosEmitidos;
    }
    // 3. MÉTODO DE CONVENIENCIA (Recomendado)
    // Permite añadir un solo certificado de forma controlada
    // -----------------------------------------------------------------
    public void agregarCertificado(Aptitud certificado) {
        if (this.certificadosEmitidos == null) {
            this.certificadosEmitidos = new ArrayList<>();
        }
    
}
}