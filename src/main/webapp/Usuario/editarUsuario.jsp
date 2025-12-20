<%-- Acá se muestra el formulario para editar los datos del propio usuario, u otro en caso de ser Administrador. --%>
<%@page import="modelo.Usuario"%>
<%@include file="../templates/menu.jsp" %>
        <% 
            // Recuperamos el usuario que SvModificarUsuario puso en la sesión
            Usuario usuario = (Usuario) session.getAttribute("usuEditar");
        %>
        <div class="card container bg-white" style="max-width: 500px">
            <div class="card-header bg-white">
                <h3>Editar Usuario</h3>
            </div>
            <div class="card-body">
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

                <button type="submit" class="btn btm-primary">Guardar Cambios</button>
                <a href="../SvUsuarios" class="btn btn-secondary">Cancelar</a>
            </form>
            </div>
        </div>
        
        
<%@include file="../templates/footer.jsp" %>