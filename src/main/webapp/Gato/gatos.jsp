<%-- Acá debería estar una tabla que muestre todos los gatos registrados, es la pagina predeterminada (después de iniciar sesión) 
para los tipos de usuarios Voluntario, Familia Adoptante, y Veterinario. Al Voluntario le debería aparecer una opción para poder 
registrar a un gato--%>
<%@page import="modelo.Gato"%>
<%@page import="java.util.List"%>}
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Gatos</h1>
    
    <div class="filtros-container">
        <form action="${pageContext.request.contextPath}/SvGatos" method="GET" style="display:flex; gap:10px; align-items:center; flex-grow: 1;">
            <input type="text" name="busquedaNombre" placeholder="Nombre del gato...">
            <input type="text" name="busquedaFamilia" placeholder="Familia adoptante...">
            <button type="submit">Filtrar</button>
            <a href="${pageContext.request.contextPath}/SvGatos" class="btn-secondary">Limpiar</a>
        </form>
        
        <a href="${pageContext.request.contextPath}/SvAltaGato">
            <button class="btn-primary">+ Registrar Gato</button>
        </a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Raza</th>
                <th>Color</th>
                <th>Sexo</th>
                <th>Zona</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Gato> lista = (List<Gato>) session.getAttribute("listaGatos");
                if(lista != null && !lista.isEmpty()) {
                    for(Gato g : lista) {
                        String nZona = (g.getZona() != null) ? g.getZona().getNombreZona() : "-";
            %>
                <tr>
                    <td><%= g.getNombre() %></td>
                    <td><%= g.getRaza() %></td>
                    <td><%= g.getColor() %></td>
                    <td><%= g.getSexo() %></td>
                    <td><%= nZona %></td>
                    <td><%= g.getestadoFisico() %></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/SvVerPerfilGato" method="GET" style="display:inline;">
                            <input type="hidden" name="idGato" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon" title="Ver"><i class="fas fa-eye"></i></button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvModificarGato" method="GET" style="display:inline;">
                            <input type="hidden" name="idEditar" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon" title="Editar"><i class="fas fa-edit"></i></button>
                        </form>
                        <form action="${pageContext.request.contextPath}/SvEliminarGato" method="POST" style="display:inline;">
                            <input type="hidden" name="idEliminar" value="<%= g.getIdGato() %>">
                            <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar?');" title="Borrar"><i class="fas fa-trash"></i></button>
                        </form>
                    </td>
                </tr>
            <% }} else { %>
                <tr><td colspan="7" style="text-align:center;">No hay gatos registrados.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>

<%@include file="../templates/footer.jsp" %>
