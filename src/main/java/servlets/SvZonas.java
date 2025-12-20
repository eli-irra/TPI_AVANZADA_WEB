package servlets;

import controladora.Controladora;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.OperacionException;
import modelo.Zona;

@WebServlet(name = "SvZonas", urlPatterns = {"/SvZonas"})
public class SvZonas extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            List<Zona> listaZonas = null;
        try {
            listaZonas = control.traerTodasLasZonas();
        } catch (OperacionException ex) {
            System.getLogger(SvZonas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            HttpSession session = request.getSession();
            session.setAttribute("listaZonas", listaZonas);
            
            // Redirigir a la carpeta Zona
            response.sendRedirect("Zona/zonas.jsp");
        
    }
}