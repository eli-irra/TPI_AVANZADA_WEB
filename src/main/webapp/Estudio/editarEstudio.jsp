<%@page import="modelo.Estudio"%>
<%@include file="../templates/menu.jsp" %>

<%
    String idGato = (String) session.getAttribute("idGatoVolver");
    Estudio e = (Estudio) session.getAttribute("estudioEditar");
    long idHistoria = e.getHistoriaClinica().getidHistoria();
%>

<div class="container" style="max-width: 600px; margin-top: 40px;">
    <div class="card">
        <div class="header-flex" style="justify-content: center;">
            <h2>Editar Estudio</h2>
        </div>
        <hr>
        
        <form action="${pageContext.request.contextPath}/SvModificarEstudio" method="POST">
            <input type="hidden" name="idEstudio" value="<%= e.getIdEstudio() %>">
            <input type="hidden" name="idGato" value="<%= idGato %>">
            
            <div class="form-group">
                <label>Tipo de Estudio:</label>
                <input type="text" name="nombreEstudio" class="form-control" value="<%= e.getNombreEstudio() %>" required>
            </div>
            
            <div class="form-group">
                <label>Resultado:</label>
                <textarea name="descripcion" class="form-control" rows="5" required><%= e.getDescripcion() %></textarea>
            </div>
            
            <div style="display: flex; gap: 10px; margin-top: 20px;">
                <button type="submit" class="btn-primary full-width">Guardar Cambios</button>
                
                <a href="${pageContext.request.contextPath}/SvVerHistoriaClinica?idHistoria=<%= idHistoria %>&idGato=<%= idGato %>" class="btn-secondary full-width" style="text-align: center;">
                    Cancelar
                </a>
            </div>
        </form>
    </div>
</div>

<%@include file="../templates/footer.jsp" %>