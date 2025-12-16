<%@page import="modelo.Gato, modelo.HistoriaClinica, modelo.Estudio, modelo.Tratamiento, java.util.List"%>
<%@include file="../templates/menu.jsp" %>

<%
    // Recuperamos los objetos de la sesión
    Gato gato = (Gato) session.getAttribute("gatoActual");
    HistoriaClinica hc = (HistoriaClinica) session.getAttribute("historiaClinica");
%>

<div class="container">
    
    <div <div class="header-section header-flex">>
        <div>
            <h1><i class="fas fa-file-medical"></i> Historia Clínica</h1>
            <h3 class="text-muted">Paciente: <%= (gato != null) ? gato.getNombre() : "Desconocido" %></h3>
        </div>
        
        </div>

    <div class="card w-100 mb-30">
        <div class="header-flex">
            <h2>? Estudios Realizados</h2>
            
            <% if (hc != null) { %>
            <form action="${pageContext.request.contextPath}/SvAgregarEstudio" method="GET">
                <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                <button type="submit" class="btn-primary" style="font-size: 0.9rem;">+ Nuevo Estudio</button>
            </form>
            <% } %>
        </div>
        <hr>
        
        <table class="w-100 mt-1">
            <thead>
                <tr>
                    <th>Nombre del Estudio</th>
                    <th>Resultados</th>
                    <th class="w-150px">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (hc != null && hc.getEstudios() != null && !hc.getEstudios().isEmpty()) {
                    for (Estudio e : hc.getEstudios()) { 
                %>
                    <tr>
                        <td><strong><%= e.getNombreEstudio() %></strong></td>
                        <td><%= (e.getDescripcion().length() > 60) ? e.getDescripcion().substring(0, 60) + "..." : e.getDescripcion() %></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/SvVerEstudio" method="GET" style="display:inline;">
                                <input type="hidden" name="idEstudio" value="<%= e.getIdEstudio() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon" title="Ver">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                            </button>
                            </form>
                            
                            <form action="${pageContext.request.contextPath}/SvEliminarEstudio" method="POST" style="display:inline;">
                                <input type="hidden" name="idEliminar" value="<%= e.getIdEstudio() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar estudio?');">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                </button>
                            </form>
                        </td>
                    </tr>
                <% 
                    } 
                } else { 
                %>
                    <tr><td colspan="3" class="text-center text-muted italic">No hay estudios registrados.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="card w-100 card-top-accent">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <h2>? Tratamientos y Medicación</h2>
            
            <% if (hc != null) { %>
            <form action="${pageContext.request.contextPath}/SvAgregarTratamiento" method="GET">
                <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                <button type="submit" class="btn-primary" style="font-size: 0.9rem;">+ Nuevo Tratamiento</button>
            </form>
            <% } %>
        </div>
        <hr>
        
        <table class="w-100 mt-1">
            <thead>
                <tr>
                    <th>Diagnóstico</th>
                    <th>Tratamiento / Procedimiento</th>
                    <th class="w-150px">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (hc != null && hc.getTratamientos() != null && !hc.getTratamientos().isEmpty()) {
                    for (Tratamiento t : hc.getTratamientos()) { 
                %>
                    <tr>
                        <td><strong><%= t.getDiagostico() %></strong></td>
                        <td><%= (t.getTratamiento().length() > 60) ? t.getTratamiento().substring(0, 60) + "..." : t.getTratamiento() %></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/SvVerTratamiento" method="GET" style="display:inline;">
                                <input type="hidden" name="idTratamiento" value="<%= t.getidTratamiento() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon" title="Ver">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                                </button>
                            </form>
                            
                            <form action="${pageContext.request.contextPath}/SvEliminarTratamiento" method="POST" style="display:inline;">
                                <input type="hidden" name="idEliminar" value="<%= t.getidTratamiento() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar tratamiento?');">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                </button>
                            </form>
                        </td>
                    </tr>
                <% 
                    } 
                } else { 
                %>
                    <tr><td colspan="3" class="text-center text-muted italic">No hay tratamientos activos.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>

<%@include file="../templates/footer.jsp" %>