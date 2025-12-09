package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvAltaZona", urlPatterns = {"/SvAltaZona"})
public class SvAltaZona extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombreZona");
            String gps = request.getParameter("ubicacionGPS");
            
            control.registrarZona(nombre, gps);
            response.sendRedirect("SvZonas");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Zona/registrarZona.jsp");
        }
    }
}