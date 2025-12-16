<%@page import="modelo.Gato, modelo.HistoriaClinica, modelo.Estudio, modelo.Tratamiento, java.util.List"%>
<%@include file="../templates/menu.jsp" %>

<%
    // Recuperamos los objetos de la sesión
    Gato gato = (Gato) session.getAttribute("gatoActual");
    HistoriaClinica hc = (HistoriaClinica) session.getAttribute("historiaClinica");
%>

<div class="container">
    
    <div class="header-section" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
        <div>
            <h1><i class="fas fa-file-medical"></i> Historia Clínica</h1>
            <h3 style="color: #64748b;">Paciente: <%= (gato != null) ? gato.getNombre() : "Desconocido" %></h3>
        </div>
        
        </div>

    <div class="card full-width" style="margin-bottom: 30px;">
        <div style="display: flex; justify-content: space-between; align-items: center;">
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
        
        <table style="width: 100%; margin-top: 15px;">
            <thead>
                <tr>
                    <th>Nombre del Estudio</th>
                    <th>Resultados</th>
                    <th style="width: 150px;">Acciones</th>
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
                                <button type="submit" class="btn-icon" title="Ver"><i class="fas fa-eye"></i></button>
                            </form>
                            
                            <form action="${pageContext.request.contextPath}/SvEliminarEstudio" method="POST" style="display:inline;">
                                <input type="hidden" name="idEliminar" value="<%= e.getIdEstudio() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar estudio?');"><i class="fas fa-trash"></i></button>
                            </form>
                        </td>
                    </tr>
                <% 
                    } 
                } else { 
                %>
                    <tr><td colspan="3" style="text-align: center; color: gray; font-style: italic;">No hay estudios registrados.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="card full-width" style="border-top: 4px solid #2563eb;">
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
        
        <table style="width: 100%; margin-top: 15px;">
            <thead>
                <tr>
                    <th>Diagnóstico</th>
                    <th>Tratamiento / Procedimiento</th>
                    <th style="width: 150px;">Acciones</th>
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
                                <button type="submit" class="btn-icon" title="Ver"><i class="fas fa-eye"></i></button>
                            </form>
                            
                            <form action="${pageContext.request.contextPath}/SvEliminarTratamiento" method="POST" style="display:inline;">
                                <input type="hidden" name="idEliminar" value="<%= t.getidTratamiento() %>">
                                <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                                <button type="submit" class="btn-icon-danger" onclick="return confirm('¿Eliminar tratamiento?');"><i class="fas fa-trash"></i></button>
                            </form>
                        </td>
                    </tr>
                <% 
                    } 
                } else { 
                %>
                    <tr><td colspan="3" style="text-align: center; color: gray; font-style: italic;">No hay tratamientos activos.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>

<%@include file="../templates/footer.jsp" %>