package DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.LoyaltyPoints;
import utils.JPAUtil;

public class LoyaltyPointsDAO {

    public LoyaltyPoints returnLastRegisterDESC(String name) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM LoyaltyPoints t WHERE playerName = :pName order by idLoyaltyPoints DESC";

        TypedQuery<LoyaltyPoints> query = em.createQuery(jpql, LoyaltyPoints.class);
        query.setParameter("pName", name);
        query.setMaxResults(1);

        LoyaltyPoints obj = query.getSingleResult();
        em.close();

        return obj;
    }
}
