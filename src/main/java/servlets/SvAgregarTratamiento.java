package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SvAgregarTratamiento", urlPatterns = {"/SvAgregarTratamiento"})
public class SvAgregarTratamiento extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recibe IDs para volver al gato correcto
        String idHistoria = request.getParameter("idHistoria");
        String idGato = request.getParameter("idGato");
        
        request.getSession().setAttribute("idHistoriaActual", idHistoria);
        request.getSession().setAttribute("idGatoActual", idGato);
        
        response.sendRedirect("Tratamiento/registrarTratamiento.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String diagnostico = request.getParameter("diagnostico");
            String descripcion = request.getParameter("descripcion");
            long idHistoria = Long.parseLong(request.getParameter("idHistoria"));
            String idGato = request.getParameter("idGato");
            
            control.agregarTratamientoAHistoria(idHistoria, diagnostico, descripcion);
            
            response.sendRedirect("SvHistoriaClinica?idGato=" + idGato);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("menu.jsp");
        }
    }
}