<%@page import="modelo.Zona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    request.setAttribute("linkVolver", "SvZonas"); 
    Zona z = (Zona) session.getAttribute("zonaEditar");
%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Editar Zona</h2>
    <% if(z != null) { %>
    <form action="${pageContext.request.contextPath}/SvModificarZona" method="POST">
        <input type="hidden" name="idZona" value="<%= z.getIdZona() %>">
        
        <label>Nombre de la Zona:</label>
        <input type="text" name="nombreZona" value="<%= z.getNombreZona() %>" required>
        
        <label>Ubicación GPS:</label>
        <input type="text" name="ubicacionGPS" value="<%= z.getUbicacionGPS() %>" required>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
    <% } %>
</div>
<%@include file="../templates/footer.jsp" %>
