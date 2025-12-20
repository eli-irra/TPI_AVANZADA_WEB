package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modelo.Gato;
import modelo.HistoriaClinica;

@WebServlet(name = "SvVerHistoriaClinica", urlPatterns = {"/SvVerHistoriaClinica"})
public class SvVerHistoriaClinica extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Recibimos el ID de la Historia (Consulta) y del Gato (para volver atrás)
            String idHistoriaStr = request.getParameter("idHistoria");
            String idGatoStr = request.getParameter("idGato");
            
            if (idHistoriaStr == null) {
                response.sendRedirect("SvGatos");
                return;
            }

            long idHistoria = Long.parseLong(idHistoriaStr);
            long idGato = Long.parseLong(idGatoStr);

            // 2. Buscamos los objetos
            HistoriaClinica historia = control.buscarHistoriaClinica(idHistoria);
            Gato gato = control.buscarGatoCompleto(idGato);

            // 3. Guardamos en sesión para el JSP de detalle
            HttpSession session = request.getSession();
            session.setAttribute("historiaClinica", historia); // Tiene los tratamientos y estudios
            session.setAttribute("gatoHistoria", gato); // Para el botón "Volver" y datos del paciente

            // 4. Redirigimos al JSP de detalle
            response.sendRedirect("HistoriaClinica/verHistoriaClinica.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvHistoriasClinicas"); // Si falla, volvemos al listado
        }
    }
}