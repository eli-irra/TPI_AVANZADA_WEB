<%@page import="modelo.Zona"%>
<%@page import="java.util.List"%>
<%@include file="templates/menu.jsp" %>
        <label>Zona asignada:</label>
        <select name="zona_id" required>
            <% 
                List<Zona> zonas = (List<Zona>) session.getAttribute("listaZonas");
                if(zonas != null) {
                    for(Zona z : zonas) {
            %>
                <option value="<%= z.getIdZona() %>"><%= z.getNombreZona() %></option>
            <% 
                    }
                }
            %>
        </select>
<%@include file="templates/footer.jsp" %>
