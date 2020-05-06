package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtil;

public class LevelAdvanceDAO {
    
    public List<String> listAll() {
        
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT playerName FROM LevelAdvance t";

        TypedQuery<String> query = em.createQuery(jpql, String.class);

        List<String> list = query.getResultList();
        em.close();

        return list;
    }
    
    
}
