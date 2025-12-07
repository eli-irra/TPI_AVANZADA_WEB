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

@WebServlet(name = "SvAltaVisita", urlPatterns = {"/SvAltaVisita"})
public class SvAltaVisita extends HttpServlet {
    Controladora control = new Controladora();

    // GET: Carga listas y muestra formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<FamiliaAdoptante> familias = control.traerTodasLasFamilias();
            List<Voluntario> voluntarios = control.traerTodosLosVoluntarios();
            
            HttpSession session = request.getSession();
            session.setAttribute("listaFamilias", familias);
            session.setAttribute("listaVoluntarios", voluntarios);
            
            response.sendRedirect("Visita/registrarVisita.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvVisitas");
        }
    }

    // POST: Guarda la visita
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String descripcion = request.getParameter("descripcion");
            LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
            int hora = Integer.parseInt(request.getParameter("hora"));
            boolean realizada = request.getParameter("realizada") != null; // Checkbox
            
            int idFamilia = Integer.parseInt(request.getParameter("idFamilia"));
            int idVoluntario = Integer.parseInt(request.getParameter("idVoluntario"));
            
            FamiliaAdoptante familia = control.buscarFamilia(idFamilia);
            Voluntario voluntario = control.buscarVoluntario(idVoluntario);
            
            Visita nuevaVisita = new Visita();
            nuevaVisita.setDescripcion(descripcion);
            nuevaVisita.setFecha(fecha);
            nuevaVisita.setHoraVisita(hora);
            nuevaVisita.setRealizada(realizada);
            nuevaVisita.setFamilia(familia);
            nuevaVisita.setVoluntarioEncargado(voluntario);
            
            control.crearVisita(nuevaVisita);
            response.sendRedirect("SvVisitas");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvAltaVisita"); // Reintentar
        }
    }
}