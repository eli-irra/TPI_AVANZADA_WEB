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
import modelo.Visita;

@WebServlet(name = "SvVisitas", urlPatterns = {"/SvVisitas"})
public class SvVisitas extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Visita> listaVisitas = control.traerTodasLasVisitas();
            HttpSession session = request.getSession();
            session.setAttribute("listaVisitas", listaVisitas);
            response.sendRedirect("Visita/visitas.jsp");
        } catch (Exception e) {
            response.sendRedirect("menu.jsp");
        }
    }
}