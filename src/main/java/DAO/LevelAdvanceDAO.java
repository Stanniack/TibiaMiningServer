package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.LevelAdvance;
import utils.JPAUtil;

public class LevelAdvanceDAO {

    public List<String> listAllPLayerNames() {

        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT DISTINCT playerName FROM LevelAdvance t";

        TypedQuery<String> query = em.createQuery(jpql, String.class);

        List<String> list = query.getResultList();
        em.close();

        return list;
    }
    
    

    public LevelAdvance returnLastRegisterDESC(String name) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM LevelAdvance t WHERE playerName = :pName order by idLevelAdvance DESC";

        TypedQuery<LevelAdvance> query = em.createQuery(jpql, LevelAdvance.class);
        query.setParameter("pName", name);
        query.setMaxResults(1);

        LevelAdvance obj = query.getSingleResult();
        em.close();

        return obj;
    }

}
