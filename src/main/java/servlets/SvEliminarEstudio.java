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
            String idEliminarStr = request.getParameter("idEliminar");
            String idGatoStr = request.getParameter("idGato");

            if(idEliminarStr == null || idEliminarStr.isEmpty()){
                 throw new Exception("ID de estudio no recibido");
            }

            long idEstudio = Long.parseLong(idEliminarStr);
            
            Estudio estudio = control.buscarEstudio(idEstudio);
            long idHistoria = 0;
            if (estudio != null && estudio.getHistoriaClinica() != null) {
                idHistoria = estudio.getHistoriaClinica().getidHistoria();
            }

            control.eliminarEstudio(idEstudio);
            
            if (idHistoria > 0) {
                response.sendRedirect("SvVerHistoriaClinica?idHistoria=" + idHistoria + "&idGato=" + idGatoStr);
            } else {
                // Si no se pudo recuperar la historia, volvemos al listado
                response.sendRedirect("SvHistoriasClinicas?idGato=" + idGatoStr);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String idGato = request.getParameter("idGato");
            if (idGato != null) {
                response.sendRedirect("SvVerPerfilGato?idVer=" + idGato);
            } else {
                response.sendRedirect("SvGatos");
            }
        }
    }
}