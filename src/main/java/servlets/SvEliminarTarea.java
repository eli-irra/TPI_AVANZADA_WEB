package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEliminarTarea", urlPatterns = {"/SvEliminarTarea"})
public class SvEliminarTarea extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Obtener el ID oculto desde el formulario
            String idStr = request.getParameter("idEliminar");
            
            // 2. Validar y convertir
            if (idStr != null && !idStr.isEmpty()) {
                long id = Long.parseLong(idStr);
                
                // 3. Eliminar usando la controladora
                control.eliminarTarea(id);
            }
            
        } catch (Exception e) {
            // Si hay error (ej: ID inválido o problema en BD), lo mostramos en consola
            System.err.println("Error al eliminar la tarea: " + e.getMessage());
            e.printStackTrace();
        }
        
        // 4. SIEMPRE volver a la lista actualizada
        response.sendRedirect("SvTareas");
    }
}