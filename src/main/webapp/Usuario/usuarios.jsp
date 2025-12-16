<%-- Acá se muestra el listado de usuarios registrados, solamente el Administrador puede acceder a esta pagina. --%>
<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%@include file="../templates/menu.jsp" %>

    <div class="container">
        <h1>Gestión de Usuarios</h1>
        
        <div class="filtros-container flex-between">
            <span>Lista de usuarios registrados en el sistema.</span>
            <a href="${pageContext.request.contextPath}/Usuario/registrarUsuario.jsp">
                <button>+ Nuevo Usuario</button>
            </a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Rol</th>
                    <th>Teléfono</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Usuario> lista = (List<Usuario>) session.getAttribute("listaUsuarios");
                    if (lista != null && !lista.isEmpty()) {
                        for (Usuario u : lista) {
                %>
                    <tr>
                        <td><%= u.getNombre() %></td>
                        <td><%= u.getCorreo() %></td>
                        <td><%= u.getRol() %></td>
                        <td><%= (long)u.getTelefono() %></td>
                        <td class="table-actions">
                            <form action="${pageContext.request.contextPath}/SvVerUsuario" method="GET">
                                <input type="hidden" name="idUsuario" value="<%= u.getIdUsuario() %>">
                                <button type="submit" class="btn-icon" title="Ver Perfil">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                                </button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvModificarUsuario" method="GET">
                                <input type="hidden" name="idEditar" value="<%= u.getIdUsuario() %>">
                                <button type="submit" class="btn-icon" title="Editar">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-pencil"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 20h4l10.5 -10.5a2.828 2.828 0 1 0 -4 -4l-10.5 10.5v4" /><path d="M13.5 6.5l4 4" /></svg>
                                </button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvEliminarUsuario" method="POST">
                                <input type="hidden" name="idEliminar" value="<%= u.getIdUsuario() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar?');" title="Borrar">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                </button>
                            </form>
                        </td>
                    </tr>
                <% 
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="5" class="empty-state">No hay usuarios para mostrar.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

<%@include file="../templates/footer.jsp" %>
