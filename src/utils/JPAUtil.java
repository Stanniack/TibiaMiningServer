package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JPAUtil {
    
    // CARREGA A UNIDADE DE PERSISTENCIA
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tibiaminingDB");
   
    // CONECTA COM O BANCO
    public static EntityManager getInstance(){
        return emf.createEntityManager();
    }

}
