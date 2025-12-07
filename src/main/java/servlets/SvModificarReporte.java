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

@WebServlet(name = "SvModificarReporte", urlPatterns = {"/SvModificarReporte"})
public class SvModificarReporte extends HttpServlet {

    Controladora control = new Controladora();

    // GET: Busca el reporte y lo manda al formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idEditar"));
            Reporte reporte = control.buscarReporte(id);
            
            HttpSession session = request.getSession();
            session.setAttribute("reporteEditar", reporte);
            
            response.sendRedirect("Reporte/editarReporte.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvReportes");
        }
    }

    // POST: Guarda los cambios
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idReporte"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String descripcion = request.getParameter("descripcion");
            
            control.modificarReporte(id, cantidad, descripcion);
            
            response.sendRedirect("SvReportes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}