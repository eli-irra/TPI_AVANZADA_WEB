package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Reporte;

@WebServlet(name = "SvVerReporte", urlPatterns = {"/SvVerReporte"})
public class SvVerReporte extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener ID
            long id = Long.parseLong(request.getParameter("idReporte"));
            
            // 2. Buscar Reporte
            Reporte reporte = control.buscarReporte(id);
            
            // 3. Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("reporteDetalle", reporte);
            
            // 4. Ir a la vista
            response.sendRedirect("Reporte/verReporte.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvReportes");
        }
    }
}