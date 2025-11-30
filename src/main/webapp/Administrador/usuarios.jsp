<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestionar Usuarios</title>
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Rol</th>
                    <th>Teléfono</th> <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Usuario> lista = (List<Usuario>) session.getAttribute("listaUsuarios");
                    for (Usuario u : lista) {
                %>
                    <tr>
                        <td><%= u.getNombre() %></td>
                        <td><%= u.getCorreo() %></td>
                        <td><%= u.getRol() %></td>
                        <td><%= u.getTelefono() %></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/SvModificarUsuario" method="GET" style="display:inline;">
                                <input type="hidden" name="idEditar" value="<%= u.getIdUsuario() %>">
                                <button type="submit">Editar</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvEliminarUsuario" method="POST" style="display:inline;">
                                <input type="hidden" name="idEliminar" value="<%= u.getIdUsuario() %>">
                                <button type="submit" onclick="return confirm('¿Seguro?');">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
