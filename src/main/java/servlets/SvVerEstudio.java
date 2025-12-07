package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Estudio;

@WebServlet(name = "SvVerEstudio", urlPatterns = {"/SvVerEstudio"})
public class SvVerEstudio extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long idEstudio = Long.parseLong(request.getParameter("idEstudio"));
        String idGato = request.getParameter("idGato");
        
        Estudio estudio = control.buscarEstudio(idEstudio);
        
        request.getSession().setAttribute("estudioDetalle", estudio);
        request.getSession().setAttribute("idGatoVolver", idGato);
        
        response.sendRedirect("Estudio/verEstudio.jsp");
    }
}