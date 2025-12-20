<%@page import="modelo.Tratamiento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../templates/menu.jsp" %>

<%
    // Recuperamos el objeto con el MISMO NOMBRE que pusimos en el Servlet
    Tratamiento t = (Tratamiento) session.getAttribute("tratamientoActual");
    String idGatoVolver = (String) session.getAttribute("idGatoVolver");
%>

<div class="container" style="max-width: 700px; margin-top: 40px;">

    <% if (t != null) { %>

        <div class="card">
            <div class="header-flex" style="flex-direction: column; align-items: center; text-align: center;">
                <div style="background: var(--bg-color); padding: 15px; border-radius: 50%; margin-bottom: 10px;">
                    <i class="fas fa-stethoscope" style="font-size: 2rem; color: var(--primary);"></i>
                </div>
                <h2>Detalle del Tratamiento</h2>
            </div>
            
            <hr class="divider">
            
            <div style="padding: 10px;">
                <div style="margin-bottom: 20px;">
                    <label style="color: #64748b; font-size: 0.9rem; font-weight: bold; text-transform: uppercase;">Diagnóstico:</label>
                    <div style="background: #f1f5f9; padding: 15px; border-radius: 8px; margin-top: 5px; font-size: 1.1rem; color: var(--primary); font-weight: 600;">
                        <%= t.getDiagostico() %> </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="color: #64748b; font-size: 0.9rem; font-weight: bold; text-transform: uppercase;">Descripción / Procedimiento:</label>
                    <div style="background: #fff; border: 1px solid #e2e8f0; padding: 15px; border-radius: 8px; margin-top: 5px; line-height: 1.6;">
                        <%= t.getTratamiento() %>
                    </div>
                </div>
            </div>

            <div style="text-align: center; margin-top: 20px;">
                <% if (idGatoVolver != null) { %>
                    <form action="${pageContext.request.contextPath}/SvVerHistoriaClinica" method="GET">
                        <input type="hidden" name="idGato" value="<%= idGatoVolver %>">
                        <button type="submit" class="btn-secondary">Volver a Historia Clínica</button>
                    </form>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/SvGatos" class="btn-secondary">Volver al Listado</a>
                <% } %>
            </div>
        </div>

    <% } else { %>
        <div class="error-container">
            <h3>No se encontró la información del tratamiento.</h3>
            <p>Es posible que el acceso haya caducado o sea incorrecto.</p>
            <a href="${pageContext.request.contextPath}/SvGatos" class="btn-primary">Ir al inicio</a>
        </div>
    <% } %>

</div>

<%@include file="../templates/footer.jsp" %>