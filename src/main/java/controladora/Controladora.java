package controladora;

import java.time.LocalDate;
import java.util.Date;
import persistencia.ControladoraPersistencia;
import modelo.Gato;
import modelo.FamiliaAdoptante;
import modelo.Postulacion;
import modelo.OperacionException;
import java.util.List;
import modelo.Administrador;
import modelo.Estudio;
import modelo.HistoriaClinica;
import modelo.LoginException;
import modelo.RegistroException;
import modelo.Reporte;
import modelo.Tarea;
import modelo.Tratamiento;
import modelo.Usuario;
import modelo.Veterinario;
import modelo.Visita;
import modelo.Voluntario;
import modelo.Zona;
import persistencia.exceptions.NonexistentEntityException;

public class Controladora {
    
    ControladoraPersistencia controlpersis = new ControladoraPersistencia();
    
    public Usuario validarUsuario(String correo, String contrasena) throws LoginException, Exception {
        Usuario usuario = controlpersis.buscarUsuarioPorCorreo(correo);
        if (usuario == null) {
            throw new LoginException("Usuario no encontrado. Verifique el correo.");
        }
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new LoginException("Contraseña incorrecta.");
        }
        return usuario;
    }
    
    // --- LÓGICA DE GATO Y VETERINARIO ---

    public List<Gato> traerTodosLosGatos() throws OperacionException {
        try {
            List<Gato> gatos = controlpersis.traerTodosLosGatos();
            if (gatos == null || gatos.isEmpty()) {
                throw new OperacionException("No hay ningún gato registrado en el sistema.");
            }
            return gatos;
        } catch (Exception e) {
            throw new OperacionException("Error al traer los gatos: " + e.getMessage(), e);
        }
    }

    public Gato buscarGatoCompleto(long idGato) throws OperacionException { 
        Gato gato = controlpersis.buscarGato(idGato); 
        if (gato == null) {
            throw new OperacionException("Error: No se pudo encontrar el gato seleccionado.");
        }
        return gato;
    }

    public void modificarEstadoSaludGato(long idGato, Gato.EstadoSalud nuevoEstado) throws OperacionException {
        try {
            Gato gato = controlpersis.buscarGato(idGato);
            if (gato == null) {
                throw new OperacionException("No se encontró el gato a modificar.");
            }
            gato.setestadoFisico(nuevoEstado); //
            controlpersis.modificarGato(gato);
        } catch (Exception e) {
            throw new OperacionException("Error al modificar el estado de salud: " + e.getMessage(), e);
        }
    }

    public void emitirCertificadoAptitud(long idGato, int idVeterinario) throws OperacionException {
        // Lógica futura para crear un objeto Aptitud
        throw new OperacionException("La función 'Emitir Certificado' aún no está implementada.");
    }
    
    // --- LÓGICA DE HISTORIA CLÍNICA ---
    
    public HistoriaClinica buscarHistoriaClinica(long idHistoria) throws OperacionException {
        HistoriaClinica hc = controlpersis.buscarHistoriaClinica(idHistoria);
        if (hc == null) {
            throw new OperacionException("No se encontró la Historia Clínica.");
        }
        return hc;
    }

    public void agregarTratamientoAHistoria(long idHistoria, String diagnostico, String descripcion) throws OperacionException {
        if (diagnostico.isEmpty() || descripcion.isEmpty()) {
            throw new OperacionException("El diagnóstico y la descripción son obligatorios.");
        }
        try {
            HistoriaClinica hc = buscarHistoriaClinica(idHistoria);
            Tratamiento nuevoTratamiento = new Tratamiento(diagnostico, descripcion, hc); //
            controlpersis.crearTratamiento(nuevoTratamiento); 
        } catch (Exception e) {
            throw new OperacionException("Error al guardar el tratamiento: " + e.getMessage(), e);
        }
    }

    public void agregarEstudioAHistoria(long idHistoria, String nombreEstudio, String resultado) throws OperacionException {
         if (nombreEstudio.isEmpty() || resultado.isEmpty()) {
            throw new OperacionException("El nombre del estudio y el resultado son obligatorios.");
        }
        try {
            HistoriaClinica hc = buscarHistoriaClinica(idHistoria);
            Estudio nuevoEstudio = new Estudio(); //
            nuevoEstudio.setNombreEstudio(nombreEstudio); //
            nuevoEstudio.setDescripcion(resultado); //
            nuevoEstudio.setHistoriaClinica(hc); //
            controlpersis.crearEstudio(nuevoEstudio);
        } catch (Exception e) {
            throw new OperacionException("Error al guardar el estudio: " + e.getMessage(), e);
        }
    }
    
    // --- LÓGICA DE USUARIOS Y REGISTRO ---
    
    public void registrarUsuarioPorRol(String nombre, String correo, String contrasena, 
                                       String telefono, String direccion, String matricula, 
                                       String rol) throws RegistroException {
        try {
            double telefonoDouble = Double.parseDouble(telefono);
            int matriculaInt = 0; 
            
            if (rol.equals("VETERINARIO")) {
                if (matricula.isEmpty()) {
                    throw new RegistroException("El campo Matrícula es obligatorio para Veterinarios.");
                }
                try {
                    matriculaInt = Integer.parseInt(matricula); 
                } catch (NumberFormatException e) {
                    throw new RegistroException("La Matrícula debe ser un número entero válido.", e);
                }
            }
            
            Usuario nuevoUsuario = null; 

            switch (rol) {
                case "FAMILIA":
                    nuevoUsuario = new FamiliaAdoptante(
                        direccion, nombre, correo, contrasena, telefonoDouble, rol
                    );
                    controlpersis.crearFamiliaAdoptante((FamiliaAdoptante) nuevoUsuario);
                    break;
                case "VETERINARIO":
                    nuevoUsuario = new Veterinario(
                        matriculaInt, nombre, correo, contrasena, telefonoDouble, rol, direccion
                    );
                    controlpersis.crearVeterinario((Veterinario) nuevoUsuario);
                    break;
                case "ADMINISTRADOR":
                    nuevoUsuario = new Administrador(
                        nombre, correo, contrasena, telefonoDouble, rol, direccion
                    );
                    controlpersis.crearAdministrador((Administrador) nuevoUsuario);
                    break;
                case "VOLUNTARIO":
                    nuevoUsuario = new Voluntario(
                        nombre, correo, contrasena, telefonoDouble, rol, direccion
                    );
                    controlpersis.crearVoluntario((Voluntario) nuevoUsuario);
                    break;
                default:
                    throw new RegistroException("Rol de usuario inválido.");
            }
        } catch (NumberFormatException e) {
            throw new RegistroException("El campo Teléfono debe contener solo números válidos.", e);
        } catch (RegistroException e) {
            throw e;
        } catch (Exception e) {
            throw new RegistroException("Fallo de persistencia: " + e.getMessage(), e);
        }
    }
    
    // --- LÓGICA DE POSTULACIÓN Y ASIGNACIÓN ---
    
    public void crearPostulacion(long idGato, int idFamilia) throws OperacionException {
        try {
            Gato gato = controlpersis.buscarGato(idGato);
            FamiliaAdoptante familia = controlpersis.buscarFamilia(idFamilia);

            if (gato == null) throw new OperacionException("Gato no encontrado.");
            if (familia == null) throw new OperacionException("Familia no encontrada.");

            boolean yaExiste = controlpersis.existePostulacion(idGato, idFamilia);
            if (yaExiste) {
                throw new OperacionException("Ya te has postulado para este gato.");
            }
            if (gato.getDisponible() != modelo.Gato.RespuestaBinaria.SI) {
                 throw new OperacionException("Lo sentimos, este gato no está disponible para adopción.");
            }

            Postulacion nuevaPostulacion = new Postulacion();
            nuevaPostulacion.setGatoRelacionado(gato);
            nuevaPostulacion.setFamiliaPostulante(familia);
            nuevaPostulacion.setEstado(Postulacion.Estado.PENDIENTE); 
            controlpersis.crearPostulacion(nuevaPostulacion);
        } catch (OperacionException e) {
            throw e; 
        } catch (Exception e) {
            throw new OperacionException("Error de persistencia al crear la postulación: " + e.getMessage(), e);
        }
    }
   
    public List<Gato> traerGatosDisponibles() throws OperacionException {
        try {
            List<Gato> gatos = controlpersis.buscarGatosDisponibles(); 
            if (gatos == null || gatos.isEmpty()) {
                throw new OperacionException("No hay gatos disponibles para mostrar en este momento.");
            }
            return gatos;
        } catch (Exception e) {
            throw new OperacionException("Error al traer los gatos: " + e.getMessage(), e);
        }
    } 

    public void asignarGatoAFamilia(long idGato, int idFamilia) throws OperacionException {
        try {
            // 🟢 CORRECCIÓN: Eliminado el (int) cast
            Gato gato = controlpersis.buscarGato(idGato); 
            FamiliaAdoptante familia = controlpersis.buscarFamilia(idFamilia);

            if (gato == null) {
                throw new OperacionException("Error: No se encontró el gato seleccionado.");
            }
            if (familia == null) {
                throw new OperacionException("Error: No se encontró la familia destino.");
            }
            if (gato.getDisponible() != Gato.RespuestaBinaria.SI) {
                throw new OperacionException("El gato no está disponible para asignación (Estado=NO).");
            }
            
            gato.setFamiliaAdoptante(familia);
            gato.setDisponible(Gato.RespuestaBinaria.NO); 
            controlpersis.modificarGato(gato);
        } catch (OperacionException e) {
            throw e;
        } catch (Exception e) {
            throw new OperacionException("Error crítico al asignar el gato: " + e.getMessage(), e);
        }
    }

    public List<FamiliaAdoptante> traerTodasLasFamilias() throws OperacionException {
        try {
            List<FamiliaAdoptante> familias = controlpersis.traerTodasLasFamilias();
            if (familias == null || familias.isEmpty()) {
                throw new OperacionException("No hay familias adoptantes registradas.");
            }
            return familias;
        } catch (Exception e) {
            throw new OperacionException("Error al traer las familias: " + e.getMessage(), e);
        }
    }
    
    // --- LÓGICA DE VISITAS ---
    
    public List<Visita> traerTodasLasVisitas() {
        return controlpersis.traerTodasVisitas();
    }

    public List<Visita> filtrarVisitas(String nombreFamilia, String nombreVoluntario) {
        return controlpersis.buscarVisitasFiltradas(nombreFamilia, nombreVoluntario);
    }
    
    public Visita buscarVisita(long idVisita) throws OperacionException {
        Visita v = controlpersis.buscarVisita(idVisita);
        if (v == null) {
            throw new OperacionException("No se encontró la visita con ID: " + idVisita);
        }
        return v;
    }
    
    public void modificarVisita(long idVisita, String nuevaDescripcion, LocalDate nuevaFecha) throws OperacionException {
        try {
            Visita visita = this.buscarVisita(idVisita); 
            visita.setDescripcion(nuevaDescripcion);
            visita.setFecha(nuevaFecha);
            controlpersis.editarVisita(visita);
        } catch (Exception e) {
            throw new OperacionException("Error al modificar la visita: " + e.getMessage(), e);
        }
    }
    
    public void eliminarVisita(long idVisita) throws OperacionException {
        try {
            controlpersis.eliminarVisita(idVisita);
        } catch (Exception e) {
            throw new OperacionException("Error al eliminar la visita: " + e.getMessage(), e);
        }
    }

    public void registrarVisitaDeSeguimiento(int idFamilia, int idVoluntario, 
                                            LocalDate fecha, String descripcion) 
                                            throws OperacionException {
        // ... (Tu lógica para crear y guardar una nueva visita)
    }
    
    // --- LÓGICA DE REGISTRO DE GATO ---
    
    public void registrarGato(String nombre, String raza, String sexo, String color, 
                              String esterilizado, String caracteristicas, 
                              String estadoSalud, String disponible, String nombreZona,
                              String rutaFoto)
                              throws OperacionException {
        if (nombre.isEmpty() || raza.isEmpty() || sexo.equals("-") || color.isEmpty() || 
            esterilizado.equals("-") || estadoSalud.equals("-") || disponible.equals("-") || nombreZona.equals("-")) {
            throw new OperacionException("Debe completar todos los campos obligatorios.");
        }
        try {
            Gato.RespuestaBinaria esterilizadoEnum = Gato.RespuestaBinaria.valueOf(esterilizado.toUpperCase());
            Gato.EstadoSalud estadoFisicoEnum = Gato.EstadoSalud.valueOf(estadoSalud.replace(" ", "_").toUpperCase()); 
            Gato.RespuestaBinaria disponibleEnum = Gato.RespuestaBinaria.valueOf(disponible.toUpperCase());

            Zona zona = controlpersis.buscarZonaPorNombre(nombreZona);
            if (zona == null) {
                throw new OperacionException("La Zona '" + nombreZona + "' no existe. Debe registrar la zona primero.");
            }

            Gato nuevoGato = new Gato();
            nuevoGato.setNombre(nombre);
            nuevoGato.setRaza(raza);
            nuevoGato.setSexo(sexo);
            nuevoGato.setColor(color);
            nuevoGato.setEsterilizado(esterilizadoEnum);
            nuevoGato.setCaracteristicas(caracteristicas);
            nuevoGato.setestadoFisico(estadoFisicoEnum);
            nuevoGato.setDisponible(disponibleEnum);
            
            if (rutaFoto != null && !rutaFoto.isEmpty()) {
                 nuevoGato.setRutaFoto(rutaFoto);
            }
        
            nuevoGato.setZona(zona);
            nuevoGato.setHistoriaClinica(new HistoriaClinica("Historia inicial al registro")); 
            
            controlpersis.crearGato(nuevoGato);
        } catch (IllegalArgumentException e) {
            throw new OperacionException("Error de datos: Uno de los campos de selección (Enum) es inválido. Revise Sexo/Estado/Disponibilidad.", e);
        } catch (OperacionException e) {
            throw e; 
        } catch (Exception e) {
            throw new OperacionException("Error de persistencia al registrar el gato: " + e.getMessage(), e);
        }
    }
    
    // --- LÓGICA DE TAREAS ---

    public void registrarTarea(long idVoluntario, String nombreGato, 
                               String fechaStr, String tipoTareaStr, String descripcion) throws OperacionException {
        if (nombreGato.equals("-") || tipoTareaStr.equals("-") || descripcion.isEmpty() || fechaStr.isEmpty()) {
            throw new OperacionException("Debe completar todos los campos obligatorios.");
        }
        try {
            Voluntario voluntario = controlpersis.buscarVoluntario(idVoluntario);
            if (voluntario == null) {
                throw new OperacionException("Error interno: El voluntario logueado no pudo ser encontrado.");
            }
            Gato gato = controlpersis.buscarGatoPorNombre(nombreGato);
            if (gato == null) {
                throw new OperacionException("El gato seleccionado no existe.");
            }
            
            Tarea.TipoTarea tipoTarea = Tarea.TipoTarea.valueOf(tipoTareaStr.replace(" ", "_").toUpperCase());
            
            Tarea nuevaTarea = new Tarea();
            nuevaTarea.setFecha(new Date()); 
            nuevaTarea.setTipoTarea(tipoTarea);
            nuevaTarea.setDescripcion(descripcion);
            nuevaTarea.setGatoAsignado(gato);
            nuevaTarea.setVoluntarioQueRealiza(voluntario);

            controlpersis.crearTarea(nuevaTarea);
        } catch (IllegalArgumentException e) {
            throw new OperacionException("Error de datos: El tipo de tarea seleccionado no es válido.", e);
        } catch (Exception e) {
            throw new OperacionException("Error de persistencia al registrar la tarea: " + e.getMessage(), e);
        }
    }

    public void crearTarea(Tarea tarea) throws Exception {
        controlpersis.crearTarea(tarea);
    }

    public Voluntario buscarVoluntario(long idVoluntario) {
        return controlpersis.buscarVoluntario(idVoluntario);
    }

    public Gato buscarGatoPorNombre(String nombreGato) {
        return controlpersis.buscarGatoPorNombre(nombreGato);
    }

    // --- LÓGICA DE ZONAS ---

    public void registrarZona(String nombreZona, String ubicacionGPS) throws OperacionException {
        if (nombreZona.isEmpty() || ubicacionGPS.isEmpty()) {
            throw new OperacionException("El nombre de la zona y la ubicación GPS son obligatorios.");
        }
        try {
            Zona nuevaZona = new Zona();
            nuevaZona.setNombreZona(nombreZona);
            nuevaZona.setUbicacionGPS(ubicacionGPS);
            controlpersis.crearZona(nuevaZona);
        } catch (Exception e) {
            throw new OperacionException("Error al registrar la zona: Verifique duplicados.", e);
        }
    }

    public List<Zona> traerTodasLasZonas() throws OperacionException {
        List<Zona> zonas = controlpersis.traerTodasLasZonas();
        if (zonas == null || zonas.isEmpty()) {
            throw new OperacionException("No hay zonas registradas en el sistema.");
        }
        return zonas;
    }

    public void modificarZona(long idZona, String nombreZona, String ubicacionGPS) throws OperacionException {
        if (nombreZona.isEmpty() || ubicacionGPS.isEmpty()) {
            throw new OperacionException("El nombre de la zona y la ubicación GPS son obligatorios.");
        }
        try {
            Zona zona = controlpersis.buscarZona(idZona);
            if (zona == null) {
                throw new OperacionException("La zona que intenta modificar no existe.");
            }
            zona.setNombreZona(nombreZona);
            zona.setUbicacionGPS(ubicacionGPS);
            controlpersis.modificarZona(zona);
        } catch (Exception e) {
            throw new OperacionException("Error al modificar la zona.", e);
        }
    }

    public void eliminarZona(long idZona) throws OperacionException {
        try {
            controlpersis.eliminarZona(idZona);
        } catch (persistencia.exceptions.NonexistentEntityException e) {
            throw new OperacionException("Error: La zona seleccionada no existe.", e);
        } catch (Exception e) {
            throw new OperacionException("Error crítico al eliminar la zona. Verifique que no tenga gatos asignados.", e);
        }
    }

    public Zona buscarZona(long idZona) {
        return controlpersis.buscarZona(idZona);
    }
    
    public List<Usuario> traerTodosLosUsuarios() {
        // No hay lógica de negocio compleja, se delega directamente.
        return controlpersis.traerTodosLosUsuarios();
    }
    
    
    public Usuario buscarUsuario(int id) {
        // Se utiliza en la lógica de Modificar de la vista.
        return controlpersis.buscarUsuario(id);
    }
    
    
    public void modificarUsuario(Usuario usuario) throws OperacionException {
        // 🚨 Aquí iría la validación de negocio (ej: validar formato de correo, etc.)
        if (usuario.getNombre().isEmpty() || usuario.getCorreo().isEmpty()) {
            throw new OperacionException("El nombre y correo del usuario son obligatorios.");
        }
        
        try {
            controlpersis.modificarUsuario(usuario);
        } catch (NonexistentEntityException ex) {
            // El objeto de persistencia no existía. Se convierte a una excepción de negocio.
            throw new OperacionException("El usuario a modificar ya no existe en la base de datos.", ex);
        } catch (Exception ex) {
            // Error genérico de persistencia.
            throw new OperacionException("Error inesperado al intentar modificar el usuario.", ex);
        }
    }
    
    
    public void eliminarUsuario(int id) throws OperacionException {
        try {
            controlpersis.eliminarUsuario(id);
        } catch (NonexistentEntityException ex) {
            throw new OperacionException("No se puede eliminar el usuario: no existe en la base de datos.", ex);
        } catch (Exception ex) {
            // Este catch es vital para capturar errores de FK (Foreign Key)
            throw new OperacionException("Error al eliminar el usuario. Revise si tiene datos asociados (ej: reportes, gatos, etc.)", ex);
        }
    }
    
    
    public void registrarReporte(int cantidad, String descripcion) throws OperacionException {
        
        if (cantidad <= 0 || descripcion.isEmpty()) {
            throw new OperacionException("La cantidad debe ser positiva y la descripción del reporte es obligatoria.");
        }
        
        try {
            Reporte nuevoReporte = new Reporte();
            nuevoReporte.setCantidad(cantidad);
            nuevoReporte.setDescripcion(descripcion);
            nuevoReporte.setFechaReporte(LocalDate.now()); 
            // Nota: Si Reporte requiere Administrador, se debería pasar aquí el objeto Administrador logueado.
            
            controlpersis.crearReporte(nuevoReporte);
        } catch (Exception e) {
            throw new OperacionException("Error al registrar el reporte: " + e.getMessage(), e);
        }
    }
    
    /**
     * Trae todos los Reportes del sistema.
     */
    public List<Reporte> traerTodosLosReportes() throws OperacionException {
        try {
            List<Reporte> reportes = controlpersis.traerTodosLosReportes();
            if (reportes == null || reportes.isEmpty()) {
                throw new OperacionException("No hay reportes registrados en el sistema.");
            }
            return reportes;
        } catch (Exception e) {
            throw new OperacionException("Error al traer los reportes: " + e.getMessage(), e);
        }
    }

    /**
     * Busca un Reporte por su ID.
     */
    public Reporte buscarReporte(long id) throws OperacionException {
        Reporte reporte = controlpersis.buscarReporte(id);
        if (reporte == null) {
            throw new OperacionException("Reporte no encontrado con ID: " + id);
        }
        return reporte;
    }
    
    /**
     * Modifica un Reporte existente.
     */
    public void modificarReporte(long id, int cantidad, String descripcion) throws OperacionException {
        // 🚨 Validación de negocio
        if (cantidad <= 0 || descripcion.isEmpty()) {
            throw new OperacionException("La cantidad debe ser positiva y la descripción es obligatoria.");
        }
        
        try {
            Reporte reporte = buscarReporte(id); // Reutiliza el método de búsqueda
            
            reporte.setCantidad(cantidad);
            reporte.setDescripcion(descripcion);
            // La fecha no se modifica para mantener la fecha original del reporte.
            
            controlpersis.modificarReporte(reporte);
            
        } catch (NonexistentEntityException ex) {
            throw new OperacionException("El reporte que intenta modificar no existe.", ex);
        } catch (Exception ex) {
            throw new OperacionException("Error al modificar el reporte: " + ex.getMessage(), ex);
        }
    }

    /**
     * Elimina un Reporte por su ID.
     */
    public void eliminarReporte(long id) throws OperacionException {
        try {
            controlpersis.eliminarReporte(id);
        } catch (NonexistentEntityException ex) {
            throw new OperacionException("El reporte que intenta eliminar no existe.", ex);
        } catch (Exception ex) {
            throw new OperacionException("Error crítico al eliminar el reporte: " + ex.getMessage(), ex);
        }
    }
    
    public List<Postulacion> traerTodasLasPostulaciones() throws OperacionException {
    try {
        List<Postulacion> postulaciones = controlpersis.traerTodasLasPostulaciones();
        if (postulaciones == null || postulaciones.isEmpty()) {
            throw new OperacionException("No hay postulaciones registradas en el sistema.");
        }
        return postulaciones;
    } catch (Exception e) {
        throw new OperacionException("Error al traer postulaciones: " + e.getMessage(), e);
    }
}

