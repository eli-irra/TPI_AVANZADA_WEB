package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEliminarUsuario", urlPatterns = {"/SvEliminarUsuario"})
public class SvEliminarUsuario extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener el ID que viene oculto en el formulario de la tabla
        String idStr = request.getParameter("idEliminar");
        
        try {
            int id = Integer.parseInt(idStr);
            
            // 2. Llamar a la controladora para eliminar
            control.eliminarUsuario(id);
            
        } catch (Exception e) {
            // Podrías guardar el error en sesión para mostrarlo en la pantalla
            System.err.println("Error al eliminar: " + e.getMessage());
        }
        
        // 3. Redirigir de nuevo a la lista (refrescar)
        response.sendRedirect("SvUsuarios");
    }
}