package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList; // Agregado
import java.util.List;
import modelo.Tarea;

@WebServlet(name = "SvTareas", urlPatterns = {"/SvTareas"})
public class SvTareas extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Tarea> listaTareas = new ArrayList<>();
        
        try {
            // Intentamos traer las tareas
            listaTareas = control.traerTodasLasTareas();
            
        } catch (Exception e) {
            System.out.println("Aviso en SvTareas: " + e.getMessage());
        }
        
        // Guardamos la lista (llena o vacía) en sesión
        HttpSession session = request.getSession();
        session.setAttribute("listaTareas", listaTareas);
        
        // ¡IMPORTANTE! Redirigimos al JSP pase lo que pase
        response.sendRedirect("Tarea/tareas.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}