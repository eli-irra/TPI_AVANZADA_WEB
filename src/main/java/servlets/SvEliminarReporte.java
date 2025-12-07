package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEliminarReporte", urlPatterns = {"/SvEliminarReporte"})
public class SvEliminarReporte extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idEliminar"));
            control.eliminarReporte(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("SvReportes");
    }
}