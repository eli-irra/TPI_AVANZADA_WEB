<%@page import="modelo.Reporte"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% request.setAttribute("linkVolver", "menu.jsp"); %>

<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Reportes</h1>
    
    <div class="filtros-container" style="justify-content: flex-end;">
        <a href="registrarReporte.jsp">
            <button class="btn-primary">+ Nuevo Reporte</button>
        </a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Fecha</th>
                <th>Cantidad</th>
                <th>Descripción</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Reporte> lista = (List<Reporte>) session.getAttribute("listaReportes");
                if (lista != null && !lista.isEmpty()) {
                    for (Reporte r : lista) {
            %>
                <tr>
                    <td><%= r.getFechaReporte() %></td>
                    <td><%= r.getCantidad() %></td>
                    <td><%= r.getDescripcion() %></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/SvVerReporte" method="GET" style="display:inline;">
                            <input type="hidden" name="idReporte" value="<%= r.getIdReporte() %>">
                            <button type="submit" class="btn-icon" title="Ver Detalle">👁</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvModificarReporte" method="GET" style="display:inline;">
                            <input type="hidden" name="idEditar" value="<%= r.getIdReporte() %>">
                            <button type="submit" class="btn-icon" title="Editar">✎</button>
                        </form>
                        
                        <form action="${pageContext.request.contextPath}/SvEliminarReporte" method="POST" style="display:inline;">
                            <input type="hidden" name="idEliminar" value="<%= r.getIdReporte() %>">
                            <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar este reporte?');" title="Borrar">🗑</button>
                        </form>
                    </td>
                </tr>
            <% }} else { %>
                <tr><td colspan="4" style="text-align:center;">No hay reportes registrados.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>

<%@include file="../templates/footer.jsp" %>