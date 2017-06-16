package Servicios;

import org.h2.tools.Server;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by darle on 6/5/2017.
 */
public class BootStrapService {

    private static BootStrapService instancia;

    private BootStrapService(){

    }

    public static BootStrapService getInstancia(){
        if(instancia == null){
            instancia=new BootStrapService();
        }
        return instancia;
    }

    public void startDb() {
        try {
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon").start();
        }catch (SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }
    }

    public void init(){
        startDb();
    }

}
