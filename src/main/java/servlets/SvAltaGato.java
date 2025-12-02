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
import modelo.Gato;
import modelo.Zona;

@WebServlet(name = "SvAltaGato", urlPatterns = {"/SvAltaGato"})
public class SvAltaGato extends HttpServlet {
    Controladora control = new Controladora();

    // GET: Carga la lista de zonas y muestra el formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Zona> listaZonas = control.traerTodasLasZonas();
            HttpSession session = request.getSession();
            session.setAttribute("listaZonas", listaZonas);
            response.sendRedirect("Gato/registrarGato.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvGatos");
        }
    }

    // POST: Guarda el gato en la BD
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            String raza = request.getParameter("raza");
            String color = request.getParameter("color");
            String sexo = request.getParameter("sexo");
            String caracteristicas = request.getParameter("caracteristicas");
            int idZona = Integer.parseInt(request.getParameter("zona_id"));
            
            // Buscar zona completa
            Zona zona = control.traerZona(idZona); 
            
            Gato nuevoGato = new Gato();
            nuevoGato.setNombre(nombre);
            nuevoGato.setRaza(raza);
            nuevoGato.setColor(color);
            nuevoGato.setSexo(sexo);
            nuevoGato.setCaracteristicas(caracteristicas);
            nuevoGato.setZona(zona);
            
            // Valores por defecto obligatorios
            nuevoGato.setDisponible(Gato.RespuestaBinaria.SI);
            nuevoGato.setEsterilizado(Gato.RespuestaBinaria.NO);
            nuevoGato.setestadoFisico(Gato.EstadoSalud.SANO);
            
            control.crearGato(nuevoGato);
            response.sendRedirect("SvGatos");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Gato/registrarGato.jsp"); // Volver si falla
        }
    }
}