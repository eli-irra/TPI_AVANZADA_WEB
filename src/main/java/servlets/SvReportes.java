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
import java.util.ArrayList; // Importante para crear la lista vacía
import modelo.Reporte;

@WebServlet(name = "SvReportes", urlPatterns = {"/SvReportes"})
public class SvReportes extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Inicializamos la lista vacía para asegurar que siempre exista
        List<Reporte> listaReportes = new ArrayList<>();

        try {
            // 2. Intentamos traer los datos de la BD
            listaReportes = control.traerTodosLosReportes();
            
        } catch (Exception e) {
            // 3. Si falla (porque está vacío), solo avisamos en consola
            System.out.println("Aviso: No hay reportes cargados aún.");
        }
        
        // 4. Guardamos la lista (llena o vacía) en la sesión
        HttpSession session = request.getSession();
        session.setAttribute("listaReportes", listaReportes);
        
        // 5. SIEMPRE vamos a la vista de reportes
        response.sendRedirect("Reporte/reportes.jsp");
    }
}