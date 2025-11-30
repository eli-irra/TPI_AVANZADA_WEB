<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Usuario</title>
        <link rel="stylesheet" href="../css/styles.css">
    </head>
    <body>
        <h1>Modificar Usuario</h1>
        
        <% 
            // Recuperamos el usuario que el Servlet guardó en la sesión
            Usuario usu = (Usuario) session.getAttribute("usuEditar");
            
            if(usu != null) {
        %>
        
        <form action="servlets/SvModificarUsuario" method="POST">
            
            <input type="hidden" name="idUsuario" value="<%= usu.getIdUsuario() %>">
            
            <label>Nombre:</label>
            <input type="text" name="nombre" value="<%= usu.getNombre() %>" required>
            <br><br>
            
            <label>Correo:</label>
            <input type="email" name="correo" value="<%= usu.getCorreo() %>" required>
            <br><br>
            
            <label>Teléfono:</label>
            <input type="number" name="telefono" value="<%= (long)usu.getTelefono() %>" required>
            <br><br>
            
            <label>Dirección:</label>
            <input type="text" name="direccion" value="<%= usu.getdireccion() %>" required>
            <br><br>
            
            <label>Rol:</label>
            <input type="text" value="<%= usu.getRol() %>" disabled>
            <br><br>

            <button type="submit">Guardar Cambios</button>
            <a href="${pageContext.request.contextPath}/SvUsuarios">Cancelar</a>
        </form>
        
        <% 
            } else { 
        %>
            <p>Error: No se pudo cargar la información del usuario.</p>
            <a href="${pageContext.request.contextPath}/SvUsuarios">Volver a la lista</a>
        <% 
            } 
        %>
    </body>
</html>
