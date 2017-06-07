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
                <label>Permisos:</label>
               <input name="permiso1"/>
            </div>
            <div class="form-group">
                <label>Permisos:</label>
                <input name="permiso2"/>
            </div>
        </form>
    </div>
</div>

<#include "Includes/Footer.ftl">

