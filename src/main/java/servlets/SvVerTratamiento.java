package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modelo.Tratamiento;

@WebServlet(name = "SvVerTratamiento", urlPatterns = {"/SvVerTratamiento"})
public class SvVerTratamiento extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener el ID desde el formulario
            String idStr = request.getParameter("idTratamiento");
            String idGatoStr = request.getParameter("idGato");
            
            if (idStr != null && !idStr.isEmpty()) {
                long id = Long.parseLong(idStr);
                
                // 2. Buscar el objeto en la BD
                Tratamiento trat = control.buscarTratamiento(id);
                
                // 3. Guardar en la sesión con un nombre CLARO
                HttpSession session = request.getSession();
                session.setAttribute("tratamientoActual", trat);
                session.setAttribute("idGatoVolver", idGatoStr);
                
                // 4. Redirigir al JSP
                response.sendRedirect("Tratamiento/verTratamiento.jsp");
            } else {
                response.sendRedirect("menu.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("menu.jsp");
        }
    }
}