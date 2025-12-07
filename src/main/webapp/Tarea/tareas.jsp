<%-- Acá se encuentra el listado de tareas asociadas a un gato o zona, solamente el Voluntario puede acceder a esta pagina.--%>
<%@page import="modelo.Tarea, java.util.List, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "menu.jsp"); %>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Tareas</h1>
    
    <div class="filtros-container" style="justify-content: flex-end;">
        <a href="${pageContext.request.contextPath}/SvAltaTarea">
            <button class="btn-primary">+ Nueva Tarea</button>
        </a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Fecha</th>
                <th>Tipo</th>
                <th>Objetivo</th> <th>Voluntario</th>
                <th>Descripción</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
    <%
        List<Tarea> lista = (List<Tarea>) session.getAttribute("listaTareas");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (lista != null && !lista.isEmpty()) {
            for (Tarea t : lista) {
                // Lógica de visualización: Gato o Zona
                String objetivo = "-";
                String icono = "";
                
                if (t.getGatoAsignado() != null) {
                    objetivo = "Gato: " + t.getGatoAsignado().getNombre();
                    icono = "🐱";
                } else if (t.getZonaAsignada() != null) {
                    objetivo = "Zona: " + t.getZonaAsignada().getNombreZona();
                    icono = "📍";
                }
                
                String nombreVol = (t.getVoluntarioQueRealiza() != null) ? t.getVoluntarioQueRealiza().getNombre() : "-";
    %>
        <tr>
            <td><%= sdf.format(t.getFecha()) %></td>
            <td><span class="badge"><%= t.getTipoTarea() %></span></td>
            <td><%= icono %> <%= objetivo %></td>
            <td><%= nombreVol %></td>
            <td><%= t.getDescripcion() %></td>
            
            <td style="display:flex; gap:5px;">
                <form action="${pageContext.request.contextPath}/SvVerTarea" method="GET">
                    <input type="hidden" name="idTarea" value="<%= t.getIdTarea() %>">
                    <button type="submit" class="btn-icon" title="Ver Detalle">👁</button>
                </form>

                <form action="${pageContext.request.contextPath}/SvModificarTarea" method="GET">
                    <input type="hidden" name="idEditar" value="<%= t.getIdTarea() %>">
                    <button type="submit" class="btn-icon" title="Editar">✎</button>
                </form>

                <form action="${pageContext.request.contextPath}/SvEliminarTarea" method="POST">
                    <input type="hidden" name="idEliminar" value="<%= t.getIdTarea() %>">
                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar tarea?');" title="Borrar">🗑</button>
                </form>
            </td>
        </tr>
    <% }} else { %>
        <tr>
            <td colspan="6" style="text-align:center;">No hay tareas registradas.</td>
        </tr>
    <% } %>
</tbody>
    </table>
</div>
<%@include file="../templates/footer.jsp" %>