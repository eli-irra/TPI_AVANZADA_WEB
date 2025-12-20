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

@WebServlet(name = "SvCambiarEstadoSalud", urlPatterns = {"/SvCambiarEstadoSalud"})
public class SvCambiarEstadoSalud extends HttpServlet {

    Controladora control = new Controladora();

    // GET: Recibe la petición del botón y redirige al formulario de selección
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("idGato");
            if (idStr != null && !idStr.isEmpty()) {
                long idGato = Long.parseLong(idStr);
                
                // Buscamos el gato para mostrar sus datos en el JSP
                Gato gato = control.buscarGatoCompleto(idGato);
                
                HttpSession session = request.getSession();
                session.setAttribute("gatoSalud", gato); // Guardamos en sesión
                
                // Redirigimos a la vista de selección
                response.sendRedirect("Gato/cambiarEstadoSalud.jsp");
            } else {
                response.sendRedirect("SvGatos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos");
        }
    }

    // POST: Recibe el formulario y aplica el cambio en la BD
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long idGato = Long.parseLong(request.getParameter("idGato"));
            String nuevoEstadoStr = request.getParameter("nuevoEstado");
            
            // Convertimos el String del formulario al Enum de la clase Gato
            Gato.EstadoSalud nuevoEstado = Gato.EstadoSalud.valueOf(nuevoEstadoStr);
            
            // Llamamos al método que YA TIENES en tu Controladora
            control.modificarEstadoSaludGato(idGato, nuevoEstado);
            
            // Volvemos al perfil del gato para ver el cambio
            response.sendRedirect("SvVerPerfilGato?idVer=" + idGato);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Si falla, volvemos al listado
            response.sendRedirect("SvGatos");
        }
    }
}