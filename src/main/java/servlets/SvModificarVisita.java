package servlets;

import controladora.Controladora;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.FamiliaAdoptante;
import modelo.Visita;
import modelo.Voluntario;

@WebServlet(name = "SvModificarVisita", urlPatterns = {"/SvModificarVisita"})
public class SvModificarVisita extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idEditar"));
            Visita visita = control.buscarVisita(id);
            
            List<FamiliaAdoptante> familias = control.traerTodasLasFamilias();
            List<Voluntario> voluntarios = control.traerTodosLosVoluntarios();
            
            HttpSession session = request.getSession();
            session.setAttribute("visitaEditar", visita);
            session.setAttribute("listaFamilias", familias);
            session.setAttribute("listaVoluntarios", voluntarios);
            
            response.sendRedirect("Visita/modificarVisita.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvVisitas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idVisita"));
            Visita visita = control.buscarVisita(id);
            
            visita.setDescripcion(request.getParameter("descripcion"));
            visita.setFecha(LocalDate.parse(request.getParameter("fecha")));
            visita.setHoraVisita(Integer.parseInt(request.getParameter("hora")));
            visita.setRealizada(request.getParameter("realizada") != null);
            
            int idFamilia = Integer.parseInt(request.getParameter("idFamilia"));
            int idVoluntario = Integer.parseInt(request.getParameter("idVoluntario"));
            
            visita.setFamilia(control.buscarFamilia(idFamilia));
            visita.setVoluntarioEncargado(control.buscarVoluntario(idVoluntario));
            
            control.modificarVisita(visita);
            response.sendRedirect("SvVisitas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}