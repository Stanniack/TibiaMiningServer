package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

        LevelAdvance obj = null;

        try {
            obj = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        em.close();

        return obj;
    }

    public LevelAdvance returnLastRegisterDESC(int id) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM LevelAdvance t WHERE idCharacter = :pId order by idLevelAdvance DESC";

        TypedQuery<LevelAdvance> query = em.createQuery(jpql, LevelAdvance.class);
        query.setParameter("pId", id);
        query.setMaxResults(1);

        LevelAdvance obj = null;

        try {
            obj = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        return obj;
    }

    public List<LevelAdvance> returnAllRegisterById(int id) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM LevelAdvance t WHERE idCharacter = :pId";

        TypedQuery<LevelAdvance> query = em.createQuery(jpql, LevelAdvance.class);
        query.setParameter("pId", id);

        List<LevelAdvance> objects = null;

        try {
            objects = query.getResultList();
        } catch (NoResultException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        return objects;
    }

    public List<Integer> returnAllIds() {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT DISTINCT idCharacter FROM LevelAdvance t";

        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);

        List<Integer> objects = null;

        try {
            objects = query.getResultList();
        } catch (NoResultException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        return objects;
    }

}
