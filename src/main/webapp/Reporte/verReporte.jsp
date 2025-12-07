<%@page import="modelo.Reporte"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% request.setAttribute("linkVolver", "SvReportes"); %>

<%@include file="../templates/menu.jsp" %>

<%
    Reporte reporte = (Reporte) session.getAttribute("reporteDetalle");
%>

<div class="container">
    <% if (reporte != null) { %>
        
        <div class="card" style="max-width: 800px; margin: 20px auto; padding: 30px;">
            <div style="border-bottom: 1px solid #eee; margin-bottom: 20px;">
                <h1 style="color: var(--primary-color); margin-bottom: 5px;">Reporte #<%= reporte.getIdReporte() %></h1>
                <p style="color: #64748b; font-size: 0.9rem;">Registrado el: <%= reporte.getFechaReporte() %></p>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 30px;">
                <div style="background: #f8fafc; padding: 15px; border-radius: 8px;">
                    <strong style="display: block; color: #475569; margin-bottom: 5px;">Cantidad/Recursos</strong>
                    <span style="font-size: 1.5rem; font-weight: bold; color: #0f172a;"><%= reporte.getCantidad() %></span>
                </div>
                
                <div style="background: #f8fafc; padding: 15px; border-radius: 8px;">
                    <strong style="display: block; color: #475569; margin-bottom: 5px;">Responsable</strong>
                    <span style="font-size: 1.1rem; color: #0f172a;">Administrador</span>
                </div>
            </div>

            <div style="margin-bottom: 30px;">
                <h3 style="color: #334155; margin-bottom: 10px;">Descripción Detallada</h3>
                <div style="background: #fff; border: 1px solid #e2e8f0; padding: 20px; border-radius: 8px; line-height: 1.6; color: #334155;">
                    <%= reporte.getDescripcion() %>
                </div>
            </div>

            <div style="display: flex; justify-content: flex-end; gap: 10px; border-top: 1px solid #eee; padding-top: 20px;">
                
                <form action="${pageContext.request.contextPath}/SvModificarReporte" method="GET">
                    <input type="hidden" name="idEditar" value="<%= reporte.getIdReporte() %>">
                    <button type="submit" class="btn-secondary">Editar Reporte</button>
                </form>
                
                <form action="${pageContext.request.contextPath}/SvEliminarReporte" method="POST">
                    <input type="hidden" name="idEliminar" value="<%= reporte.getIdReporte() %>">
                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Seguro que deseas eliminar este reporte?');">Eliminar</button>
                </form>
                
            </div>
        </div>

    <% } else { %>
        <div style="text-align: center; padding: 50px;">
            <h2>⚠️ Error</h2>
            <p>No se pudo cargar la información del reporte.</p>
            <a href="${pageContext.request.contextPath}/SvReportes" class="btn-primary">Volver al listado</a>
        </div>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>