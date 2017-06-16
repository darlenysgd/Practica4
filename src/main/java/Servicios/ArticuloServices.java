package Servicios;

import entidades.Articulo;
import entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by darle on 6/15/2017.
 */
public class ArticuloServices extends GestionDB<Articulo> {


    private static ArticuloServices instancia;


    private ArticuloServices(){
        super(Articulo.class);
    }

    public static ArticuloServices getInstancia(){
        if(instancia==null){
            instancia = new ArticuloServices();
        }
        return instancia;
    }

    /**
     *
     * @param id
     * @return
     */


}
