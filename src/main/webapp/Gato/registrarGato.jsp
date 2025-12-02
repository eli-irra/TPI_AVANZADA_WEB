<%-- Acá debería estar el formulario para registrar al gato, esta acción solamente la puede hacer el Voluntario.--%>
<%@page import="modelo.Zona, java.util.List"%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Registrar Nuevo Gato</h2>
    
    <form action="${pageContext.request.contextPath}/SvAltaGato" method="POST">
        <label>Nombre:</label>
        <input type="text" name="nombre" required>
        
        <div style="display:flex; gap:10px;">
            <div style="flex:1;">
                <label>Raza:</label>
                <input type="text" name="raza" required>
            </div>
            <div style="flex:1;">
                <label>Color:</label>
                <input type="text" name="color" required>
            </div>
        </div>

        <label>Sexo:</label>
        <select name="sexo">
            <option value="Macho">Macho</option>
            <option value="Hembra">Hembra</option>
        </select>
        
        <label>Zona Asignada:</label>
        <select name="zona_id">
            <% 
                List<Zona> zonas = (List<Zona>) session.getAttribute("listaZonas");
                if(zonas != null) {
                    for(Zona z : zonas) {
            %>
                <option value="<%= z.getIdZona() %>"><%= z.getNombreZona() %></option>
            <% }} %>
        </select>

        <label>Características:</label>
        <textarea name="caracteristicas" rows="3" style="width:100%;"></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Gato</button>
    </form>
</div>

<%@include file="../templates/footer.jsp" %>
