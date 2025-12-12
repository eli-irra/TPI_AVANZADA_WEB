package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modelo.OperacionException;

@WebServlet(name = "SvPostularse", urlPatterns = {"/SvPostularse"})
public class SvPostularse extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idGatoStr = request.getParameter("idGato");
        
        try {
            // 1. Obtener los datos del formulario (vienen ocultos en el JSP)
            String idFamiliaStr = request.getParameter("idFamilia");
            
            // Validar que no lleguen nulos
            if (idGatoStr == null || idFamiliaStr == null) {
                throw new Exception("Error: Faltan datos para procesar la solicitud.");
            }
            
            long idGato = Long.parseLong(idGatoStr);
            int idFamilia = Integer.parseInt(idFamiliaStr);
            
            // 2. Llamar a la Controladora
            // Este método ya verifica si el gato está disponible y si la familia ya se postuló
            control.crearPostulacion(idGato, idFamilia);
            
            // 3. Manejo de Éxito
            HttpSession session = request.getSession();
            session.setAttribute("mensajeExito", "¡Te has postulado con éxito!");
            
            // Redirigimos de vuelta al perfil del gato para que vea el cambio de estado
            // Nota: SvVerPerfilGato usa el parámetro 'idVer'
            response.sendRedirect("SvVerPerfilGato?idVer=" + idGatoStr);
            
        } catch (OperacionException e) {
            // 4. Manejo de Errores de Lógica (Ej: "Ya te has postulado", "Gato no disponible")
            System.err.println("Error de lógica: " + e.getMessage());
            
            // Podrías guardar el error en sesión para mostrarlo en el JSP
            request.getSession().setAttribute("error", e.getMessage());
            
            // Volvemos al perfil del gato
            response.sendRedirect("SvVerPerfilGato?idVer=" + idGatoStr);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos"); // En caso de fallo grave, volver a la lista general
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // No se permite acceso por GET directo, redirigimos al listado
        response.sendRedirect("SvGatos");
    }
}