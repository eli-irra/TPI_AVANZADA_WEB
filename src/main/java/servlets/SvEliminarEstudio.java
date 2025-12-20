package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Estudio;

@WebServlet(name = "SvEliminarEstudio", urlPatterns = {"/SvEliminarEstudio"})
public class SvEliminarEstudio extends HttpServlet {
    Controladora control = new Controladora();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Validar que el ID venga en la petición
            String idStr = request.getParameter("idEliminar");
            String idGato = request.getParameter("idGato");
            
            if (idStr == null || idStr.isEmpty()) {
                throw new Exception("El ID del estudio no fue recibido.");
            }

            long idEstudio = Long.parseLong(idStr);

            Estudio estudio = control.buscarEstudio(idEstudio);
            long idHistoria = 0;
            
            if (estudio != null) {
                idHistoria = estudio.getHistoriaClinica().getidHistoria();
                
                control.eliminarEstudio(idEstudio);
            }
            
            if (idHistoria > 0 && idGato != null) {
                response.sendRedirect("SvVerHistoriaClinica?idHistoria=" + idHistoria + "&idGato=" + idGato);
            } else {
                response.sendRedirect("SvGatos");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String idGato = request.getParameter("idGato");
            if (idGato != null) {
                response.sendRedirect("SvVerPerfilGato?idVer=" + idGato);
            } else {
                response.sendRedirect("index.jsp");
            }
        }
    }
}