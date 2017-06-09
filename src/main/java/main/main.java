package main;

import Servicios.BlogService;
import Servicios.BootStrapService;
import Servicios.DataBase;
import entidades.Comentario;
import entidades.Etiqueta;
import entidades.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.*;

import entidades.Articulo;

import static spark.Spark.*;


/**
 * Createdby darle on 6/3/2017.
 */
public class main {

    static List<Articulo> lista = new ArrayList<>();
    static List<Usuario> listaUsuarios = new ArrayList<>();

    public static void main(String[] args) {

        staticFiles.location("/");
        Articulo articulo = new Articulo();
        articulo.setArticulos(lista);

        ArrayList<Comentario> comentarios = new ArrayList<>();
        ArrayList<Etiqueta> etiquetas = new ArrayList<>();

        Usuario usuario1 = new Usuario("admin", "admin", "admin", true, false);
       // lista.add(new Articulo("probando la vaina eta", "tu real articulo namah", usuario1, "05-6-17"));

      //  comentarios.add(new Comentario(1, "Nice", usuario1, lista.get(0)));
        //comentarios.add(new Comentario(2, "Lol", usuario1, lista.get(0)));
        etiquetas.add(new Etiqueta(1, "etiqueta1"));
        etiquetas.add(new Etiqueta(2, "etiqueta2"));


        Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);



        try {
            BootStrapService.startDb();
            DataBase.getInstancia().testConexion();
            BootStrapService.crearTablas();


        } catch (SQLException e) {
            e.printStackTrace();
        }







        for(int i = 0; i < lista.size(); i++) {
            if (BlogService.getAutor(lista.get(i).getAutor().getUsername()) == null) {
                BlogService.crearUsuario(lista.get(i).getAutor());
            }
        }



        List<Articulo> listaArticulos = BlogService.listaArticulos();

        System.out.println("Cantidad: "+ listaArticulos.size());


        articulo.setArticulos(listaArticulos);



        before("/NuevoUsuario", (request, response) -> {

           String str = request.session().attribute("usuario");
            System.out.println(str);
            if (str == null || !usuario1.isAdministrator()){
                    response.redirect("/Home");
            }
        });

        before("/NuevoPost", (request, response) -> {

            String str = request.session(true).attribute("usuario");
            if (str == null ){
                if ((usuario1.getUsername() == str && !usuario1.isAdministrator()) || (usuario1.getUsername() == str && !usuario1.isAutor()) ){
                    response.redirect("/Home");
                }

            }
        });

        get("/Home", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulos", articulo.getArticulos());
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        get("/NuevoPost", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "NuevoPost.ftl");
        }, freeMarkerEngine);

        get("/NuevoUsuario", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "RegistroUsuario.ftl");
        }, freeMarkerEngine);

        get("/Entrada/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            attributes.put("articulo", articulo.getArticulos().get(indice));
            attributes.put("comentarios", comentarios);
            attributes.put("etiquetas", etiquetas);
            return new ModelAndView(attributes, "entrada.ftl");
            }, freeMarkerEngine);




        post("/crear", (request, response) -> {


            String titulo = request.queryParams("titulo");
            String contenido = request.queryParams("contenido");
            String fecha = new Date().toString();
            Articulo art = new Articulo(titulo, contenido, usuario1, fecha);
            BlogService.crearArticulo(art);
            articulo.getArticulos().add(art);


            response.redirect("/Home");

            return  null;

        }, freeMarkerEngine);

        post("/eliminarArticulo/:id", (request, response) -> {

            int id = Integer.parseInt(request.params("id"));
            for(int i = 0; i < articulo.getArticulos().size(); i++){


                if(articulo.getArticulos().get(i).getId() == id){

                    BlogService.borrarArticulo(articulo.getArticulos().get(i).getId());
                    articulo.getArticulos().remove(i);


                }
            }

              response.redirect("/Home");

            return null;

        }, freeMarkerEngine);


        post("/crearUsuario", (request, response) -> {



            int id = 1;
            String titulo = request.queryParams("titulo");
            String contenido = request.queryParams("contenido");
            String fecha = new Date().toString();
            Articulo art = new Articulo(titulo, contenido, usuario1, fecha);
            lista.add(art);




            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulos", lista);


            return new ModelAndView(attributes, "index.ftl");

        }, freeMarkerEngine);

        get ("/login", (request, response) ->{
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "login.ftl");
                }, freeMarkerEngine );


        post("/loginForm", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            String Usuario = request.queryParams("Usuario");
            String clave = request.queryParams("password");

            if(usuario1.getUsername().equals(Usuario)){

                if (usuario1.getPassword().equals(clave)){

                    request.session().attribute("usuario", Usuario);
                    System.out.println(Usuario);
                    response.redirect("/Home");
                }
            }
            else {

                response.redirect("/login");
            }

           return null;

        }, freeMarkerEngine);


    }
}

