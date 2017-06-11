package Servicios;

import org.h2.tools.Server;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by darle on 6/5/2017.
 */
public class BootStrapService {

    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    /**
     *
     * @throws SQLException
     */
    public static void stopDb() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }


    /**
     * Metodo para recrear las tablas necesarios
     * @throws SQLException
     */
    public static void crearTablas() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS USUARIO \n" +
                "(\n" +
                "USERNAME VARCHAR (50) PRIMARY KEY NOT NULL, \n" +
                "NOMBRE VARCHAR (50) NOT NULL, \n" +
                "PASSWORD VARCHAR (25) NOT NULL, \n" +
                "ADMINISTRADOR BOOLEAN NOT NULL, \n" +
                "AUTOR BOOLEAN NOT NULL \n" +
                ");" +
                "\n CREATE TABLE IF NOT EXISTS ARTICULO" +
                "(\n" +
                "  ID INTEGER PRIMARY KEY auto_increment,\n" +
                "  TITULO VARCHAR(100) NOT NULL,\n" +
                "  CONTENIDO VARCHAR(5000) NOT NULL,\n" +
                "  AUTOR VARCHAR(25) REFERENCES USUARIO(USERNAME),\n" +
                "  FECHA VARCHAR(50) NOT NULL" +
                ");" +
                "\n CREATE TABLE IF NOT EXISTS COMENTARIO" +
                "(\n" +
                "ID INTEGER PRIMARY KEY auto_increment, \n"+
                "COMENTARIO VARCHAR (1000) NOT NULL, \n" +
                "USUARIO VARCHAR (50) REFERENCES USUARIO(USERNAME)," +
                "ARTICULO INTEGER REFERENCES ARTICULO(ID) " +
                ");" +
                "\n CREATE TABLE IF NOT EXISTS ETIQUETA" +
                "(\n" +
                "ID INTEGER PRIMARY KEY auto_increment \n," +
                "NOMBRE VARCHAR (20) NOT NULL" +
                 ");" +
                "\n CREATE TABLE IF NOT EXISTS ETIQUETA_ARTICULO" +
                "(\n" +
                "ETIQUETA INTEGER REFERENCES ETIQUETA(ID)," +
                "ARTICULO INTEGER REFERENCES ARTICULO(ID)" +
                ");";
        Connection con = DataBase.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

}
