<%@page import="modelo.Zona, modelo.Gato, java.util.List"%>
<%@include file="../templates/menu.jsp" %>

<%
    Zona z = (Zona) session.getAttribute("zonaDetalle");
%>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<div class="container">
    <% if (z != null) { %>
        <div class="card" style="max-width: 800px; margin: 20px auto;">
            <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding-bottom: 10px; margin-bottom: 20px;">
                <h1 style="color: var(--primary); margin: 0;"><i class="fas fa-map-marker-alt"></i> <%= z.getNombreZona() %></h1>
                <span class="badge">ID: <%= z.getIdZona() %></span>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
                <div>
                    <h3>Información</h3>
                    <p><strong>Ubicación GPS:</strong> <%= z.getUbicacionGPS() %></p>
                    
                    <h3 style="margin-top: 20px;">Gatos en esta Zona</h3>
                    <% 
                       // Nota: Asegúrate de que tu controladora traiga la lista de gatos al buscar la zona
                       List<Gato> gatosEnZona = z.getGatosEnZona(); 
                       if(gatosEnZona != null && !gatosEnZona.isEmpty()) { 
                    %>
                        <ul style="list-style: none; padding: 0;">
                        <% for(Gato g : gatosEnZona) { %>
                            <li style="padding: 8px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between;">
                                <span><%= g.getNombre() %></span>
                                <a href="${pageContext.request.contextPath}/SvVerPerfilGato?idVer=<%= g.getIdGato() %>" style="color: var(--primary); font-size: 0.9rem;">Ver Perfil</a>
                            </li>
                        <% } %>
                        </ul>
                    <% } else { %>
                        <p style="color: #64748b; font-style: italic;">No hay gatos asignados a esta zona actualmente.</p>
                    <% } %>
                </div>

                <div>
                    <div id="mapaVer" style="height: 300px; width: 100%; border-radius: 8px; border: 1px solid #ddd;"></div>
                </div>
            </div>

            <div style="margin-top: 30px; text-align: right;">
                <form action="${pageContext.request.contextPath}/SvModificarZona" method="GET" style="display:inline;">
                    <input type="hidden" name="idEditar" value="<%= z.getIdZona() %>">
                    <button type="submit" class="btn-secondary">Editar Zona</button>
                </form>
            </div>
        </div>

        <script>
            var gps = "<%= z.getUbicacionGPS() %>";
            var partes = gps.split(",");
            var lat = parseFloat(partes[0].trim());
            var lng = parseFloat(partes[1].trim());

            var map = L.map('mapaVer').setView([lat, lng], 16);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; OpenStreetMap contributors'
            }).addTo(map);

            L.marker([lat, lng]).addTo(map)
                .bindPopup("<b><%= z.getNombreZona() %></b>")
                .openPopup();
        </script>

    <% } else { %>
        <div style="text-align: center; padding: 50px;">
            <h2>Error</h2>
            <p>No se pudo cargar la información de la zona.</p>
            <a href="${pageContext.request.contextPath}/SvZonas" class="btn-primary">Volver al listado</a>
        </div>
    <% } %>
</div>
<%@include file="../templates/footer.jsp" %>