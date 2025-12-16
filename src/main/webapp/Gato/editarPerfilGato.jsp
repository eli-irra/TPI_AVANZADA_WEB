<%--Se accede a través de un botón editar perfil, dentro del perfil del gato. Acá debería estar el perfil del gato (parecido al del registrar gato) 
para modificar sus datos(menos el estado de salud) solamente el Voluntario. Si el Veterinario presiona para editar el perfil, solamente será para modificar
el estado de salud del gato.--%>
<%@page import="modelo.Gato, modelo.Zona, java.util.List"%>
<% 
    Gato gato = (Gato) session.getAttribute("gatoEditar");
    List<Zona> zonas = (List<Zona>) session.getAttribute("listaZonas");
%>

<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Editar Gato: <%= (gato != null ? gato.getNombre() : "") %></h2>
    
    <% if (gato != null) { %>
    <form action="${pageContext.request.contextPath}/SvModificarGato" method="POST">
        <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
        
        <label>Nombre:</label>
        <input type="text" name="nombre" value="<%= gato.getNombre() %>" required>
        
        <label>Raza:</label>
        <input type="text" name="raza" value="<%= gato.getRaza() %>" required>
        
        <label>Color:</label>
        <input type="text" name="color" value="<%= gato.getColor() %>" required>
        
        <label>Sexo:</label>
        <select name="sexo">
            <option value="Macho" <%= "Macho".equals(gato.getSexo()) ? "selected" : "" %>>Macho</option>
            <option value="Hembra" <%= "Hembra".equals(gato.getSexo()) ? "selected" : "" %>>Hembra</option>
        </select>
        
        <label>Zona:</label>
        <select name="zona_id">
            <% if(zonas != null) { for(Zona z : zonas) { %>
                <option value="<%= z.getIdZona() %>" 
                        <%= (gato.getZona() != null && gato.getZona().getIdZona() == z.getIdZona()) ? "selected" : "" %>>
                    <%= z.getNombreZona() %>
                </option>
            <% }} %>
        </select>
        
        <label>Características:</label>
        <textarea name="caracteristicas" rows="3"><%= gato.getCaracteristicas() %></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
    <% } else { %>
        <p>Error al cargar datos del gato.</p>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>
