package DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.LevelAdvance;
import model.LoyaltyPoints;
import utils.JPAUtil;

public class LoyaltyPointsDAO {

    public LoyaltyPoints returnLastRegisterDESC(String name) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM LoyaltyPoints t WHERE playerName = :pName order by idLoyaltyPoints DESC";

        TypedQuery<LoyaltyPoints> query = em.createQuery(jpql, LoyaltyPoints.class);
        query.setParameter("pName", name);
        query.setMaxResults(1);

        LoyaltyPoints obj = null;

        try {
            obj = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        em.close();

        return obj;
    }
}
