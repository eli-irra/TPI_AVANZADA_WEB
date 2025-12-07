<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String idGato = (String) session.getAttribute("idGatoActual");
    String idHistoria = (String) session.getAttribute("idHistoriaActual");
    request.setAttribute("linkVolver", "SvVerPerfilGato?idGato=" + idGato);
%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Registrar Nuevo Estudio</h2>
    <form action="${pageContext.request.contextPath}/SvAgregarEstudio" method="POST">
        <input type="hidden" name="idHistoria" value="<%= idHistoria %>">
        <input type="hidden" name="idGato" value="<%= idGato %>">
        
        <label>Tipo de Estudio:</label>
        <input type="text" name="nombreEstudio" required placeholder="Ej: Radiografía, Análisis de Sangre...">
        
        <label>Resultado / Diagnóstico:</label>
        <textarea name="descripcion" rows="5" required></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Estudio</button>
    </form>
</div>
<%@include file="../templates/footer.jsp" %>