<%-- Acá se muestra el formulario de inicio de sesión.  --%>
<%@include file="templates/header.jsp" %>

    <div class="formulario login-wrapper">
        <h2 class="text-center">Iniciar Sesión</h2>
        
        <% 
            String error = (String) request.getSession().getAttribute("error");
            if(error != null) {
        %>
            <div class="alert-error">
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

        <div class="login-footer">
            <p>¿No tienes cuenta?</p>
            <a href="Usuario/registrarUsuario.jsp" class="btn-secondary btn-login-register">Registrarse</a>
        </div>
    </div>

<%@include file="templates/footer.jsp" %>
 