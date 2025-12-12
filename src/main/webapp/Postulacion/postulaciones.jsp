<%@page import="modelo.Postulacion, modelo.Gato, modelo.FamiliaAdoptante, java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("linkVolver", "menu.jsp"); %>
<%@include file="../templates/menu.jsp" %>

<div class="container">
    <h1>Gestión de Postulaciones</h1>
    <%-- SECCIÓN 2: FORMULARIO MANUAL (En caso de que ninguna familia sirva o no haya postulaciones) --%>
    <div class="form-container" style="background-color: #f8fafc; border: 2px dashed #cbd5e1;">
        <h3 style="color: var(--secondary);">Registrar Postulación Manual</h3>
        <p style="font-size: 0.9rem; color: #64748b;">Use este formulario si la familia no se registró por sí misma o desea forzar una solicitud.</p>
        
        <form action="${pageContext.request.contextPath}/SvPostulaciones" method="POST">
            <input type="hidden" name="accion" value="registroManual">
            
            <label>Seleccionar Gato Disponible:</label>
            <select name="idGato" required>
                <% 
                    List<Gato> gatosDisp = (List<Gato>) session.getAttribute("gatosDisponibles");
                    if(gatosDisp != null && !gatosDisp.isEmpty()) { 
                        for(Gato g : gatosDisp) {
                %>
                    <option value="<%= g.getIdGato() %>"><%= g.getNombre() %> (<%= g.getRaza() %>)</option>
                <%      } 
                   } else { %>
                    <option value="" disabled selected>No hay gatos disponibles</option>
                <% } %>
            </select>

            <label>Seleccionar Familia:</label>
            <select name="idFamilia" required>
                <% 
                    List<FamiliaAdoptante> familias = (List<FamiliaAdoptante>) session.getAttribute("listaFamilias");
                    if(familias != null) { 
                        for(FamiliaAdoptante f : familias) {
                %>
                    <option value="<%= f.getIdUsuario() %>"><%= f.getNombre() %> (Tel: <%= (long)f.getTelefono() %>)</option>
                <% }} %>
            </select>

            <br><br>
            <button type="submit" class="btn-primary">Crear Solicitud</button>
        </form>
    </div>
    <%-- SECCIÓN 1: TABLA DE POSTULACIONES EXISTENTES --%>
    <div class="card full-width">
        <h3>Solicitudes Recibidas</h3>
        <table>
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Gato</th>
                    <th>Familia Interesada</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Postulacion> lista = (List<Postulacion>) session.getAttribute("listaPostulaciones");
                    if (lista != null && !lista.isEmpty()) {
                        for (Postulacion p : lista) {
                            String colorEstado = "gray";
                            if(p.getEstado() == Postulacion.Estado.PENDIENTE) colorEstado = "#f59e0b"; // Naranja
                            if(p.getEstado() == Postulacion.Estado.APROBADA) colorEstado = "#10b981"; // Verde
                            if(p.getEstado() == Postulacion.Estado.RECHAZADA) colorEstado = "#ef4444"; // Rojo
                %>
                <tr>
                    <td><%= p.getFechaCreacion() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/SvVerPerfilGato?idVer=<%= p.getGatoRelacionado().getIdGato() %>" style="color:var(--primary); font-weight:bold;">
                            <%= p.getGatoRelacionado().getNombre() %>
                        </a>
                    </td>
                    <td><%= p.getFamiliaPostulante().getNombre() %> <br><small>(<%= (long) p.getFamiliaPostulante().getTelefono() %>)</small></td>
                    <td>
                        <span style="color: <%= colorEstado %>; font-weight: bold; border: 1px solid <%= colorEstado %>; padding: 2px 8px; border-radius: 10px;">
                            <%= p.getEstado() %>
                        </span>
                    </td>
                    <td>
                        <% if (p.getEstado() == Postulacion.Estado.PENDIENTE) { %>
                            <div style="display:flex; gap:5px;">
                                <%-- BOTÓN APROBAR --%>
                                <form action="${pageContext.request.contextPath}/SvPostulaciones" method="POST">
                                    <input type="hidden" name="accion" value="cambiarEstado">
                                    <input type="hidden" name="idPostulacion" value="<%= p.getIdPostulacion() %>">
                                    <input type="hidden" name="estado" value="APROBADA">
                                    <button type="submit" class="btn-icon" 
                                            onclick="return confirm('¿Aprobar adopción? Esto asignará el gato automáticamente.');">
                                        Aprobar
                                    </button>
                                </form>
                                
                                <%-- BOTÓN RECHAZAR --%>
                                <form action="${pageContext.request.contextPath}/SvPostulaciones" method="POST">
                                    <input type="hidden" name="accion" value="cambiarEstado">
                                    <input type="hidden" name="idPostulacion" value="<%= p.getIdPostulacion() %>">
                                    <input type="hidden" name="estado" value="RECHAZADA">
                                    <button type="submit" class="btn-icon-danger" title="Rechazar">Rechazar</button>
                                </form>
                            </div>
                        <% } else { %>
                            <span style="color: #cbd5e1;">- Cerrada -</span>
                        <% } %>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr><td colspan="5" style="text-align:center;">No hay postulaciones registradas.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <br><hr><br>

    

</div>

<%@include file="../templates/footer.jsp" %>