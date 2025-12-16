<%@page import="modelo.Tratamiento"%>
<%
    String idGato = (String) session.getAttribute("idGatoVolver");
    Tratamiento t = (Tratamiento) session.getAttribute("tratamientoEditar");
%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Editar Tratamiento</h2>
    <form action="${pageContext.request.contextPath}/SvModificarTratamiento" method="POST">
        <input type="hidden" name="idTratamiento" value="<%= t.getidTratamiento() %>">
        <input type="hidden" name="idGato" value="<%= idGato %>">
        
        <label>Diagnóstico:</label>
        <input type="text" name="diagnostico" value="<%= t.getDiagostico() %>" required>
        
        <label>Tratamiento / Medicación:</label>
        <textarea name="descripcion" rows="5" required><%= t.getTratamiento() %></textarea>
        
        <br><br>
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
</div>
<%@include file="../templates/footer.jsp" %>