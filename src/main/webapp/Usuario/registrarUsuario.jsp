<%-- Acá se muestra el formulario para registrarse como usuario --%>
<%@include file="../templates/header.jsp" %>
        <h1>Registrar Nuevo Usuario</h1>
        <div class="container register-container-lg">
        <form action="${pageContext.request.contextPath}/SvAltaUsuario" method="POST">
            <label>Nombre:</label>
            <input type="text" name="nombre" required><br><br>
            
            <label>Correo:</label>
            <input type="email" name="correo" required><br><br>
            
            <label>Contraseña:</label>
            <input type="password" name="contrasena" required><br><br>
            
            <label>Teléfono:</label>
            <input type="text" name="telefono" required><br><br>
            
            <label>Dirección:</label>
            <input type="text" name="direccion" required><br><br>
            
            
            <label>Rol:</label>
            <select name="rol" id="selectRol" onchange="mostrarOcultarMatricula()">
                <option value="ADMINISTRADOR">Administrador</option>
                <option value="VETERINARIO">Veterinario</option>
                <option value="VOLUNTARIO">Voluntario</option>
                <option value="FAMILIA">Familia Adoptante</option>
            </select><br><br>
            <div id="divMatricula" class="hidden">
                <label>Matrícula:</label>
                <input type="text" name="matricula" id="inputMatricula">
                <br><br>
            </div>
            <button type="submit">Guardar</button>
        </form>
        <a href="../index.jsp"><button>Volver atrás</button></a>
        </div>
        <script>
            function mostrarOcultarMatricula() {
                // 1. Obtener los elementos
                var rol = document.getElementById("selectRol").value;
                var divMatricula = document.getElementById("divMatricula");
                var inputMatricula = document.getElementById("inputMatricula");

                // 2. Verificar si es VETERINARIO
                if (rol === "VETERINARIO") {
                    // Mostrar el campo
                    divMatricula.style.display = "block";
                    // Hacerlo obligatorio para que no se pueda enviar vacío
                    inputMatricula.setAttribute("required", "true");
                } else {
                    // Ocultar el campo
                    divMatricula.style.display = "none";
                    // Quitar la obligatoriedad para poder enviar el formulario
                    inputMatricula.removeAttribute("required");
                    // (Opcional) Limpiar el valor si cambia de opinión
                    inputMatricula.value = "";
                }
            }
        </script>
<%@include file="../templates/footer.jsp" %>