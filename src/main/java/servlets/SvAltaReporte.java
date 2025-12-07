package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvAltaReporte", urlPatterns = {"/SvAltaReporte"})
public class SvAltaReporte extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener datos
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String descripcion = request.getParameter("descripcion");
            
            // 2. Guardar (La fecha se pone automática en el método de la controladora)
            control.registrarReporte(cantidad, descripcion);
            
            // 3. Redirigir
            response.sendRedirect("SvReportes");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Reporte/registrarReporte.jsp");
        }
    }
}