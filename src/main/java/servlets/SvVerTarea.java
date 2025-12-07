package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Tarea;

@WebServlet(name = "SvVerTarea", urlPatterns = {"/SvVerTarea"})
public class SvVerTarea extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener ID
            long id = Long.parseLong(request.getParameter("idTarea"));
            
            // 2. Buscar Tarea
            Tarea tarea = control.buscarTarea(id);
            
            // 3. Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("tareaDetalle", tarea);
            
            // 4. Ir a la vista
            response.sendRedirect("Tarea/verTarea.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvTareas");
        }
    }
}
