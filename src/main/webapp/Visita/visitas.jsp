<%@page import="modelo.Visita, java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "menu.jsp"); %>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Visitas de Seguimiento</h1>
    
    <div class="filtros-container" style="justify-content: flex-end;">
        <a href="${pageContext.request.contextPath}/SvAltaVisita">
            <button class="btn-primary">+ Registrar Visita</button>
        </a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Fecha</th>
                <th>Familia</th>
                <th>Voluntario</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Visita> lista = (List<Visita>) session.getAttribute("listaVisitas");
                if (lista != null && !lista.isEmpty()) {
                    for (Visita v : lista) {
                        String nombreFam = (v.getFamilia() != null) ? v.getFamilia().getNombre() : "-";
                        String nombreVol = (v.getVoluntarioEncargado() != null) ? v.getVoluntarioEncargado().getNombre() : "-";
            %>
                <tr>
                    <td><%= v.getFecha() %></td>
                    <td><%= nombreFam %></td>
                    <td><%= nombreVol %></td>
                    <td>
                        <% if(v.isRealizada()) { %>
                            <span style="color: green; font-weight: bold;">Realizada</span>
                        <% } else { %>
                            <span style="color: orange; font-weight: bold;">Pendiente</span>
                        <% } %>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/SvVerVisita" method="GET" style="display:inline;">
                            <input type="hidden" name="idVisita" value="<%= v.getIdVisita() %>">
                            <button type="submit" class="btn-icon">👁</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvModificarVisita" method="GET" style="display:inline;">
                            <input type="hidden" name="idEditar" value="<%= v.getIdVisita() %>">
                            <button type="submit" class="btn-icon">✎</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvEliminarVisita" method="POST" style="display:inline;">
                            <input type="hidden" name="idEliminar" value="<%= v.getIdVisita() %>">
                            <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Borrar?');">🗑</button>
                        </form>
                    </td>
                </tr>
            <% }} else { %>
                <tr><td colspan="5" style="text-align:center;">No hay visitas registradas.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>
<%@include file="../templates/footer.jsp" %>