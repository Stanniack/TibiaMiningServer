package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.LevelAdvanceDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.NoResultException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.WorldsTibiaUtil;
import model.CharacterSkills;
import model.FormerName;
import model.LevelAdvance;
import model.LoyaltyPoints;
import model.Player;
import utils.DateUtil;
import utils.SkillsTibiaUtil;

public class CheckRank {

    // Conteúdo na lista começa no índice 13 para skills, 14 para experience
    private static final int CONTENT_START_SKILLS = 13;
    private static final int CONTENT_START_EXP = 14;
    private static final int CONTENT_START_LOYALTY = 14;

    // O último elemento da lista é lixo
    private static final int TRASH_ELIMINATOR_SKILLS = 1;
    private static final int TRASH_ELIMINATOR_EXP = 5;
    private static final int TRASH_ELIMINATOR_LOYALTY = 1;
    /* O incrementador é 5 para skills, 4 para experience devido aos atributos de cada char */
    private static final int INCREMENTOR_SKILLS = 4;
    private static final int INCREMENTOR_EXP = 5;
    private static final int INCREMENTOR_LOYALTY = 5;

    /* Atribuição */
    private static final int PROFESSION = 4;
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = 12;
    private static final int NAME = 1;
    private static final int VOCATION = 2;
    private static final int LEVEL = 3;
    private static final int POINTS = 3;
    private static final int EXPERIENCE = 4;
    private static final int LOYALTY = 4;

    /* Para a lista objects */
    private static final int LAST_NICK = 1;
    private static final int ELEMENTS_LIST = 0;

