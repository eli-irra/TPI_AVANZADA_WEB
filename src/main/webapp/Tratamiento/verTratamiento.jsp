<%@page import="modelo.Tratamiento"%>
<%
    String idGato = (String) session.getAttribute("idGatoVolver");
    Tratamiento t = (Tratamiento) session.getAttribute("tratamientoDetalle");
%>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <div class="card" style="max-width: 800px; margin: 0 auto;">
        <h1 style="color: var(--primary-color);"><i class="fas fa-pills"></i> Detalle del Tratamiento</h1>
        <hr>
        
        <div style="margin: 20px 0;">
            <h3 style="color: #64748b;">Diagnóstico:</h3>
            <p style="font-size: 1.2rem; font-weight: bold;"><%= t.getDiagostico() %></p>
        </div>
        
        <div style="margin: 20px 0;">
            <h3 style="color: #64748b;">Procedimiento / Medicación:</h3>
            <p style="background: #f8fafc; padding: 15px; border-radius: 8px; border: 1px solid #e2e8f0; white-space: pre-wrap;"><%= t.getTratamiento() %></p>
        </div>
        
        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 30px;">
            <form action="${pageContext.request.contextPath}/SvModificarTratamiento" method="GET">
                <input type="hidden" name="idEditar" value="<%= t.getidTratamiento() %>">
                <input type="hidden" name="idGato" value="<%= idGato %>">
                <button type="submit" class="btn-secondary">Editar</button>
            </form>
            <form action="${pageContext.request.contextPath}/SvEliminarTratamiento" method="POST">
                <input type="hidden" name="idEliminar" value="<%= t.getidTratamiento() %>">
                <input type="hidden" name="idGato" value="<%= idGato %>">
                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar?');">Borrar</button>
            </form>
        </div>
    </div>
</div>
<%@include file="../templates/footer.jsp" %>