package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import modelo.OperacionException;
import modelo.Zona;

@WebServlet(name = "SvAltaGato", urlPatterns = {"/SvAltaGato"})
public class SvAltaGato extends HttpServlet {
    
    Controladora control = new Controladora();

    // doGet: SE USA PARA MOSTRAR EL FORMULARIO CON LAS LISTAS CARGADAS
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Traer las listas necesarias de la BD
        List<Zona> listaZonas = null;
        try {
            listaZonas = control.traerTodasLasZonas();
            // List<Voluntario> listaVoluntarios = control.traerVoluntarios(); // Si aplica
        } catch (OperacionException ex) {
            System.getLogger(SvAltaGato.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        // 2. Ponerlas en sesión para que el JSP las vea
        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaZonas", listaZonas);
        
        // 3. Redirigir al formulario
        response.sendRedirect("registrarGato.jsp");
    }

    // doPost: SE USA PARA GUARDAR EL GATO (Lo que ya sabes hacer)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ... lógica para leer parámetros y guardar ...
    }
}