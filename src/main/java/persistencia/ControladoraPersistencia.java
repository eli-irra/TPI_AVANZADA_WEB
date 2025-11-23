
package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Administrador;
import modelo.FamiliaAdoptante;
import modelo.Usuario;
import modelo.Veterinario;
import modelo.Voluntario;
import modelo.Gato;
import modelo.Postulacion; 
import java.util.List;
import modelo.Estudio;
import modelo.HistoriaClinica;
import modelo.Reporte;
import modelo.Tarea;
import modelo.Tratamiento;
import modelo.Visita;
import modelo.Zona;
import persistencia.exceptions.NonexistentEntityException;



public class ControladoraPersistencia {
    
    AdministradorJpaController adminJpa = new AdministradorJpaController();
    EstudioJpaController estudiojpa= new EstudioJpaController();
    FamiliaAdoptanteJpaController familiaAdoptanteJpa = new FamiliaAdoptanteJpaController();
    GatoJpaController gatoJpa = new GatoJpaController();
    HistoriaClinicaJpaController historiaClinicaJpa = new HistoriaClinicaJpaController();
    PostulacionJpaController postulacionJpa = new PostulacionJpaController();
    ReporteJpaController reporteJpa = new ReporteJpaController();
    SesionJpaController sesionJpa = new SesionJpaController();
    TareaJpaController tareaJpa = new TareaJpaController();
    TratamientoJpaController tratamientoJpa = new TratamientoJpaController();
    UsuarioJpaController usuarioJpa = new UsuarioJpaController();
    VeterinarioJpaController veterinarioJpa = new VeterinarioJpaController();
    VisitaJpaController visitaJpa = new VisitaJpaController();
    VoluntarioJpaController voluntarioJpa = new VoluntarioJpaController();
    ZonaJpaController zonaJpa = new ZonaJpaController();
    AptitudJpaController aptitudjpa = new AptitudJpaController();
    
   public void crearFamiliaAdoptante(FamiliaAdoptante familia) throws Exception {
        familiaAdoptanteJpa.create(familia); //
    }
   
    public void crearAdministrador(Administrador admin) throws Exception {
        adminJpa.create(admin); 
    }
    
    public void crearVeterinario(Veterinario vet) throws Exception {
        veterinarioJpa.create(vet);
    }
    
    public void crearVoluntario(Voluntario vol) throws Exception {
        voluntarioJpa.create(vol);
    }
    
