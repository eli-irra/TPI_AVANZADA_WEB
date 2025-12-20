<%@page import="modelo.Gato"%>
<%@include file="../templates/menu.jsp" %>

<%
    Gato gato = (Gato) session.getAttribute("gatoSalud");
%>

<div class="container" style="max-width: 600px; margin-top: 50px;">
    
    <% if (gato != null) { %>
        <div class="card">
            <div class="header-flex" style="justify-content: center; flex-direction: column; text-align: center;">
                <div style="background: var(--bg-color); padding: 15px; border-radius: 50%; margin-bottom: 10px; display: inline-block;">
                    <i class="fas fa-heartbeat" style="font-size: 2rem; color: var(--primary);"></i>
                </div>
                <h2>Actualizar Estado de Salud</h2>
                <h4 class="text-muted">Paciente: <strong><%= gato.getNombre() %></strong></h4>
            </div>
            
            <hr class="divider">
            
            <div style="padding: 20px; background-color: #f8fafc; border-radius: 8px; margin-bottom: 20px; text-align: center;">
                <p>Estado Actual:</p>
                <span class="badge-lg" style="
                      background-color: <%= (gato.getestadoFisico() == Gato.EstadoSalud.SANO) ? "#10b981" : (gato.getestadoFisico() == Gato.EstadoSalud.EN_TRATAMIENTO ? "#f59e0b" : "#ef4444") %>;
                      color: white;">
                    <%= gato.getestadoFisico().toString().replace("_", " ") %>
                </span>
            </div>

            <form action="${pageContext.request.contextPath}/SvCambiarEstadoSalud" method="POST">
                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                
                <div class="form-group">
                    <label for="nuevoEstado">Seleccione el Nuevo Estado:</label>
                    <select name="nuevoEstado" id="nuevoEstado" class="form-select" required style="padding: 10px; font-size: 1.1rem;">
                        <option value="SANO" <%= (gato.getestadoFisico() == Gato.EstadoSalud.SANO) ? "selected" : "" %>>SANO</option>
                        <option value="EN_TRATAMIENTO" <%= (gato.getestadoFisico() == Gato.EstadoSalud.EN_TRATAMIENTO) ? "selected" : "" %>>EN TRATAMIENTO</option>
                        <option value="ENFERMO" <%= (gato.getestadoFisico() == Gato.EstadoSalud.ENFERMO) ? "selected" : "" %>>ENFERMO</option>
                    </select>
                </div>

                <div style="display: flex; gap: 10px; margin-top: 20px;">
                    <button type="submit" class="btn-primary full-width">Guardar Cambio</button>
                    <a href="${pageContext.request.contextPath}/SvVerPerfilGato?idVer=<%= gato.getIdGato() %>" class="btn-secondary full-width" style="text-align: center;">Cancelar</a>
                </div>
            </form>
        </div>
    <% } else { %>
        <div class="error-container">
            <h3>Error: Gato no seleccionado</h3>
            <a href="${pageContext.request.contextPath}/SvGatos" class="btn-primary">Volver al Listado</a>
        </div>
    <% } %>
</div>

<%@include file="../templates/footer.jsp" %>