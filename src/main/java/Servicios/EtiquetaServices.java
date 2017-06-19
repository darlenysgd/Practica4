package Servicios;

import entidades.Articulo;
import entidades.Etiqueta;
import entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by darle on 6/15/2017.
 */
public class EtiquetaServices extends GestionDB<Etiqueta> {


    private static EtiquetaServices instancia;

    private EtiquetaServices() {
        super(Etiqueta.class);
    }

    public static EtiquetaServices getInstancia() {
        if (instancia == null) {
            instancia = new EtiquetaServices();
        }
        return instancia;
    }

    /**
     * @param etiqueta
     * @return
     */


    public Etiqueta findEtiqueta(String etiqueta) {

        EntityManager em = getEntityManager();

        try {
            Query q = em.createNamedQuery(Etiqueta.QUERY_FIND_ETIQUETA_BY_NAME);
            q.setParameter("etiqueta", etiqueta);
            return (Etiqueta) q.getSingleResult();
        } catch (NoResultException e){
            return null;
        }

    }
}
