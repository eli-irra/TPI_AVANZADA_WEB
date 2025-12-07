<%@page import="modelo.FamiliaAdoptante, modelo.Voluntario, java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "SvVisitas"); %>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Programar Nueva Visita</h2>
    
    <form action="${pageContext.request.contextPath}/SvAltaVisita" method="POST">
        <div style="display:flex; gap:20px;">
            <div style="flex:1;">
                <label>Fecha:</label>
                <input type="date" name="fecha" required>
            </div>
            <div style="flex:1;">
                <label>Hora (0-23):</label>
                <input type="number" name="hora" min="0" max="23" required>
            </div>
        </div>

        <label>Familia a Visitar:</label>
        <select name="idFamilia" required>
            <% 
                List<FamiliaAdoptante> fams = (List<FamiliaAdoptante>) session.getAttribute("listaFamilias");
                if(fams != null) { for(FamiliaAdoptante f : fams) {
            %>
                <option value="<%= f.getIdUsuario() %>"><%= f.getNombre() %> (Dir: <%= f.getdireccion() %>)</option>
            <% }} %>
        </select>

        <label>Voluntario Asignado:</label>
        <select name="idVoluntario" required>
            <% 
                List<Voluntario> vols = (List<Voluntario>) session.getAttribute("listaVoluntarios");
                if(vols != null) { for(Voluntario v : vols) {
            %>
                <option value="<%= v.getIdUsuario() %>"><%= v.getNombre() %></option>
            <% }} %>
        </select>
        
        <label>Notas / Descripción:</label>
        <textarea name="descripcion" rows="3" required></textarea>
        
        <div style="margin-top: 10px;">
            <input type="checkbox" name="realizada" id="checkRealizada">
            <label for="checkRealizada" style="display:inline;">Marcar como ya realizada</label>
        </div>

        <br><br>
        <button type="submit" class="btn-primary">Guardar Visita</button>
    </form>
</div>
<%@include file="../templates/footer.jsp" %>