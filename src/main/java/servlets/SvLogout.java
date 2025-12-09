package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SvLogout", urlPatterns = {"/SvLogout"})
public class SvLogout extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener la sesión actual (si existe)
        HttpSession session = request.getSession(false); // false = no crear si no existe
        
        if (session != null) {
            // 2. Invalidar la sesión (borrar usuario y datos)
            session.invalidate();
        }
        
        // 3. Redirigir al Login (index.jsp)
        // Usamos request.getContextPath() para asegurar que la ruta sea correcta desde cualquier subcarpeta
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // Permitimos cerrar sesión tanto por POST (botón del menú) como por GET (enlace directo)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}