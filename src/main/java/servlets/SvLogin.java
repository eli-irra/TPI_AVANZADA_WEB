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

@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

    // Instanciamos la controladora globalmente
    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener datos del JSP
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        try {
            // 2. Usar tu método existente validarUsuario
            Usuario usu = control.validarUsuario(correo, contrasena);
            
            // 3. Crear sesión y guardar el usuario
            HttpSession misesion = request.getSession();
            misesion.setAttribute("usuarioLogueado", usu);
            
            // 4. Redirigir al menú principal (crearemos este archivo luego)
            response.sendRedirect("menu.jsp");
            
        } catch (Exception e) {
            // Si falla (contraseña mal, usuario no existe)
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
