package servlets;

import controladora.Controladora;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Gato;
import modelo.Tarea;
import modelo.Voluntario;
import modelo.Zona;

@WebServlet(name = "SvModificarTarea", urlPatterns = {"/SvModificarTarea"})
public class SvModificarTarea extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idEditar"));
            Tarea tarea = control.buscarTarea(id);
            
            List<Gato> listaGatos = control.traerTodosLosGatos();
            List<Zona> listaZonas = control.traerTodasLasZonas(); // NUEVO
            List<Voluntario> listaVoluntarios = control.traerTodosLosVoluntarios();
            
            HttpSession session = request.getSession();
            session.setAttribute("tareaEditar", tarea);
            session.setAttribute("listaGatos", listaGatos);
            session.setAttribute("listaZonas", listaZonas); // NUEVO
            session.setAttribute("listaVoluntarios", listaVoluntarios);
            
            response.sendRedirect("Tarea/editarTarea.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvTareas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idTarea"));
            Tarea tarea = control.buscarTarea(id);
            
            // Actualizar campos simples...
            tarea.setDescripcion(request.getParameter("descripcion"));
            tarea.setUbicacion(request.getParameter("ubicacion"));
            tarea.setTipoTarea(modelo.Tarea.TipoTarea.valueOf(request.getParameter("tipoTarea")));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            tarea.setFecha(sdf.parse(request.getParameter("fecha")));
            
            // Actualizar Voluntario
            int idVol = Integer.parseInt(request.getParameter("idVoluntario"));
            tarea.setVoluntarioQueRealiza(control.buscarVoluntario(idVol));
            
            // Actualizar Gato o Zona
            String idGatoStr = request.getParameter("idGato");
            String idZonaStr = request.getParameter("idZona");
            
            if (idGatoStr != null && !idGatoStr.equals("0")) {
                tarea.setGatoAsignado(control.buscarGatoCompleto(Integer.parseInt(idGatoStr)));
                tarea.setZonaAsignada(null);
            } else if (idZonaStr != null && !idZonaStr.equals("0")) {
                tarea.setZonaAsignada(control.traerZona(Integer.parseInt(idZonaStr)));
                tarea.setGatoAsignado(null);
            } else {
                // Caso borde: dejar ambos null si se selecciona "Ninguno"
                tarea.setGatoAsignado(null);
                tarea.setZonaAsignada(null);
            }
            
            control.modificarTarea(tarea);
            response.sendRedirect("SvTareas");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}