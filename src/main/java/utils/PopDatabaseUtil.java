package utils;

import DAO.AbstractDAO;
import DAO.PlayerDAO;
import java.util.List;
import model.FormerName;
import model.LevelAdvance;
import model.LoyaltyPoints;
import model.Player;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckRank;

/* Classe contendo método para popular o banco de dados inicialmente. */
public class PopDatabaseUtil {

    /* Popula todos os personagens contidos na lista dos ranks*/
    public static void popPlayersByExpRank(List<LevelAdvance> list) {
//        List<LevelAdvance> list = new CheckRank().checkGlobalRankExperience();

        for (LevelAdvance la : list) {

            Long register = new AbstractDAO<>(Player.class).countRegistersByName(la.getPlayerName());

            /* Se não existir, captura o player */
            if (register == 0) {
                new CheckCharacter().discoverCharacter(la.getPlayerName());
                
                Player p = new PlayerDAO().returnCharacterByName(la.getPlayerName());

                /* Vincula L.A com Player*/
                if (p != null) {
                    la.setPlayer(p);
                    new AbstractDAO<>(LevelAdvance.class).update(la);

                } else {
                    FormerName pFN = new PlayerDAO().returnFormerNameByOldName(la.getPlayerName());

                    /* Vincula L.A com player que teve seu nome alterado, busca o atual nick */
                    if (pFN != null) {
                        la.setPlayerName(pFN.getPlayer().getPlayerName());
                        la.setPlayer(pFN.getPlayer());
                        new AbstractDAO<>(LevelAdvance.class).update(la);
                    }
                }
            }

        }
    }

    public static void popPlayersByLoyaltyRank() {
        List<LoyaltyPoints> list = new CheckRank().checkGlobalRankLoyalty();

        for (LoyaltyPoints lp : list) {
            Long register = new AbstractDAO<>(Player.class).countRegistersByName(lp.getPlayerName());

            /* Se não existir, captura o player */
            if (register == 0) {
                new CheckCharacter().discoverCharacter(lp.getPlayerName());

                Player p = new PlayerDAO().returnCharacterByName(lp.getPlayerName());

                /* Vincula L.P com Player*/
                if (p != null) {
                    lp.setPlayer(p);
                    new AbstractDAO<>(LoyaltyPoints.class).update(lp);

                } else {
                    FormerName pFN = new PlayerDAO().returnFormerNameByOldName(lp.getPlayerName());

                    /* Vincula L.P com player que teve seu nome alterado, busca o atual nick */
                    if (pFN != null) {
                        lp.setPlayerName(pFN.getPlayer().getPlayerName());
                        lp.setPlayer(pFN.getPlayer());
                        new AbstractDAO<>(LoyaltyPoints.class).update(lp);
                    }
                }
            }

        }
    }

    public static void popPlayersBySkillsRank() {

    }
}
