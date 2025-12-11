package servlets;
import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.RegistroException;

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
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String matricula = request.getParameter("matricula");
        
        try {
            control.registrarUsuarioPorRol(nombre, correo, contrasenia,  telefono, direccion, matricula, rol);
        } catch (RegistroException ex) {
            System.getLogger(SvAltaUsuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
        // 3. Redireccionar para actualizar la tabla
        response.sendRedirect("SvUsuarios"); 
    }
}
