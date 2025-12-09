package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Zona;

@WebServlet(name = "SvModificarZona", urlPatterns = {"/SvModificarZona"})
public class SvModificarZona extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("idEditar"));
        Zona z = control.buscarZona(id);
        request.getSession().setAttribute("zonaEditar", z);
        response.sendRedirect("Zona/editarZona.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idZona"));
            String nombre = request.getParameter("nombreZona");
            String gps = request.getParameter("ubicacionGPS");
            
            control.modificarZona(id, nombre, gps);
            response.sendRedirect("SvZonas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}