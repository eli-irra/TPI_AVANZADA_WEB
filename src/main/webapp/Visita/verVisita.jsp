<%@page import="modelo.Visita"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "SvVisitas"); %>
<%@include file="../templates/menu.jsp" %>
<% Visita v = (Visita) session.getAttribute("visitaDetalle"); %>

<div class="container">
    <% if(v != null) { %>
    <div class="card" style="max-width: 600px; margin: auto; padding: 30px;">
        <h1>Detalle de Visita</h1>
        <hr>
        <p><strong>Fecha:</strong> <%= v.getFecha() %> a las <%= v.getHoraVisita() %>hs</p>
        <p><strong>Estado:</strong> <%= v.isRealizada() ? "✅ Realizada" : "⏳ Pendiente" %></p>
        <p><strong>Familia:</strong> <%= v.getFamilia().getNombre() %></p>
        <p><strong>Voluntario:</strong> <%= v.getVoluntarioEncargado().getNombre() %></p>
        <div style="background:#f9f9f9; padding:15px; margin-top:15px;">
            <strong>Notas:</strong><br>
            <%= v.getDescripcion() %>
        </div>
    </div>
    <% } %>
</div>
<%@include file="../templates/footer.jsp" %>