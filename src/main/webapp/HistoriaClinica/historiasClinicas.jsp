<%@page import="modelo.Gato, modelo.HistoriaClinica, java.util.List, java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../templates/menu.jsp" %>

<%
    // Recuperamos los datos de la sesión (Colocados por SvHistoriasClinicas)
    Gato gato = (Gato) session.getAttribute("gatoSeleccionado");
    List<HistoriaClinica> listaConsultas = (List<HistoriaClinica>) session.getAttribute("listaConsultas");
%>

<div class="container">
    
    <% if (gato != null) { %>
        <div class="profile-header">
             <div>
                    <h1>Historial Médico: <%= gato.getNombre() %></h1>
                    <p style="color: #64748b; margin: 0;">Consulta y gestión de visitas médicas</p>
            </div>
            <form action="${pageContext.request.contextPath}/SvVerPerfilGato" method="GET" style="display: inline;">
                <input type="hidden" name="idVer" value="<%= gato.getIdGato() %>">
                <button type="submit" class="btn-secondary">
                    <i class="fas fa-arrow-left"></i> Volver al Perfil
                </button>
            </form>
        </div>

        <hr class="divider">

        <div style="margin-bottom: 20px; text-align: right;">
            <form action="${pageContext.request.contextPath}/SvNuevaConsulta" method="POST" style="display:inline;">
                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                <button type="submit" class="btn-primary bg-violet">
                    <i class="fas fa-notes-medical"></i> Nueva Consulta
                </button>
            </form>
        </div>

        <div class="card full-width">
            <table class="table-hover">
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Motivo / Descripción</th>
                        <th>ID Consulta</th>
                        <th style="text-align: center;">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        if (listaConsultas != null && !listaConsultas.isEmpty()) {
                            for (HistoriaClinica hc : listaConsultas) {
                    %>
                    <tr>
                        <td>
                            <i class="far fa-calendar-alt" style="color: var(--primary);"></i> 
                            <%= hc.getFechaCreacion() %>
                        </td>
                        
                        <td>
                            <strong><%= (hc.getDescripcion() != null && !hc.getDescripcion().isEmpty()) ? hc.getDescripcion() : "Consulta General" %></strong>
                        </td>
                        
                        <td>
                            <span class="badge" style="background-color: #e2e8f0; color: #475569;">#<%= hc.getidHistoria() %></span>
                        </td>
                        
                        <td style="display: flex; justify-content: center; gap: 10px;">
                            
                            <form action="${pageContext.request.contextPath}/SvVerHistoriaClinica" method="GET">
                                <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon" title="Ver Detalles">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                                </button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvEliminarHistoriaClinica" method="POST">
                                <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Está seguro de eliminar esta consulta completa? Se borrarán sus tratamientos y estudios.');" title="Eliminar Consulta">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                </button>
                            </form>
                            
                        </td>
                    </tr>
                    <% 
                            }
                        } else { 
                    %>
                    <tr>
                        <td colspan="4" class="text-center" style="padding: 30px; color: #64748b;">
                            <i class="fas fa-folder-open" style="font-size: 2rem; margin-bottom: 10px; display: block;"></i>
                            Este paciente no tiene consultas registradas.
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

    <% } else { %>
        <div class="error-container">
            <h2>Error de Navegación</h2>
            <p>No se ha seleccionado ningún gato.</p>
            <a href="${pageContext.request.contextPath}/SvGatos" class="btn-primary">Ir al Listado de Gatos</a>
        </div>
    <% } %>

</div>

<%@include file="../templates/footer.jsp" %>