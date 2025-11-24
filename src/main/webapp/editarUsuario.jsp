<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar Usuario</title>
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <h1>Editar Usuario</h1>
        
        <% 
            // Recuperamos el usuario que SvModificarUsuario puso en la sesión
            Usuario usu = (Usuario) session.getAttribute("usuEditar");
        %>
        
        <form action="SvModificarUsuario" method="POST">
            <input type="hidden" name="idUsuario" value="<%= usu.getIdUsuario() %>">
            
            <label>Nombre:</label>
            <input type="text" name="nombre" value="<%= usu.getNombre() %>" required><br><br>
            
            <label>Correo:</label>
            <input type="email" name="correo" value="<%= usu.getCorreo() %>" required><br><br>
            
            <label>Teléfono:</label>
            <input type="number" name="telefono" value="<%= (long)usu.getTelefono() %>" required><br><br>
            
            <label>Dirección:</label>
            <input type="text" name="direccion" value="<%= usu.getdireccion() %>" required><br><br>
            
            <label>Rol:</label>
            <input type="text" value="<%= usu.getRol() %>" disabled><br><br>

            <button type="submit">Guardar Cambios</button>
            <a href="SvUsuarios">Cancelar</a>
        </form>
    </body>
</html>