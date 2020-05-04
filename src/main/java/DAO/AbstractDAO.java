package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtil;

public class AbstractDAO<Generic> {

    private final Class<Generic> className;

    public AbstractDAO(Class<Generic> className) {
        this.className = className;
    }

    public void insert(Generic object) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Generic object) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Integer id) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        Generic objeto = em.find(className, id);
        em.remove(objeto);
        em.getTransaction().commit();
        em.close();
    }

    public Generic searchObjectById(int id) {

        EntityManager em = JPAUtil.getInstance();
        Generic obj = em.find(className, id);
        em.close();

        return obj;
    }

    public Generic searchObjectByColumn(String column, String equals) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + className.getName() + " t where " + column + " = :pId";

        TypedQuery<Generic> query = em.createQuery(jpql, className);
        query.setParameter("pId", equals);

        Generic obj = query.getSingleResult();

        return obj;

    }

    public List<String> listAll(String idCharacter, String column) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT " + column + " FROM " + className.getName() + " t where idCharacter = :pId";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("pId", idCharacter);

        List<String> lista = query.getResultList();

        em.close();

        return lista;
    }

    public Generic searchLastRegisterDESC(int id, String column) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM " + className.getName() + " t WHERE idCharacter = "
                + id + "order by " + column + " DESC";

        TypedQuery<Generic> query = em.createQuery(jpql, className);
        query.setMaxResults(1);

        Generic obj = query.getSingleResult();
        em.close();

        return obj;
    }

    public Generic searchLastRegisterASC(int id, String column) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT t FROM " + className.getName() + " t WHERE idCharacter = "
                + id + "order by " + column + " ASC";

        TypedQuery<Generic> query = em.createQuery(jpql, className);
        query.setMaxResults(1);

        Generic obj = query.getSingleResult();
        em.close();

        return obj;
    }

    public Long countRegistersById(int id) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT COUNT(t) FROM " + className.getName() + " t WHERE idCharacter = :pId";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("pId", id);

        Long result = query.getSingleResult();
        em.close();

        return result;
    }

    public Long countRegistersByName(String name) {

        EntityManager em = JPAUtil.getInstance();
        String jpql = "SELECT COUNT(t) FROM " + className.getName() + " t WHERE playerName = :pName";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("pName", name);

        Long result = query.getSingleResult();
        em.close();

        return result;
    }

    public List<Generic> listAll() {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + className.getName() + " t";

        TypedQuery<Generic> query = em.createQuery(jpql, className);

        List<Generic> list = query.getResultList();
        em.close();

        return list;
    }

    public List<Generic> listAll(int idCharacter) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + className.getName() + " t " + "where idCharacter = :pId";

        TypedQuery<Generic> query = em.createQuery(jpql, className);
        query.setParameter("pId", idCharacter);

        List<Generic> list = query.getResultList();
        em.close();

        return list;
    }

}
