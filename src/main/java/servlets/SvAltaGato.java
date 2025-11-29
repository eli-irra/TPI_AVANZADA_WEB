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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            String raza = request.getParameter("raza");
            String color = request.getParameter("color");
            String sexo = request.getParameter("sexo");
            String caracteristicas = request.getParameter("caracteristicas"); // Usamos este campo existente
            String idZonaStr = request.getParameter("zona_id");
            
            // 2. Convertir y Validar
            int idZona = Integer.parseInt(idZonaStr);
            
            // 3. Buscar la Zona (usando el método puente que agregaremos en Controladora)
            Zona zonaSeleccionada = control.traerZona(idZona);
            
            // 4. Crear el Gato con los datos QUE SÍ EXISTEN en tu modelo
            Gato nuevoGato = new Gato();
            nuevoGato.setNombre(nombre);
            nuevoGato.setRaza(raza);
            nuevoGato.setColor(color);
            nuevoGato.setSexo(sexo);
            nuevoGato.setCaracteristicas(caracteristicas);
            
            // Valores por defecto para los Enums (necesarios para que no sea null)
            nuevoGato.setDisponible(Gato.RespuestaBinaria.SI);
            nuevoGato.setEsterilizado(Gato.RespuestaBinaria.NO); 
            nuevoGato.setestadoFisico(Gato.EstadoSalud.SANO);
            
            nuevoGato.setZona(zonaSeleccionada);
            
            // 5. Guardar
            control.crearGato(nuevoGato);
            
            response.sendRedirect("SvGatos");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error: " + e.getMessage());
        }
    }
}