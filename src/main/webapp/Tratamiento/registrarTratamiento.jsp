<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String idGato = (String) session.getAttribute("idGatoActual");
    String idHistoria = (String) session.getAttribute("idHistoriaActual");
    request.setAttribute("linkVolver", "SvVerPerfilGato?idGato=" + idGato);
%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Registrar Nuevo Tratamiento</h2>
    <form action="${pageContext.request.contextPath}/SvAgregarTratamiento" method="POST">
        <input type="hidden" name="idHistoria" value="<%= idHistoria %>">
        <input type="hidden" name="idGato" value="<%= idGato %>">
        
        <label>Diagnóstico:</label>
        <input type="text" name="diagnostico" required placeholder="Ej: Gripe Felina, Otitis...">
        
        <label>Tratamiento / Medicación:</label>
        <textarea name="descripcion" rows="5" required placeholder="Detalle del procedimiento o medicación..."></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Tratamiento</button>
    </form>
</div>
<%@include file="../templates/footer.jsp" %>