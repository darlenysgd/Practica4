package Servicios;

import entidades.Articulo;
import entidades.Comentario;

/**
 * Created by darle on 6/15/2017.
 */
public class ComentarioServices extends GestionDB<Comentario> {

    private static ComentarioServices instancia;


    private ComentarioServices(){
        super(Comentario.class);
    }

    public static ComentarioServices getInstancia(){
        if(instancia==null){
            instancia = new ComentarioServices();
        }
        return instancia;
    }

}
