package DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.PlayerSkills;
import utils.JPAUtil;

public class PlayerSkillsDAO {

    public PlayerSkills returnLastRegisterDESC(String name) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM PlayerSkills t WHERE playerName = :pName order by idPlayerSkills DESC";

        TypedQuery<PlayerSkills> query = em.createQuery(jpql, PlayerSkills.class);
        query.setParameter("pName", name);
        query.setMaxResults(1);

        PlayerSkills obj = null;

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
