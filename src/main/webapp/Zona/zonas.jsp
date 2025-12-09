<%@page import="modelo.Zona, java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "menu.jsp"); %>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Zonas</h1>
    
    <div class="filtros-container" style="justify-content: flex-end;">
        <a href="registrarZona.jsp">
            <button class="btn-primary">+ Nueva Zona</button>
        </a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Nombre Zona</th>
                <th>Ubicación GPS</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Zona> lista = (List<Zona>) session.getAttribute("listaZonas");
                if (lista != null && !lista.isEmpty()) {
                    for (Zona z : lista) {
            %>
                <tr>
                    <td><%= z.getNombreZona() %></td>
                    <td><a href="https://www.google.com/maps/search/?api=1&query=<%= z.getUbicacionGPS() %>" target="_blank" style="color:var(--primary); text-decoration:underline;">
                        <%= z.getUbicacionGPS() %>
                    </a></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/SvModificarZona" method="GET" style="display:inline;">
                            <input type="hidden" name="idEditar" value="<%= z.getIdZona() %>">
                            <button type="submit" class="btn-icon">✎</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvEliminarZona" method="POST" style="display:inline;">
                            <input type="hidden" name="idEliminar" value="<%= z.getIdZona() %>">
                            <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar zona?');">🗑</button>
                        </form>
                    </td>
                </tr>
            <% }} else { %>
                <tr><td colspan="3" style="text-align:center;">No hay zonas registradas.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>
<%@include file="../templates/footer.jsp" %>
