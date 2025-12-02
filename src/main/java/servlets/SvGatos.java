package servlets;

import controladora.Controladora;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Gato;

@WebServlet(name = "SvGatos", urlPatterns = {"/SvGatos"})
public class SvGatos extends HttpServlet {
    
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener lista completa
            List<Gato> listaGatos = control.traerTodosLosGatos();
            
            // 2. Filtrado (Opcional, si usas el buscador del JSP)
            String bNombre = request.getParameter("busquedaNombre");
            if (listaGatos != null && bNombre != null && !bNombre.isEmpty()) {
                listaGatos = listaGatos.stream()
                    .filter(g -> g.getNombre().toLowerCase().contains(bNombre.toLowerCase()))
                    .collect(Collectors.toList());
            }
            
            // 3. Guardar en sesión y redirigir a la carpeta Gato
            HttpSession session = request.getSession();
            session.setAttribute("listaGatos", listaGatos);
            response.sendRedirect("Gato/gatos.jsp");
            
        } catch (Exception e) {
            // Si falla, redirigir al menú principal o error
            response.sendRedirect("menu.jsp");
        }
    }
}