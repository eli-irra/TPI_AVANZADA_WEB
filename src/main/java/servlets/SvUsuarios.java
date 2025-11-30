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
import modelo.Usuario;

@WebServlet(name = "SvUsuarios", urlPatterns = {"/SvUsuarios"})
public class SvUsuarios extends HttpServlet {

    // Instanciamos la controladora globalmente (Igual que en SvLogin)
    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Este método se suele dejar vacío o se usa para lógica común
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener la sesión actual
        HttpSession misesion = request.getSession();
        
        // (Opcional) Verificar si hay un usuario logueado antes de mostrar info
        Usuario usuLogueado = (Usuario) misesion.getAttribute("usuarioLogueado");
        if(usuLogueado == null){
             response.sendRedirect("index.jsp");
             return;
        }

        // 2. Traer la lista de usuarios desde la Lógica (Controladora)
        // Esto reemplaza a tu método cargarUsuarios() de la vista Swing
        List<Usuario> listaUsuarios = control.traerTodosLosUsuarios();

        // 3. Guardar la lista en la sesión para que el JSP pueda acceder a ella
        misesion.setAttribute("listaUsuarios", listaUsuarios);

        // 4. Redirigir al JSP que muestra la tabla (debes crear usuarios.jsp)
        response.sendRedirect("Administrador/usuarios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
              // El método doPost se suele usar si vas a filtrar/
            processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet que gestiona la lista de usuarios";
    }
}