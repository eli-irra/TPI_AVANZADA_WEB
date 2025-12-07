package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Tratamiento;

@WebServlet(name = "SvVerTratamiento", urlPatterns = {"/SvVerTratamiento"})
public class SvVerTratamiento extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long idTratamiento = Long.parseLong(request.getParameter("idTratamiento"));
        String idGato = request.getParameter("idGato");
        
        Tratamiento trat = control.buscarTratamiento(idTratamiento);
        
        request.getSession().setAttribute("tratamientoDetalle", trat);
        request.getSession().setAttribute("idGatoVolver", idGato);
        
        response.sendRedirect("Tratamiento/verTratamiento.jsp");
    }
}