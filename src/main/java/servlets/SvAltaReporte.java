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
import modelo.Administrador;
import modelo.Usuario;
import modelo.Zona;

@WebServlet(name = "SvAltaReporte", urlPatterns = {"/SvAltaReporte"})
public class SvAltaReporte extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Zona> zonas = control.traerTodasLasZonas();
            request.getSession().setAttribute("listaZonasReporte", zonas);
            response.sendRedirect("Reporte/registrarReporte.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvReportes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener usuario de la sesión
            HttpSession session = request.getSession();
            Usuario usu = (Usuario) session.getAttribute("usuarioLogueado");

            // Verificar que sea administrador
            if (usu == null || !usu.getRol().equals("ADMINISTRADOR")) {
                throw new Exception("No tiene permisos para realizar esta acción.");
            }

            // 2. Obtener datos del formulario
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String descripcion = request.getParameter("descripcion");
            String idZonaStr = request.getParameter("idZona");
            String nuevaZonaNombre = request.getParameter("nuevaZonaNombre");
            String nuevaZonaGPS = request.getParameter("nuevaZonaGPS");

            // 3. Llamar a la lógica pasando al usuario casteado a Administrador
            control.registrarReporte(cantidad, descripcion, idZonaStr, 
                                     nuevaZonaNombre, nuevaZonaGPS, (Administrador) usu);
            
            response.sendRedirect("SvReportes");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvAltaReporte");
        }
    }
}