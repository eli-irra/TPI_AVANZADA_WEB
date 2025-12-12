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
import modelo.FamiliaAdoptante;
import modelo.Gato;
import modelo.Postulacion;

@WebServlet(name = "SvPostulaciones", urlPatterns = {"/SvPostulaciones"})
public class SvPostulaciones extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Traer todas las postulaciones
            List<Postulacion> listaPostulaciones = control.traerTodasLasPostulaciones();
            
            // 2. Traer listas para el formulario manual (Gatos disponibles y Familias)
            List<Gato> gatosDisponibles = control.traerGatosDisponibles();
            List<FamiliaAdoptante> familias = control.traerTodasLasFamilias();

            // 3. Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("listaPostulaciones", listaPostulaciones);
            session.setAttribute("gatosDisponibles", gatosDisponibles);
            session.setAttribute("listaFamilias", familias);

            response.sendRedirect("Postulacion/postulaciones.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("menu.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion"); // "cambiarEstado" o "registroManual"
        
        try {
            if ("cambiarEstado".equals(accion)) {
                long id = Long.parseLong(request.getParameter("idPostulacion"));
                String estadoStr = request.getParameter("estado"); // "APROBADA" o "RECHAZADA"
                
                Postulacion.Estado nuevoEstado = Postulacion.Estado.valueOf(estadoStr);
                
                // La controladora se encarga de asignar el gato si es APROBADA
                control.cambiarEstadoPostulacion(id, nuevoEstado);
                
            } else if ("registroManual".equals(accion)) {
                // --- LÓGICA DE REGISTRO MANUAL ---
                long idGato = Long.parseLong(request.getParameter("idGato"));
                int idFamilia = Integer.parseInt(request.getParameter("idFamilia"));
                
                control.crearPostulacion(idGato, idFamilia);
            }
            
            // Recargar la página
            response.sendRedirect("SvPostulaciones");
            
        } catch (Exception e) {
            System.err.println("Error en SvPostulaciones: " + e.getMessage());
            // Podrías guardar un mensaje de error en sesión aquí
            response.sendRedirect("SvPostulaciones");
        }
    }
}