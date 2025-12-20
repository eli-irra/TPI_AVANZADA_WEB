package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Estudio;

@WebServlet(name = "SvVerEstudio", urlPatterns = {"/SvVerEstudio"})
public class SvVerEstudio extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long idEstudio = Long.parseLong(request.getParameter("idEstudio"));
            String idGato = request.getParameter("idGato");
            String idHistoria = request.getParameter("idHistoria");
            
            Estudio estudio = control.buscarEstudio(idEstudio);
            
            HttpSession session = request.getSession();
            session.setAttribute("estudioDetalle", estudio);
            session.setAttribute("idGatoVolver", idGato);
            session.setAttribute("idHistoriaVolver", idHistoria);
            
            response.sendRedirect("Estudio/verEstudio.jsp");
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("menu.jsp");
        }
    }
}