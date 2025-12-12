<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "SvZonas"); %>
<%@include file="../templates/menu.jsp" %>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<style>
    #mapaSeleccion {
        height: 300px; /* Altura del mapa */
        width: 100%;
        margin-bottom: 15px;
        border: 2px solid #e2e8f0;
        border-radius: 8px;
        cursor: crosshair; /* Cursor tipo mira para indicar selección */
    }
</style>

<div class="form-container">
    <h2>Registrar Nueva Zona</h2>
    <form action="${pageContext.request.contextPath}/SvAltaZona" method="POST">
        <label>Nombre de la Zona:</label>
        <input type="text" name="nombreZona" required >
        
        <label>Ubicación GPS (Selecciona en el mapa):</label>
        <input type="text" name="ubicacionGPS" id="inputGPS" required readonly>
        
        <div id="mapaSeleccion"></div>
        
        <br>
        <button type="submit" class="btn-primary">Guardar Zona</button>
    </form>
</div>

<script>
    // Coordenadas iniciales (Ej: Posadas, Misiones, o Buenos Aires)
    // Puedes ajustar esto a la ubicación predeterminada de tu refugio
    var latitudInicial = -27.3671; 
    var longitudInicial = -55.8961;
    var zoomInicial = 13;

    // Inicializar el mapa
    var map = L.map('mapaSeleccion').setView([latitudInicial, longitudInicial], zoomInicial);

    // Cargar capa de OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    // Variable para el marcador
    var marcadorActual = null;

    // Evento: Clic en el mapa
    map.on('click', function(e) {
        var lat = e.latlng.lat.toFixed(6); // Redondear a 6 decimales
        var lng = e.latlng.lng.toFixed(6);
        
        // 1. Actualizar el input visible (formato requerido por tu Servlet: "lat, long")
        var coordsString = lat + ", " + lng;
        document.getElementById("inputGPS").value = coordsString;

        // 2. Manejo visual del marcador
        if (marcadorActual) {
            // Si ya existe, lo movemos
            marcadorActual.setLatLng(e.latlng);
        } else {
            // Si no existe, lo creamos
            marcadorActual = L.marker(e.latlng).addTo(map);
        }
        
        // (Opcional) Agregar un popup indicando que se seleccionó
        marcadorActual.bindPopup("Ubicación seleccionada: " + coordsString).openPopup();
    });
</script>

<%@include file="../templates/footer.jsp" %>