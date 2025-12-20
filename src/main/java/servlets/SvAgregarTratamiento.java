package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SvAgregarTratamiento", urlPatterns = {"/SvAgregarTratamiento"})
public class SvAgregarTratamiento extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Variables para re-direccionamiento en caso de error
        String idGatoStr = request.getParameter("idGato");
        String idHistoriaStr = request.getParameter("idHistoria");

        try {
            // 1. Validar parámetros obligatorios
            if (idHistoriaStr == null || idHistoriaStr.isEmpty() || 
                idGatoStr == null || idGatoStr.isEmpty()) {
                throw new Exception("IDs faltantes en la solicitud.");
            }

            long idHistoria = Long.parseLong(idHistoriaStr);
            long idGato = Long.parseLong(idGatoStr);
            
            String diagnostico = request.getParameter("diagnostico");
            String descripcion = request.getParameter("descripcion"); // O 'tratamiento' según tu input

            // 2. Llamar a la controladora
            control.agregarTratamientoAHistoria(idHistoria, diagnostico, descripcion);

            // 3. ÉXITO: Volver a ver la Historia Clínica específica
            // Usamos SvVerHistoriaClinica para que recargue los datos actualizados
            response.sendRedirect("SvVerHistoriaClinica?idHistoria=" + idHistoria + "&idGato=" + idGato);

        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error real en la consola del servidor
            
            // ERROR: Si tenemos el ID del gato, intentamos volver a su perfil o lista
            if (idGatoStr != null && !idGatoStr.isEmpty()) {
                response.sendRedirect("SvHistoriasClinicas?idGato=" + idGatoStr);
            } else {
                response.sendRedirect("SvGatos");
            }
        }
    }
}