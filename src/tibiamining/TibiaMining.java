package tibiamining;

import DAO.AbstractDAO;
import javax.persistence.EntityManager;
import regrasdenegocio.SearchCharacter;
import regrasdenegocio.CheckOnline;
import model.CharacterTibia;
import model.FormerWorld;
import utils.JPAUtil;

public class TibiaMining {

    public static void main(String[] args) {

        CharacterTibia c = new CharacterTibia();
        c.setName("teste2");
        
        EntityManager em = JPAUtil.getInstance();
        
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();

    }

}
