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
import modelo.Gato;
import modelo.OperacionException;

@WebServlet(name = "SvGatos", urlPatterns = {"/SvGatos"})
public class SvGatos extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener lista de gatos
        List<Gato> listaGatos = null;
        try {
            listaGatos = control.traerTodosLosGatos();
        } catch (OperacionException ex) {
            System.getLogger(SvGatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        // 2. Guardar en sesión
        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaGatos", listaGatos);
        
        // 3. Redirigir a la pantalla
        response.sendRedirect("Voluntario/gatos.jsp");
    }
}
