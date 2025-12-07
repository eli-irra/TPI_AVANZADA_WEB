package servlets;

import controladora.Controladora;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

@WebServlet(name = "SvAltaTarea", urlPatterns = {"/SvAltaTarea"})
public class SvAltaTarea extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Cargar TODAS las listas necesarias
            List<Gato> listaGatos = control.traerTodosLosGatos();
            List<Zona> listaZonas = control.traerTodasLasZonas(); // NUEVO
            List<Voluntario> listaVoluntarios = control.traerTodosLosVoluntarios();
            
            HttpSession session = request.getSession();
            session.setAttribute("listaGatos", listaGatos);
            session.setAttribute("listaZonas", listaZonas); // NUEVO
            session.setAttribute("listaVoluntarios", listaVoluntarios);
            
            response.sendRedirect("Tarea/registrarTarea.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvTareas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Datos comunes
            String descripcion = request.getParameter("descripcion");
            String ubicacion = request.getParameter("ubicacion");
            String tipoStr = request.getParameter("tipoTarea");
            String fechaStr = request.getParameter("fecha");
            int idVoluntario = Integer.parseInt(request.getParameter("idVoluntario"));
            
            // Datos Opcionales (Gato o Zona)
            String idGatoStr = request.getParameter("idGato");
            String idZonaStr = request.getParameter("idZona");

            // Objetos
            Voluntario voluntario = control.buscarVoluntario(idVoluntario);
            Tarea.TipoTarea tipo = Tarea.TipoTarea.valueOf(tipoStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = (fechaStr != null && !fechaStr.isEmpty()) ? sdf.parse(fechaStr) : new Date();

            Tarea tarea = new Tarea();
            tarea.setDescripcion(descripcion);
            tarea.setUbicacion(ubicacion);
            tarea.setTipoTarea(tipo);
            tarea.setFecha(fecha);
            tarea.setVoluntarioQueRealiza(voluntario);
            
            // Lógica: Asignar Gato O Zona
            if (idGatoStr != null && !idGatoStr.isEmpty() && !idGatoStr.equals("0")) {
                Gato gato = control.buscarGatoCompleto(Integer.parseInt(idGatoStr));
                tarea.setGatoAsignado(gato);
                tarea.setZonaAsignada(null); // Asegurar que el otro sea null
            } else if (idZonaStr != null && !idZonaStr.isEmpty() && !idZonaStr.equals("0")) {
                Zona zona = control.traerZona(Integer.parseInt(idZonaStr));
                tarea.setZonaAsignada(zona);
                tarea.setGatoAsignado(null);
            }

            control.crearTarea(tarea);
            response.sendRedirect("SvTareas");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvAltaTarea");
        }
    }
}