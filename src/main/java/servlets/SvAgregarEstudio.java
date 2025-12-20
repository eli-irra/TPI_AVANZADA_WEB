package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SvAgregarEstudio", urlPatterns = {"/SvAgregarEstudio"})
public class SvAgregarEstudio extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idGatoStr = request.getParameter("idGato");
        String idHistoriaStr = request.getParameter("idHistoria");

        try {
            // 1. Validaciones
            if (idHistoriaStr == null || idHistoriaStr.isEmpty() || 
                idGatoStr == null || idGatoStr.isEmpty()) {
                throw new Exception("Error: Faltan IDs para asociar el estudio.");
            }

            long idHistoria = Long.parseLong(idHistoriaStr);
            long idGato = Long.parseLong(idGatoStr);
            
            String nombreEstudio = request.getParameter("nombreEstudio");
            String resultado = request.getParameter("resultado"); // Asegúrate que coincida con el 'name' del JSP

            // 2. Guardar
            control.agregarEstudioAHistoria(idHistoria, nombreEstudio, resultado);

            // 3. Redirigir de vuelta al DETALLE de la historia (no al menú)
            response.sendRedirect("SvVerHistoriaClinica?idHistoria=" + idHistoria + "&idGato=" + idGato);

        } catch (Exception e) {
            e.printStackTrace();
            // Si falla, intentamos volver a la lista de historias del gato
            if (idGatoStr != null && !idGatoStr.isEmpty()) {
                 response.sendRedirect("SvHistoriasClinicas?idGato=" + idGatoStr);
            } else {
                 response.sendRedirect("SvGatos");
            }
        }
    }
}