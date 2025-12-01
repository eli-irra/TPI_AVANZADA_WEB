<%-- Acá se muestra el listado de usuarios registrados, solamente el Administrador puede acceder a esta pagina. --%>
<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%
    Usuario usu = (Usuario) session.getAttribute("usuarioLogueado");
    if (usu == null || !usu.getRol().equals("ADMINISTRADOR")) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>

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
                        <td>
                            <form action="${pageContext.request.contextPath}/SvModificarUsuario" method="GET" style="display:inline;">
                                <input type="hidden" name="idEditar" value="<%= u.getIdUsuario() %>">
                                <button type="submit" class="btn-secondary" style="padding: 5px 10px; font-size: 0.8rem;">Editar</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvEliminarUsuario" method="POST" style="display:inline;">
                                <input type="hidden" name="idEliminar" value="<%= u.getIdUsuario() %>">
                                <button type="submit" onclick="return confirm('¿Seguro que deseas eliminar este usuario?');" style="background-color: #fee2e2; color: #ef4444; border: 1px solid #ef4444; padding: 5px 10px; font-size: 0.8rem;">Eliminar</button>
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
