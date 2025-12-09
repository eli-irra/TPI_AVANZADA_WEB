<%-- Acá se muestra el listado de usuarios registrados, solamente el Administrador puede acceder a esta pagina. --%>
<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%@include file="../templates/menu.jsp" %>

    <div class="container">
        <h1>Gestión de Usuarios</h1>
        
        <div class="filtros-container" style="justify-content: space-between;">
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
                        <td style="display:flex; gap: 5px;">
                            <form action="${pageContext.request.contextPath}/SvVerUsuario" method="GET">
                                <input type="hidden" name="idUsuario" value="<%= u.getIdUsuario() %>">
                                <button type="submit" class="btn-icon" title="Ver Perfil">?</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvModificarUsuario" method="GET">
                                <input type="hidden" name="idEditar" value="<%= u.getIdUsuario() %>">
                                <button type="submit" class="btn-icon" title="Editar">?</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvEliminarUsuario" method="POST">
                                <input type="hidden" name="idEliminar" value="<%= u.getIdUsuario() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar?');" title="Borrar">?</button>
                            </form>
                        </td>
                    </tr>
                <% 
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="5" style="text-align: center; padding: 20px;">No hay usuarios para mostrar.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

<%@include file="../templates/footer.jsp" %>
