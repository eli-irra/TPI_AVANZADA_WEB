package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "SvAltaHistoriaClinica", urlPatterns = {"/SvAltaHistoriaClinica"})
public class SvAltaHistoriaClinica extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idGatoStr = request.getParameter("idGato");
        
        try {
            if (idGatoStr == null || idGatoStr.isEmpty()) {
                throw new Exception("ID de Gato no recibido");
            }

            long idGato = Long.parseLong(idGatoStr);
            
            // Creamos una descripción por defecto con la fecha
            String motivoDefault = "Consulta del " + LocalDate.now().toString();
            
            // Llamamos a la controladora
            control.crearNuevaHistoriaClinica(idGato, motivoDefault);
            
            // Redirigimos a la lista de historias (SvHistoriasClinicas)
            response.sendRedirect("SvHistoriasClinicas?idGato=" + idGato);
            
        } catch (Exception e) {
            e.printStackTrace();
            if (idGatoStr != null) {
                response.sendRedirect("SvVerPerfilGato?idVer=" + idGatoStr);
            } else {
                response.sendRedirect("SvGatos");
            }
        }
    }
}