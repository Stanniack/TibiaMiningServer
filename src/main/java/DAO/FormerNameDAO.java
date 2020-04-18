
package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtil;

public class FormerNameDAO {
    
    public List<String> listAll(String idCharacter) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT oldName FROM FormerName t where idCharacter = :pId";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("pId", idCharacter);

        List<String> lista = query.getResultList();

        em.close();

        return lista;
    }
}
