<%@page import="modelo.Zona"%>
<% 
    Zona z = (Zona) session.getAttribute("zonaEditar");
%>
<%@include file="../templates/menu.jsp" %>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<style>
    #mapaEdicion {
        height: 350px;
        width: 100%;
        margin-bottom: 15px;
        border: 2px solid #e2e8f0;
        border-radius: 8px;
        cursor: crosshair;
    }
</style>

<div class="form-container">
    <h2>Editar Zona</h2>
    <% if(z != null) { %>
    <form action="${pageContext.request.contextPath}/SvModificarZona" method="POST">
        <input type="hidden" name="idZona" value="<%= z.getIdZona() %>">
        
        <label>Nombre de la Zona:</label>
        <input type="text" name="nombreZona" value="<%= z.getNombreZona() %>" required>
        
        <label>Ubicación GPS (Modificar en el mapa):</label>
        <input type="text" name="ubicacionGPS" id="inputGPS" value="<%= z.getUbicacionGPS() %>" required readonly style="background-color: #f1f5f9;">
        
        <div id="mapaEdicion"></div>
        
        <br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>

    <script>
        // 1. Obtener coordenadas actuales de la Zona
        var gpsActual = "<%= z.getUbicacionGPS() %>";
        var partes = gpsActual.split(",");
        var lat = parseFloat(partes[0].trim());
        var lng = parseFloat(partes[1].trim());

        // 2. Inicializar mapa centrado en esa ubicación
        var map = L.map('mapaEdicion').setView([lat, lng], 15);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; OpenStreetMap contributors'
        }).addTo(map);

        // 3. Poner el marcador en la posición actual
        var marcadorActual = L.marker([lat, lng], {draggable: true}).addTo(map);
        marcadorActual.bindPopup("<b>Ubicación Actual</b>").openPopup();

        // 4. Evento: Al hacer clic en el mapa, mover marcador y actualizar input
        map.on('click', function(e) {
            actualizarPosicion(e.latlng);
        });

        // 5. Evento: Al arrastrar el marcador
        marcadorActual.on('dragend', function(e) {
            actualizarPosicion(marcadorActual.getLatLng());
        });

        function actualizarPosicion(latlng) {
            marcadorActual.setLatLng(latlng);
            var latStr = latlng.lat.toFixed(6);
            var lngStr = latlng.lng.toFixed(6);
            document.getElementById("inputGPS").value = latStr + ", " + lngStr;
        }
    </script>

    <% } else { %>
        <p>Error: No se encontró la zona a editar.</p>
    <% } %>
</div>
<%@include file="../templates/footer.jsp" %>
