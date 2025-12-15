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
import modelo.Zona;

@WebServlet(name = "SvZonas", urlPatterns = {"/SvZonas"})
public class SvZonas extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Creamos una lista vacía por defecto
        List<Zona> listaZonas = new java.util.ArrayList<>();
        
        try {
            // 2. Intentamos llenar la lista desde la base de datos
            listaZonas = control.traerTodasLasZonas();
            
        } catch (Exception e) {
            // 3. Si falla (porque no hay zonas), NO hacemos nada malo.
            // Solo avisamos en la consola para que tú lo sepas.
            System.out.println("Aviso: No se encontraron zonas o hubo un error: " + e.getMessage());
            // La lista se queda vacía (new ArrayList), pero el programa sigue.
        }
        
        // 4. Guardamos la lista (llena o vacía) en la sesión
        HttpSession session = request.getSession();
        session.setAttribute("listaZonas", listaZonas);
        
        // 5. SIEMPRE redirigimos al JSP de zonas, nunca al menú
        response.sendRedirect("Zona/zonas.jsp");
    }
}