package Servicios;

import entidades.Articulo;
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
                Usuario usr = new Usuario();
                art.setId(rs.getInt("id"));
                art.setTitulo(rs.getString("titulo"));
                art.setCuerpo(rs.getString("contenido"));
                usr.setNombre(rs.getString("nombreautor"));
                usr.setUsername(rs.getString("username"));
                usr.setPassword(rs.getString("password"));
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

                art = new Articulo();
                Usuario usr1 = new Usuario();
                art.setId(rs.getInt("id"));
                art.setTitulo(rs.getString("titulo"));
                art.setCuerpo(rs.getString("contenido"));
                usr1.setNombre(rs.getString("nombreautor"));
                usr1.setUsername(rs.getString("username"));
                usr1.setPassword(rs.getString("password"));
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

    public static boolean crearArticulo(Articulo art)
    {
        boolean ok = false;

        Connection con = null;

        try {

            String query = "insert into articulo(id, titulo, contenido, nombreautor, username, password, fecha) VALUES (?,?,?,?,?,?,?)";

            con = DataBase.getInstancia().getConexion();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setLong(1, art.getId());
            preparedStatement.setString(2, art.getTitulo());
            preparedStatement.setString(3, art.getCuerpo());
            preparedStatement.setString(4, art.getAutor().getNombre());
            preparedStatement.setString(5, art.getAutor().getUsername());
            preparedStatement.setString(6, art.getAutor().getPassword());
            preparedStatement.setString(7, art.getFecha());

            int fila = preparedStatement.executeUpdate();

            ok = fila > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }


}
