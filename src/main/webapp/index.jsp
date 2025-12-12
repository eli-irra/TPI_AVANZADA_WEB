<%-- Acá se muestra el formulario de inicio de sesión.  --%>
<%@include file="templates/header.jsp" %>

    <div class="formulario" style="max-width: 400px; margin: 50px auto;">
        <h2 style="text-align: center;">Iniciar Sesión</h2>
        
        <% 
            String error = (String) request.getSession().getAttribute("error");
            if(error != null) {
        %>
            <div style="color: #ef4444; background-color: #fee2e2; padding: 10px; border-radius: 5px; margin-bottom: 15px; text-align: center;">
                <%= error %>
            </div>
        <% 
                // Limpiar el error después de mostrarlo
                request.getSession().removeAttribute("error");
            } 
        %>

        <form action="SvLogin" method="POST">
            <label>Correo:</label>
            <input type="text" name="correo" required>
            
            <label>Contraseña:</label>
            <input type="password" name="contrasena" required>
            
            <button type="submit">Ingresar</button>
        </form>

        <div style="text-align: center; margin-top: 15px;">
            <p>¿No tienes cuenta?</p>
            <a href="Usuario/registrarUsuario.jsp" class="btn-secondary" style="display: inline-block; padding: 8px 16px;">Registrarse</a>
        </div>
    </div>

<%@include file="templates/footer.jsp" %>
 