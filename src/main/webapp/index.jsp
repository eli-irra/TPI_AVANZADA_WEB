<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Iniciar Sesión</title>
        <link rel="stylesheet" href="css/styles.css"> 
    </head>
    <body>
        <h2>Iniciar Sesión</h2>
        <form action="SvLogin" method="POST">
            <label>Correo:</label>
            <input type="text" name="correo" required>
            
            <label>Contraseña:</label>
            <input type="password" name="contrasena" required>
            
            <button type="submit">Ingresar</button>
        </form>

        <% 
            String error = (String) request.getSession().getAttribute("error");
            if(error != null) {
        %>
            <p style="color: red;"><%= error %></p>
        <% 
            } 
        %>
    </body>
</html>
