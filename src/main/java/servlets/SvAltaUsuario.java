package servlets;
import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvAltaUsuario", urlPatterns = {"/SvAltaUsuario"})
public class SvAltaUsuario extends HttpServlet {
    
    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener datos del JSP
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasena");
        String rol = request.getParameter("rol");
        
        // 2. Llamar a la lógica
        // NOTA: Ajusta los parámetros según tu método crearUsuario real
        // control.crearUsuario(nombre, correo, contrasenia, rol, ...); 
        
        // 3. Redireccionar para actualizar la tabla
        response.sendRedirect("SvUsuarios"); 
    }
}
