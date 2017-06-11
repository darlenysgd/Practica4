<#include "Includes/Header.ftl">


<div class="panel panel-default panel-css">
    <div class="panel-heading panel-css">Nueva Publicación</div>
    <div class="panel-body">
        <form action="/crear" method="post">
        <div class="form-group">
            <label>Título: </label>
            <input name="titulo" type="text" class="form-control"/>
        </div>
        <div class="form-group">
            <label>Contenido: </label>
            <textarea id="contenido" name="contenido" class="contenido form-control" ></textarea>
        </div>
        <div class="form-group">
            <label>Etiquetas: </label>
            <input class=" form-control" name="etiquetas"/>
        </div>
        <button name="guardar" type="submit">Guardar</button>
    </form>
    </div>
</div>

<#include "Includes/Footer.ftl">