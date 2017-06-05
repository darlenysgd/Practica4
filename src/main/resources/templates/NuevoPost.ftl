<#include "Includes/Header.ftl">


    <h2>Nueva Publicación</h2>

    <form action="/crear" method="post">
        <div class="form-group">
            <label>Título: </label>
            <input name="titulo" class="entrada"/>
        </div>
        <div class="form-group">
            <label>Contenido: </label>
            <textarea name="contenido" class="contenido"></textarea>
        </div>
        <div class="form-group">
            <label>Etiquetas: </label>
            <input name="etiquetas" class="etiquetas"/>
        </div>

        <button name="guardar" type="submit">Guardar</button>
    </form>

<#include "Includes/Footer.ftl">