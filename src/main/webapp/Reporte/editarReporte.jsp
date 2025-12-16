<%@page import="modelo.Reporte, modelo.Zona, java.util.List"%>
<% 
    Reporte rep = (Reporte) session.getAttribute("reporteEditar");
%>

<%@include file="../templates/menu.jsp" %>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<div class="form-container" style="max-width: 600px;">
    <h2>Editar Reporte #<%= (rep != null ? rep.getIdReporte() : "") %></h2>
    
    <% if (rep != null) { %>
    <form action="${pageContext.request.contextPath}/SvModificarReporte" method="POST">
        <input type="hidden" name="idReporte" value="<%= rep.getIdReporte() %>">
        
        <label>Fecha de Creación:</label>
        <input type="text" value="<%= rep.getFechaReporte() %>" disabled style="background-color: #f0f0f0;">
        
        <label>Cantidad:</label>
        <input type="number" name="cantidad" value="<%= rep.getCantidad() %>" required min="1">
        
        <label>Zona del Avistamiento:</label>
        <select name="idZona" id="selectZona" onchange="toggleNuevaZona()" required>
            <% 
                List<Zona> zonas = (List<Zona>) session.getAttribute("listaZonasReporte");
                long idZonaActual = (rep.getZonaReportada() != null) ? rep.getZonaReportada().getIdZona() : -1;
                
                if(zonas != null) {
                    for(Zona z : zonas) {
            %>
                <option value="<%= z.getIdZona() %>" <%= (z.getIdZona() == idZonaActual) ? "selected" : "" %>>
                    <%= z.getNombreZona() %>
                </option>
            <%      } 
               } 
            %>
            <option value="nueva" style="font-weight: bold; color: var(--primary);">+ Registrar Nueva Zona</option>
        </select>

        <div id="divNuevaZona" style="display: none; background: #f0f9ff; padding: 15px; border: 1px dashed #0ea5e9; border-radius: 8px; margin-bottom: 15px; margin-top: 10px;">
            <h4 style="margin-top:0; color: #0284c7;">Datos de la Nueva Zona</h4>
            
            <label>Nombre de la Zona:</label>
            <input type="text" name="nuevaZonaNombre" id="inputNombreZona">
            
            <label>Ubicación GPS:</label>
            <input type="text" name="nuevaZonaGPS" id="inputGPS" readonly placeholder="Haga clic en el mapa" style="background-color: #fff;">
            
            <div id="mapaSeleccion" style="height: 250px; width: 100%; border-radius: 8px; margin-top: 10px; cursor: crosshair;"></div>
        </div>
        
        <label>Descripción:</label>
        <textarea name="descripcion" rows="5" required><%= rep.getDescripcion() %></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
    
    <script>
        var mapInitialized = false;
        var map;
        var marker;

        function toggleNuevaZona() {
            var select = document.getElementById("selectZona");
            var divNueva = document.getElementById("divNuevaZona");
            var inputNombre = document.getElementById("inputNombreZona");
            var inputGPS = document.getElementById("inputGPS");

            if (select.value === "nueva") {
                divNueva.style.display = "block";
                inputNombre.setAttribute("required", "true");
                inputGPS.setAttribute("required", "true");
                
                if (!mapInitialized) {
                    setTimeout(iniciarMapa, 100);
                    mapInitialized = true;
                }
            } else {
                divNueva.style.display = "none";
                inputNombre.removeAttribute("required");
                inputGPS.removeAttribute("required");
            }
        }

        function iniciarMapa() {
            var lat = -27.3671; 
            var lng = -55.8961;

            map = L.map('mapaSeleccion').setView([lat, lng], 13);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; OpenStreetMap contributors'
            }).addTo(map);

            map.on('click', function(e) {
                var latStr = e.latlng.lat.toFixed(6);
                var lngStr = e.latlng.lng.toFixed(6);
                
                document.getElementById("inputGPS").value = latStr + ", " + lngStr;

                if (marker) {
                    marker.setLatLng(e.latlng);
                } else {
                    marker = L.marker(e.latlng).addTo(map);
                }
            });
        }
    </script>

    <% } else { %>
        <p>No se pudo cargar el reporte.</p>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>