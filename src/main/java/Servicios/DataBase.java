package Servicios;

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
           // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
