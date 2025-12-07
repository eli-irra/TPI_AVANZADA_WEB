<%-- Acá se muestra el perfil de un usuario --%>
<%@page import="modelo.Usuario, modelo.Veterinario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% request.setAttribute("linkVolver", "SvUsuarios"); %>

<%@include file="../templates/menu.jsp" %>

<%
    Usuario u = (Usuario) session.getAttribute("usuarioDetalle");
%>

<div class="container">
    <% if (u != null) { %>
        
        <div class="card" style="max-width: 600px; margin: 20px auto; padding: 30px;">
            <div style="text-align: center; margin-bottom: 20px;">
                <div style="font-size: 3rem; color: var(--primary-color); margin-bottom: 10px;">
                    <% if(u.getRol().equals("VETERINARIO")) { %> 🩺 <% } 
                       else if(u.getRol().equals("ADMINISTRADOR")) { %> 🛡️ <% } 
                       else { %> 👤 <% } %>
                </div>
                
                <h1 style="margin: 0; color: #1e293b;"><%= u.getNombre() %></h1>
                <span class="badge" style="margin-top: 10px; display: inline-block;"><%= u.getRol() %></span>
            </div>
            
            <hr>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-top: 20px;">
                <div>
                    <strong style="color: #64748b; display: block; margin-bottom: 5px;">Correo Electrónico</strong>
                    <span style="font-size: 1.1rem;"><%= u.getCorreo() %></span>
                </div>
                
                <div>
                    <strong style="color: #64748b; display: block; margin-bottom: 5px;">Teléfono</strong>
                    <span style="font-size: 1.1rem;"><%= (long)u.getTelefono() %></span>
                </div>
                
                <div style="grid-column: 1 / -1;">
                    <strong style="color: #64748b; display: block; margin-bottom: 5px;">Dirección</strong>
                    <span style="font-size: 1.1rem;"><%= u.getdireccion() %></span>
                </div>

                <% if (u instanceof Veterinario) { 
                       Veterinario vet = (Veterinario) u; 
                %>
                <div style="grid-column: 1 / -1; background-color: #f0f9ff; padding: 15px; border-radius: 8px; border: 1px solid #bae6fd;">
                    <strong style="color: #0369a1; display: block; margin-bottom: 5px;">Matrícula Profesional</strong>
                    <span style="font-size: 1.2rem; font-weight: bold; color: #0c4a6e;"><%= vet.getMatricula() %></span>
                </div>
                <% } %>
            </div>

            <div style="display: flex; justify-content: center; gap: 15px; margin-top: 40px;">
                <form action="${pageContext.request.contextPath}/SvModificarUsuario" method="GET">
                    <input type="hidden" name="idEditar" value="<%= u.getIdUsuario() %>">
                    <button type="submit" class="btn-secondary">Editar Datos</button>
                </form>
                
                <form action="${pageContext.request.contextPath}/SvEliminarUsuario" method="POST">
                    <input type="hidden" name="idEliminar" value="<%= u.getIdUsuario() %>">
                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Seguro que deseas eliminar este usuario?');">
                        Eliminar Usuario
                    </button>
                </form>
            </div>
        </div>

    <% } else { %>
        <div style="text-align: center; padding: 50px;">
            <h2>⚠️ Usuario no encontrado</h2>
            <a href="${pageContext.request.contextPath}/SvUsuarios" class="btn-primary">Volver a la lista</a>
        </div>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>