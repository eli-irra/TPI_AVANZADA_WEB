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

@WebServlet(name = "SvModificarGato", urlPatterns = {"/SvModificarGato"})
public class SvModificarGato extends HttpServlet {
    Controladora control = new Controladora();

    // GET: Busca el gato y lo manda al formulario de edición
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("idEditar"));
            Gato gato = control.buscarGatoCompleto(id);
            List<Zona> listaZonas = control.traerTodasLasZonas();
            
            HttpSession session = request.getSession();
            session.setAttribute("gatoEditar", gato);
            session.setAttribute("listaZonas", listaZonas);
            
            response.sendRedirect("Gato/editarPerfilGato.jsp");
        } catch (Exception e) {
            response.sendRedirect("SvGatos");
        }
    }

    // POST: Actualiza los datos
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("idGato"));
            Gato gato = control.buscarGatoCompleto(id);
            
            gato.setNombre(request.getParameter("nombre"));
            gato.setRaza(request.getParameter("raza"));
            gato.setColor(request.getParameter("color"));
            gato.setSexo(request.getParameter("sexo"));
            gato.setCaracteristicas(request.getParameter("caracteristicas"));
            
            int idZona = Integer.parseInt(request.getParameter("zona_id"));
            Zona zona = control.traerZona(idZona);
            gato.setZona(zona);
            
            control.modificarGato(gato);
            response.sendRedirect("SvGatos");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
