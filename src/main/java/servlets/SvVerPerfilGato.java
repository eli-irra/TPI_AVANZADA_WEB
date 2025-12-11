package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Gato;

@WebServlet(name = "SvVerPerfilGato", urlPatterns = {"/SvVerPerfilGato"})
public class SvVerPerfilGato extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener ID
            int id = Integer.parseInt(request.getParameter("idVer"));
            
            // 2. Buscar Gato Completo
            Gato gato = control.buscarGatoCompleto(id);
            
            // 3. Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("gatoPerfil", gato);
            
            // 4. Ir a la vista
            response.sendRedirect("Gato/perfilGato.jsp");
            
        } catch (Exception e) {
            // Si algo falla, volvemos a la lista
            e.printStackTrace();
            response.sendRedirect("SvGatos");
        }
    }
}