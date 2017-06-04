package main;

import entidades.Comentario;
import entidades.Etiqueta;
import entidades.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;
import static spark.Spark.staticFiles;
import entidades.Articulo;


/**
 * Created by darle on 6/3/2017.
 */
public class main {

    public static void main(String[] args) {

        staticFiles.location("/");
        ArrayList<Articulo> articulos = new ArrayList<>();
        ArrayList<Comentario> comentarios = new ArrayList<>();
        ArrayList<Etiqueta> etiquetas = new ArrayList<>();
        Usuario usuario1 = new Usuario("monti", "isaac", "lol", true, false);
        articulos.add(new Articulo(1, "probando la mielda eta", "tu real articulo namah", usuario1, new Date(), comentarios, etiquetas));

        comentarios.add(new Comentario(1, "Nice", usuario1, articulos.get(0)));
        comentarios.add(new Comentario(2, "Lol", usuario1, articulos.get(0)));
        etiquetas.add(new Etiqueta(1, "etiqueta1"));
        etiquetas.add(new Etiqueta(2, "etiqueta2"));


        Configuration configuration=new Configuration();
        configuration.setClassForTemplateLoading(main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

       get("/Home", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        get("/Entrada", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulo", articulos.get(0));
            return new ModelAndView(attributes, "entrada.ftl");
        }, freeMarkerEngine);


    }
}
