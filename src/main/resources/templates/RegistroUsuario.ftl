<#include "Includes/Header.ftl">

<div class="panel panel-primary">
    <div class="panel-heading">Registro de Usuarios</div>
    <div class="panel-body">
        <form action="/crearUsuario" method="post">
            <div class="form-group">
                <label>Nombre Completo:</label>
                <input name="nombre"/>
            </div>
            <div class="form-group">
                <label>Nombre de usuario:</label>
                <input name="usuario"/>
            </div>
            <div class="form-group">
                <label>Constraseña:</label>
                <input name="clave"/>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label" for="rolename">Permisos</label>
                <div class="col-md-4">
                    <select id="demo-cs-multiselect" data-placeholder="Choose a Country..." multiple tabindex="2">
                        <option value="cheese">Administrador</option>
                        <option value="tomatoes">Autor</option>
                    </select>
                </div>
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

