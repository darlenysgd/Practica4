package Servicios;

import entidades.Articulo;
import entidades.Etiqueta;
import entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by darle on 6/15/2017.
 */
public class ArticuloServices extends GestionDB<Articulo> {


    private static ArticuloServices instancia;


    private ArticuloServices() {
        super(Articulo.class);
    }

    public static ArticuloServices getInstancia() {
        if (instancia == null) {
            instancia = new ArticuloServices();
        }
        return instancia;
    }

    /**
     * @param id
     * @return
     */

    public List<Articulo> findAllEtiquetas(long id) {

        EntityManager em = getEntityManager();
        try {
            return  (List<Articulo>) em.createNamedQuery(Articulo.QUERY_NAME_FIND_ARTICULO_BY_ETIQUETA).setParameter("id", id).getResultList();



        } catch (Exception ex) {
            throw ex;

        }

    }
}