    public Usuario buscarUsuarioPorCorreo(String correo) {
        
        // Obtenemos el EntityManager del JpaController de Usuario
        EntityManager em = usuarioJpa.getEntityManager();
        
        try {
            // 1. Crear la consulta JPQL (Java Persistence Query Language)
            // Busca en la entidad Usuario
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.correo = :correo", 
                Usuario.class
            );
            
            // 2. Asignar el parámetro :correo
            query.setParameter("correo", correo);
            
            // 3. Ejecutar la consulta y obtener un único resultado
            return query.getSingleResult();
            
        } catch (NoResultException e) {
            // Esto es normal y esperado: significa que no se encontró
            // ningún usuario con ese correo.
            return null;
        } catch (Exception e) {
            // Otro error (ej. DB caída, error de sintaxis JPQL)
            System.err.println("Error al buscar usuario por correo: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public List<Gato> buscarGatosDisponibles() {
        EntityManager em = gatoJpa.getEntityManager();
        try {
            // JPQL: "Selecciona el objeto Gato g DONDE el atributo 'disponible' sea true"
            TypedQuery<Gato> query = em.createQuery(
                "SELECT g FROM Gato g WHERE g.disponible = :disponibleStatus", 
                Gato.class
            );
            
            query.setParameter("disponibleStatus", Gato.RespuestaBinaria.SI);
            
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Gato buscarGato(long idGato) {
        try {
            return gatoJpa.findGato(idGato);
        } catch (Exception e) {
            return null;
        }
    }
    
    public FamiliaAdoptante buscarFamilia(int idFamilia) {
        try {
            return familiaAdoptanteJpa.findFamiliaAdoptante(idFamilia);
        } catch (Exception e) {
            return null;
        }
        
        
    }
    
    public boolean existePostulacion(long idGato, int idFamilia) {
        EntityManager em = postulacionJpa.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                // El JPQL usa el nombre del atributo (idGato), no de la columna
                "SELECT COUNT(p) FROM Postulacion p WHERE p.gatoRelacionado.idGato = :idGato AND p.familiaPostulante.idUsuario = :idFamilia", 
                Long.class
            );
            query.setParameter("idGato", idGato); // Pasa el 'long'
            query.setParameter("idFamilia", idFamilia);
            
            return query.getSingleResult() > 0;
            
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Gato> traerTodosLosGatos() {
        return gatoJpa.findGatoEntities(); // Devuelve la lista completa
    }

    // 🟢 NUEVO: Busca una Historia Clínica por ID
    public HistoriaClinica buscarHistoriaClinica(long id) {
        return historiaClinicaJpa.findHistoriaClinica(id);
    }

    // 🟢 NUEVO: Modifica una Historia Clínica (para agregar estudios/tratamientos)
    public void modificarHistoriaClinica(HistoriaClinica historia) throws Exception {
        historiaClinicaJpa.edit(historia);
    }
    
    // 🟢 NUEVO: Crea un Tratamiento (JPA se encargará de la relación)
    public void crearTratamiento(Tratamiento tratamiento) {
        tratamientoJpa.create(tratamiento);
    }

    // 🟢 NUEVO: Crea un Estudio (JPA se encargará de la relación)
    public void crearEstudio(Estudio estudio) {
        estudiojpa.create(estudio);
    }
    
    public void crearPostulacion(Postulacion postulacion) throws Exception {
        // Asumiendo que tienes 'postulacionJpa' declarado arriba con los otros JpaControllers
        postulacionJpa.create(postulacion);
    }
    
    public Visita buscarVisita(long id) {
        return visitaJpa.findVisita(id);
    }
    
    public void editarVisita(Visita visita) throws Exception {
        visitaJpa.edit(visita);
    }
    
    public void eliminarVisita(long id) throws persistencia.exceptions.NonexistentEntityException {
        visitaJpa.destroy(id);
    }
    
    public List<Visita> traerTodasVisitas() {
        return visitaJpa.findVisitaEntities();
    }
    
    /**
     * Busca visitas filtrando por el nombre de la familia y el nombre del voluntario.
     */
    public List<Visita> buscarVisitasFiltradas(String nombreFamilia, String nombreVoluntario) {
        EntityManager em = visitaJpa.getEntityManager();
        try {
            // Consulta base JPQL
            String jpql = "SELECT v FROM Visita v WHERE 1=1";
            
            // Añadir filtros dinámicamente
            if (nombreFamilia != null && !nombreFamilia.isEmpty()) {
                // 'familia' es el nombre del atributo en la entidad Visita
                // 'nombre' es el atributo en la entidad FamiliaAdoptante (que hereda de Usuario)
                jpql += " AND v.familia.nombre LIKE :familia";
            }
            if (nombreVoluntario != null && !nombreVoluntario.isEmpty()) {
                // 'voluntarioEncargado' es el atributo en Visita
                // 'nombre' es el atributo en Voluntario (que hereda de Usuario)
                jpql += " AND v.voluntarioEncargado.nombre LIKE :voluntario";
            }
            
            TypedQuery<Visita> query = em.createQuery(jpql, Visita.class);
            
            // Asignar los parámetros
            if (nombreFamilia != null && !nombreFamilia.isEmpty()) {
                query.setParameter("familia", "%" + nombreFamilia + "%");
            }
            if (nombreVoluntario != null && !nombreVoluntario.isEmpty()) {
                query.setParameter("voluntario", "%" + nombreVoluntario + "%");
            }
            
            return query.getResultList();
            
        } finally {
            em.close();
        }
    }
    
    public void crearGato(Gato gato) throws Exception {
        // Delega la operación al GatoJpaController
        gatoJpa.create(gato);
    }
    
    public Zona buscarZonaPorNombre(String nombreZona) {
        EntityManager em = zonaJpa.getEntityManager();
        try {
            // JPQL para encontrar la Zona por su nombre
            TypedQuery<Zona> query = em.createQuery(
                "SELECT z FROM Zona z WHERE z.nombreZona = :nombreZona", 
                Zona.class
            );
            query.setParameter("nombreZona", nombreZona);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Retorna null si la zona no existe (se valida en la Controladora)
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<Zona> traerTodasLasZonas() {
        // Llama al método del JpaController que devuelve todas las zonas
        return zonaJpa.findZonaEntities();
    }
    
    public List<FamiliaAdoptante> traerTodasLasFamilias() {
    try {
        // Asumo que tienes FamiliaAdoptanteJpaController (familiaAdoptanteJpa)
        return familiaAdoptanteJpa.findFamiliaAdoptanteEntities();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

// --- Método para actualizar un gato (reutiliza el JpaController) ---
public void modificarGato(Gato gato) throws Exception {
    // Asumo que tienes GatoJpaController (gatoJpa)
    gatoJpa.edit(gato); 
}
public void crearTarea(Tarea tarea) throws Exception {
    // Asumo que tienes TareaJpaController (tareaJpa)
    tareaJpa.create(tarea);
}


public Voluntario buscarVoluntario(long idVoluntario) {
  
    return voluntarioJpa.findVoluntario((int)idVoluntario);
}


public Gato buscarGatoPorNombre(String nombreGato) {
    EntityManager em = gatoJpa.getEntityManager();
    try {
        // JPQL: Buscar Gato donde el atributo 'nombre' coincida con el parámetro
        TypedQuery<Gato> query = em.createQuery(
            "SELECT g FROM Gato g WHERE g.nombre = :nombre", 
            Gato.class
        );
        query.setParameter("nombre", nombreGato);
        return query.getSingleResult();
    } catch (NoResultException e) {
        // Retorna null si no se encuentra ningún gato con ese nombre
        return null; 
    } catch (Exception e) {
        // Manejo general de errores
        e.printStackTrace();
        return null;
    } finally {
        em.close();
    } 
    
}

public void crearZona(Zona zona) throws Exception {
    zonaJpa.create(zona);
}

// --- READ (Por ID) ---
public modelo.Zona buscarZona(long id) {
    return zonaJpa.findZona(id);
}


// --- UPDATE ---
public void modificarZona(Zona zona) throws Exception {
    zonaJpa.edit(zona);
}

// --- DELETE ---
public void eliminarZona(long id) throws NonexistentEntityException {
    zonaJpa.destroy(id);
}

public List<Usuario> traerTodosLosUsuarios() {
    // Delega directamente al JpaController de la clase base Usuario
    return usuarioJpa.findUsuarioEntities();
}

// --- NUEVO MÉTODO: BUSCAR USUARIO POR ID ---
public Usuario buscarUsuario(int id) {
    return usuarioJpa.findUsuario(id);
}

// --- NUEVO MÉTODO: MODIFICAR USUARIO ---
public void modificarUsuario(Usuario usuario) throws NonexistentEntityException, Exception {
    usuarioJpa.edit(usuario);
}

// --- NUEVO MÉTODO: ELIMINAR USUARIO ---
public void eliminarUsuario(int id) throws NonexistentEntityException {
    usuarioJpa.destroy(id);
}

public void crearReporte(Reporte reporte) throws Exception {
        reporteJpa.create(reporte);
    }
    
    public List<Reporte> traerTodosLosReportes() {
        // Utiliza el método que trae todas las entidades
        return reporteJpa.findReporteEntities();
    }
    
    public Reporte buscarReporte(long id) {
        // Utiliza el método que busca por ID
        return reporteJpa.findReporte(id);
    }
    
    public void modificarReporte(Reporte reporte) throws NonexistentEntityException, Exception {
        reporteJpa.edit(reporte);
    }
    
    public void eliminarReporte(long id) throws NonexistentEntityException {
        reporteJpa.destroy(id);
    }
    public List<Postulacion> traerTodasLasPostulaciones() {
        // Delega al método que trae todas las entidades
        return postulacionJpa.findPostulacionEntities();
    }
    
    
    public Postulacion buscarPostulacion(long id) {
        // Delega al método findPostulacion
        return postulacionJpa.findPostulacion(id);
    }
    
    
    public void modificarPostulacion(Postulacion postulacion) throws Exception {
        // Delega la operación al JpaController
        postulacionJpa.edit(postulacion);
    }
    
    
public List<Voluntario> traerTodosLosVoluntarios() {
    // Delega al método que trae todas las entidades Voluntario
    return voluntarioJpa.findVoluntarioEntities(); //
}
public List<Tarea> traerTodasLasTareas() {
    // Asume que tareaJpa es una instancia de TareaJpaController
    return tareaJpa.findTareaEntities(); 
}
}
    
