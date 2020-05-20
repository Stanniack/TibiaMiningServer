package DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.HouseInfo;
import utils.JPAUtil;


public class HouseInfoDAO {
    
    public HouseInfo returnHouseInfoByName(String string) {

        EntityManager em = JPAUtil.getInstance();

        String jpql = "SELECT t FROM HouseInfo t WHERE houseName = :pString";

        TypedQuery<HouseInfo> query = em.createQuery(jpql, HouseInfo.class);
        query.setParameter("pString", string);

        HouseInfo houseInfo = null;

        try {
            houseInfo = query.getSingleResult();
        } catch (NoResultException e) {
            //System.out.println("returnCharacterByName: Não possui resultados para este nome em player: " + string);
        }

        em.close();

        return houseInfo;
    }
}
