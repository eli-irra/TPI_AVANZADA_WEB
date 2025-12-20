<%@page import="modelo.Reporte"%>
<%@include file="../templates/menu.jsp" %>

<%
    Reporte reporte = (Reporte) session.getAttribute("reporteDetalle");
%>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<div class="container">
    <% if (reporte != null) { 
       String gps = (reporte.getZonaReportada() != null) ? reporte.getZonaReportada().getUbicacionGPS() : null;
       String nombreZona = (reporte.getZonaReportada() != null) ? reporte.getZonaReportada().getNombreZona() : "Sin zona asignada";
    %>
        
        <div class="card" style="max-width: 800px; margin: 20px auto; padding: 30px;">
            <div style="border-bottom: 1px solid #eee; margin-bottom: 20px; display: flex; justify-content: space-between;">
                <div>
                    <h1 style="color: var(--primary); margin-bottom: 5px;">Reporte #<%= reporte.getIdReporte() %></h1>
                    <p style="color: #64748b;">Fecha: <%= reporte.getFechaReporte() %></p>
                </div>
                <div style="text-align: right;">
                    <span class="badge" style="font-size: 1rem; background-color: #e0f2fe; color: #0284c7;">
                        <%= nombreZona %>
                    </span>
                </div>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
                
                <%-- Columna Izquierda: Datos --%>
                <div>
                    <div style="background: #f8fafc; padding: 15px; border-radius: 8px; margin-bottom: 15px;">
                        <strong style="color: #475569;">Cantidad de Gatos:</strong>
                        <span style="font-size: 1.5rem; font-weight: bold; color: #0f172a; margin-left: 10px;"><%= reporte.getCantidad() %></span>
                    </div>
                    
                    <strong style="color: #475569;">Descripción:</strong>
                    <div style="background: #fff; border: 1px solid #e2e8f0; padding: 15px; border-radius: 8px; margin-top: 5px; color: #334155; line-height: 1.6;">
                        <%= reporte.getDescripcion() %>
                    </div>
                </div>

                <%-- Columna Derecha: Mapa --%>
                <div>
                    <% if (gps != null && !gps.isEmpty()) { %>
                        <div id="mapaReporte" style="height: 300px; width: 100%; border-radius: 8px; border: 1px solid #cbd5e1;"></div>
                        
                        <script>
                            var gpsStr = "<%= gps %>";
                            var partes = gpsStr.split(",");
                            var lat = parseFloat(partes[0].trim());
                            var lng = parseFloat(partes[1].trim());

                            var map = L.map('mapaReporte').setView([lat, lng], 15);

                            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                                attribution: '&copy; OpenStreetMap contributors'
                            }).addTo(map);

                            L.marker([lat, lng]).addTo(map)
                                .bindPopup("<b><%= nombreZona %></b><br>Lugar del avistamiento").openPopup();
                        </script>
                    <% } else { %>
                        <div style="height: 300px; background: #f1f5f9; display: flex; align-items: center; justify-content: center; border-radius: 8px; color: #94a3b8;">
                            Sin ubicación GPS registrada
                        </div>
                    <% } %>
                </div>
            </div>

            <div style="margin-top: 30px; text-align: right;">
                <form action="${pageContext.request.contextPath}/SvEliminarReporte" method="POST" style="display:inline;">
                    <input type="hidden" name="idEliminar" value="<%= reporte.getIdReporte() %>">
                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Borrar reporte?');">Eliminar Reporte</button>
                </form>
            </div>
        </div>

    <% } else { %>
        <p style="text-align:center;">Reporte no encontrado.</p>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>