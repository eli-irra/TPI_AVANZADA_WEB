package servlets;

import controladora.Controladora;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.OperacionException;
import modelo.Reporte;

@WebServlet(name = "SvReportes", urlPatterns = {"/SvReportes"})
public class SvReportes extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            // 1. Traer lista de reportes
            List<Reporte> listaReportes = null;
        try {
            listaReportes = control.traerTodosLosReportes();
        } catch (OperacionException ex) {
            System.getLogger(SvReportes.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            
            // 2. Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("listaReportes", listaReportes);
            
            // 3. Redirigir a la vista
            response.sendRedirect("Reporte/reportes.jsp");
            
        
    }
}