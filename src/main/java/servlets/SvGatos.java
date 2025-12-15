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
    
    // Lista vacía por defecto para que no falle el JSP
    List<Gato> listaGatos = new java.util.ArrayList<>(); 
    
    try {
        // Intentamos traer los gatos
        listaGatos = control.traerTodosLosGatos();
        
        // Filtro de búsqueda (tu código original)
        String bNombre = request.getParameter("busquedaNombre");
        if (listaGatos != null && bNombre != null && !bNombre.isEmpty()) {
            listaGatos = listaGatos.stream()
                .filter(g -> g.getNombre().toLowerCase().contains(bNombre.toLowerCase()))
                .collect(Collectors.toList());
        }
        
    } catch (Exception e) {
        // SI FALLA (porque no hay gatos), NO HACEMOS NADA MALO.
        // Solo avisamos en consola y seguimos adelante con la lista vacía.
        System.out.println("Advertencia: " + e.getMessage());
    }

    // --- ESTO AHORA SE EJECUTA SIEMPRE, HAYA GATOS O NO ---
    HttpSession session = request.getSession();
    session.setAttribute("listaGatos", listaGatos);
    response.sendRedirect("Gato/gatos.jsp");
}
}