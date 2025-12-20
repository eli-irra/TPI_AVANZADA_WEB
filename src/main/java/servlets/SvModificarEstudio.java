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

    // GET: Prepara el formulario (Esto ya lo tenías bien, solo revisa la redirección final)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long idEstudio = Long.parseLong(request.getParameter("idEditar"));
            String idGato = request.getParameter("idGato");
            
            Estudio estudio = control.buscarEstudio(idEstudio);
            request.getSession().setAttribute("estudioEditar", estudio);
            request.getSession().setAttribute("idGatoVolver", idGato);
            
            response.sendRedirect("Estudio/editarEstudio.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvGatos");
        }
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
            
            // 1. Buscamos el estudio para obtener el ID de la Historia Clínica
            Estudio e = control.buscarEstudio(idEstudio);
            long idHistoria = e.getHistoriaClinica().getidHistoria();
            
            // 2. Modificamos
            control.modificarEstudio(idEstudio, nombre, descripcion);
            
            // 3. Redirigimos al DETALLE de la Historia Clínica
            response.sendRedirect("SvVerHistoriaClinica?idHistoria=" + idHistoria + "&idGato=" + idGato);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos");
        }
    }
}