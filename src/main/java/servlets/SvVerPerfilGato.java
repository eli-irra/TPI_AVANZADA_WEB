package servlets;

import controladora.Controladora;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Gato;

@WebServlet(name = "SvVerPerfilGato", urlPatterns = {"/SvVerPerfilGato"})
public class SvVerPerfilGato extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener ID del gato
            int id = Integer.parseInt(request.getParameter("idVer"));
            
            // 2. Buscar Gato Completo
            Gato gato = control.buscarGatoCompleto(id);
            
            // 3. Obtener Usuario de la sesión
            HttpSession session = request.getSession();
            modelo.Usuario usu = (modelo.Usuario) session.getAttribute("usuarioLogueado");
            
            // 4. Verificar si hay postulación (Solo si es Familia)
            modelo.Postulacion postulacionPropia = null;
            
            if (usu != null && usu.getRol().equals("FAMILIA")) {
                postulacionPropia = control.verificarPostulacionFamilia(usu.getIdUsuario(), id);
            }
            
            // 5. Guardar en sesión
            session.setAttribute("gatoPerfil", gato);
            session.setAttribute("postulacionPropia", postulacionPropia); // Guardamos la postulación (o null)
            
            // 6. Ir a la vista
            response.sendRedirect("Gato/perfilGato.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SvGatos");
        }
    }
}