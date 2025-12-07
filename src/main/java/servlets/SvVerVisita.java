package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Visita;

@WebServlet(name = "SvVerVisita", urlPatterns = {"/SvVerVisita"})
public class SvVerVisita extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idVisita"));
            Visita visita = control.buscarVisita(id);
            request.getSession().setAttribute("visitaDetalle", visita);
            response.sendRedirect("Visita/verVisita.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvVisitas");
        }
    }
}