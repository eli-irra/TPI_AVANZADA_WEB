<%@page import="modelo.Gato"%>
<%@page import="java.util.List"%>
<%@include file="templates/menu.jsp" %>
        <h1>Mis Gatos Rescatados</h1>
        <button onclick="window.location.href='SvCargarAltaGato'">+ Registrar Nuevo Gato</button>
        <br><br>
        
        <table border="1">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Raza</th>
                    <th>Color</th>
                    <th>Sexo</th>
                    <th>Zona</th> <th>Disponible</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Gato> lista = (List<Gato>) session.getAttribute("listaGatos");
                    if(lista != null) {
                        for(Gato g : lista) {
                %>
                    <tr>
                        <td><%= g.getNombre() %></td>
                        <td><%= g.getRaza() %></td>
                        <td><%= g.getColor() %></td>
                        <td><%= g.getSexo() %></td>
                        
                        <td><%= (g.getZona() != null) ? g.getZona().getNombreZona() : "Sin Zona" %></td>
                        
                        <td><%= g.getDisponible() %></td>
                        <td>
                            <button>Editar</button>
                            <button>Historia C.</button>
                        </td>
                    </tr>
                <%      }
                    }
                %>
            </tbody>
        </table>
        <br>
        <a href="menu.jsp">Volver al Menú</a>
<%@include file="templates/footer.jsp" %>
