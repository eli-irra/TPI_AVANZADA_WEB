<%@page import="modelo.Gato, modelo.HistoriaClinica, modelo.Estudio, modelo.Tratamiento, java.util.List"%>
<%@include file="../templates/menu.jsp" %>
<%
    Gato gato = (Gato) session.getAttribute("gatoHistoria");
    
    // Fallback por seguridad
    if(gato == null) gato = (Gato) session.getAttribute("gatoActual"); 
    
    HistoriaClinica hc = (HistoriaClinica) session.getAttribute("historiaClinica");
%>

<div class="container">
    
    <div class="header-section header-flex">
        <div>
            <h1><i class="fas fa-file-medical"></i> Historia Clínica</h1>
            <h3 class="text-muted">Paciente: <%= (gato != null) ? gato.getNombre() : "Desconocido" %></h3>
        </div>
        
        <form action="${pageContext.request.contextPath}/SvVerPerfilGato" method="GET">
             <input type="hidden" name="idVer" value="<%= (gato != null) ? gato.getIdGato() : "" %>">
             <button type="submit" class="btn-secondary">Volver al Perfil</button>
        </form>
    </div>

    <div class="card w-100 mb-30 card-top-accent">
        <div class="header-flex">
            <h2><i class="fas fa-stethoscope"></i> Tratamientos y Diagnósticos</h2>
            
            <% if (hc != null && gato != null) { %>
            <form action="${pageContext.request.contextPath}/Tratamiento/registrarTratamiento.jsp" method="GET">
                <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                <button type="submit" class="btn-primary" style="font-size: 0.9rem;">+ Nuevo Tratamiento</button>
            </form>
            <% } %>
        </div>
        <hr>
        
        <table class="w-100 mt-1 table-hover">
            <thead>
                <tr>
                    <th style="width: 25%;">Diagnóstico</th>
                    <th>Tratamiento / Descripción</th>
                    <th class="w-150px" style="text-align: center;">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (hc != null && hc.getTratamientos() != null && !hc.getTratamientos().isEmpty()) {
                    for (Tratamiento t : hc.getTratamientos()) { 
                %>
                    <tr>
                        <td>
                            <strong style="color: var(--primary); font-size: 1.05rem;">
                                <%= t.getDiagostico() %>
                            </strong>
                        </td>
                        
                        <td>
                            <%= (t.getTratamiento() != null && t.getTratamiento().length() > 80) 
                                ? t.getTratamiento().substring(0, 80) + "..." 
                                : t.getTratamiento() %>
                        </td>
                        
                        <td style="text-align: center;">
                            <div style="display: flex; justify-content: center; gap: 5px;">
                                <form action="${pageContext.request.contextPath}/SvVerTratamiento" method="GET">
                                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                    <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                                    <button type="submit" class="btn-icon" title="Ver Detalle Completo">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                                    </button>
                                </form>
                                
                                <form action="${pageContext.request.contextPath}/SvEliminarTratamiento" method="POST">
                                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                    <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar este tratamiento?');" title="Eliminar">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                <% 
                    } 
                } else { 
                %>
                    <tr><td colspan="3" class="text-center text-muted italic" style="padding: 20px;">No hay tratamientos registrados en esta consulta.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="card w-100 mb-30">
        <div class="header-flex">
            <h2><i class="fas fa-microscope"></i> Estudios Complementarios</h2>
            
            <% if (hc != null && gato != null) { %>
            <form action="${pageContext.request.contextPath}/Estudio/registrarEstudio.jsp" method="GET">
                <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                <button type="submit" class="btn-secondary" style="font-size: 0.9rem;">+ Nuevo Estudio</button>
            </form>
            <% } %>
        </div>
        <hr>
        
        <table class="w-100 mt-1 table-hover">
            <thead>
                <tr>
                    <th style="width: 25%;">Estudio</th>
                    <th>Resultados / Observaciones</th>
                    <th class="w-150px" style="text-align: center;">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (hc != null && hc.getEstudios() != null && !hc.getEstudios().isEmpty()) {
                    for (Estudio e : hc.getEstudios()) { 
                %>
                    <tr>
                        <td><strong><%= e.getNombreEstudio() %></strong></td>
                        <td>
                            <%= (e.getDescripcion() != null && e.getDescripcion().length() > 80) 
                                ? e.getDescripcion().substring(0, 80) + "..." 
                                : e.getDescripcion() %>
                        </td>
                        <td style="text-align: center;">
                            <div style="display: flex; justify-content: center; gap: 5px;">
                                <form action="${pageContext.request.contextPath}/SvVerEstudio" method="GET">
                                    <input type="hidden" name="idEstudio" value="<%= e.getIdEstudio() %>">
                                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                    <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                                    <button type="submit" class="btn-icon" title="Ver Detalle">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-eye"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" /><path d="M21 12c-2.4 4 -5.4 6 -9 6c-3.6 0 -6.6 -2 -9 -6c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6" /></svg>
                                    </button>
                                </form>
                                
                                <form action="${pageContext.request.contextPath}/SvEliminarEstudio" method="POST">
                                    <input type="hidden" name="idEliminar" value="<%= e.getIdEstudio() %>">
                                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                    <input type="hidden" name="idHistoria" value="<%= hc.getidHistoria() %>">
                                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar estudio?');" title="Eliminar">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-trash"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                <% 
                    } 
                } else { 
                %>
                    <tr><td colspan="3" class="text-center text-muted italic" style="padding: 20px;">No hay estudios registrados.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>

<%@include file="../templates/footer.jsp" %>