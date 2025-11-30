<%@page import="modelo.Zona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Gato</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body>
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
    </body>
</html>
