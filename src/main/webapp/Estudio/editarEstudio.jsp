<%@page import="modelo.Estudio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String idGato = (String) session.getAttribute("idGatoVolver");
    Estudio e = (Estudio) session.getAttribute("estudioEditar");
    request.setAttribute("linkVolver", "SvVerEstudio?idEstudio=" + e.getIdEstudio() + "&idGato=" + idGato);
%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Editar Estudio</h2>
    <form action="${pageContext.request.contextPath}/SvModificarEstudio" method="POST">
        <input type="hidden" name="idEstudio" value="<%= e.getIdEstudio() %>">
        <input type="hidden" name="idGato" value="<%= idGato %>">
        
        <label>Tipo de Estudio:</label>
        <input type="text" name="nombreEstudio" value="<%= e.getNombreEstudio() %>" required>
        
        <label>Resultado:</label>
        <textarea name="descripcion" rows="5" required><%= e.getDescripcion() %></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
</div>
<%@include file="../templates/footer.jsp" %>