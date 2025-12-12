<%-- Acá debería estar el perfil del gato (sus datos), visualizando las opciones dependiendo de que tipo de usuario esté accediendo, por ejemplo, el veterinario va a poder 
acceder al historial clinico y modificar el estado de salud, mientras que la familia adoptante solamente podra ver los datos normales del gato y postularse, 
asi como visualizar el mapa, qr, etc. Y el voluntario va a poder presionar un boton de editarPerfil para modificar los datos, o registrar una tarea asociada al gato, etc--%>

<%@page import="modelo.Gato"%>
<%@page import="modelo.Gato, modelo.Postulacion"%>
<%@include file="../templates/menu.jsp" %>

<%
    Gato gato = (Gato) session.getAttribute("gatoPerfil");
    Postulacion miPostulacion = (Postulacion) session.getAttribute("postulacionPropia");
%>

<div class="container">
    <% if (gato != null) { %>
        
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>Perfil de <%= gato.getNombre() %></h1>
            
            <% if (gato.getDisponible() == modelo.Gato.RespuestaBinaria.SI) { %>
                <span style="background: #28a745; color: white; padding: 5px 15px; border-radius: 20px; font-weight: bold;">Disponible</span>
            <% } else { %>
                <span style="background: #dc3545; color: white; padding: 5px 15px; border-radius: 20px; font-weight: bold;">Adoptado</span>
            <% } %>
        </div>

        <div class="perfil-grid">
            
            <div class="card">
                <h3>Datos Generales</h3>
                <ul class="lista-datos">
                    <li><strong>Raza:</strong> <%= gato.getRaza() %></li>
                    <li><strong>Sexo:</strong> <%= gato.getSexo() %></li>
                    <li><strong>Color:</strong> <%= gato.getColor() %></li>
                    <li><strong>Fecha Alta:</strong> <%= gato.getFecha() %></li>
                    <li><strong>Esterilizado:</strong> <%= gato.getEsterilizado() %></li>
                    <li><strong>Salud:</strong> <%= gato.getestadoFisico() %></li>
                </ul>
            </div>

            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
            <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

            <div class="card">
                <h3>Ubicación y Estado</h3>
                <p>
                    <strong>Zona Actual:</strong> 
                    <%= (gato.getZona() != null) ? gato.getZona().getNombreZona() : "Sin asignar" %>
                </p>

                <% 
                   // Validar que existan la zona y las coordenadas
                   if (gato.getZona() != null && 
                       gato.getZona().getUbicacionGPS() != null && 
                       !gato.getZona().getUbicacionGPS().isEmpty()) { 

                       String gps = gato.getZona().getUbicacionGPS(); 
                %>
                    <div id="mapaGato" style="height: 250px; width: 100%; border-radius: 8px; margin-top: 10px; z-index: 1;"></div>

                    <script>
                        // Obtenemos las coordenadas del String "lat, long"
                        var gpsStr = "<%= gps %>";
                        var partes = gpsStr.split(",");
                        var lat = parseFloat(partes[0].trim());
                        var lng = parseFloat(partes[1].trim());

                        // Crear el mapa
                        var map = L.map('mapaGato').setView([lat, lng], 16);

                        // Capa de OpenStreetMap
                        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                            attribution: '© OpenStreetMap'
                        }).addTo(map);

                        // Marcador en la posición de la zona
                        L.marker([lat, lng]).addTo(map)
                            .bindPopup("<b>Zona: <%= gato.getZona().getNombreZona() %></b><br>Aquí se encuentra <%= gato.getNombre() %>")
                            .openPopup();
                    </script>

                    <div style="text-align: right; margin-top: 5px;">
                        <a href="https://www.google.com/maps/search/?api=1&query=<%= gps %>" target="_blank" style="font-size: 0.85rem; color: var(--primary);">
                            Abrir en Google Maps <i class="fas fa-external-link-alt"></i>
                        </a>
                    </div>

                <% } else { %>
                    <p style="color: #64748b;"><i>No hay ubicación GPS registrada para esta zona.</i></p>
                <% } %>
            </div>
            
            <script src="https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js"></script>

            <div class="card">
                <h3>Código QR (Uso Interno)</h3>
                <div style="display: flex; flex-direction: column; align-items: center; gap: 10px;">
                    <div id="qrcode"></div>
                    <p style="font-size: 0.9rem; color: #64748b; text-align: center;">
                        Escanea para acceder a este perfil.<br>
                        (Requiere inicio de sesión)
                    </p>
                </div>
            </div>

            <script type="text/javascript">
                // 1. Obtener el ID del gato actual desde la variable de scriptlet ya existente
                var idGato = "<%= gato.getIdGato() %>";

                // 2. Construir la URL absoluta al Servlet existente (SvVerPerfilGato)
                // window.location.origin devuelve ej: http://localhost:8080
                var baseUrl = window.location.origin + "${pageContext.request.contextPath}";
                var urlDestino = baseUrl + "/SvVerPerfilGato?idVer=" + idGato;

                // 3. Generar el código QR
                var qrcode = new QRCode(document.getElementById("qrcode"), {
                    text: urlDestino,
                    width: 150,
                    height: 150,
                    colorDark : "#000000",
                    colorLight : "#ffffff",
                    correctLevel : QRCode.CorrectLevel.H
                });
            </script>

            <div class="card">
                <h3>Características y Observaciones</h3>
                <p style="line-height: 1.6; color: #4a5568;">
                    <%= (gato.getCaracteristicas() != null && !gato.getCaracteristicas().isEmpty()) 
                        ? gato.getCaracteristicas() 
                        : "No se han registrado observaciones adicionales." %>
                </p>
            </div>
            
            <div class="full-width" style="text-align: right; margin-top: 10px; display: flex; justify-content: flex-end; align-items: center; gap: 15px;">

                <% if (usu.getRol().equals("VETERINARIO")) { %>
                    <form action="${pageContext.request.contextPath}/SvHistoriaClinica" method="GET">
                        <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                        <button type="submit" class="btn-secondary"><i class="fas fa-file-medical"></i> Historia C.</button>
                    </form>
                <% } %>

                <% if (usu.getRol().equals("VOLUNTARIO")) { %>
                    <form action="${pageContext.request.contextPath}/SvModificarGato" method="GET">
                        <input type="hidden" name="idEditar" value="<%= gato.getIdGato() %>">
                        <button type="submit" class="btn-secondary">Editar Datos</button>
                    </form>

                    <% if(gato.getDisponible() == modelo.Gato.RespuestaBinaria.SI) { %>
                    <form action="${pageContext.request.contextPath}/SvPostulaciones" method="GET">
                        <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                        <button type="submit" class="btn-primary" style="background-color: #8b5cf6;">Gestionar Adopción</button>
                    </form>
                    <% } %>
                <% } %>

                <% if (usu.getRol().equals("FAMILIA")) { %> 

                    <% if (miPostulacion != null) { 
                        String colorEstado = "#64748b"; // Gris por defecto
                        if(miPostulacion.getEstado() == Postulacion.Estado.APROBADA) {
                            colorEstado = "#10b981"; // Verde
                        } else if (miPostulacion.getEstado() == Postulacion.Estado.RECHAZADA) {
                            colorEstado = "#ef4444"; // Rojo
                        } else {
                            colorEstado = "#f59e0b"; // Naranja (Pendiente)
                        }
                    %>
                        <div style="padding: 10px 20px; background-color: #fff; border: 2px solid <%= colorEstado %>; border-radius: 30px; color: <%= colorEstado %>; font-weight: bold;">
                            Estado de Solicitud: <%= miPostulacion.getEstado() %>
                        </div>

                    <% } else if (gato.getDisponible() == modelo.Gato.RespuestaBinaria.SI) { %>
                        <form action="${pageContext.request.contextPath}/SvPostularse" method="POST">
                            <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                            <input type="hidden" name="idFamilia" value="<%= usu.getIdUsuario() %>">
                            <button type="submit" class="btn-primary" onclick="return confirm('¿Confirmar postulación?');">
                                Adoptar
                            </button>
                        </form>
                    <% } %>

                <% } %>
            </div>

    <% } else { %>
        <div style="text-align: center; padding: 50px;">
            <h2>?? Error</h2>
            <p>No se pudo cargar la información del gato.</p>
            <a href="gatos.jsp">Volver a la lista</a>
        </div>
    <% } %>
</div>

<style>
    .perfil-grid {
        display: grid;
        grid-template-columns: 1fr 1fr; /* Dos columnas iguales */
        gap: 20px;
    }
    
    /* En móviles, una sola columna */
    @media (max-width: 768px) {
        .perfil-grid { grid-template-columns: 1fr; }
    }

    .card {
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        border: 1px solid #e2e8f0;
    }

    .full-width {
        grid-column: 1 / -1; /* Ocupa todo el ancho disponible */
    }

    .lista-datos {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .lista-datos li {
        padding: 8px 0;
        border-bottom: 1px solid #f1f5f9;
        display: flex;
        justify-content: space-between;
    }
    
    .lista-datos li:last-child {
        border-bottom: none;
    }
</style>

<%@include file="../templates/footer.jsp" %>