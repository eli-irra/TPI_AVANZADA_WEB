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
        
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();

        try {
            if ("verDetalle".equals(accion)) {
                // --- CASO 2: Ver el detalle de una Historia específica ---
                long idHistoria = Long.parseLong(request.getParameter("idHistoria"));
                
                // Buscamos la historia y la guardamos en sesión para verHistoriaClinica.jsp
                HistoriaClinica hc = control.buscarHistoriaClinica(idHistoria);
                session.setAttribute("historiaClinica", hc);
                
                // Redirigimos al detalle (Estudios y Tratamientos)
                response.sendRedirect("HistoriaClinica/verHistoriaClinica.jsp");
                
            } else {
                // --- CASO 1: Listar Historias (Desde el Perfil del Gato) ---
                long idGato = Long.parseLong(request.getParameter("idGato"));
                
                // Buscamos el gato completo
                Gato gato = control.buscarGatoCompleto(idGato);
                
                // Guardamos el gato en sesión
                session.setAttribute("gatoActual", gato);
                
                // Redirigimos a la nueva tabla de historias
                response.sendRedirect("HistoriaClinica/historiasClinicas.jsp");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        String idGatoStr = request.getParameter("idGato");
        
        try {
            if ("eliminar".equals(accion)) {
                long idHistoria = Long.parseLong(request.getParameter("idHistoria"));
                
                // Llamamos a la controladora para eliminar
                // Nota: Asegúrate de tener este método en tu Controladora
                // control.eliminarHistoriaClinica(idHistoria);
                System.out.println("Solicitud de eliminación de Historia ID: " + idHistoria);
                
                // Volvemos a la lista
                response.sendRedirect("SvHistoriaClinica?idGato=" + idGatoStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos");
        }
    }
}