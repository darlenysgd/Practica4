package Servicios;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by darle on 6/3/2017.
 */
public class DataBase {

    private static DataBase instancia;
    private String URL = "jdbc:h2:tcp://localhost/~/Practica3"; //Modo Server...


    private  DataBase(){
        registrarDriver();
    }


    public static DataBase getInstancia(){
        if(instancia==null){
            instancia = new DataBase();
        }
        return instancia;
    }


    private void registrarDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
           Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
