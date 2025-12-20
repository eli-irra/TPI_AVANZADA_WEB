<%
    String idHistoria = request.getParameter("idHistoria");
    String idGato = request.getParameter("idGato");
%>
<%@include file="../templates/menu.jsp" %>

<div class="form-container">
    <h2>Registrar Nuevo Tratamiento</h2>
    <form action="${pageContext.request.contextPath}/SvAgregarTratamiento" method="POST">
    
        <input type="hidden" name="idHistoria" value="<%= idHistoria %>">
        <input type="hidden" name="idGato" value="<%= idGato %>">

        <label>Diagnóstico:</label>
        <input type="text" name="diagnostico" required>

        <label>Tratamiento / Descripción:</label>
        <input type="text" name="descripcion" required>

        <button type="submit" class="btn-primary">Guardar</button>
    </form>
</div>
<%@include file="../templates/footer.jsp" %>