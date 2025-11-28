package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import modelo.Gato;
import modelo.OperacionException;
import modelo.Zona;

@WebServlet(name = "SvAltaGato", urlPatterns = {"/SvAltaGato"})
public class SvAltaGato extends HttpServlet {
    
    Controladora control = new Controladora();

    // doGet: SE USA PARA MOSTRAR EL FORMULARIO CON LAS LISTAS CARGADAS
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Traer las listas necesarias de la BD
        List<Zona> listaZonas = null;
        try {
            listaZonas = control.traerTodasLasZonas();
            // List<Voluntario> listaVoluntarios = control.traerVoluntarios(); // Si aplica
        } catch (OperacionException ex) {
            System.getLogger(SvAltaGato.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        // 2. Ponerlas en sesión para que el JSP las vea
        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaZonas", listaZonas);
        
        // 3. Redirigir al formulario
        response.sendRedirect("Voluntario/registrarGato.jsp");
    }

    // doPost: SE USA PARA GUARDAR EL GATO (Lo que ya sabes hacer)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Obtener datos del formulario (Strings)
            String nombre = request.getParameter("nombre");
            String apodo = request.getParameter("apodo");
            String raza = request.getParameter("raza");
            String color = request.getParameter("color");
            String sexo = request.getParameter("sexo");
            String edadStr = request.getParameter("edad");
            String observaciones = request.getParameter("observaciones");
            String idZonaStr = request.getParameter("zona_id");
            
            // 2. Convertir datos (Parsing)
            int edad = (edadStr != null && !edadStr.isEmpty()) ? Integer.parseInt(edadStr) : 0;
            int idZona = Integer.parseInt(idZonaStr);
            
            // 3. Buscar la Zona completa en la BD (Necesario para la relación)
            Zona zonaSeleccionada = control.traerZona(idZona); // Asegúrate de tener este método en Controladora
            
            // 4. Crear el Objeto Gato
            Gato nuevoGato = new Gato();
            nuevoGato.setNombre(nombre);
            nuevoGato.setApodo(apodo);
            nuevoGato.setRaza(raza);
            nuevoGato.setColor(color);
            nuevoGato.setSexo(sexo);
            nuevoGato.setEdad(edad);
            nuevoGato.setObservaciones(observaciones);
            nuevoGato.setDisponible(true); // Por defecto disponible
            nuevoGato.setZona(zonaSeleccionada); // Asignamos la relación
            
            // 5. Llamar a la Controladora para guardar
            // Nota: El método crearGato debería encargarse de crear la HistoriaClinica vacía internamente
            control.crearGato(nuevoGato);
            
            // 6. Redirigir a la lista de gatos
            response.sendRedirect("SvGatos");
            
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, podrías redirigir a una página de error o volver al form
            response.sendError(500, "Error al guardar el gato: " + e.getMessage());
        }
    }
}