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

        Usuario usuario1 = new Usuario("admin", "admin", "admin", true, true);

         Articulo arti =   new Articulo("probando la vaina eta", "tu real articulo namah", usuario1, "05-6-17");
         articulo.getArticulos().add(arti);
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


        List<Comentario> listaComentarios = BlogService.listaComentarios();

        System.out.println("Cantidad: "+ listaArticulos.size());


        articulo.setArticulos(listaArticulos);
        listaUsuarios.add(usuario1);


        before("/NuevoUsuario", (request, response) -> {

           String str = request.session().attribute("usuario");
            if (str == null || !usuario1.isAdministrator()){
                    response.redirect("/login");
            }
        });

        before("/NuevoPost", (request, response) -> {

            String str = request.session().attribute("usuario");
            if (str == null || !usuario1.isAutor()){
                response.redirect("/login");
            }
        });


        before("/comentar/:id", (request, response) -> {

            String str = request.session().attribute("usuario");
            if (str == null ){

                    response.redirect("/Home");

            }
        });

        before("/eliminarArticulo/:id", (request, response) -> {

            String str = request.session().attribute("usuario");
            if (str == null || !usuario1.isAutor()){
                response.redirect("/login");
            }
        });

        before("/modificarArticulo/:indice", (request, response) -> {

            String str = request.session().attribute("usuario");
            if (str == null || !usuario1.isAutor()){
                response.redirect("/login");
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
            if(comentarios!=null) {
                attributes.put("comentarios", listaComentarios);
            }
            attributes.put("etiquetas", etiquetas);
            attributes.put("indice", indice);
            return new ModelAndView(attributes, "entrada.ftl");
            }, freeMarkerEngine);


        get("/modificarArticulo/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            attributes.put("articulo", articulo.getArticulos().get(indice));
            attributes.put("indice", indice);
            return new ModelAndView(attributes, "modificarPost.ftl");
        }, freeMarkerEngine);

        post("/modificarArticuloForm/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            String titulo = request.queryParams("titulo");
            String contenido = request.queryParams("contenido");
            String fecha = new Date().toString();
            Articulo art = new Articulo(titulo, contenido, usuario1, fecha);
            BlogService.crearArticulo(art);
            articulo.getArticulos().set(indice, art);
            response.redirect("/Home");
            return null;
        }, freeMarkerEngine);



        post("/crear", (request, response) -> {

            String str = request.session().attribute("usuario");
            Usuario usr = new Usuario();
            for(Usuario aux: listaUsuarios){
                if(aux.getUsername().equals(str)){
                    usr = aux;
                }
            }
            String titulo = request.queryParams("titulo");
            String contenido = request.queryParams("contenido");
            String fecha = new Date().toString();
            String tags = request.queryParams("etiquetas");

            Articulo art = new Articulo(titulo, contenido, usr, fecha);
            ArrayList<String> listaEtiquetas = new ArrayList<>(Arrays.asList(tags.split(",")));

            BlogService.crearArticulo(art);
            articulo.getArticulos().add(art);


            response.redirect("/Home");

            return  null;

        }, freeMarkerEngine);

        post("/comentar/:id", (request, response) -> {

            String str = request.session().attribute("usuario");
            System.out.println(str);
            Usuario usr = new Usuario();
            for(Usuario aux: listaUsuarios){
                if(aux.getUsername().equals(str)){
                    usr = aux;
                }
            }
            String comentario = request.queryParams("comentario");
            int artId = Integer.parseInt(request.params("id"));
            Articulo art = new Articulo();

            for (Articulo aux : articulo.getArticulos())
            {

                if(aux.getId() == artId){
                    art = aux;
                }
            }

            Comentario cm = new Comentario(comentario, usr, art);
            BlogService.crearComentario(cm);
            listaComentarios.add(cm);


            response.redirect("/Home");

            return null;

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

            boolean admin= false;
            boolean author = false;
            String Nombre = request.queryParams("nombre");
            String Username = request.queryParams("usuario");
            String Password = request.queryParams("clave");
            String TipoUsuario = request.queryParams("tipoUsuario");

            switch (TipoUsuario){
                case "Administrador":
                    admin = true;
                    author = true;
                    break;
                case "Autor":
                    author = true;

            }

            Map<String, Object> attributes = new HashMap<>();



            return new ModelAndView(attributes, "RegistroUsuario.ftl");

        }, freeMarkerEngine);

        get ("/login", (request, response) ->{
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "login.ftl");
                }, freeMarkerEngine );


        post("/loginForm", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            String NombreDeUsuario = request.queryParams("Usuario");
            String clave = request.queryParams("password");

            if(usuario1.getUsername().equals(NombreDeUsuario)){



                    request.session().attribute("usuario", NombreDeUsuario);
                    System.out.println(NombreDeUsuario);
                    response.redirect("/Home");

            }
            else {

                response.redirect("/login");
            }

           return null;

        }, freeMarkerEngine);


    }
}

