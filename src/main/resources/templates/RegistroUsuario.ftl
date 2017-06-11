<#include "Includes/Header.ftl">

<div class="panel panel-primary">
    <div class="panel-heading">Registro de Usuarios</div>
    <div class="panel-body">
        <form action="/crearUsuario" method="post">
            <div class="form-group">
                <label>Nombre Completo:</label>
                <input type="text" class="form-control" name="nombre"/>
            </div>
            <div class="form-group">
                <label>Nombre de usuario:</label>
                <input type="text" class="form-control" name="usuario"/>
            </div>
            <div class="form-group">
                <label>Constraseña:</label>
                <input type="password" class="form-control"name="clave"/>
            </div>
            <div class="form-group">
                <label for="sel1">Tipo De Usuario:</label>
                <select class="form-control" id="sel1" name="tipoUsuario">
                    <option>Administrador</option>
                    <option>Autor</option>
                </select>
            </div>

            <div class="form-group">
                <input type="submit" id="btnSubmit" class="form-control" value="Crear">
            </div>
            <script type="text/javascript">
                $(function() {
                    $('.multiselect-ui').multiselect({
                        includeSelectAllOption: true
                    });
                });
            </script>

            <button class="btn" type="submit">Guardar</button>
        </form>
    </div>
</div>

<#include "Includes/Footer.ftl">

