<%-- Acá se muestra el detalle (datos) de una tarea relacionada a un gato a una zona. Solamente el Voluntario puede acceder. --%>
<%@page import="modelo.Tarea, java.text.SimpleDateFormat"%>
<%@include file="../templates/menu.jsp" %>

<%
    Tarea t = (Tarea) session.getAttribute("tareaDetalle");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<div class="container">
    <% if (t != null) { 
        // Lógica de visualización del objetivo
        String objetivo = "Sin asignar";
        String icono = "";
        String linkObjetivo = "#";
        
        if (t.getGatoAsignado() != null) {
            objetivo = "Gato: " + t.getGatoAsignado().getNombre();
            icono = "?";
            // Opción extra: Link al perfil del gato
            linkObjetivo = request.getContextPath() + "/SvVerPerfilGato?idGato=" + t.getGatoAsignado().getIdGato();
        } else if (t.getZonaAsignada() != null) {
            objetivo = "Zona: " + t.getZonaAsignada().getNombreZona();
            icono = "?";
        }
    %>
        
        <div class="card" style="max-width: 700px; margin: 20px auto; padding: 30px;">
            <div style="border-bottom: 1px solid #eee; margin-bottom: 20px; display:flex; justify-content:space-between; align-items:center;">
                <h1 style="color: var(--primary-color); margin: 0;"><%= t.getTipoTarea() %></h1>
                <span style="background: #e2e8f0; padding: 5px 10px; border-radius: 5px; font-weight: bold; color: #475569;">
                    ? <%= sdf.format(t.getFecha()) %>
                </span>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 30px;">
                
                <div>
                    <strong style="display: block; color: #64748b; margin-bottom: 5px;">Voluntario Responsable</strong>
                    <div style="display:flex; align-items:center; gap: 10px;">
                        <span style="font-size: 1.2rem; font-weight: bold; color: #0f172a;">
                            <%= (t.getVoluntarioQueRealiza() != null) ? t.getVoluntarioQueRealiza().getNombre() : "Sin asignar" %>
                        </span>
                    </div>
                </div>
                
                <div>
                    <strong style="display: block; color: #64748b; margin-bottom: 5px;">Asignado a</strong>
                    <div style="font-size: 1.1rem; color: #0f172a;">
                        <%= icono %> 
                        <% if(t.getGatoAsignado() != null) { %>
                            <a href="<%= linkObjetivo %>" style="text-decoration: underline; color: #2563eb;"><%= t.getGatoAsignado().getNombre() %></a>
                        <% } else { %>
                            <%= (t.getZonaAsignada() != null) ? t.getZonaAsignada().getNombreZona() : "-" %>
                        <% } %>
                    </div>
                </div>
            </div>
            
            <% if(t.getUbicacion() != null && !t.getUbicacion().isEmpty()) { %>
            <div style="margin-bottom: 20px;">
                <strong style="color: #64748b;">Ubicación Específica:</strong>
                <span style="margin-left: 10px;"><%= t.getUbicacion() %></span>
            </div>
            <% } %>

            <div style="margin-bottom: 30px;">
                <h3 style="color: #334155; margin-bottom: 10px;">Descripción de la Tarea</h3>
                <div style="background: #f8fafc; border: 1px solid #e2e8f0; padding: 20px; border-radius: 8px; line-height: 1.6; color: #334155; white-space: pre-wrap;">
<%= t.getDescripcion() %></div>
            </div>

            <div style="display: flex; justify-content: flex-end; gap: 10px; border-top: 1px solid #eee; padding-top: 20px;">
                <form action="${pageContext.request.contextPath}/SvModificarTarea" method="GET">
                    <input type="hidden" name="idEditar" value="<%= t.getIdTarea() %>">
                    <button type="submit" class="btn-secondary">Editar</button>
                </form>
                
                <form action="${pageContext.request.contextPath}/SvEliminarTarea" method="POST">
                    <input type="hidden" name="idEliminar" value="<%= t.getIdTarea() %>">
                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Seguro que deseas eliminar esta tarea?');">Eliminar</button>
                </form>
            </div>
        </div>

    <% } else { %>
        <div style="text-align: center; padding: 50px;">
            <h2>?? Error</h2>
            <p>No se pudo cargar la información de la tarea.</p>
            <a href="${pageContext.request.contextPath}/SvTareas" class="btn-primary">Volver al listado</a>
        </div>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>