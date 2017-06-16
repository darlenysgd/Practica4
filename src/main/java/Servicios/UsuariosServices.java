package Servicios;

import entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by darle on 6/14/2017.
 */
public class UsuariosServices extends GestionDB<Usuario>{

        private static UsuariosServices instancia;

        private UsuariosServices(){
            super(Usuario.class);
        }

        public static UsuariosServices getInstancia(){
            if(instancia==null){
                instancia = new UsuariosServices();
            }
            return instancia;
        }

    /**
     *
     * @param username
     * @return
     */

    public List<Usuario> findAllByUsername(String username){

        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Usuario e where e.username like :username");
        query.setParameter("username", username+"%");
        List<Usuario> lista = query.getResultList();

        return lista;
    }

}
