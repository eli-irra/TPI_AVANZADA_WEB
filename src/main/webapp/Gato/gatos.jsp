<%-- Acá debería estar una tabla que muestre todos los gatos registrados, es la pagina predeterminada (después de iniciar sesión) 
para los tipos de usuarios Voluntario, Familia Adoptante, y Veterinario. Al Voluntario le debería aparecer una opción para poder 
registrar a un gato--%>
<%@page import="modelo.Gato"%>
<%@page import="java.util.List"%>
<%@include file="../templates/menu.jsp" %>
<div class="container">
    <h1>Gestión de Gatos</h1>
    
    <div class="filtros-container">
        <form action="${pageContext.request.contextPath}/SvGatos" method="GET" class="flex-grow flex-gap-10">
            <input type="text" name="busquedaNombre" placeholder="Buscar por nombre...">
            <button type="submit" class="btn-primary">Buscar</button>
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
                    <td class="table-actions">
                        <form action="${pageContext.request.contextPath}/SvVerPerfilGato" method="GET">
                            <input type="hidden" name="idVer" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon" title="Ver">
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                            </button>
                        </form>
                        <% if (usu.getRol().equals("VOLUNTARIO")) { %>
                        <form action="${pageContext.request.contextPath}/SvModificarGato" method="GET">
                            <input type="hidden" name="idEditar" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon" title="Editar">
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-pencil"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 20h4l10.5 -10.5a2.828 2.828 0 1 0 -4 -4l-10.5 10.5v4" /><path d="M13.5 6.5l4 4" /></svg>
                            </button>
                        </form>
                        
                        <form action="${pageContext.request.contextPath}/SvEliminarGato" method="POST">
                            <input type="hidden" name="idEliminar" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Borrar gato?');" title="Eliminar">
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                            </button>
                        </form>
                        <% } %>
                        
                        </td>
                </tr>
            <% }} else { %>
                <tr><td colspan="6" class="text-center">No hay gatos registrados.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>
<%@include file="../templates/footer.jsp" %>