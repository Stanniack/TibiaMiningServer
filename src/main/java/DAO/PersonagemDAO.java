package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.FormerName;
import model.Personagem;
import utils.JPAUtil;

public class PersonagemDAO {

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

        Personagem personagem = null;

        try {
            personagem = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Não possui resultados para este nome em personagem: " + string);
            e.printStackTrace();
        }

        em.close();

        return personagem;
    }

    public FormerName returnFormerNameByOldName(String oldName) {

        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM FormerName t WHERE oldName = :pOldName order by dateEnd DESC";

        TypedQuery<FormerName> query = em.createQuery(jpql, FormerName.class);
        query.setParameter("pOldName", oldName);
        query.setMaxResults(1);

        FormerName fn = null;

        try {
            fn = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Não possui resultados para este oldaname em formerName: " + oldName);
            e.printStackTrace();
        }

        em.close();

        return fn;
    }

    public List<String> listAll() {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT playerName FROM Personagem p";

        TypedQuery<String> query = em.createQuery(jpql, String.class);

        List<String> lista = query.getResultList();

        em.close();

        return lista;
    }
}
