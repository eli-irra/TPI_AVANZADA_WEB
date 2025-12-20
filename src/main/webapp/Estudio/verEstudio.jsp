<%@page import="modelo.Estudio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String idGato = (String) session.getAttribute("idGatoVolver");
    Estudio e = (Estudio) session.getAttribute("estudioDetalle");
    String idHistoria = (String) session.getAttribute("idHistoriaVolver");
    request.setAttribute("linkVolver", "SvVerPerfilGato?idGato=" + idGato);
%>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <div class="card">
        <h1>Detalle del Estudio</h1>
        
        <div style="margin: 20px 0;">
            <h3><%= e.getNombreEstudio() %></h3>
            <p style="white-space: pre-wrap; background: #f8f9fa; padding: 15px; border-radius: 5px;"><%= e.getDescripcion() %></p>
        </div>
        
        <div style="display: flex; gap: 10px; margin-top: 20px;">
            <form action="${pageContext.request.contextPath}/SvModificarEstudio" method="GET">
                <input type="hidden" name="idEditar" value="<%= e.getIdEstudio() %>">
                <input type="hidden" name="idGato" value="<%= idGato %>">
                <button type="submit" class="btn-secondary">Editar</button>
            </form>
            
            <form action="${pageContext.request.contextPath}/SvEliminarEstudio" method="POST">
                <input type="hidden" name="idEliminar" value="<%= e.getIdEstudio() %>">
                <input type="hidden" name="idGato" value="<%= idGato %>">
                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar este estudio?');">Eliminar</button>
            </form>
        </div>
    </div>
</div>
<%@include file="../templates/footer.jsp" %>
