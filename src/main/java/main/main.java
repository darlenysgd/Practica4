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

import javax.persistence.Query;

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
    static boolean calificoComment;
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

        before("/likeComment/:indiceComment/:indiceArt", (request, response) -> {

            String str = request.session().attribute("usuario");
            if (str == null ){
                response.redirect("/login");
            }
        });

        before("/dislikeComment/:indiceComment/:indiceArt", (request, response) -> {

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
            pag = numPag;
            boolean mas = false;
            boolean vacio = true;



            List<Articulo> subLista = ArticuloServices.getInstancia().pagination(numPag);

            if (ArticuloServices.getInstancia().pagination(numPag) == null){

                vacio = false;


            } else {
                mas = true;
                attributes.put("vacio", vacio);
                attributes.put("articulos", subLista);

            }


            attributes.put("vacio", vacio);
            attributes.put("mas", mas);
            attributes.put("numPag", numPag);
            attributes.put("etiquetas", listaEtiquetas);
            return new ModelAndView(attributes, "index.ftl");



        }, freeMarkerEngine);

        get("/Home/tags/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));

            List<Articulo> arts = new ArrayList<>();
            for(Articulo art : lista){
                for(Etiqueta et: art.getEtiquetas()){
                    if(et.getEtiqueta().equals(listaEtiquetas.get(indice).getEtiqueta())){
                       arts.add(art);
                    }
                }
            }
            attributes.put("articulos", arts);
            response.redirect("/HomePage/tags/" + indice + "/" + 1);
            return null;


        }, freeMarkerEngine);

        get("/HomePage/tags/:indice/:numPag", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            int numPag = Integer.parseInt(request.params("numPag"));
            int numPagAux = numPag*5;
            boolean mas = false;
            boolean vacio = true;


            //buscar todos los articulos que contengan esa etiqueta y generar una lista

            attributes.put("numPag", numPag);
            attributes.put("mas", mas);
            attributes.put("vacio", vacio);
            List<Articulo> articulos_etq = ArticuloServices.getInstancia().findAllEtiquetas(listaEtiquetas.get(indice).getId());
            attributes.put("articulos", articulos_etq);
            attributes.put("etiquetas", listaEtiquetas);

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

            attributes.put("articulo", lista.get(indice + ((pag -1) *5)));


            if(lista.get(indice + ((pag -1) *5)).getComentarios() != null) {

                attributes.put("comentarios", lista.get(indice + ((pag -1) *5)).getComentarios());
            }
            else
            {
                x = false;
            }

            attributes.put("comentarioNull", x);

            if(lista.get(indice + ((pag -1) *5)).getEtiquetas() != null) {

                attributes.put("etiquetas", lista.get(indice + ((pag -1) *5)).getEtiquetas());
            }

            attributes.put("indice", indice);
            attributes.put("logged", logged);
             return new ModelAndView(attributes, "entrada.ftl");
            }, freeMarkerEngine);


        get("/modificarArticulo/:indice", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            int indice = Integer.parseInt(request.params("indice"));
            attributes.put("articulo", lista.get(indice + ((pag -1) *5)));
            attributes.put("indice", indice);
            return new ModelAndView(attributes, "modificarPost.ftl");
        }, freeMarkerEngine);


        post("/modificarArticuloForm/:indice", (request, response) -> {

            Articulo art = new Articulo();

            int indice = Integer.parseInt(request.params("indice"));
            art.setId(lista.get(indice + ((pag -1) *5)).getId());

            art.setTitulo(request.queryParams("titulo"));
            art.setCuerpo(request.queryParams("contenido"));
            String fecha = new Date().toString();
            art.setFecha(fecha);
            art.setAutor(usuario1);

            String tags = request.queryParams("etiquetas");


            String auxEtiquetas = tags.replaceAll(" ", "");
            List<String> listaEtiquetas1 = Arrays.asList(auxEtiquetas.split(","));
            List<Etiqueta> aux = new ArrayList<>();

            Etiqueta x;

            for(int i = 0; i < listaEtiquetas1.size(); i++){

                for(int j = 0; j < listaEtiquetas.size(); j++) {

                    if (listaEtiquetas.get(j).getEtiqueta().equals(listaEtiquetas1.get(i))) {

                        x = EtiquetaServices.getInstancia().find(listaEtiquetas.get(j).getId());
                        aux.add(x);

                    }
                    else {

                        x = new Etiqueta(listaEtiquetas1.get(i));
                        EtiquetaServices.getInstancia().crear(x);
                        aux.add(x);
                        listaEtiquetas = EtiquetaServices.getInstancia().findAll();
                    }
                }

            }


            art.setEtiquetas(aux);
            listaEtiquetas = EtiquetaServices.getInstancia().findAll();
            ArticuloServices.getInstancia().editar(art);
            lista.set(indice, art);
            response.redirect("/Home");
            return null;
        }, freeMarkerEngine);

        post("/like/:indice", (request, response) ->{

            int indice = Integer.parseInt(request.params("indice"));
            if (califico == false) {
                lista.get(indice + ((pag -1) *5)).setLikes(lista.get(indice + ((pag -1) *5)).getLikes() + 1);
                Articulo art = lista.get(indice+ ((pag -1) *5));
                art.setLikes(lista.get(indice + ((pag -1) *5)).getLikes());
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
                lista.get(indice + ((pag -1) *5)).setDislikes(lista.get(indice + ((pag -1) *5)).getDislikes() + 1);

                Articulo art = lista.get(indice+ ((pag -1) *5));
                art.setDislikes(lista.get(indice+ ((pag -1) *5)).getDislikes());
                ArticuloServices.getInstancia().editar(art);
                califico = true;
            }
            response.redirect("/Entrada/" + indice);
            return null;
        }, freeMarkerEngine);

        post("/likeComment/:indiceComment/:indiceArt", (request, response) ->{

            int indice = Integer.parseInt(request.params("indiceComment"));
            int indiceArticulo = Integer.parseInt(request.params("indiceArt"));
            if (calificoComment == false) {
                lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice).setLikes(lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice).getLikes() + 1);
                Comentario com = lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice);
                com.setLikes(lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice).getLikes());
                ComentarioServices.getInstancia().editar(com);
                calificoComment = true;
                //ACTUALIZAR LIKE EN BD
            }
            response.redirect("/Entrada/" + indiceArticulo);
            return null;
        }, freeMarkerEngine);


        post("/dislikeComment/:indiceComment/:indiceArt", (request, response) ->{

            int indice = Integer.parseInt(request.params("indiceComment"));
            int indiceArticulo = Integer.parseInt(request.params("indiceArt"));
            if (calificoComment == false) {
                lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice).setDislikes(lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice).getDislikes() + 1);
                Comentario com = lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice);
                com.setDislikes(lista.get(indiceArticulo + ((pag -1) *5)).getComentarios().get(indice).getDislikes());
                ComentarioServices.getInstancia().editar(com);
                calificoComment = true;
                //ACTUALIZAR LIKE EN BD
            }
            response.redirect("/Entrada/" + indiceArticulo);
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


            for(String et: listaEtiquetas1){
                x = EtiquetaServices.getInstancia().findEtiqueta(et);
                if(x == null){

                    Etiqueta etiqueta = new Etiqueta(et);
                    aux.add(etiqueta);
                }
                else{

                    aux.add(x);
                }
            }

            art.setEtiquetas(aux);
            ArticuloServices.getInstancia().editar(art);
            lista = ArticuloServices.getInstancia().findAll();
            listaEtiquetas = EtiquetaServices.getInstancia().findAll();

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
                    calificoComment = false;
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

