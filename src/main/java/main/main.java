package main;

import Servicios.*;
import entidades.Comentario;
import entidades.Etiqueta;
import entidades.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.*;
import java.util.Arrays;
import entidades.Articulo;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


/**
 * Createdby darle on 6/3/2017.
 */
public class main {

    static List<Articulo> lista = new ArrayList<>();
    static List<Usuario> listaUsuarios = new ArrayList<>();
    static List<Etiqueta> listaEtiquetas = new ArrayList<>();
    static boolean logged = false;
    static boolean califico;
    static Usuario usuario1 = new Usuario();
    static List<Comentario> listaComentarios = new ArrayList<>();
    static int pag = 1;

    public static void main(String[] args) {

        staticFiles.location("/");
        enableDebugScreen();
        Articulo articulo = new Articulo();



        ArrayList<Comentario> comentarios = new ArrayList<>();



        Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        usuario1 = new Usuario("admin", "", "admin", true, true);
        BootStrapService.getInstancia().init();
        listaUsuarios.add(usuario1);

       // articulo.setArticulos(listaArticulos);

        listaUsuarios = UsuariosServices.getInstancia().findAll();
        lista = ArticuloServices.getInstancia().findAll();
        listaEtiquetas = EtiquetaServices.getInstancia().findAll();

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
                response.redirect("/login");
            }
        });

        before("/like/:id", (request, response) -> {

            String str = request.session().attribute("usuario");
            if (str == null ){
                response.redirect("/login");
            }
        });

        before("/dislike/:id", (request, response) -> {

            String str = request.session().attribute("usuario");
            if (str == null ){
                response.redirect("/login");
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

            response.redirect("/HomePage/" + 1);
            return null;


        }, freeMarkerEngine);

        get("/HomePage/:numPag", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int numPag = Integer.parseInt(request.params("numPag"));
            int numPagAux = numPag*5;
            boolean mas = false;
            boolean vacio = true;


            List<Articulo> subLista = ArticuloServices.getInstancia().pagination(numPag);

            if (ArticuloServices.getInstancia().pagination(numPag) == null){

                vacio = false;


            } else {
                mas = true;
                attributes.put("articulos", subLista);


            }
            attributes.put("vacio", vacio);

                numPag = numPag - 1;

            attributes.put("mas", mas);
            attributes.put("numPag", numPag);
            return new ModelAndView(attributes, "index.ftl");



        }, freeMarkerEngine);

        get("/Home/tags/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));

            response.redirect("/HomePage/tags/" + indice + "/" + 1);
            return null;


        }, freeMarkerEngine);

        get("/HomePage/tags/:indice/:numPag", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            int numPag = Integer.parseInt(request.params("numPag"));
            int numPagAux = numPag*5;
            boolean mas = false;

            attributes.put("mas", mas);
            attributes.put("numPag", numPag);

            listaEtiquetas.get(indice);
            //buscar todos los articulos que contengan esa etiqueta y generar una lista
            List<Articulo> articulos_etq = new ArrayList<>();

            for(Articulo art :  lista){
                for(Etiqueta et : art.getEtiquetas()){

                    if (et.getEtiqueta().equals(listaEtiquetas.get(indice).getEtiqueta())){
                        articulos_etq.add(art);
                    }
                }
            }

            if(numPagAux + 5 >  articulos_etq.size() ){
                ArrayList<Articulo> subLista = new ArrayList<>(articulos_etq.subList(numPagAux, articulos_etq.size()));
                attributes.put("articulos", subLista);
            } else{
                ArrayList<Articulo> subLista = new ArrayList(articulos_etq.subList(numPagAux, numPagAux + 5));
                attributes.put("articulos", subLista);
                mas = true;
            }
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

            boolean x = true;
            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            attributes.put("articulo", lista.get(indice));
            if(lista.get(indice).getComentarios() != null) {

                attributes.put("comentarios", lista.get(indice).getComentarios());
            }
            else
            {
                x = false;
            }

            attributes.put("comentarioNull", x);

            if(lista.get(indice).getEtiquetas() != null) {

                attributes.put("etiquetas", lista.get(indice).getEtiquetas());
            }

            attributes.put("indice", indice);
            attributes.put("logged", logged);
             return new ModelAndView(attributes, "entrada.ftl");
            }, freeMarkerEngine);


        get("/modificarArticulo/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            attributes.put("articulo", lista.get(indice));
            attributes.put("indice", indice);
            return new ModelAndView(attributes, "modificarPost.ftl");
        }, freeMarkerEngine);


        post("/modificarArticuloForm/:indice", (request, response) -> {

            Articulo art = new Articulo();

            int indice = Integer.parseInt(request.params("indice"));
            art.setId(lista.get(indice).getId());

            art.setTitulo(request.queryParams("titulo"));
            art.setCuerpo(request.queryParams("contenido"));
            String fecha = new Date().toString();
            art.setFecha(fecha);
            art.setAutor(usuario1);

            String tags = request.queryParams("etiquetas");


            String auxEtiquetas = tags.replaceAll(" ", "");
            List<String> listaEtiquetas1 = Arrays.asList(auxEtiquetas.split(","));
            List<Etiqueta> aux = new ArrayList<>();

            Etiqueta x ;
            for(int i = 0; i < listaEtiquetas1.size(); i++){

                if(EtiquetaServices.getInstancia().find(listaEtiquetas1.get(i))==null){
                    x = new Etiqueta(listaEtiquetas1.get(i));

                    EtiquetaServices.getInstancia().crear(x);
                    aux.add(x);
                    listaEtiquetas = EtiquetaServices.getInstancia().findAll();
                }
                else {
                    x = EtiquetaServices.getInstancia().find(listaEtiquetas1.get(i));
                    aux.add(x);
                }
            }

            art.setEtiquetas(aux);

            ArticuloServices.getInstancia().editar(art);
            lista.set(indice, art);
            response.redirect("/Home");
            return null;
        }, freeMarkerEngine);

        post("/like/:indice", (request, response) ->{

            int indice = Integer.parseInt(request.params("indice"));
            if (califico == false) {
                lista.get(indice).setLikes(lista.get(indice).getLikes() + 1);
                Articulo art = lista.get(indice);
                art.setLikes(lista.get(indice).getLikes());
                ArticuloServices.getInstancia().editar(art);
                califico = true;
                //ACTUALIZAR LIKE EN BD
            }
            response.redirect("/Entrada/" + indice);
            return null;
        }, freeMarkerEngine);

        post("/dislike/:indice", (request, response) ->{

                int indice = Integer.parseInt(request.params("indice"));
            if (califico == false) {
                lista.get(indice).setDislikes(lista.get(indice).getDislikes() + 1);

                Articulo art = lista.get(indice);
                art.setDislikes(lista.get(indice).getDislikes());
                ArticuloServices.getInstancia().editar(art);
                califico = true;
            }
            response.redirect("/Entrada/" + indice);
            return null;
        }, freeMarkerEngine);


        post("/crear", (request, response) -> {

            Articulo art = new Articulo();
            String str = request.session().attribute("usuario");
            Usuario usr = new Usuario();
            for(Usuario aux: listaUsuarios){
                if(aux.getUsername().equals(str)){
                    usr = aux;
                }
            }
            art.setTitulo(request.queryParams("titulo"));
            art.setCuerpo(request.queryParams("contenido"));
            String fecha = new Date().toString();
            art.setFecha(fecha);
            art.setAutor(usr);
            String tags = request.queryParams("etiquetas");



            tags = tags.replaceAll(" ", "");

            ArrayList<String> listaEtiquetas1 = new ArrayList<>(Arrays.asList(tags.split(",")));
            List<Etiqueta> aux = new ArrayList<>();

            Etiqueta x ;
            for(int i = 0; i < listaEtiquetas1.size(); i++){

                for(int j = 0; j < listaEtiquetas.size(); j++) {
                    if (listaEtiquetas.get(j).getEtiqueta().equals(listaEtiquetas1.get(i))) {
                        x = new Etiqueta(listaEtiquetas1.get(i));
                        EtiquetaServices.getInstancia().crear(x);
                        aux.add(x);
                        listaEtiquetas = EtiquetaServices.getInstancia().findAll();
                    }
                    else {
                        x = EtiquetaServices.getInstancia().find(listaEtiquetas1.get(i));
                        aux.add(x);
                    }
                }

            }

            art.setEtiquetas(aux);
            ArticuloServices.getInstancia().crear(art);
            lista = ArticuloServices.getInstancia().findAll();


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

            for(Articulo x : lista){

                if(x.getId() == artId){
                    art = x;
                }
            }

            Comentario cm = new Comentario(comentario, usr);
            art.getComentarios().add(cm);
            ComentarioServices.getInstancia().crear(cm);
            ArticuloServices.getInstancia().editar(art);



            response.redirect("/Home");

            return null;

        }, freeMarkerEngine);

        post("/eliminarArticulo/:id", (request, response) -> {

            int id = Integer.parseInt(request.params("id"));

            ArticuloServices.getInstancia().eliminar(id);

            lista = ArticuloServices.getInstancia().findAll();


              response.redirect("/Home");

            return null;

        }, freeMarkerEngine);


        post("/crearUsuario", (request, response) -> {

            Usuario usr = new Usuario();
            usr.setUsername(request.queryParams("usuario"));
            usr.setNombre(request.queryParams("nombre"));
            usr.setPassword(request.queryParams("clave"));
            String TipoUsuario = request.queryParams("tipoUsuario");

            switch (TipoUsuario){
                case "Administrador":
                    usr.setAdministrator(true);
                    usr.setAutor(true);
                    break;
                case "Autor":
                    usr.setAutor(true);
                    usr.setAdministrator(false);
            }



            UsuariosServices.getInstancia().crear(usr);

            listaUsuarios.add(usr);

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
            int aux = 0;


            for(Usuario usr : listaUsuarios) {

                if (usr.getUsername().equals(NombreDeUsuario) && usr.getPassword().equals(clave)) {
                    aux = 1;
                    request.session().attribute("usuario", NombreDeUsuario);
                    System.out.println(NombreDeUsuario);
                    response.redirect("/Home");
                    usuario1 = usr;
                    logged = true;
                    califico = false;
                    break;
                }

            }

            if (aux == 1){
              response.redirect("/login");
             }

              return null;

           }, freeMarkerEngine);
        get("/cerrarSesion", (request, response) -> {

            request.session().invalidate();
            logged = false;
            usuario1 = null;
            response.redirect("/Home");
            return null;
            }, freeMarkerEngine );



    }
}

