<%@page import="modelo.Zona, java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "menu.jsp"); %>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Zonas</h1>
    
    <div class="filtros-container justify-end">
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
                    <td><a href="https://www.google.com/maps/search/?api=1&query=<%= z.getUbicacionGPS() %>" target="_blank" class="link-primary-underline">
                        <%= z.getUbicacionGPS() %>
                    </a></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/SvVerZona" method="GET" class="form-inline">
                            <input type="hidden" name="idZona" value="<%= z.getIdZona() %>">
                            <button type="submit" class="btn-icon" title="Ver">
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvModificarZona" method="GET" class="form-inline">
                            <input type="hidden" name="idEditar" value="<%= z.getIdZona() %>">
                            <button type="submit" class="btn-icon" title="Editar">
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-pencil"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 20h4l10.5 -10.5a2.828 2.828 0 1 0 -4 -4l-10.5 10.5v4" /><path d="M13.5 6.5l4 4" /></svg>
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvEliminarZona" method="POST" class="form-inline">
                            <input type="hidden" name="idEliminar" value="<%= z.getIdZona() %>">
                            <button type="submit" class="btn-icon-danger" title="Eliminar" onclick="return confirm('¿Eliminar zona?');">
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                            </button>
                        </form>
                    </td>
                </tr>
            <% }} else { %>
                <tr><td colspan="3" class="text-center">No hay zonas registradas.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>
<%@include file="../templates/footer.jsp" %>