public Postulacion buscarPostulacionPorId(long id) throws OperacionException {
    Postulacion p = controlpersis.buscarPostulacion(id); // Asumo controlpersis.buscarPostulacion(long)
    if (p == null) {
        throw new OperacionException("Postulación no encontrada.");
    }
    return p;
}

public void cambiarEstadoPostulacion(long idPostulacion, Postulacion.Estado nuevoEstado) throws OperacionException {
    try {
        Postulacion p = controlpersis.buscarPostulacion(idPostulacion);
        if (p == null) {
            throw new OperacionException("Postulación no encontrada para modificar el estado.");
        }
        
        p.setEstado(nuevoEstado);
        controlpersis.modificarPostulacion(p);
        
       
        if (nuevoEstado == Postulacion.Estado.APROBADA) {
             long idGato = p.getGatoRelacionado().getIdGato();
             int idFamilia = p.getFamiliaPostulante().getIdUsuario();
             
             // Esto marcará el gato como NO disponible y le asignará la familia.
             this.asignarGatoAFamilia(idGato, idFamilia); // Ya existe en su Controladora
        }
        
    } catch (Exception ex) {
        throw new OperacionException("Error al cambiar el estado de la postulación.", ex);
    }
}

public List<Voluntario> traerTodosLosVoluntarios() throws OperacionException {
    try {
        List<Voluntario> voluntarios = controlpersis.traerTodosLosVoluntarios(); // 🟢 LLAMADA A PERSISTENCIA
        if (voluntarios == null || voluntarios.isEmpty()) {
            throw new OperacionException("No hay voluntarios registrados en el sistema.");
        }
        return voluntarios;
    } catch (Exception e) {
        throw new OperacionException("Error al traer la lista de voluntarios: " + e.getMessage(), e);
    }
}


