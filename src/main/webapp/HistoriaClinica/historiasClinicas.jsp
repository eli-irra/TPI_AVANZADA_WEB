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
             <div style="display: flex; align-items: center; gap: 15px;">
                <div style="background: white; padding: 5px; border-radius: 50%; border: 2px solid #ddd;">
                    <img src="${pageContext.request.contextPath}/images/paw.svg" alt="Gato" width="40">
                </div>
                <div>
                    <h1>Historial Médico: <%= gato.getNombre() %></h1>
                    <p style="color: #64748b; margin: 0;">Consulta y gestión de visitas médicas</p>
                </div>
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
                                    <i class="fas fa-eye"></i>
                                </button>
                            </form>

                            <form action="${pageContext.request.contextPath}/SvEliminarHistoriaClinica" method="POST">
                                <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Está seguro de eliminar esta consulta completa? Se borrarán sus tratamientos y estudios.');" title="Eliminar Consulta">
                                    <i class="fas fa-trash-alt"></i>
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