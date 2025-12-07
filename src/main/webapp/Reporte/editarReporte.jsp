<%@page import="modelo.Reporte"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("linkVolver", "SvReportes"); 
    Reporte rep = (Reporte) session.getAttribute("reporteEditar");
%>

<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Editar Reporte #<%= (rep != null ? rep.getIdReporte() : "") %></h2>
    
    <% if (rep != null) { %>
    <form action="${pageContext.request.contextPath}/SvModificarReporte" method="POST">
        <input type="hidden" name="idReporte" value="<%= rep.getIdReporte() %>">
        
        <label>Fecha de Creación:</label>
        <input type="text" value="<%= rep.getFechaReporte() %>" disabled style="background-color: #f0f0f0;">
        <br><br>
        
        <label>Cantidad:</label>
        <input type="number" name="cantidad" value="<%= rep.getCantidad() %>" required min="1">
        
        <label>Descripción:</label>
        <textarea name="descripcion" rows="5" required><%= rep.getDescripcion() %></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
    <% } else { %>
        <p>No se pudo cargar el reporte.</p>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>