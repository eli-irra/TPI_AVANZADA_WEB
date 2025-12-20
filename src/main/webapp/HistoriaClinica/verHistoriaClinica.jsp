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
                                    <input type="hidden" name="idTratamiento" value="<%= t.getidTratamiento() %>">
                                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                    <button type="submit" class="btn-icon" title="Ver Detalle Completo">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </form>
                                
                                <form action="${pageContext.request.contextPath}/SvEliminarTratamiento" method="POST">
                                    <input type="hidden" name="idEliminar" value="<%= t.getidTratamiento() %>">
                                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar este tratamiento?');" title="Eliminar">
                                        <i class="fas fa-trash-alt"></i>
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
                                    <button type="submit" class="btn-icon" title="Ver Detalle"><i class="fas fa-eye"></i></button>
                                </form>
                                
                                <form action="${pageContext.request.contextPath}/SvEliminarEstudio" method="POST">
                                    <input type="hidden" name="idEliminar" value="<%= e.getIdEstudio() %>">
                                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                    <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar estudio?');" title="Eliminar">
                                        <i class="fas fa-trash-alt"></i>
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