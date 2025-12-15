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

    // --- AGREGA ESTO ---
System.out.println("--- DEPURACIÓN LOGIN ---");
System.out.println("Correo recibido: " + correo);
System.out.println("Contraseña recibida: " + contrasena);

    try {
        // 2. Buscar el usuario
        Usuario usu = control.validarUsuario(correo, contrasena);
        System.out.println("Objeto Usuario encontrado: " + usu);
        // --- CORRECCIÓN AQUÍ ---
        // Verificamos si trajo algo o vino vacío
        if (usu != null) {
            // LOGIN EXITOSO
            HttpSession misesion = request.getSession();
            misesion.setAttribute("usuarioLogueado", usu);
            response.sendRedirect("SvGatos");
        } else {
            // LOGIN FALLIDO (Usuario null)
            request.getSession().setAttribute("error", "Usuario o contraseña incorrectos");
            response.sendRedirect("index.jsp");
        }
        
    } catch (Exception e) {
        // Error técnico (Base de datos caída, etc.)
        System.out.println("Error en login: " + e.getMessage()); // Mensaje para consola
        request.getSession().setAttribute("error", "Error técnico al iniciar sesión");
        response.sendRedirect("index.jsp");
    }
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
