<%@include file="../templates/menu.jsp" %>
<%
    String idHistoria = request.getParameter("idHistoria");
    String idGato = request.getParameter("idGato");
%>

<div class="container" style="max-width: 600px; margin-top: 50px;">
    <div class="card">
        <div class="header-flex" style="justify-content: center;">
            <h2>Registrar Nuevo Estudio</h2>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/SvAgregarEstudio" method="POST">
            <input type="hidden" name="idHistoria" value="<%= idHistoria %>">
            <input type="hidden" name="idGato" value="<%= idGato %>">

            <div class="form-group">
                <label for="nombreEstudio">Tipo de Estudio:</label>
                <input type="text" class="form-control" id="nombreEstudio" name="nombreEstudio" required placeholder="Ej: Radiografía de Tórax, Hemograma...">
            </div>

            <div class="form-group">
                <label for="resultado">Resultados / Observaciones:</label>
                <textarea class="form-control" id="resultado" name="resultado" rows="4" required placeholder="Describa los resultados obtenidos..."></textarea>
            </div>

            <div style="margin-top: 20px;">
                <button type="submit" class="btn-primary full-width">Guardar Estudio</button>
            </div>
        </form>
    </div>
</div>

<%@include file="../templates/footer.jsp" %>