package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtil;

public class AbstractDAO<Generic> {

    private final Class<Generic> classe;

    public AbstractDAO(Class<Generic> classe) {
        this.classe = classe;
    }

    public void insert(Generic objeto) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Generic objeto) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        em.merge(objeto);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Integer id) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        Generic objeto = em.find(classe, id);
        em.remove(objeto);
        em.getTransaction().commit();
        em.close();
    }

    public Generic searchObjectById(int id) {

        EntityManager em = JPAUtil.getInstance();
        Generic obj = em.find(classe, id);
        em.close();

        return obj;
    }

    public Generic searchObjectByColumn(String column, String equals) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + classe.getName() + " t where " + column + " = :pId";

        TypedQuery<Generic> query = em.createQuery(jpql, classe);
        query.setParameter("pId", equals);

        Generic obj = query.getSingleResult();

        return obj;

    }

    public List<String> listAll(String idCharacter, String column) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT " + column + " FROM " + classe.getName() + " t where idCharacter = :pId";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("pId", idCharacter);

        List<String> lista = query.getResultList();

        em.close();

        return lista;
    }

    public Generic searchLastRegisterDESC(int id, String column) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM " + classe.getName() + " t WHERE idCharacter = "
                + id + "order by " + column + " DESC";

        TypedQuery<Generic> query = em.createQuery(jpql, classe);
        query.setMaxResults(1);

        Generic obj = query.getSingleResult();
        em.close();

        return obj;
    }

    public Generic searchLastRegisterASC(int id, String column) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM " + classe.getName() + " t WHERE idCharacter = "
                + id + "order by " + column + " ASC";

        TypedQuery<Generic> query = em.createQuery(jpql, classe);
        query.setMaxResults(1);

        Generic obj = query.getSingleResult();
        em.close();

        return obj;
    }

    public Long countRegistersById(int id) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT COUNT(t) FROM " + classe.getName() + " t WHERE idCharacter = :pId";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("pId", id);

        Long result = query.getSingleResult();
        em.close();

        return result;
    }

    public Long countRegistersByName(String name) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT COUNT(t) FROM " + classe.getName() + " t WHERE playerName = :pName";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("pName", name);

        Long result = query.getSingleResult();
        em.close();

        return result;
    }

    public List<Generic> listAll() {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + classe.getName() + " t";

        TypedQuery<Generic> query = em.createQuery(jpql, classe);

        List<Generic> lista = query.getResultList();
        em.close();

        return lista;
    }

    public List<Generic> listAll(int idCharacter) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + classe.getName() + " t " + "where idCharacter = :pId";

        TypedQuery<Generic> query = em.createQuery(jpql, classe);
        query.setParameter("pId", idCharacter);

        List<Generic> lista = query.getResultList();
        em.close();

        return lista;
    }

}
