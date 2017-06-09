package Servicios;

import com.sun.xml.internal.bind.v2.TODO;
import entidades.Articulo;
import entidades.Comentario;
import entidades.Etiqueta;
import entidades.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by darle on 6/5/2017.
 */
public class BlogService {



    public static List<Articulo> listaArticulos() {
        List<Articulo> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {

            String query = "select * from articulo";
            con = DataBase.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Articulo art = new Articulo();
                String username = rs.getString("autor");
                Usuario usr = getAutor(username);

                art.setId(rs.getInt("id"));
                art.setTitulo(rs.getString("titulo"));
                art.setCuerpo(rs.getString("contenido"));
                art.setAutor(usr);
                art.setFecha(rs.getString("fecha"));

                //Faltan comentarios y eqtiqueta.

                art.setAutor(usr);
                lista.add(art);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lista;
    }

    public static Articulo getArticulo(int id)
    {

        Articulo art = null;
        Connection con = null;

        try {
            String query = "SELECT * FROM articulo WHERE id = ?";
            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){

                String username = rs.getString("autor");
                Usuario usr = getAutor(username);
                art = new Articulo();
                art.setId(rs.getLong("id"));
                art.setTitulo(rs.getString("titulo"));
                art.setCuerpo(rs.getString("contenido"));
                art.setAutor(usr);
                art.setFecha(rs.getString("fecha"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return art;
    }

    public static Usuario getAutor(String username)
    {

        Usuario usr = null;
        Connection con = null;

        try {
            String query = "SELECT * FROM usuario WHERE username = ?";
            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){

                usr = new Usuario();
                usr.setUsername(rs.getString("username"));
                usr.setNombre(rs.getString("nombre"));
                usr.setPassword(rs.getString("password"));
                usr.setAutor(rs.getBoolean("autor"));
                usr.setAdministrator(rs.getBoolean("administrador"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return usr;
    }

    public static boolean crearArticulo(Articulo art)
    {
        boolean ok = false;

        Connection con = null;

        try {

            String query = "insert into articulo(titulo, contenido, autor, fecha) VALUES (?,?,?,?)";

            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, art.getTitulo());
            preparedStatement.setString(2, art.getCuerpo());
            preparedStatement.setString(3, art.getAutor().getUsername());
            preparedStatement.setString(4, art.getFecha());

            int fila = preparedStatement.executeUpdate();

            ok = fila > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean crearUsuario(Usuario usr)
    {
        boolean ok = false;

        Connection con = null;

        try {

            String query = "insert into usuario(username, nombre, password, administrador, autor) VALUES (?,?,?,?,?)";

            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, usr.getUsername());
            preparedStatement.setString(2, usr.getNombre());
            preparedStatement.setString(3,usr.getPassword());
            preparedStatement.setBoolean(4, usr.isAdministrator());
            preparedStatement.setBoolean(5, usr.isAutor());


            int fila = preparedStatement.executeUpdate();

            ok = fila > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean crearComentario(Comentario comentario)
    {
        boolean ok = false;

        Connection con = null;

        try {

            String query = "insert into comentario(id, comentario, usuario, articulo) VALUES (?,?,?,?)";

            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setLong(1, comentario.getId());
            preparedStatement.setString(2, comentario.getComentatio());
            preparedStatement.setObject(3, comentario.getAutor());
            preparedStatement.setObject(4, comentario.getArticulo());



            int fila = preparedStatement.executeUpdate();

            ok = fila > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean crearEtiqueta(Etiqueta etq)
    {
        boolean ok = false;

        Connection con = null;

        try {

            String query = "insert into etiqueta(id, nombre) VALUES (?,?)";

            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setLong(1, etq.getId());
            preparedStatement.setString(2, etq.getEtiqueta());



            int fila = preparedStatement.executeUpdate();

            ok = fila > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean crearEtiqueta_Articulo(Etiqueta etq, Articulo art)
    {
        boolean ok = false;

        Connection con = null;

        try {

            String query = "insert into etiqueta_articulo(etiqueta, articulo) VALUES (?,?)";

            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setLong(1, etq.getId());
            preparedStatement.setLong(2, art.getId());

            int fila = preparedStatement.executeUpdate();

            ok = fila > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean borrarArticulo(long id){

        boolean ok = false;

        Connection con = null;

        try{

            String query = "delete from articulo where id = ?";

            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setLong(1,id);

            int fila= preparedStatement.executeUpdate();
            ok = fila > 0;

        }  catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        return ok;
    }

    public static boolean actualizarArticulo(Articulo art){

        boolean ok = false;

        Connection con = null;



        String query = "update articulo set titulo=?, contenido=? where id = ?";
        con = DataBase.getInstancia().getConexion();
        //
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, art.getTitulo());
            preparedStatement.setString(2, art.getCuerpo());

            int fila = preparedStatement.executeUpdate();

            ok = fila > 0 ;

        } catch (SQLException e) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ok;
    }

}
