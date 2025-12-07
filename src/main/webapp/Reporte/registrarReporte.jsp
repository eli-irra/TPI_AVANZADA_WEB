<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% request.setAttribute("linkVolver", "SvReportes"); %>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Registrar Nuevo Reporte</h2>
    
    <form action="${pageContext.request.contextPath}/SvAltaReporte" method="POST">
        <label>Cantidad (Insumos/Recursos):</label>
        <input type="number" name="cantidad" required min="1">
        
        <label>Descripción / Detalle:</label>
        <textarea name="descripcion" rows="5" required placeholder="Detalle del reporte..."></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Reporte</button>
    </form>
</div>

<%@include file="../templates/footer.jsp" %>