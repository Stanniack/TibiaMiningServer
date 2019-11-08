/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtil;

public class AbstractDAO<Generico> {

    private final Class<Generico> classe;

    public AbstractDAO(Class<Generico> classe) {
        this.classe = classe;
    }

    public void insert(Generico objeto) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Generico objeto) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        em.merge(objeto);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Integer id) {
        EntityManager em = JPAUtil.getInstance();
        em.getTransaction().begin();
        Generico objeto = em.find(classe, id);
        em.remove(objeto);
        em.getTransaction().commit();
        em.close();
    }

    public Generico searchForId(int id) {

        EntityManager em = JPAUtil.getInstance();
        Generico obj = em.find(classe, id);
        em.close();

        return obj;
    }

    public List<Generico> listAll() {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + classe.getName() + " t";

        TypedQuery<Generico> query = em.createQuery(jpql, classe);

        List<Generico> lista = query.getResultList();

        em.close();

        return lista;
    }

    public List<Generico> listAll(int idCharacter) {
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM " + classe.getName() + " t " + "where idCharacter = :pId";

        TypedQuery<Generico> query = em.createQuery(jpql, classe);
        query.setParameter("pId", idCharacter);

        List<Generico> lista = query.getResultList();

        em.close();

        return lista;
    }

}
