<%-- Acá debería estar una tabla que muestre todos los gatos registrados, es la pagina predeterminada (después de iniciar sesión) 
para los tipos de usuarios Voluntario, Familia Adoptante, y Veterinario. Al Voluntario le debería aparecer una opción para poder 
registrar a un gato--%>
<%@page import="modelo.Gato"%>
<%@page import="java.util.List"%>
<%@include file="../templates/menu.jsp" %>
<div class="container">
    <h1>Gestión de Gatos</h1>
    
    <div class="filtros-container">
        <form action="${pageContext.request.contextPath}/SvGatos" method="GET" style="display:flex; gap:10px; flex-grow:1;">
            <input type="text" name="busquedaNombre" placeholder="Buscar por nombre...">
            <button type="submit">Buscar</button>
            <a href="${pageContext.request.contextPath}/SvGatos" class="btn-secondary">Ver Todos</a>
        </form>
        <% if (usu.getRol().equals("VOLUNTARIO")) { %>
            <a href="${pageContext.request.contextPath}/SvAltaGato">
            <button class="btn-primary">+ Nuevo Gato</button>
            </a>
        <% } %>
        
    </div>

    <table>
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Raza</th>
                <th>Sexo</th>
                <th>Zona</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Gato> lista = (List<Gato>) session.getAttribute("listaGatos");
                if (lista != null && !lista.isEmpty()) {
                    for (Gato g : lista) {
                        String nombreZona = (g.getZona() != null) ? g.getZona().getNombreZona() : "-";
            %>
                <tr>
                    <td><%= g.getNombre() %></td>
                    <td><%= g.getRaza() %></td>
                    <td><%= g.getSexo() %></td>
                    <td><%= nombreZona %></td>
                    <td><%= g.getestadoFisico() %></td>
                    <td style="display:flex; gap:5px;">
                        <form action="${pageContext.request.contextPath}/SvVerPerfilGato" method="GET">
                            <input type="hidden" name="idVer" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon" title="Ver">?</button>
                        </form>
                        <% if (usu.getRol().equals("VOLUNTARIO")) { %>
                        <form action="${pageContext.request.contextPath}/SvModificarGato" method="GET">
                            <input type="hidden" name="idEditar" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon" title="Editar">?</button>
                        </form>
                        
                        <form action="${pageContext.request.contextPath}/SvEliminarGato" method="POST">
                            <input type="hidden" name="idEliminar" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Borrar gato?');" title="Eliminar">?</button>
                        </form>
                        <% } %>
                        
                        </td>
                </tr>
            <% }} else { %>
                <tr><td colspan="6" style="text-align:center;">No hay gatos registrados.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>

<%@include file="../templates/footer.jsp" %>