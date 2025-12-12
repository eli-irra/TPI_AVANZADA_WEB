package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import modelo.Reporte;
import modelo.Zona;

@WebServlet(name = "SvModificarReporte", urlPatterns = {"/SvModificarReporte"})
public class SvModificarReporte extends HttpServlet {

    Controladora control = new Controladora();

    // GET: Prepara el formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idEditar"));
            Reporte reporte = control.buscarReporte(id);
            
            List<Zona> zonas = control.traerTodasLasZonas();
            
            HttpSession session = request.getSession();
            session.setAttribute("reporteEditar", reporte);
            session.setAttribute("listaZonasReporte", zonas); // Guardamos la lista
            
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
            
            // Nuevos datos de Zona
            String idZonaStr = request.getParameter("idZona");
            String nuevaZonaNombre = request.getParameter("nuevaZonaNombre");
            String nuevaZonaGPS = request.getParameter("nuevaZonaGPS");
            
            control.modificarReporte(id, cantidad, descripcion, idZonaStr, nuevaZonaNombre, nuevaZonaGPS);
            
            response.sendRedirect("SvReportes");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvReportes");
        }
    }
}