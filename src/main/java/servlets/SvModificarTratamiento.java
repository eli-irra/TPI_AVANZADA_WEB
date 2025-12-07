package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Tratamiento;

@WebServlet(name = "SvModificarTratamiento", urlPatterns = {"/SvModificarTratamiento"})
public class SvModificarTratamiento extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("idEditar"));
        String idGato = request.getParameter("idGato");
        
        Tratamiento trat = control.buscarTratamiento(id);
        request.getSession().setAttribute("tratamientoEditar", trat);
        request.getSession().setAttribute("idGatoVolver", idGato);
        
        response.sendRedirect("Tratamiento/modificarTratamiento.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idTratamiento"));
            String diag = request.getParameter("diagnostico");
            String desc = request.getParameter("descripcion");
            String idGato = request.getParameter("idGato");
            
            control.modificarTratamiento(id, diag, desc);
            
            response.sendRedirect("SvHistoriaClinica?idGato=" + idGato);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}