<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "SvZonas"); %>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Registrar Nueva Zona</h2>
    <form action="${pageContext.request.contextPath}/SvAltaZona" method="POST">
        <label>Nombre de la Zona:</label>
        <input type="text" name="nombreZona" required placeholder="Ej: Patio Trasero, Cuarentena...">
        
        <label>Ubicación GPS (Lat, Long):</label>
        <input type="text" name="ubicacionGPS" required placeholder="-34.6037, -58.3816">
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Zona</button>
    </form>
</div>
<%@include file="../templates/footer.jsp" %>