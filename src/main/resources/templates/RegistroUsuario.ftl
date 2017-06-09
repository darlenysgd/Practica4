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
            <div class="radio">
                <label><input type="radio" name="administrador">Administrador</label>
            </div>
            <div class="radio">
                <label><input type="radio" name="autor">Autor</label>
            </div>

            <script type="text/javascript">
                $(function() {
                    $('.multiselect-ui').multiselect({
                        includeSelectAllOption: true
                    });
                });
            </script>
        </form>
    </div>
</div>

<#include "Includes/Footer.ftl">

