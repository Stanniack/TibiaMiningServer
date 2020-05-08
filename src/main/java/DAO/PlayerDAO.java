package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.FormerName;
import model.Player;
import utils.JPAUtil;

public class PlayerDAO {

    public Integer returnID(String name) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT idCharacter FROM Player p where playerName = :pName";

        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);

        query.setParameter("pName", name);

        Integer idReturn;

        idReturn = query.getSingleResult();

        em.close();

        return idReturn;
    }

    public Player returnCharacterByName(String string) {

        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM Player t WHERE playerName = :pString";

        TypedQuery<Player> query = em.createQuery(jpql, Player.class);
        query.setParameter("pString", string);

        Player player = null;

        try {
            player = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Não possui resultados para este nome em player: " + string);
            e.printStackTrace();
        }

        em.close();

        return player;
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

        String jpql = "SELECT playerName FROM Player p";

        TypedQuery<String> query = em.createQuery(jpql, String.class);

        List<String> lista = query.getResultList();

        em.close();

        return lista;
    }
}
