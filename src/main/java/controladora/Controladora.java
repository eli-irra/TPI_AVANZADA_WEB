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
            throw new LoginException("Usuario no encontrado.  Verifique el correo.");
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
    
    public void crearNuevaHistoriaClinica(long idGato, String motivo) throws OperacionException {
        try {
            // 1. Buscamos al Gato (Padre)
            Gato gato = controlpersis.buscarGato(idGato);
            
            if (gato != null) {
                // 2. Creamos la nueva historia
                HistoriaClinica nuevaHC = new HistoriaClinica();
                nuevaHC.setDescripcion(motivo);
                
                // 3. La agregamos a la lista del Gato (usando tu método helper si existe, o directo)
                // gato.agregarConsulta(nuevaHC); // Si tienes este método en Gato
                // O directamente:
                if (gato.getHistoriasClinicas() == null) {
                    gato.setHistoriasClinicas(new java.util.ArrayList<>());
                }
                gato.getHistoriasClinicas().add(nuevaHC);
                
                // 4. Modificamos al Gato (El CascadeType.ALL guardará la historia automáticamente)
                controlpersis.modificarGato(gato);
            }
        } catch (Exception e) {
            throw new OperacionException("Error al crear la nueva consulta: " + e.getMessage(), e);
        }
    }
    
    public HistoriaClinica buscarHistoriaClinica(long idHistoria) throws OperacionException {
        HistoriaClinica hc = controlpersis.buscarHistoriaClinica(idHistoria);
        if (hc == null) {
            throw new OperacionException("No se encontró la Historia Clínica.");
        }
        return hc;
    }
    
    public void eliminarHistoriaClinica(long idGato, long idHistoria) throws OperacionException {
        try {

            Gato gato = controlpersis.buscarGato(idGato);
            
            if (gato != null && gato.getHistoriasClinicas() != null) {

                gato.getHistoriasClinicas().removeIf(hc -> hc.getidHistoria() == idHistoria);

                controlpersis.modificarGato(gato);
            }
        } catch (Exception e) {
            throw new OperacionException("Error al eliminar la historia clínica.", e);
        }
    }
    
    public void modificarHistoriaClinica(HistoriaClinica hc) throws OperacionException {
        try {
            controlpersis.modificarHistoriaClinica(hc);
        } catch (Exception e) {
            throw new OperacionException("Error al actualizar la historia clínica: " + e.getMessage(), e);
        }
    }
    
    public void agregarTratamientoAHistoria(long idHistoria, String diagnostico, String descripcion) throws OperacionException {
        if (diagnostico.isEmpty() || descripcion.isEmpty()) {
            throw new OperacionException("El diagnóstico y la descripción son obligatorios.");
        }
        try {
            HistoriaClinica hc = buscarHistoriaClinica(idHistoria);

            Tratamiento nuevoTratamiento = new Tratamiento(diagnostico, descripcion, hc);

            if (hc.getTratamientos() != null) {
                hc.getTratamientos().add(nuevoTratamiento);
            } else {
                // Si la lista es nula, la inicializamos (aunque el constructor debería hacerlo)
                java.util.List<Tratamiento> lista = new java.util.ArrayList<>();
                lista.add(nuevoTratamiento);
                hc.setTratamientos(lista);
            }

            this.modificarHistoriaClinica(hc);

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

           Estudio nuevoEstudio = new Estudio();
           nuevoEstudio.setNombreEstudio(nombreEstudio);
           nuevoEstudio.setDescripcion(resultado);
           nuevoEstudio.setHistoriaClinica(hc);

           if (hc.getEstudios() != null) {
               hc.getEstudios().add(nuevoEstudio);
           } else {
               java.util.List<Estudio> lista = new java.util.ArrayList<>();
               lista.add(nuevoEstudio);
               hc.setEstudios(lista);
           }

           this.modificarHistoriaClinica(hc);

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
    
    // 1. Validaciones básicas de entrada
    if (fecha == null) {
        throw new OperacionException("La fecha de la visita es obligatoria.");
    }
    if (descripcion == null || descripcion.trim().isEmpty()) {
        throw new OperacionException("La descripción de la visita es obligatoria.");
    }

    try {
        // 2. Buscar las entidades relacionadas (Familia y Voluntario)
        // Nota: Asumo que en tu persistencia existen estos métodos de búsqueda.
        FamiliaAdoptante familia = controlpersis.buscarFamilia(idFamilia); 
        Voluntario voluntario = controlpersis.buscarVoluntario(idVoluntario);

        // 3. Verificar existencia
        if (familia == null) {
            throw new OperacionException("No se encontró la Familia Adoptante con ID: " + idFamilia);
        }
        if (voluntario == null) {
            throw new OperacionException("No se encontró el Voluntario con ID: " + idVoluntario);
        }

        // 4. Crear el objeto Visita y asignar relaciones
        Visita nuevaVisita = new Visita();
        nuevaVisita.setFecha(fecha);
        nuevaVisita.setDescripcion(descripcion);
        nuevaVisita.setFamilia(familia);       // Asigna la familia a la visita
        nuevaVisita.setVoluntarioEncargado(voluntario); // Asigna el voluntario a la visita

        // 5. Persistir en la Base de Datos
        controlpersis.crearVisita(nuevaVisita); 

    } catch (OperacionException e) {
        throw e; // Re-lanzamos excepciones de negocio propias
    } catch (Exception e) {
        // Capturamos cualquier otro error de base de datos
        throw new OperacionException("Error al registrar la visita: " + e.getMessage(), e);
    }
}
    
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

            nuevoGato.agregarConsulta(new HistoriaClinica("Historia inicial al registro")); 
            // -----------------------

            controlpersis.crearGato(nuevoGato);
        } catch (IllegalArgumentException e) {
            throw new OperacionException("Error de datos: Uno de los campos de selección (Enum) es inválido. Revise Sexo/Estado/Disponibilidad.", e);
        } catch (OperacionException e) {
            throw e; 
        } catch (Exception e) {
            throw new OperacionException("Error de persistencia al registrar el gato: " + e.getMessage(), e);
        }
    }
    
    public void modificarGato(Gato gato) throws Exception {
        controlpersis.modificarGato(gato);
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
        return controlpersis.traerTodosLosUsuarios();
    }
    
    
    public Usuario buscarUsuario(int id) {
        // Se utiliza en la lógica de Modificar de la vista.
        return controlpersis.buscarUsuario(id);
    }
    
    
   public void modificarUsuario(Usuario usuario) throws OperacionException {
    
        // 1. Validaciones
        if (usuario == null) {
            throw new OperacionException("El usuario a modificar no puede ser nulo.");
        }

        // Validamos que no sea null Y que no esté vacío (usando trim para ignorar espacios en blanco)
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new OperacionException("El nombre del usuario es obligatorio.");
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new OperacionException("El correo del usuario es obligatorio.");
        }

        // 2. Persistencia
        try {
            controlpersis.modificarUsuario(usuario);

        } catch (NonexistentEntityException ex) {
            // El objeto de persistencia no existía. Se convierte a una excepción de negocio.
            throw new OperacionException("El usuario a modificar ya no existe en la base de datos.", ex);

        } catch (Exception ex) {
            // Error genérico de persistencia (ej: desconexión de BD, constraint violation)
            throw new OperacionException("Error inesperado al intentar modificar el usuario: " + ex.getMessage(), ex);
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
    
    public void registrarReporte(int cantidad, String descripcion, String idZonaStr, 
                             String nuevaZonaNombre, String nuevaZonaGPS, 
                             Administrador adminLogueado) throws OperacionException { // <--- Agregado aquí
    
        if (cantidad <= 0 || descripcion.isEmpty()) {
            throw new OperacionException("La cantidad debe ser positiva y la descripción es obligatoria.");
        }

        try {
            Zona zonaAsignada = null;

            if ("nueva".equals(idZonaStr) || "0".equals(idZonaStr)) {
                if (nuevaZonaNombre == null || nuevaZonaNombre.isEmpty() || nuevaZonaGPS == null || nuevaZonaGPS.isEmpty()) {
                    throw new OperacionException("Para crear una zona nueva, debe indicar nombre y coordenadas GPS.");
                }
                zonaAsignada = new Zona();
                zonaAsignada.setNombreZona(nuevaZonaNombre);
                zonaAsignada.setUbicacionGPS(nuevaZonaGPS);
                controlpersis.crearZona(zonaAsignada);
            } else {
                int idZona = Integer.parseInt(idZonaStr);
                zonaAsignada = controlpersis.buscarZona(idZona);
                if (zonaAsignada == null) {
                    throw new OperacionException("La zona seleccionada no existe.");
                }
            }

            // Crear Reporte
            Reporte nuevoReporte = new Reporte();
            nuevoReporte.setCantidad(cantidad);
            nuevoReporte.setDescripcion(descripcion);
            nuevoReporte.setFechaReporte(java.time.LocalDate.now());
            nuevoReporte.setZonaReportada(zonaAsignada);

            // Asignar el administrador que recibimos por parámetro
            nuevoReporte.setAdministrador(adminLogueado);

            controlpersis.crearReporte(nuevoReporte);

        } catch (NumberFormatException e) {
            throw new OperacionException("Error en el formato del ID de zona.", e);
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
    public void modificarReporte(long id, int cantidad, String descripcion, 
                             String idZonaStr, String nuevaZonaNombre, String nuevaZonaGPS) throws OperacionException {
    
    if (cantidad <= 0 || descripcion.isEmpty()) {
        throw new OperacionException("La cantidad debe ser positiva y la descripción es obligatoria.");
    }
    
    try {
        Reporte reporte = buscarReporte(id);

        Zona zonaAsignada = null;

        if ("nueva".equals(idZonaStr) || "0".equals(idZonaStr)) {
            // Crear nueva zona
            if (nuevaZonaNombre == null || nuevaZonaNombre.isEmpty() || nuevaZonaGPS == null || nuevaZonaGPS.isEmpty()) {
                throw new OperacionException("Para crear una zona nueva, debe indicar nombre y coordenadas GPS.");
            }
            zonaAsignada = new Zona();
            zonaAsignada.setNombreZona(nuevaZonaNombre);
            zonaAsignada.setUbicacionGPS(nuevaZonaGPS);
            controlpersis.crearZona(zonaAsignada);
        } else {
            // Buscar zona existente
            int idZona = Integer.parseInt(idZonaStr);
            zonaAsignada = controlpersis.buscarZona(idZona);
            if (zonaAsignada == null) {
                throw new OperacionException("La zona seleccionada no existe.");
            }
        }

        reporte.setCantidad(cantidad);
        reporte.setDescripcion(descripcion);
        reporte.setZonaReportada(zonaAsignada); // Actualizamos la zona
        
        controlpersis.modificarReporte(reporte);
        
    } catch (NumberFormatException ex) {
        throw new OperacionException("Error en el formato de datos numéricos.", ex);
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
        List<Voluntario> voluntarios = controlpersis.traerTodosLosVoluntarios();
        if (voluntarios == null || voluntarios.isEmpty()) {
            throw new OperacionException("No hay voluntarios registrados en el sistema.");
        }
        return voluntarios;
    } catch (Exception e) {
        throw new OperacionException("Error al traer la lista de voluntarios: " + e.getMessage(), e);
    }
}


public void registrarTareaCompleta(long idVoluntario, long idGato, String ubicacion, String fechaStr, String tipoTareaStr, String descripcion) throws OperacionException {
    
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

    public Zona traerZona(int idZona) {
        return this.buscarZona(idZona);
    }
    
    public void crearGato(Gato gato) throws Exception {
        HistoriaClinica hc = new HistoriaClinica();
        controlpersis.crearHistoriaClinica(hc);
        gato.agregarConsulta(hc);
        controlpersis.crearGato(gato);
    }
    
    public Estudio buscarEstudio(long id) {
        return controlpersis.buscarEstudio(id);
    }

    public void modificarEstudio(long idEstudio, String nombre, String descripcion) throws OperacionException {
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            throw new OperacionException("Todos los campos del estudio son obligatorios.");
        }
        try {
            Estudio estudio = controlpersis.buscarEstudio(idEstudio);
            if (estudio != null) {
                estudio.setNombreEstudio(nombre);
                estudio.setDescripcion(descripcion);
                controlpersis.modificarEstudio(estudio);
                
            }
        } catch (Exception e) {
            throw new OperacionException("Error al modificar el estudio: " + e.getMessage(), e);
        }
    }

    public void eliminarEstudio(long id) throws OperacionException {
        try {
            Estudio estudio = controlpersis.buscarEstudio(id);
            if (estudio != null) {
                HistoriaClinica hc = estudio.getHistoriaClinica();
                if (hc.getEstudios() != null) {
                    hc.getEstudios().removeIf(e -> e.getIdEstudio() == id);
                }
                controlpersis.modificarHistoriaClinica(hc);
            }
        } catch (Exception e) {
            throw new OperacionException("Error al eliminar el estudio: " + e.getMessage(), e);
        }
    }
    
    public Tratamiento buscarTratamiento(long id) {
        return controlpersis.buscarTratamiento(id);
    }

    public void modificarTratamiento(long idTratamiento, String diagnostico, String descripcion) throws OperacionException {
        if (diagnostico.isEmpty() || descripcion.isEmpty()) {
            throw new OperacionException("El diagnóstico y la descripción son obligatorios.");
        }
        try {
            Tratamiento trat = controlpersis.buscarTratamiento(idTratamiento);
            if (trat != null) {
                trat.setDiagostico(diagnostico);
                trat.setTratamiento(descripcion);
                controlpersis.modificarTratamiento(trat);
            }
        } catch (Exception e) {
            throw new OperacionException("Error al modificar el tratamiento: " + e.getMessage(), e);
        }
    }

    public void eliminarTratamiento(long id) throws OperacionException {
        try {
            Tratamiento trat = controlpersis.buscarTratamiento(id);

            if (trat != null) {
                HistoriaClinica hc = trat.getHistoriaClinica();

                hc.getTratamientos().removeIf(t -> t.getidTratamiento() == id);

                this.modificarHistoriaClinica(hc);
            }
        } catch (Exception e) {
            throw new OperacionException("No se pudo eliminar el tratamiento.", e);
        }
    }
    public Tarea buscarTarea(long id) {
        return controlpersis.buscarTarea(id);
    }

    public void modificarTarea(modelo.Tarea tarea) throws OperacionException {
        try {
            controlpersis.modificarTarea(tarea);
        } catch (Exception e) {
            throw new OperacionException("Error al intentar modificar la tarea: " + e.getMessage(), e);
        }
    } 

    public void eliminarTarea(long id) throws OperacionException {
        try {
            controlpersis.eliminarTarea(id);
        } catch (Exception e) {
            throw new OperacionException("No se pudo eliminar la tarea. Verifique que no tenga datos asociados.", e);
        }
    }

    public void crearVisita(Visita visita) throws OperacionException {
        try {
            controlpersis.crearVisita(visita);
        } catch (Exception e) {
            throw new OperacionException("Error al crear la visita: " + e.getMessage(), e);
        }
    }

    public void modificarVisita(Visita visita) throws OperacionException {
        try {
            controlpersis.editarVisita(visita);
        } catch (Exception e) {
            throw new OperacionException("Error al modificar la visita: " + e.getMessage(), e);
        }
    }

    public FamiliaAdoptante buscarFamilia(int idFamilia) {
        return controlpersis.buscarFamilia(idFamilia);
    }
    
    public List<Postulacion> listarPostulacionesPorGato(long idGato) {
        return controlpersis.buscarPostulacionesPorGato(idGato);
    }

    public void aceptarPostulacion(long idPostulacion) throws OperacionException {
        try {
            // 1. Buscar la postulación que queremos aceptar
            Postulacion postulacionAceptada = controlpersis.buscarPostulacion(idPostulacion);
            if (postulacionAceptada == null) {
                throw new OperacionException("La postulación no existe");
            }

            // 2. Cambiar estado a APROBADA y guardar
            postulacionAceptada.setEstado(Postulacion.Estado.APROBADA);
            controlpersis.modificarPostulacion(postulacionAceptada);

            // 3. Obtener el ID del gato para buscar las otras postulaciones
            long idGato = postulacionAceptada.getGatoRelacionado().getIdGato();

            // Buscamos todas las postulaciones de este gato (se asume que este método trae las PENDIENTES)
            List<Postulacion> postulacionesDelGato = this.listarPostulacionesPorGato(idGato);
            
            if (postulacionesDelGato != null) {
                for (Postulacion p : postulacionesDelGato) {
                    // Verificamos que no sea la misma que acabamos de aceptar
                    if (p.getIdPostulacion() != idPostulacion) {
                        p.setEstado(Postulacion.Estado.RECHAZADA);
                        controlpersis.modificarPostulacion(p);
                    }
                }
            }

            // 4. Finalmente, asignar el gato a la familia (esto lo marca como NO disponible)
            this.asignarGatoAFamilia(
                idGato, 
                postulacionAceptada.getFamiliaPostulante().getIdUsuario()
            );

        } catch (Exception e) {
            throw new OperacionException("Error al aceptar la adopción: " + e.getMessage(), e);
        }
    }

    public void rechazarPostulacion(long idPostulacion) throws OperacionException {
        try {
            Postulacion postulacion = controlpersis.buscarPostulacion(idPostulacion);
            if(postulacion != null) {
                postulacion.setEstado(Postulacion.Estado.RECHAZADA);
                controlpersis.modificarPostulacion(postulacion);
            }
        } catch (Exception e) {
            throw new OperacionException("Error al rechazar: " + e.getMessage(), e);
        }
    }
    
    public Postulacion verificarPostulacionFamilia(int idFamilia, long idGato) {
        return controlpersis.buscarPostulacionPorFamiliaYGato(idFamilia, idGato);
    }
}