<%-- Acá se muestra el formulario para editar los datos del propio usuario, u otro en caso de ser Administrador. --%>
<%@page import="modelo.Usuario"%>
<%@include file="../templates/menu.jsp" %>
        <h1>Editar Usuario</h1>
        
        <% 
            // Recuperamos el usuario que SvModificarUsuario puso en la sesión
            Usuario usuario = (Usuario) session.getAttribute("usuEditar");
        %>
        
        <form action="SvModificarUsuario" method="POST">
            <input type="hidden" name="idUsuario" value="<%= usuario.getIdUsuario() %>">
            
            <label>Nombre:</label>
            <input type="text" name="nombre" value="<%= usuario.getNombre() %>" required><br><br>
            
            <label>Correo:</label>
            <input type="email" name="correo" value="<%= usuario.getCorreo() %>" required><br><br>
            
            <label>Teléfono:</label>
            <input type="number" name="telefono" value="<%= (long)usuario.getTelefono() %>" required><br><br>
            
            <label>Dirección:</label>
            <input type="text" name="direccion" value="<%= usuario.getdireccion() %>" required><br><br>
            
            <label>Rol:</label>
            <input type="text" value="<%= usuario.getRol() %>" disabled><br><br>

            <button type="submit">Guardar Cambios</button>
            <a href="../SvUsuarios">Cancelar</a>
        </form>
<%@include file="../templates/footer.jsp" %>