<%@page import="modelo.Usuario"%>
<%
    // Verificación de seguridad (Sesión)
    Usuario usu = (Usuario) session.getAttribute("usuarioLogueado");
    if (usu == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<%@include file="templates/header.jsp" %>
        <h1>Bienvenido, <%= usu.getNombre() %></h1>
        <h3>Rol: <%= usu.getRol() %></h3>
        <nav>
            <% if (usu.getRol().equals("ADMINISTRADOR")) { %>
            <a href="SvUsuarios">Gestionar Usuarios</a>
            <a href="SvZonas">Gestionar Zonas</a>
        <% } %>
        
        <% if (usu.getRol().equals("VETERINARIO")) { %>
            <a href="SvHistoriasClinicas">Ver Historias Clínicas</a>
        <% } %>
        
        <% if (usu.getRol().equals("VOLUNTARIO")) { %>
            <a href="SvGatos">Gestionar Gatos</a>
        <% } %>
        <a href="SvLogout" class="aLaIzquierda">Cerrar Sesión</a>
        </nav>