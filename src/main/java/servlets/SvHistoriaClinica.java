package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Gato;
import modelo.HistoriaClinica;

@WebServlet(name = "SvHistoriaClinica", urlPatterns = {"/SvHistoriaClinica"})
public class SvHistoriaClinica extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener el ID del gato desde la URL
            int idGato = Integer.parseInt(request.getParameter("idGato"));
            
            // 2. Buscar el gato completo (que incluye su Historia Clínica por la relación 1:1)
            Gato gato = control.buscarGatoCompleto(idGato);
            HistoriaClinica historia = gato.getHistoriaClinica();
            
            // 3. Guardar en sesión para usarlo en el JSP
            HttpSession session = request.getSession();
            session.setAttribute("gatoActual", gato);
            session.setAttribute("historiaClinica", historia);
            
            // 4. Redirigir a la vista de la Historia Clínica
            response.sendRedirect("HistoriaClinica/verHistoriaClinica.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos"); // En caso de error, volver a la lista
        }
    }
}