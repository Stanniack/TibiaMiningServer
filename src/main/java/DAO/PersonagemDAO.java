package DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.Personagem;
import utils.JPAUtil;

public class PersonagemDAO {

    public String searchName(String name) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT playerName FROM Personagem p where playerName = :pName";

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

    public Integer returnID(String name) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT idCharacter FROM Personagem p where playerName = :pName";

        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);

        query.setParameter("pName", name);

        Integer idReturn;

        idReturn = query.getSingleResult();

        em.close();

        return idReturn;
    }

    public Personagem returnCharacterByName(String string) {

        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM Personagem t WHERE playerName = :pString";

        TypedQuery<Personagem> query = em.createQuery(jpql, Personagem.class);
        query.setParameter("pString", string);

        Personagem personagem = query.getSingleResult();
        em.close();

        return personagem;
    }
}
