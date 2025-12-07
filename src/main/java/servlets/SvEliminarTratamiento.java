package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEliminarTratamiento", urlPatterns = {"/SvEliminarTratamiento"})
public class SvEliminarTratamiento extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idEliminar"));
            String idGato = request.getParameter("idGato");
            
            control.eliminarTratamiento(id);
            
            response.sendRedirect("SvHistoriaClinica?idGato=" + idGato);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("menu.jsp");
        }
    }
}