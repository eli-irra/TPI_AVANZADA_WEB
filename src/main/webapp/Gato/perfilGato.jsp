<%-- Acá debería estar el perfil del gato (sus datos), visualizando las opciones dependiendo de que tipo de usuario esté accediendo, por ejemplo, el veterinario va a poder 
acceder al historial clinico y modificar el estado de salud, mientras que la familia adoptante solamente podra ver los datos normales del gato y postularse, 
asi como visualizar el mapa, qr, etc. Y el voluntario va a poder presionar un boton de editarPerfil para modificar los datos, o registrar una tarea asociada al gato, etc--%>

<%@page import="modelo.Gato"%>
<%@include file="../templates/menu.jsp" %>

<%
    // Recuperamos el gato cargado por el Servlet
    Gato gato = (Gato) session.getAttribute("gatoPerfil");
%>

<div class="container">
    <% if (gato != null) { %>
        
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>Perfil de <%= gato.getNombre() %></h1>
            
            <% if (gato.getDisponible() == modelo.Gato.RespuestaBinaria.SI) { %>
                <span style="background: #28a745; color: white; padding: 5px 15px; border-radius: 20px; font-weight: bold;">Disponible</span>
            <% } else { %>
                <span style="background: #dc3545; color: white; padding: 5px 15px; border-radius: 20px; font-weight: bold;">Adoptado</span>
            <% } %>
        </div>

        <div class="perfil-grid">
            
            <div class="card">
                <h3>? Datos Generales</h3>
                <ul class="lista-datos">
                    <li><strong>Raza:</strong> <%= gato.getRaza() %></li>
                    <li><strong>Sexo:</strong> <%= gato.getSexo() %></li>
                    <li><strong>Color:</strong> <%= gato.getColor() %></li>
                    <li><strong>Fecha Alta:</strong> <%= gato.getFecha() %></li>
                    <li><strong>Esterilizado:</strong> <%= gato.getEsterilizado() %></li>
                    <li><strong>Salud:</strong> <%= gato.getestadoFisico() %></li>
                </ul>
            </div>

            <div class="card">
                <h3>? Ubicación y Estado</h3>
                <p style="margin-bottom: 15px;">
                    <strong>Zona Actual:</strong> 
                    <%= (gato.getZona() != null) ? gato.getZona().getNombreZona() : "Sin asignar" %>
                </p>
                
                <hr style="border: 0; border-top: 1px solid #eee; margin: 10px 0;">
                
                <% if(gato.getFamiliaAdoptante() != null) { %>
                    <div style="background-color: #f0fff4; padding: 10px; border-radius: 5px; border: 1px solid #c3e6cb;">
                        <h4 style="margin: 0 0 5px 0; color: #155724;">? ¡Tiene Familia!</h4>
                        <p style="margin: 0;"><strong>Adoptante:</strong> <%= gato.getFamiliaAdoptante().getNombre() %></p>
                        <p style="margin: 0;"><strong>Contacto:</strong> <%= (long)gato.getFamiliaAdoptante().getTelefono() %></p>
                        <p style="margin: 0;"><strong>Dirección:</strong> <%= gato.getFamiliaAdoptante().getdireccion() %></p>
                    </div>
                <% } else { %>
                    <p style="color: #6c757d; font-style: italic;">Este gato aún busca un hogar.</p>
                <% } %>
            </div>

            <div class="card full-width">
                <h3>? Características y Observaciones</h3>
                <p style="line-height: 1.6; color: #4a5568;">
                    <%= (gato.getCaracteristicas() != null && !gato.getCaracteristicas().isEmpty()) 
                        ? gato.getCaracteristicas() 
                        : "No se han registrado observaciones adicionales." %>
                </p>
            </div>
            
            <div class="full-width" style="text-align: right; margin-top: 10px;">
                <form action="${pageContext.request.contextPath}/SvModificarGato" method="GET" style="display:inline;">
                    <input type="hidden" name="idEditar" value="<%= gato.getIdGato() %>">
                    <button type="submit" class="btn-primary">Editar Datos</button>
                </form>
            </div>
            <td>
                <form action="${pageContext.request.contextPath}/SvHistoriaClinica" method="GET" style="display:inline;">
                    <input type="hidden" name="idGato" value="<%= gato.getIdGato() %>">
                    <button type="submit" class="btn-secondary" title="Ver Historia Clínica" style="padding: 5px 10px;">
                        <i class="fas fa-file-medical"></i> Historia C.
                    </button>
                </form>
            </td>
        </div>

    <% } else { %>
        <div style="text-align: center; padding: 50px;">
            <h2>?? Error</h2>
            <p>No se pudo cargar la información del gato.</p>
            <a href="gatos.jsp">Volver a la lista</a>
        </div>
    <% } %>
</div>

<style>
    .perfil-grid {
        display: grid;
        grid-template-columns: 1fr 1fr; /* Dos columnas iguales */
        gap: 20px;
    }
    
    /* En móviles, una sola columna */
    @media (max-width: 768px) {
        .perfil-grid { grid-template-columns: 1fr; }
    }

    .card {
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        border: 1px solid #e2e8f0;
    }

    .full-width {
        grid-column: 1 / -1; /* Ocupa todo el ancho disponible */
    }

    .lista-datos {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .lista-datos li {
        padding: 8px 0;
        border-bottom: 1px solid #f1f5f9;
        display: flex;
        justify-content: space-between;
    }
    
    .lista-datos li:last-child {
        border-bottom: none;
    }
</style>

<%@include file="../templates/footer.jsp" %>