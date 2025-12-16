<%@page import="modelo.Visita, modelo.FamiliaAdoptante, modelo.Voluntario, java.util.List"%>
<% 
    // Recuperamos los objetos que el Servlet puso en sesión
    Visita v = (Visita) session.getAttribute("visitaEditar");
    List<FamiliaAdoptante> familias = (List<FamiliaAdoptante>) session.getAttribute("listaFamilias");
    List<Voluntario> voluntarios = (List<Voluntario>) session.getAttribute("listaVoluntarios");
%>

<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Modificar Visita</h2>
    
    <% if (v != null) { %>
    
    <form action="${pageContext.request.contextPath}/SvModificarVisita" method="POST">
        <input type="hidden" name="idVisita" value="<%= v.getIdVisita() %>">
        
        <div style="display:flex; gap:20px;">
            <div style="flex:1;">
                <label>Fecha:</label>
                <input type="date" name="fecha" value="<%= v.getFecha() %>" required>
            </div>
            <div style="flex:1;">
                <label>Hora (0-23):</label>
                <input type="number" name="hora" min="0" max="23" value="<%= v.getHoraVisita() %>" required>
            </div>
        </div>

        <label>Familia a Visitar:</label>
        <select name="idFamilia" required>
            <% if(familias != null) { 
                for(FamiliaAdoptante f : familias) {
                    // Verificamos si esta familia es la que ya tiene asignada la visita
                    boolean esLaActual = (v.getFamilia() != null && v.getFamilia().getIdUsuario() == f.getIdUsuario());
            %>
                <option value="<%= f.getIdUsuario() %>" <%= esLaActual ? "selected" : "" %>>
                    <%= f.getNombre() %> (Dir: <%= f.getdireccion() %>)
                </option>
            <% }} %>
        </select>

        <label>Voluntario Asignado:</label>
        <select name="idVoluntario" required>
            <% if(voluntarios != null) { 
                for(Voluntario vol : voluntarios) {
                    // Verificamos si este voluntario es el asignado actualmente
                    boolean esElActual = (v.getVoluntarioEncargado() != null && v.getVoluntarioEncargado().getIdUsuario() == vol.getIdUsuario());
            %>
                <option value="<%= vol.getIdUsuario() %>" <%= esElActual ? "selected" : "" %>>
                    <%= vol.getNombre() %>
                </option>
            <% }} %>
        </select>
        
        <label>Notas / Descripción:</label>
        <textarea name="descripcion" rows="3" required><%= v.getDescripcion() %></textarea>
        
        <div style="margin-top: 15px; padding: 10px; background-color: #f8fafc; border-radius: 5px; border: 1px solid #e2e8f0;">
            <input type="checkbox" name="realizada" id="checkRealizada" <%= v.isRealizada() ? "checked" : "" %>>
            <label for="checkRealizada" style="display:inline; margin-left: 5px; cursor: pointer; color: #334155; font-weight: bold;">
                Marcar como Realizada
            </label>
        </div>

        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
    
    <% } else { %>
        <div style="text-align: center; padding: 20px;">
            <p style="color: red;">Error: No se ha cargado ninguna visita para editar.</p>
            <a href="${pageContext.request.contextPath}/SvVisitas" class="btn-secondary">Volver al listado</a>
        </div>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>
