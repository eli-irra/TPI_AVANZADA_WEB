package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Gato;
import modelo.HistoriaClinica;

@WebServlet(name = "SvHistoriasClinicas", urlPatterns = {"/SvHistoriasClinicas"})
public class SvHistoriasClinicas extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Recibimos el ID del Gato desde el Perfil
            String idGatoStr = request.getParameter("idGato");
            
            if (idGatoStr == null || idGatoStr.isEmpty()) {
                response.sendRedirect("SvGatos"); // Si no hay ID, volvemos a la lista general
                return;
            }

            long idGato = Long.parseLong(idGatoStr);
            
            // 2. Buscamos el Gato completo
            Gato gato = control.buscarGatoCompleto(idGato);
            
            // 3. Obtenemos su lista de consultas (Historias Clínicas)
            List<HistoriaClinica> listaConsultas = gato.getHistoriasClinicas();
            
            // Validación por si la lista es null (evitar errores en JSP)
            if (listaConsultas == null) {
                listaConsultas = new ArrayList<>();
            }

            // 4. Guardamos en sesión
            HttpSession session = request.getSession();
            session.setAttribute("gatoSeleccionado", gato); // Para mostrar nombre y foto en el encabezado
            session.setAttribute("listaConsultas", listaConsultas); // Para la tabla

            // 5. Redirigimos al JSP de listado
            response.sendRedirect("HistoriaClinica/historiasClinicas.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos");
        }
    }
}