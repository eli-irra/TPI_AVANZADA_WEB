<%-- Acá se muestra el formulario para editar una tarea existente relacionada a un gato a una zona. Solamente el Voluntario puede acceder. --%>
<%@page import="modelo.Gato, modelo.Voluntario, modelo.Tarea, modelo.Tarea.TipoTarea, java.util.List, java.text.SimpleDateFormat"%>
<% 
    Tarea t = (Tarea) session.getAttribute("tareaEditar");
    List<Gato> gatos = (List<Gato>) session.getAttribute("listaGatos");
    List<Voluntario> vols = (List<Voluntario>) session.getAttribute("listaVoluntarios");
    
    // Formato para el input date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fechaValue = (t != null) ? sdf.format(t.getFecha()) : "";
%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Editar Tarea</h2>
    
    <% if(t != null) { %>
    <form action="${pageContext.request.contextPath}/SvModificarTarea" method="POST">
        <input type="hidden" name="idTarea" value="<%= t.getIdTarea() %>">
        
        <div style="display:flex; gap:20px;">
            <div style="flex:1;">
                <label>Tipo de Tarea:</label>
                <select name="tipoTarea">
                    <% for(TipoTarea tipo : TipoTarea.values()) { %>
                        <option value="<%= tipo %>" <%= (tipo == t.getTipoTarea()) ? "selected" : "" %>><%= tipo %></option>
                    <% } %>
                </select>
            </div>
            <div style="flex:1;">
                <label>Fecha:</label>
                <input type="date" name="fecha" value="<%= fechaValue %>" required>
            </div>
        </div>

        <label>Gato Asignado:</label>
        <select name="idGato">
            <% if(gatos != null) { for(Gato g : gatos) { %>
                <option value="<%= g.getIdGato() %>" <%= (t.getGatoAsignado() != null && t.getGatoAsignado().getIdGato() == g.getIdGato()) ? "selected" : "" %>>
                    <%= g.getNombre() %>
                </option>
            <% }} %>
        </select>

        <label>Voluntario:</label>
        <select name="idVoluntario">
            <% if(vols != null) { for(Voluntario v : vols) { %>
                <option value="<%= v.getIdUsuario() %>" <%= (t.getVoluntarioQueRealiza() != null && t.getVoluntarioQueRealiza().getIdUsuario() == v.getIdUsuario()) ? "selected" : "" %>>
                    <%= v.getNombre() %>
                </option>
            <% }} %>
        </select>
        
        <label>Ubicación:</label>
        <input type="text" name="ubicacion" value="<%= t.getUbicacion() %>" required>

        <label>Descripción:</label>
        <textarea name="descripcion" rows="3" required><%= t.getDescripcion() %></textarea>

        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
    <% } else { %><p>No se encontró la tarea.</p><% } %>
</div>
<%@include file="../templates/footer.jsp" %>