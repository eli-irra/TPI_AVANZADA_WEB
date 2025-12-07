package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;

@WebServlet(name = "SvVerUsuario", urlPatterns = {"/SvVerUsuario"})
public class SvVerUsuario extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener ID
            int id = Integer.parseInt(request.getParameter("idUsuario"));
            
            // 2. Buscar Usuario (Usamos buscarUsuario que ya tienes en la Controladora)
            Usuario usu = control.buscarUsuario(id);
            
            // 3. Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuarioDetalle", usu);
            
            // 4. Redirigir a la vista
            response.sendRedirect("Usuario/verUsuario.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvUsuarios");
        }
    }
}