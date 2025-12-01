<%@include file="templates/header.jsp" %>
        <div class="formulario">
            <h2>Iniciar Sesión</h2>
        <form action="SvLogin" method="POST">
            <label>Correo:</label>
            <input type="text" name="correo" required>
            
            <label>Contraseña:</label>
            <input type="password" name="contrasena" required>
            
            <button type="submit">Ingresar</button>
            
        </form>
            <a href="registrarUsuario.jsp"><button> Registrarse</button></a>
        </div>
        

        <% 
            String error = (String) request.getSession().getAttribute("error");
            if(error != null) {
        %>
            <p style="color: red;"><%= error %></p>
        <% 
            } 
        %>
<%@include file="templates/footer.jsp" %>
