<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Nuevo Usuario</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body>
        <h1>Registrar Nuevo Usuario</h1>
        <form action="SvAltaUsuario" method="POST">
            <label>Nombre:</label>
            <input type="text" name="nombre" required><br><br>
            
            <label>Correo:</label>
            <input type="email" name="correo" required><br><br>
            
            <label>Contraseña:</label>
            <input type="password" name="contrasena" required><br><br>
            
            <label>Rol:</label>
            <select name="rol">
                <option value="ADMINISTRADOR">Administrador</option>
                <option value="VETERINARIO">Veterinario</option>
                <option value="VOLUNTARIO">Voluntario</option>
                <option value="FAMILIA">Familia Adoptante</option>
            </select><br><br>
            
            <button type="submit">Guardar</button>
        </form>
        <a href="index.jsp"><button>Volver atrás</button></a>
    </body>
</html>