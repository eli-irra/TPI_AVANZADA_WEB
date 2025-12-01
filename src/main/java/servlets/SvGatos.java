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
import modelo.Gato;

@WebServlet(name = "SvGatos", urlPatterns = {"/SvGatos"})
public class SvGatos extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Gato> listaGatos = control.traerTodosLosGatos();
            
            // Lógica de Filtros
            String bNombre = request.getParameter("busquedaNombre");
            String bFamilia = request.getParameter("busquedaFamilia");
            
            if (listaGatos != null) {
                if (bNombre != null && !bNombre.isEmpty()) {
                    listaGatos = listaGatos.stream()
                        .filter(g -> g.getNombre().toLowerCase().contains(bNombre.toLowerCase()))
                        .collect(Collectors.toList());
                }
                if (bFamilia != null && !bFamilia.isEmpty()) {
                    listaGatos = listaGatos.stream()
                        .filter(g -> g.getFamiliaAdoptante() != null && 
                                     g.getFamiliaAdoptante().getNombre().toLowerCase().contains(bFamilia.toLowerCase()))
                        .collect(Collectors.toList());
                }
            }
            
            request.getSession().setAttribute("listaGatos", listaGatos);
            response.sendRedirect("Voluntario/gatos.jsp");
            
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
        }
    }
}
