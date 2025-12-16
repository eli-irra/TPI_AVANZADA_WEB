<%@page import="modelo.Zona, java.util.List"%>
<%@include file="../templates/menu.jsp" %>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<div class="form-container" style="max-width: 600px;">
    <h2>Registrar Avistamiento / Reporte</h2>
    
    <form action="${pageContext.request.contextPath}/SvAltaReporte" method="POST">
        
        <label>Cantidad de Gatos vistos:</label>
        <input type="number" name="cantidad" required min="1" value="1">
        
        <label>Zona del Avistamiento:</label>
        <select name="idZona" id="selectZona" onchange="toggleNuevaZona()" required>
            <option value="" disabled selected>-- Seleccione una Zona --</option>
            <% 
                List<Zona> zonas = (List<Zona>) session.getAttribute("listaZonasReporte");
                if(zonas != null) {
                    for(Zona z : zonas) {
            %>
                <option value="<%= z.getIdZona() %>"><%= z.getNombreZona() %></option>
            <%      } 
               } 
            %>
            <option value="nueva" style="font-weight: bold; color: var(--primary);">+ Registrar Nueva Zona</option>
        </select>

        <%-- SECCIÓN PARA NUEVA ZONA (Oculta por defecto) --%>
        <div id="divNuevaZona" style="display: none; background: #f0f9ff; padding: 15px; border: 1px dashed #0ea5e9; border-radius: 8px; margin-bottom: 15px;">
            <h4 style="margin-top:0; color: #0284c7;">Datos de la Nueva Zona</h4>
            
            <label>Nombre de la Zona:</label>
            <input type="text" name="nuevaZonaNombre" id="inputNombreZona" placeholder="Ej: Parque Central, Baldío Calle 5...">
            
            <label>Ubicación GPS (Seleccione en el mapa):</label>
            <input type="text" name="nuevaZonaGPS" id="inputGPS" readonly placeholder="Haga clic en el mapa" style="background-color: #fff;">
            
            <div id="mapaSeleccion" style="height: 250px; width: 100%; border-radius: 8px; margin-top: 10px; cursor: crosshair;"></div>
            <small style="color: gray;">Haga clic en el mapa para marcar la ubicación exacta.</small>
        </div>

        <label>Descripción / Observaciones:</label>
        <textarea name="descripcion" rows="4" required placeholder="Detalles del avistamiento, estado de los gatos, etc..."></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Reporte</button>
    </form>
</div>

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
            inputNombre.setAttribute("required", "true"); // Hacer obligatorios
            inputGPS.setAttribute("required", "true");
            
            // Inicializar mapa solo si no se ha hecho antes (para evitar errores de renderizado)
            if (!mapInitialized) {
                setTimeout(iniciarMapa, 100); // Pequeño delay para asegurar que el div es visible
                mapInitialized = true;
            }
        } else {
            divNueva.style.display = "none";
            inputNombre.removeAttribute("required");
            inputGPS.removeAttribute("required");
        }
    }

    function iniciarMapa() {
        // Coordenadas por defecto (puedes cambiarlas a tu ciudad)
        var lat = -27.3671; 
        var lng = -55.8961;

        map = L.map('mapaSeleccion').setView([lat, lng], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; OpenStreetMap contributors'
        }).addTo(map);

        map.on('click', function(e) {
            var latStr = e.latlng.lat.toFixed(6);
            var lngStr = e.latlng.lng.toFixed(6);
            
            // Actualizar input
            document.getElementById("inputGPS").value = latStr + ", " + lngStr;

            // Mover o crear marcador
            if (marker) {
                marker.setLatLng(e.latlng);
            } else {
                marker = L.marker(e.latlng).addTo(map);
            }
        });
    }
</script>

<%@include file="../templates/footer.jsp" %>