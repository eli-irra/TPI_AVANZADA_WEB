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

@WebServlet(name = "SvModificarUsuario", urlPatterns = {"/SvModificarUsuario"})
public class SvModificarUsuario extends HttpServlet {

    Controladora control = new Controladora();

    // 1. DO GET: Se usa para TRAER el usuario y mostrarlo en el formulario de edición
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idStr = request.getParameter("idEditar");
        int id = Integer.parseInt(idStr);
        
        // Buscamos al usuario por ID
        Usuario usu = control.buscarUsuario(id);
        
        // Lo guardamos en sesión para usarlo en el JSP de edición
        HttpSession misesion = request.getSession();
        misesion.setAttribute("usuEditar", usu);
        
        // Redirigimos a la pantalla de edición
        response.sendRedirect("editarUsuario.jsp");
    }

    // 2. DO POST: Se usa para GUARDAR los cambios realizados
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Obtenemos los datos nuevos del formulario
            String idStr = request.getParameter("idUsuario"); // Ojo: campo oculto o read-only
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefonoStr = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");

            // Convertimos datos
            int id = Integer.parseInt(idStr);
            double telefono = Double.parseDouble(telefonoStr);

            // Buscamos el objeto original en BD para no perder datos como la contraseña o rol
            Usuario usu = control.buscarUsuario(id);
            
            // Actualizamos solo los campos permitidos
            usu.setNombre(nombre);
            usu.setCorreo(correo);
            usu.setTelefono(telefono);
            usu.setdireccion(direccion); // Tu método se llama setdireccion (minúscula)
            
            // Persistir cambios
            control.modificarUsuario(usu);
            
            // Volver a la lista
            response.sendRedirect("SvUsuarios");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Opcional
        }
    }
}