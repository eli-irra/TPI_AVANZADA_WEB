<%@page import="modelo.Usuario"%>
<%
    // Verificación de seguridad (Sesión)
    Usuario usu = (Usuario) session.getAttribute("usuarioLogueado");
    if (usu == null ) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<%@include file="header.jsp" %>
        <h1>Bienvenido, <%= usu.getNombre() %></h1>
        <h3>Rol: <%= usu.getRol() %></h3>
        <nav>
            <% if (usu.getRol().equals("ADMINISTRADOR")) { %>
            <a href="${pageContext.request.contextPath}/SvUsuarios">Gestionar Usuarios</a>
            <a href="${pageContext.request.contextPath}/SvZonas">Gestionar Zonas</a>
        <% } %>
        
        <% if (usu.getRol().equals("VETERINARIO")) { %>
            <a href="${pageContext.request.contextPath}/SvGatos">Gestionar Gatos</a>
        <% } %>
        
        <% if (usu.getRol().equals("VOLUNTARIO")) { %>
            <a href="${pageContext.request.contextPath}/SvGatos">Gestionar Gatos</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/SvLogout" class="aLaDerecha">Cerrar Sesión</a>
        </nav>