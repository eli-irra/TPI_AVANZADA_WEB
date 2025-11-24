<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Verificación de seguridad (Sesión)
    Usuario usu = (Usuario) session.getAttribute("usuarioLogueado");
    if (usu == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Menú Principal</title>
    </head>
    <body>
        <h1>Bienvenido, <%= usu.getNombre() %></h1>
        <h3>Rol: <%= usu.getRol() %></h3>
        
        <% if (usu.getRol().equals("ADMINISTRADOR")) { %>
            <a href="SvUsuarios">Gestionar Usuarios</a>
            <a href="SvZonas">Gestionar Zonas</a>
        <% } %>
        
        <% if (usu.getRol().equals("VETERINARIO")) { %>
            <a href="SvHistoriasClinicas">Ver Historias Clínicas</a>
        <% } %>
        
        <br><br>
        <form action="SvLogout" method="POST">
            <button type="submit">Cerrar Sesión</button>
        </form>
    </body>
</html>