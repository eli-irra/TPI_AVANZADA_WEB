package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEliminarEstudio", urlPatterns = {"/SvEliminarEstudio"})
public class SvEliminarEstudio extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long idEstudio = Long.parseLong(request.getParameter("idEliminar"));
            String idGato = request.getParameter("idGato"); // Para saber a dónde volver
            
            control.eliminarEstudio(idEstudio);
            
            response.sendRedirect("SvHistoriaClinica?idGato=" + idGato);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}