package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEliminarZona", urlPatterns = {"/SvEliminarZona"})
public class SvEliminarZona extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idEliminar"));
            control.eliminarZona(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar zona: " + e.getMessage());
        }
        response.sendRedirect("SvZonas");
    }
}