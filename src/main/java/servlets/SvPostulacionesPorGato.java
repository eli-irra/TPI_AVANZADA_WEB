package servlets;

import controladora.Controladora;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.FamiliaAdoptante;
import modelo.Gato;
import modelo.Postulacion;

@WebServlet(name = "SvPostulacionesPorGato", urlPatterns = {"/SvPostulacionesPorGato"})
public class SvPostulacionesPorGato extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener ID del gato
            String idGatoStr = request.getParameter("idGato");
            if (idGatoStr == null || idGatoStr.isEmpty()) {
                response.sendRedirect("SvPostulaciones");
                return;
            }
            long idGato = Long.parseLong(idGatoStr);

            // 2. Traer postulaciones FILTRADAS
            List<Postulacion> listaFiltrada = control.listarPostulacionesPorGato(idGato);
            if (listaFiltrada == null) {
                listaFiltrada = new ArrayList<>();
            }

            // 3. CAMBIO: Crear lista con UN SOLO GATO para el desplegable
            // Buscamos el gato específico por su ID
            Gato gatoSeleccionado = control.buscarGatoCompleto(idGato);
            
            // Creamos una lista auxiliar y lo agregamos
            List<Gato> listaSoloEsteGato = new ArrayList<>();
            if (gatoSeleccionado != null) {
                listaSoloEsteGato.add(gatoSeleccionado);
            }

            // 4. Traer todas las familias (para que puedas seleccionar quién adopta)
            List<FamiliaAdoptante> familias = control.traerTodasLasFamilias();

            // 5. Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("listaPostulaciones", listaFiltrada);
            
            // Aquí pisamos la variable "gatosDisponibles" con nuestra lista de un solo elemento
            session.setAttribute("gatosDisponibles", listaSoloEsteGato); 
            
            session.setAttribute("listaFamilias", familias);

            // 6. Redirigir al JSP reutilizado
            response.sendRedirect("Postulacion/postulaciones.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvPostulaciones");
        }
    }
}