    public void checkGlobalRankSkills() {

        Long startTime = System.currentTimeMillis();
        List<String> worlds = WorldsTibiaUtil.getWorldsTibia();
        List<String> skills = SkillsTibiaUtil.getSkillsTibia();

        for (int x = 0; x < SkillsTibiaUtil.getSkillsTibia().size(); x++) {

            Long serverStartTime = System.currentTimeMillis();

            for (int i = 0; i < WorldsTibiaUtil.getWorldsTibia().size(); i++) {

                Long worldStartTime = System.currentTimeMillis();

                for (int n = 1; n <= PROFESSION; n++) {

                    Long professionStartTime = System.currentTimeMillis();
                    int j = 2;

                    for (j = FIRST_PAGE; j <= LAST_PAGE; j++) {
                        int k = 17;

                        try {

                            String url = "https://www.tibia.com/community/?subtopic=highscores&world="
                                    + worlds.get(i) + "&list=" + skills.get(x) + "&profession=" + n + "&currentpage=" + j;

                            Document htmlContent = Jsoup.connect(url).get();
                            List<String> elementsList = htmlContent.getElementsByTag("td").eachText();
                            Player p;

                            for (k = CONTENT_START_SKILLS; k < elementsList.size() - TRASH_ELIMINATOR_SKILLS; k += INCREMENTOR_SKILLS) {

                                /* Busca último nick do personagem */
                                List<Object> objects = new CheckCharacter().getNick(elementsList.get(k + NAME));

                                /* !(Char deletado ou não existe ou a net caiu) */
                                if (objects != null) {

                                    String lastNick = objects.get(LAST_NICK).toString();
                                    List<String> lastNickElementsList = (List<String>) objects.get(ELEMENTS_LIST);

                                    Long isRegistered = new AbstractDAO<>(Player.class)
                                            .countRegistersByName(elementsList.get(k + NAME));

                                    /* Nick trocado e char não existe no BD */
                                    if (!lastNick.equals(elementsList.get(k + NAME)) && isRegistered == 0) {

                                        new CheckCharacter().discoverCharacter(lastNickElementsList);
                                        p = new PlayerDAO().returnCharacterByName(lastNick);

                                        /* Nick trocado e char exite no BD */
                                    } else if (!lastNick.equals(elementsList.get(k + NAME)) && isRegistered != 0) {

                                        new CheckCharacter()
                                                .updateCharacter(lastNick, lastNickElementsList);
                                        p = new PlayerDAO().returnCharacterByName(lastNick);

                                        /* Nick não foi trocado e char não existe no BD */
                                    } else if (lastNick.equals(elementsList.get(k + NAME)) && isRegistered == 0) {

                                        new CheckCharacter().discoverCharacter(lastNickElementsList);
                                        p = new PlayerDAO().returnCharacterByName(elementsList.get(k + NAME));

                                        /* Nick não foi trocado e char existe no BD */
                                    } else {
                                        p = new PlayerDAO().returnCharacterByName(elementsList.get(k + NAME));

                                    }

                                    Long register = new AbstractDAO<>(CharacterSkills.class)
                                            .countRegistersById(p.getIdCharacter());
                                    CharacterSkills cs0;
                                    CharacterSkills cs = new CharacterSkills();


                                    /* Se tiver registro */
                                    if (register > 0) {

                                        cs0 = new AbstractDAO<>(CharacterSkills.class)
                                                .returnLastRegisterDESC(p.getIdCharacter(), "registerDate");

                                        /* Transfere todos os outros skills do registro anterior para o novo registro 
                                             * Isso serve para caso um char esteja em mais de 1 ranking score */
                                        cs = new CharacterSkills(
                                                cs0.getAxeFighting(),
                                                cs0.getClubFighting(),
                                                cs0.getDistanceFighting(),
                                                cs0.getFishing(),
                                                cs0.getFistFighting(),
                                                cs0.getLoyaltyPoints(),
                                                cs0.getMagicLevel(),
                                                cs0.getShielding(),
                                                cs0.getSwordFighting()
                                        );

                                        /* Então é o primeiro registro */
                                    } else {

                                        cs0 = new CharacterSkills();
                                        cs0.setPlayer(p);
                                        new AbstractDAO<>(CharacterSkills.class).insert(cs0);
                                    }

                                    boolean flagUpdate = false;

                                    /* Regras de negócio para cada skill */
                                    switch (skills.get(x).toLowerCase()) {

                                        case "axe":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getAxeFighting()))) {
                                                cs.setAxeFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;
                                            }

                                            break;

                                        case "club":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getClubFighting()))) {
                                                cs.setClubFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "distance":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getDistanceFighting()))) {
                                                cs.setDistanceFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "fishing":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getFishing()))) {
                                                cs.setFishing(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "fist":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getFistFighting()))) {
                                                cs.setFistFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "magic":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getMagicLevel()))) {
                                                cs.setMagicLevel(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "shielding":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getShielding()))) {
                                                cs.setShielding(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "sword":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(String.valueOf(cs0.getSwordFighting()))) {
                                                cs.setSwordFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        default:
                                            break;
                                    }

                                    /* Caso algo tenha mudado */
                                    if (flagUpdate == true) {
                                        cs.setRegisterDate(Calendar.getInstance());
                                        cs.setPlayer(p);
                                        new AbstractDAO<>(CharacterSkills.class).update(cs);
                                    }

                                }

                            } // for personagens da página

                        } catch (IOException e) {
                            /* Volta no índice que deu hostexception */
                            j--;
                            k -= INCREMENTOR_SKILLS;

                        } catch (NumberFormatException | NoResultException e) {
                            System.out.println("Error:: " + e);
                            e.printStackTrace();

                        }

                    } // for das páginas

                    Long professionFinalTime = System.currentTimeMillis();
                    System.out.println("A profissão " + n + " foi minerada com "
                            + ((professionFinalTime - professionStartTime) / 1000) + " segundos.");

                } // for das profissões

                Long worldFinalTime = System.currentTimeMillis();
                System.out.println("O servidor de " + worlds.get(i) + " gastou " + ((worldFinalTime - worldStartTime) / 1000)
                        + " segundos para ser minerado");

            } // for dos mundos

            Long serverFinalTime = System.currentTimeMillis();
            System.out.println("O tempo total para minerar todos  os servidores para o skill de " + skills.get(x) + " foi de "
                    + ((serverFinalTime - serverStartTime) / 1000)
                    + " segundos");

        } // for skills

        Long finalTime = System.currentTimeMillis();

        System.out.println("O tempo total para minerar todos os skills, servidores e profissões foram de: "
                + ((finalTime - startTime) / 1000) + " segundos.");

    }

    public void checkGlobalRankLoyalty() {

        Long startTime = System.currentTimeMillis();
        List<String> worlds = WorldsTibiaUtil.getWorldsTibia();

        Long serverStartTime = System.currentTimeMillis();

        for (int i = 0; i < WorldsTibiaUtil.getWorldsTibia().size(); i++) {

            Long worldStartTime = System.currentTimeMillis();

            for (int n = 1; n <= PROFESSION; n++) {

                Long professionStartTime = System.currentTimeMillis();
                int j = 2;

                for (j = FIRST_PAGE; j <= LAST_PAGE; j++) {
                    int k = 17;

                    try {

                        String url = "https://www.tibia.com/community/?subtopic=highscores&world="
                                + worlds.get(i) + "&list=" + "loyalty" + "&profession=" + n + "&currentpage=" + j;

                        Document htmlContent = Jsoup.connect(url).get();
                        List<String> elementsList = htmlContent.getElementsByTag("td").eachText();
                        Player p;

                        for (k = CONTENT_START_LOYALTY; k < elementsList.size() - TRASH_ELIMINATOR_LOYALTY; k += INCREMENTOR_LOYALTY) {

                            /* Busca último nick do personagem */
                            List<Object> objects = new CheckCharacter().getNick(elementsList.get(k + NAME));

                            /* !(Char deletado ou não existe ou a net caiu) */
                            if (objects != null) {

                                String lastNick = objects.get(LAST_NICK).toString();
                                List<String> lastNickElementsList = (List<String>) objects.get(ELEMENTS_LIST);

                                Long isRegistered = new AbstractDAO<>(Player.class)
                                        .countRegistersByName(elementsList.get(k + NAME));

                                /* Nick trocado e char não existe no BD */
                                if (!lastNick.equals(elementsList.get(k + NAME)) && isRegistered == 0) {

                                    new CheckCharacter().discoverCharacter(lastNickElementsList);
                                    p = new PlayerDAO().returnCharacterByName(lastNick);

                                    /* Nick trocado e char exite no BD */
                                } else if (!lastNick.equals(elementsList.get(k + NAME)) && isRegistered != 0) {

                                    new CheckCharacter()
                                            .updateCharacter(lastNick, lastNickElementsList);
                                    p = new PlayerDAO().returnCharacterByName(lastNick);

                                    /* Nick não foi trocado e char não existe no BD */
                                } else if (lastNick.equals(elementsList.get(k + NAME)) && isRegistered == 0) {

                                    new CheckCharacter().discoverCharacter(lastNickElementsList);
                                    p = new PlayerDAO().returnCharacterByName(elementsList.get(k + NAME));

                                    /* Nick não foi trocado e char existe no BD */
                                } else {
                                    p = new PlayerDAO().returnCharacterByName(elementsList.get(k + NAME));

                                }

                                Long register = new AbstractDAO<>(LoyaltyPoints.class)
                                        .countRegistersById(p.getIdCharacter());

                                /* Se tiver registro */
                                if (register > 0) {

                                    LoyaltyPoints lp0 = new AbstractDAO<>(LoyaltyPoints.class)
                                            .returnLastRegisterDESC(p.getIdCharacter(), "idLoyaltyPoints");

                                    String strValue = elementsList.get(k + LOYALTY).replace(",", "");
                                    Integer loyaltyValue = Integer.valueOf(strValue);

                                    if (!lp0.getLoyaltyPoints().equals(loyaltyValue)) {

                                        LoyaltyPoints lp = new LoyaltyPoints(p, loyaltyValue, Calendar.getInstance());

                                        new AbstractDAO<>(LoyaltyPoints.class).insert(lp);

                                    }

                                    /* Então é o primeiro registro */
                                } else {

                                    String strValue = elementsList.get(k + LOYALTY).replace(",", "");
                                    Integer loyaltyValue = Integer.valueOf(strValue);

                                    LoyaltyPoints lp = new LoyaltyPoints(p, loyaltyValue, Calendar.getInstance());

                                    new AbstractDAO<>(LoyaltyPoints.class).insert(lp);
                                }

                            }

                        } // for personagens da página

                    } catch (IOException e) {
                        /* Volta no índice que deu hostexception */
                        j--;
                        k -= INCREMENTOR_SKILLS;

                    } catch (NumberFormatException | NoResultException e) {
                        System.out.println("Error: " + e);
                        e.printStackTrace();

                    }

                } // for das páginas

                Long professionFinalTime = System.currentTimeMillis();
                System.out.println("A profissão " + n + " foi minerada com "
                        + ((professionFinalTime - professionStartTime) / 1000) + " segundos.");

            } // for das profissões

            Long worldFinalTime = System.currentTimeMillis();
            System.out.println("O servidor de " + worlds.get(i) + " gastou " + ((worldFinalTime - worldStartTime) / 1000)
                    + " segundos para ser minerado");

        } // for dos mundos

        Long serverFinalTime = System.currentTimeMillis();
        System.out.println("O tempo total para minerar todos  os servidores foi de "
                + ((serverFinalTime - serverStartTime) / 1000)
                + " segundos");

    }

    public List<LevelAdvance> checkGlobalRankExperience() {

        Long startTime = System.currentTimeMillis();
        List<LevelAdvance> laList = new ArrayList<>();
        List<String> worlds = WorldsTibiaUtil.getWorldsTibia();

        Long serverStartTime = System.currentTimeMillis();

        for (int i = 0; i < worlds.size(); i++) {

            Long worldStartTime = System.currentTimeMillis();

            for (int n = 1; n <= PROFESSION; n++) {

                Long professionStartTime = System.currentTimeMillis();
                int j = 2;

                for (j = FIRST_PAGE; j <= LAST_PAGE; j++) {
                    int k = 17;

                    try {

                        String url = "https://www.tibia.com/community/?subtopic=highscores&world="
                                + worlds.get(i) + "&list=" + "experience" + "&profession=" + n + "&currentpage=" + j;

                        Document htmlContent = Jsoup.connect(url).get();
                        List<String> elementsList = htmlContent.getElementsByTag("td").eachText();

                        for (k = CONTENT_START_EXP; k < elementsList.size() - TRASH_ELIMINATOR_EXP; k += INCREMENTOR_EXP) {

                            LevelAdvance la0 = null;

                            try {

                                /* Busca último registro */
                                la0 = new LevelAdvanceDAO().returnLastRegisterDESC(elementsList.get(k + NAME));

                            } catch (NoResultException e) {

                            }

                            /* flag para verificar se precisa vincular L.A com Player */
                            boolean flagUpdate = false;
                            LevelAdvance la = null;

                            /* Conversão da exp String-> Long*/
                            String strValue = elementsList.get(k + EXPERIENCE).replace(",", "");
                            Long expValue = Long.valueOf(strValue);

                            /* Não é o primeiro registro */
                            if (la0 != null) {

                                /* Esse if só faz ter um update por dia */
                                if (DateUtil.sameDate(Calendar.getInstance(), la0.getLastUpdate()) != true
                                        && (!String.valueOf(la0.getLevelDay()).equals(elementsList.get(k + LEVEL))
                                        || !Objects.equals(la0.getExpDay(), expValue))) {

                                    la = new LevelAdvance(
                                            expValue,
                                            Integer.valueOf(elementsList.get(k + LEVEL)),
                                            elementsList.get(k + NAME),
                                            Calendar.getInstance());

                                    new AbstractDAO<>(LevelAdvance.class).insert(la);

                                    flagUpdate = true;

                                }

                            } else {

                                la = new LevelAdvance(
                                        expValue,
                                        Integer.valueOf(elementsList.get(k + LEVEL)),
                                        elementsList.get(k + NAME),
                                        Calendar.getInstance());

                                new AbstractDAO<>(LevelAdvance.class).insert(la);

                                flagUpdate = true;
                            }

                            /* Regra para vincular L.A com Player */
                            if (flagUpdate == true) {

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
                                
                                /* Adiciona o L.A capturado */
                                laList.add(la);
                                
                            } // fim if flag

                        } // for personagens da página

                    } catch (UnknownHostException e) {
                        /* Volta no índice que deu hostexception */
                        j--;
                        k -= INCREMENTOR_SKILLS;

                    } catch (IOException | NumberFormatException | NoResultException e) {
                        System.out.println("Checkrank EXP error: " + e);
                        e.printStackTrace();

                    }

                } // for das páginas

                Long professionFinalTime = System.currentTimeMillis();
                System.out.println("A profissão " + n + " foi minerada com "
                        + ((professionFinalTime - professionStartTime) / 1000) + " segundos.");

            } // for das profissões

            Long worldFinalTime = System.currentTimeMillis();
            System.out.println("O servidor de " + worlds.get(i) + " gastou " + ((worldFinalTime - worldStartTime) / 1000)
                    + " segundos para ser minerado");

        } // for dos mundos

        Long serverFinalTime = System.currentTimeMillis();
        System.out.println("O tempo total para minerar todos  os servidores foi de "
                + ((serverFinalTime - serverStartTime) / 1000)
                + " segundos");

        return laList;
    }

}
