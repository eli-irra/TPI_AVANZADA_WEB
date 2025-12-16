<%-- Acá se encuentra el listado de tareas asociadas a un gato o zona, solamente el Voluntario puede acceder a esta pagina.--%>
<%@page import="modelo.Tarea, java.util.List, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "menu.jsp"); %>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Tareas</h1>
    
    <div class="filtros-container justify-end">
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
            
            <td class="table-actions">
                <form action="${pageContext.request.contextPath}/SvVerTarea" method="GET">
                    <input type="hidden" name="idTarea" value="<%= t.getIdTarea() %>">
                    <button type="submit" class="btn-icon" title="Ver Detalle">
                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                    </button>
                </form>

                <form action="${pageContext.request.contextPath}/SvModificarTarea" method="GET">
                    <input type="hidden" name="idEditar" value="<%= t.getIdTarea() %>">
                        <button type="submit" class="btn-icon" title="Editar">
                            <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-pencil"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 20h4l10.5 -10.5a2.828 2.828 0 1 0 -4 -4l-10.5 10.5v4" /><path d="M13.5 6.5l4 4" /></svg>
                        </button>
                </form>

                <form action="${pageContext.request.contextPath}/SvEliminarTarea" method="POST">
                    <input type="hidden" name="idEliminar" value="<%= t.getIdTarea() %>">
                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar tarea?');" title="Borrar">
                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                    </button>
                </form>
            </td>
        </tr>
    <% }} else { %>
        <tr>
            <td colspan="6" class="text-center">No hay tareas registradas.</td>
        </tr>
    <% } %>
</tbody>
    </table>
</div>
<%@include file="../templates/footer.jsp" %>