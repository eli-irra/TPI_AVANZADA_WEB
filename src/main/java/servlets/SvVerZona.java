package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Zona;

@WebServlet(name = "SvVerZona", urlPatterns = {"/SvVerZona"})
public class SvVerZona extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idZona"));
            Zona zona = control.buscarZona(id); // Asegúrate de que este método en Controladora traiga también la lista de gatos si usas Lazy Loading
            
            request.getSession().setAttribute("zonaDetalle", zona);
            response.sendRedirect("Zona/verZona.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvZonas");
        }
    }
}