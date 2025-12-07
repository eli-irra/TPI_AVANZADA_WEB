package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvAgregarEstudio", urlPatterns = {"/SvAgregarEstudio"})
public class SvAgregarEstudio extends HttpServlet {
    Controladora control = new Controladora();

    // GET: Muestra el formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idHistoria = request.getParameter("idHistoria");
        String idGato = request.getParameter("idGato");
        
        request.getSession().setAttribute("idHistoriaActual", idHistoria);
        request.getSession().setAttribute("idGatoActual", idGato);
        
        response.sendRedirect("Estudio/registrarEstudio.jsp");
    }

    // POST: Guarda el estudio
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombreEstudio");
            String descripcion = request.getParameter("descripcion");
            long idHistoria = Long.parseLong(request.getParameter("idHistoria"));
            String idGato = request.getParameter("idGato");
            
            control.agregarEstudioAHistoria(idHistoria, nombre, descripcion);
            
            response.sendRedirect("SvHistoriaClinica?idGato=" + idGato);
        } catch (Exception e) {
            response.sendRedirect("menu.jsp"); // O manejo de error
        }
    }
}