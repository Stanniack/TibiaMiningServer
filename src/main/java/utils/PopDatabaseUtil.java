package utils;

import DAO.AbstractDAO;
import DAO.PlayerDAO;
import java.util.List;
import model.LevelAdvance;
import model.LoyaltyPoints;
import model.Player;
import model.PlayerSkills;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckRank;

/* Classe contendo método para popular o banco de dados inicialmente. */
public class PopDatabaseUtil {

    /* Popula ou atualiza todos os personagens contidos na lista dos ranks*/
    public static void popPlayersByExpRank() {
        List<LevelAdvance> list = new CheckRank().checkGlobalRankExperience();

        for (LevelAdvance la : list) {

            Long register = new AbstractDAO<>(Player.class).countRegistersByName(la.getPlayerName());

            if (register == 0) {

                new CheckCharacter().discoverCharacter(la.getPlayerName());

                Player p = new PlayerDAO().returnCharacterByName(la.getPlayerName());

                /* Vincula L.A com Player*/
                if (p != null) {
                    la.setPlayer(p);
                    new AbstractDAO<>(LevelAdvance.class).update(la);

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

                }
            }
        }

    }

    public static void popPlayersBySkillsRank() {
        List<PlayerSkills> list = new CheckRank().checkGlobalRankSkills();

        for (PlayerSkills ps : list) {
            Long register = new AbstractDAO<>(Player.class).countRegistersByName(ps.getPlayerName());

            /* Se não existir, captura o player */
            if (register == 0) {
                new CheckCharacter().discoverCharacter(ps.getPlayerName());

                Player p = new PlayerDAO().returnCharacterByName(ps.getPlayerName());

                /* Vincula L.P com Player*/
                if (p != null) {
                    ps.setPlayer(p);
                    new AbstractDAO<>(PlayerSkills.class).update(ps);

                }
            }
        }
    }

    /* - Problema ocorre ao procurar o lastRegister - : 
     * Métodos para evitarem casos raros onde um player tem o mesmo nick que um outro char já teve,
     * logo o personagem original tomaria os avanços de level/skill para ele. 
     * Estes métodos podem ter períodos para serem invocados (Menos de 6 meses) */
    public static void assignLevelAdvanceToItsOwner() {
    }

    public static void assignLoyalToItsOwner() {

    }

    public static void assignSkillsToItsOwner() {

    }
}
