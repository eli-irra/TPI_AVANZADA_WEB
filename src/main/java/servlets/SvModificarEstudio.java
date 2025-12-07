package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Estudio;

@WebServlet(name = "SvModificarEstudio", urlPatterns = {"/SvModificarEstudio"})
public class SvModificarEstudio extends HttpServlet {
    Controladora control = new Controladora();

    // GET: Prepara el formulario de edición
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long idEstudio = Long.parseLong(request.getParameter("idEditar"));
        String idGato = request.getParameter("idGato");
        
        Estudio estudio = control.buscarEstudio(idEstudio);
        request.getSession().setAttribute("estudioEditar", estudio);
        request.getSession().setAttribute("idGatoVolver", idGato);
        
        response.sendRedirect("Estudio/editarEstudio.jsp");
    }

    // POST: Guarda los cambios
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long idEstudio = Long.parseLong(request.getParameter("idEstudio"));
            String nombre = request.getParameter("nombreEstudio");
            String descripcion = request.getParameter("descripcion");
            String idGato = request.getParameter("idGato");
            
            control.modificarEstudio(idEstudio, nombre, descripcion);
            
            // Vuelve al detalle del estudio o al perfil del gato
            response.sendRedirect("SvHistoriaClinica?idGato=" + idGato);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
