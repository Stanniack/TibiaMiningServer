package tibiamining;

import DAO.AbstractDAO;
import javax.persistence.EntityManager;
import regrasdenegocio.CheckOnline;
import model.Personagem;
import utils.JPAUtil;

public class TibiaMining {

    public static void main(String[] args) {
            
        Personagem c = new Personagem();
        c.setName("teste2");

        new AbstractDAO<>(Personagem.class).insert(c);
//        EntityManager em = JPAUtil.getInstance();
//
//        em.getTransaction().begin();
//        em.persist(c);
//        em.getTransaction().commit();
//        em.close();

    }

}
