package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Personagem;
import utils.JPAUtil;

public class PersonagemDAO {

    public String searchName(String name) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT name FROM Personagem p where name = :pName";

        TypedQuery<String> query = em.createQuery(jpql, String.class);

        query.setParameter("pName", name);

        String nameReturn = "no results";

        try {
            nameReturn = (String) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Não possui resultados para este nome. ");
        }

        em.close();

        return nameReturn;
    }

    public int returnID(String name) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT idCharacter FROM Personagem p where name = :pName";

        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);

        query.setParameter("pName", name);

        int idReturn = -1;

        try {
            idReturn = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Não possui resultados para este nome. ");
        }

        em.close();

        return idReturn;
    }
}
