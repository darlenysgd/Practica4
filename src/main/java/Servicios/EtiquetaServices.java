package Servicios;

import entidades.Etiqueta;
import entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by darle on 6/15/2017.
 */
public class EtiquetaServices extends GestionDB<Etiqueta> {

    private static EtiquetaServices instancia;

    private EtiquetaServices(){
        super(Etiqueta.class);
    }

    public static EtiquetaServices getInstancia(){
        if(instancia==null){
            instancia = new EtiquetaServices();
        }
        return instancia;
    }

    /**
     *
     * @param id
     * @return
     */


}
