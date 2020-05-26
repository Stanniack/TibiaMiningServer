package DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.GuildInfo;
import model.Player;
import utils.JPAUtil;

public class GuildDAO {
    
    public List<String> listAll(String world) {
        
        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT DISTINCT guildName FROM GuildInfo g WHERE world = :pWorld";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("pWorld", world);
        
        List<String> list = new ArrayList<>();

        try {
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error - GuildDao - listAll: " + ex);
        }

        

        em.close();

        return list;
    }
    
    public GuildInfo returnGuildByWorld(String world, String name) {

        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT g FROM GuildInfo g WHERE guildName = :pName AND world = :pString";

        TypedQuery<GuildInfo> query = em.createQuery(jpql, GuildInfo.class);
        query.setParameter("pString", world);
        query.setParameter("pName", name);

        GuildInfo guildInfo = null;

        try {
            guildInfo = query.getSingleResult();
        } catch (NoResultException e) {
            //System.out.println("returnCharacterByName: Não possui resultados para este nome em player: " + string);
        }

        em.close();

        return guildInfo;
    }
}
