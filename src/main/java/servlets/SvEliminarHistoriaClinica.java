package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SvEliminarHistoriaClinica", urlPatterns = {"/SvEliminarHistoriaClinica"})
public class SvEliminarHistoriaClinica extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long idHistoria = Long.parseLong(request.getParameter("idHistoria"));
            long idGato = Long.parseLong(request.getParameter("idGato"));

            // Necesitas agregar este método en tu Controladora
            control.eliminarHistoriaClinica(idHistoria);
            
            // Recargamos la lista pasando el idGato
            response.sendRedirect("SvHistoriaClinica?idGato=" + idGato);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("menu.jsp");
        }
    }
}