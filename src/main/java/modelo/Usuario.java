package modelo;

import java.io.Serializable; // Añadir: Interfaz estándar para entidades
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne; // Para mapear la relación 1 a 1
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario") // Opcional, pero útil para diferenciar
@Table(name = "usuarios")
public abstract class Usuario implements Serializable { 
    
    public enum EstadoSesion{
        ACTIVO,
        INACTIVO,
        EXPIRADO
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    
  
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) 
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion; 
    
    private String nombre;
    private String correo;
    private String contrasena; 
    private double telefono;
    private String direccion;
    
   
    @Enumerated(EnumType.STRING)
    private EstadoSesion estadoSesion; 
    
    private LocalDate fechaCreacion;
    private String rol;

   
    public Usuario() {
        this.fechaCreacion = LocalDate.now();
        this.estadoSesion = EstadoSesion.INACTIVO;
    }

    
    public Usuario(String nombre, String correo, String contrasena, double telefono, String rol, String direccion) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena; // Usar el campo corregido
        this.telefono = telefono;
        this.fechaCreacion = LocalDate.now();
        this.rol = rol;
        this.estadoSesion = EstadoSesion.INACTIVO;
        this.direccion = direccion;
    }

    // 🟢 CORRECCIÓN: Casing correcto en getters/setters del ID
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getContrasena() { 
        return contrasena;
    }

    public void setContrasena(String contrasena) { 
        this.contrasena = contrasena;
    }

    public double getTelefono() {
        return telefono;
    }

    public void setTelefono(double telefono) {
        this.telefono = telefono;
    }

    public EstadoSesion getEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(EstadoSesion estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public Sesion getSesion() { return sesion; }
    public void setSesion(Sesion sesion) { this.sesion = sesion; }
    
    public void iniciarSesion(){
        this.sesion = new Sesion();
        this.estadoSesion = EstadoSesion.ACTIVO;
    }
    
    public void cerrarSesion(){
        if(this.sesion != null) {
            this.sesion = null;
        }
        this.estadoSesion = EstadoSesion.INACTIVO;
    }
    
    public Usuario eliminarUser(){
        
        return null;
    }
    
    public String getdireccion() {
        return direccion;
    }

    public void setdireccion(String direccion) {
        this.direccion = direccion;
    }
}
