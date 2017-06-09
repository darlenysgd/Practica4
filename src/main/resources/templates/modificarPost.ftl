<#include "Includes/Header.ftl">


<div class="panel panel-default panel-css">
    <div class="panel-heading panel-css">Nueva Publicación</div>
    <div class="panel-body">
        <form action="/modificarArticuloForm/${indice}" method="post">
        <div class="form-group">
            <label>Título: </label>
            <input name="titulo" type="text" class="form-control" value="${articulo.titulo}"/>
        </div>
        <div class="form-group">
            <label>Contenido: </label>
            <textarea id="contenido" name="contenido" class="contenido form-control" placeholder="${articulo.cuerpo}"></textarea>
        </div>
        <div class="form-group">
            <label>Etiquetas: </label>
            <input class=" form-control" ></select>
        </div>
        <button name="guardar" type="submit">Guardar</button>
    </form>
    </div>
</div>

<#include "Includes/Footer.ftl">