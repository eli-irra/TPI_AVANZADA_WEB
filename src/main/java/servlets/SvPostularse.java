package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Esta anotación es la que "conecta" la URL con este archivo
@WebServlet(name = "SvPostularse", urlPatterns = {"/SvPostularse"})
public class SvPostularse extends HttpServlet {

    // Se ejecuta cuando envías datos por formulario (method="POST")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Aquí capturarías los datos del formulario, por ejemplo:
        // String nombre = request.getParameter("nombre");
        
        // 2. Aquí llamarías a tu lógica (Controladora)
        
        // 3. Finalmente rediriges al usuario a una página de éxito o error
        response.sendRedirect("index.jsp"); // O la página que quieras mostrar después
    }

    // Se ejecuta si intentas entrar escribiendo la URL directamente en el navegador
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Por ahora redirigimos al inicio si alguien intenta entrar por GET
        response.sendRedirect("index.jsp");
    }
}