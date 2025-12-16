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

<header class="main-header">
    <div class="container-nav">
        
        <%-- PARTE 1: Información del Usuario (Izquierda) --%>
        <div class="user-info">
            <span class="welcome">Bienvenido, <strong><%= usu.getNombre() %></strong></span>
            <span class="role-badge"><%= usu.getRol() %></span>
        </div>

        <%-- PARTE 2: Navegación y Logout (Derecha) --%>
        <nav class="nav-menu">
            
            <div class="nav-links">
                <%-- Enlaces para ADMINISTRADOR --%>
                <% if (usu.getRol().equals("ADMINISTRADOR")) { %>
                    <a href="${pageContext.request.contextPath}/SvUsuarios">Usuarios</a>
                    <a href="${pageContext.request.contextPath}/SvZonas">Zonas</a>
                    <a href="${pageContext.request.contextPath}/SvReportes">Reportes</a>
                <% } %>
            
                <%-- Enlaces para VETERINARIO --%>
                <% if (usu.getRol().equals("VETERINARIO")) { %>
                    <a href="${pageContext.request.contextPath}/SvGatos">Gatos</a>
                <% } %>
                
                <%-- Enlaces para VOLUNTARIO --%>
                <% if (usu.getRol().equals("VOLUNTARIO")) { %>
                    <a href="${pageContext.request.contextPath}/SvGatos">Gatos</a>
                    <a href="${pageContext.request.contextPath}/SvTareas">Tareas</a>
                    <a href="${pageContext.request.contextPath}/SvPostulaciones">Postulaciones</a>
                <% } %>
                
                <%-- Enlaces para FAMILIA --%>
                <% if (usu.getRol().equals("FAMILIA")) { %>
                    <a href="${pageContext.request.contextPath}/SvGatos">Ver Gatos</a>
                <% } %>
            </div>

            <%-- Botón Cerrar Sesión (Siempre a la derecha) --%>
            <a href="${pageContext.request.contextPath}/SvLogout" class="logout-link">
                <i class="fas fa-sign-out-alt"></i>Cerrar Sesión
            </a>
            
        </nav>
    </div>
</header>

<div class="spacer-menu"></div>