public void registrarTareaCompleta(long idVoluntario, long idGato, String ubicacion, String fechaStr, String tipoTareaStr, String descripcion) throws OperacionException {
    
    // 🚨 1. Validación de campos de la interfaz
    if (ubicacion.isEmpty() || tipoTareaStr.equals("-") || descripcion.isEmpty() || fechaStr.isEmpty()) {
        throw new OperacionException("Todos los campos de la tarea son obligatorios.");
    }

    try {
        // 2. Buscar entidades por ID
        Voluntario voluntario = controlpersis.buscarVoluntario(idVoluntario); //
        Gato gato = controlpersis.buscarGato(idGato); 
        
        if (voluntario == null) throw new OperacionException("Error interno: Voluntario no encontrado.");
        if (gato == null) throw new OperacionException("Error: Gato no encontrado.");
        
        // 3. Convertir Enum
        Tarea.TipoTarea tipoTarea = Tarea.TipoTarea.valueOf(tipoTareaStr.toUpperCase());
        
       
        Date fecha = new Date(); 
        
        // 4. Crear la entidad Tarea
        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setFecha(fecha);
        nuevaTarea.setTipoTarea(tipoTarea); //
        nuevaTarea.setDescripcion(descripcion);
        nuevaTarea.setUbicacion(ubicacion); //
        nuevaTarea.setGatoAsignado(gato);
        nuevaTarea.setVoluntarioQueRealiza(voluntario);

        // 5. Persistir
        controlpersis.crearTarea(nuevaTarea); //
        
    } catch (IllegalArgumentException e) {
        throw new OperacionException("Error de formato: El tipo de tarea seleccionado no es válido.", e);
    } catch (OperacionException e) {
        throw e;
    } catch (Exception e) {
        throw new OperacionException("Error de persistencia al registrar la tarea: " + e.getMessage(), e);
    }
}

public List<Tarea> traerTodasLasTareas() throws OperacionException {
    try {
        List<Tarea> tareas = controlpersis.traerTodasLasTareas();
        
        if (tareas == null || tareas.isEmpty()) {
            throw new OperacionException("No hay tareas registradas en el sistema.");
        }
        return tareas;
    } catch (Exception e) {
        throw new OperacionException("Error al intentar cargar todas las tareas: " + e.getMessage(), e);
    }
}
}