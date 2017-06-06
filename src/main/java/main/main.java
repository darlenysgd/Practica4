package main;

import entidades.Comentario;
import entidades.Etiqueta;
import entidades.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;

import entidades.Articulo;

import static spark.Spark.*;


/**
 * Createdby darle on 6/3/2017.
 */
public class main {

    static List<Articulo> lista = new ArrayList<>();

    public static void main(String[] args) {

        staticFiles.location("/");
        Articulo articulo = new Articulo();
        articulo.setArticulos(lista);

        ArrayList<Comentario> comentarios = new ArrayList<>();
        ArrayList<Etiqueta> etiquetas = new ArrayList<>();

        Usuario usuario1 = new Usuario("monti", "isaac", "lol", true, false);
        lista.add(new Articulo(1, "probando la mielda eta", "tu real articulo namah", usuario1, "05-6-17"));

        comentarios.add(new Comentario(1, "Nice", usuario1, lista.get(0)));
        comentarios.add(new Comentario(2, "Lol", usuario1, lista.get(0)));
        etiquetas.add(new Etiqueta(1, "etiqueta1"));
        etiquetas.add(new Etiqueta(2, "etiqueta2"));


        Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);



        get("/Home", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulos", articulo.getArticulos());
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        get("/NuevoPost", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "NuevoPost.ftl");
        }, freeMarkerEngine);

        get("/Entrada/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            attributes.put("articulo", lista.get(indice));
            attributes.put("comentarios", comentarios);
            attributes.put("etiquetas", etiquetas);
            return new ModelAndView(attributes, "entrada.ftl");}, freeMarkerEngine);



        post("/crear", (request, response) -> {



            int id = 1;
            String titulo = request.queryParams("titulo");
            String contenido = request.queryParams("contenido");
            String fecha = new Date().toString();
            Articulo art = new Articulo(id, titulo, contenido, usuario1, fecha);
            lista.add(art);



            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulos", lista);


            return new ModelAndView(attributes, "index.ftl");

        }, freeMarkerEngine);
    }
}

