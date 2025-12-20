<%-- Acá se muestra el perfil de un usuario --%>
<%@page import="modelo.Usuario, modelo.Veterinario"%>
<%@include file="../templates/menu.jsp" %>

<%
    Usuario u = (Usuario) session.getAttribute("usuarioDetalle");
%>

<div class="container">
    <% if (u != null) { %>
        
        <div class="card" style="max-width: 600px; margin: 20px auto; padding: 30px;">
            <div style="text-align: center; margin-bottom: 20px;">
                <div style="font-size: 3rem; color: var(--primary-color); margin-bottom: 10px;">
                    <% if(u.getRol().equals("VETERINARIO")) { %> 
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-stethoscope"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M6 4h-1a2 2 0 0 0 -2 2v3.5h0a5.5 5.5 0 0 0 11 0v-3.5a2 2 0 0 0 -2 -2h-1" /><path d="M8 15a6 6 0 1 0 12 0v-3" /><path d="M11 3v2" /><path d="M6 3v2" /><path d="M20 10m-2 0a2 2 0 1 0 4 0a2 2 0 1 0 -4 0" /></svg>
                    <% } 
                       else if(u.getRol().equals("ADMINISTRADOR")) { %>
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-shield"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M12 3a12 12 0 0 0 8.5 3a12 12 0 0 1 -8.5 15a12 12 0 0 1 -8.5 -15a12 12 0 0 0 8.5 -3" /></svg>
                    <% } 
                       else if(u.getRol().equals("VOLUNTARIO")){ %>
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-heart-handshake"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M19.5 12.572l-7.5 7.428l-7.5 -7.428a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572" /><path d="M12 6l-3.293 3.293a1 1 0 0 0 0 1.414l.543 .543c.69 .69 1.81 .69 2.5 0l1 -1a3.182 3.182 0 0 1 4.5 0l2.25 2.25" /><path d="M12.5 15.5l2 2" /><path d="M15 13l2 2" /></svg>
                    <% }else{ %>
                       <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-user-circle"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M12 12m-9 0a9 9 0 1 0 18 0a9 9 0 1 0 -18 0" /><path d="M12 10m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0" /><path d="M6.168 18.849a4 4 0 0 1 3.832 -2.849h4a4 4 0 0 1 3.834 2.855" /></svg>
                    <%}%>
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
                    <button type="submit" class="btn btn-secondary">Editar Datos</button>
                </form>
                
                <form action="${pageContext.request.contextPath}/SvEliminarUsuario" method="POST">
                    <input type="hidden" name="idEliminar" value="<%= u.getIdUsuario() %>">
                    <button type="submit" class="btn btn-primary" onclick="return confirm('¿Seguro que deseas eliminar este usuario?');">
                        Eliminar Usuario
                    </button>
                </form>
            </div>
        </div>

    <% } else { %>
        <div style="text-align: center; padding: 50px;">
            <h2>Usuario no encontrado</h2>
            <a href="${pageContext.request.contextPath}/SvUsuarios" class=" btn btn-primary">Volver a la lista</a>
        </div>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>