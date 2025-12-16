<%-- Acá se muestra el formulario para añadir una tarea relacionada a un gato a una zona. Solamente el Voluntario puede acceder. --%>
<%@page import="modelo.Gato, modelo.Zona, modelo.Voluntario, modelo.Tarea.TipoTarea, java.util.List"%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Asignar Nueva Tarea</h2>
    
    <form action="${pageContext.request.contextPath}/SvAltaTarea" method="POST">
        
        <div style="display:flex; gap:20px; margin-bottom: 15px;">
            <div style="flex:1;">
                <label>Tipo de Tarea:</label>
                <select name="tipoTarea" required>
                    <% for(TipoTarea tipo : TipoTarea.values()) { %>
                        <option value="<%= tipo %>"><%= tipo %></option>
                    <% } %>
                </select>
            </div>
            <div style="flex:1;">
                <label>Fecha Límite:</label>
                <input type="date" name="fecha" required>
            </div>
        </div>

        <div style="background-color: #f8fafc; padding: 15px; border-radius: 8px; margin-bottom: 15px; border: 1px solid #e2e8f0;">
            <p style="margin-top:0; font-weight:bold; color: #475569;">Asignar a (Seleccione uno):</p>
            
            <div style="display:flex; gap:20px;">
                <div style="flex:1;">
                    <label>Gato:</label>
                    <select name="idGato" id="selectGato" onchange="limpiarZona()">
                        <option value="0">-- Ninguno --</option>
                        <% 
                            List<Gato> gatos = (List<Gato>) session.getAttribute("listaGatos");
                            if(gatos != null) { for(Gato g : gatos) {
                        %>
                            <option value="<%= g.getIdGato() %>"><%= g.getNombre() %></option>
                        <% }} %>
                    </select>
                </div>
                
                <div style="flex:0.1; display:flex; align-items:center; justify-content:center; color: #94a3b8; font-weight:bold;">
                    O
                </div>

                <div style="flex:1;">
                    <label>Zona:</label>
                    <select name="idZona" id="selectZona" onchange="limpiarGato()">
                        <option value="0">-- Ninguna --</option>
                        <% 
                            List<Zona> zonas = (List<Zona>) session.getAttribute("listaZonas");
                            if(zonas != null) { for(Zona z : zonas) {
                        %>
                            <option value="<%= z.getIdZona() %>"><%= z.getNombreZona() %></option>
                        <% }} %>
                    </select>
                </div>
            </div>
        </div>

        <label>Voluntario Responsable:</label>
        <select name="idVoluntario" required>
            <% 
                List<Voluntario> vols = (List<Voluntario>) session.getAttribute("listaVoluntarios");
                if(vols != null) { for(Voluntario v : vols) {
            %>
                <option value="<%= v.getIdUsuario() %>"><%= v.getNombre() %></option>
            <% }} %>
        </select>
        
        <label>Ubicación (Detalle):</label>
        <input type="text" name="ubicacion" placeholder="Ej: Jaula 4, Pasillo Norte...">

        <label>Descripción:</label>
        <textarea name="descripcion" rows="3" required></textarea>

        <br><br>
        <button type="submit" class="btn-primary">Guardar Tarea</button>
    </form>
</div>

<script>
    function limpiarZona() {
        document.getElementById("selectZona").value = "0";
    }
    function limpiarGato() {
        document.getElementById("selectGato").value = "0";
    }
</script>

<%@include file="../templates/footer.jsp" %>