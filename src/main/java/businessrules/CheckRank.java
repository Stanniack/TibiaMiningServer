package businessrules;

import DAO.AbstractDAO;
import DAO.LevelAdvanceDAO;
import DAO.LoyaltyPointsDAO;
import DAO.PlayerDAO;
import DAO.PlayerSkillsDAO;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.NoResultException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.TibiaUtil;
import model.PlayerSkills;
import model.LevelAdvance;
import model.LoyaltyPoints;
import model.Player;
import utils.DateUtil;

public class CheckRank {

    // Conteúdo na lista começa no índice 13 para skills, 14 para experience
    private static final int START_CONTENT_SKILLS = 15;
    private static final int START_CONTENT_EXP = 14;
    private static final int START_CONTENT_LOYALTY = 14;

    /* Eliminação de elementos indesejávies */
    private static final int TRASH_ELIMINATOR_SKILLS = 1;
    private static final int TRASH_ELIMINATOR_EXP = 5;
    private static final int TRASH_ELIMINATOR_LOYALTY = 1;

    /* O incrementador é 5 para skills, 4 para experience devido aos atributos de cada char */
    private static final int INCREMENTOR_SKILLS = 6;
    private static final int INCREMENTOR_EXP = 6;
    private static final int INCREMENTOR_LOYALTY = 7;

    /* Atribuição */
    private static final int PROFESSION = 5;
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = 20;
    private static final int NAME = 2;
    private static final int NAME_SKILLS = 1;
    private static final int NAME_LOYALTY = 3;
    private static final int VOCATION = 3;
    private static final int LEVEL = 5;
    private static final int POINTS = 6;
    private static final int POINTS_SKILLS = 5;
    private static final int EXPERIENCE = 6;
    private static final int LOYALTY = 8;

    /* Para a lista objects */
    private static final int LAST_NICK = 1;
    private static final int ELEMENTS_LIST = 0;

    public List<PlayerSkills> getRankSkills(List<String> worlds) {

        Long startTime = System.currentTimeMillis();
        List<String> skills = TibiaUtil.getSkills();
        List<PlayerSkills> psList = new ArrayList<>();

        for (int x = 0; x < skills.size(); x++) {

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
                                    + worlds.get(i) + "&category=" + skills.get(x) + "&profession=" + n + "&currentpage=" + j;

                            Document htmlContent = Jsoup.connect(url).get();
                            List<String> elementsList = htmlContent.getElementsByTag("td").eachText();

                            for (k = START_CONTENT_SKILLS; k < elementsList.size() - TRASH_ELIMINATOR_SKILLS; k += INCREMENTOR_SKILLS) {

                                PlayerSkills ps0 = new PlayerSkillsDAO().returnLastRegisterDESC(elementsList.get(k + NAME_SKILLS));

                                PlayerSkills ps = new PlayerSkills();

                                boolean flagUpdate = false;

                                System.out.println(elementsList.get(k + NAME_SKILLS));
                                System.out.println(elementsList.get(k + 6));

                                /* Não é o primeiro registro */
                                if (ps0 != null) {

                                    /* Transfere todos os outros skills do registro anterior para o novo registro 
                                             * Isso serve para caso um char esteja em mais de 1 ranking score */
                                    ps = new PlayerSkills(
                                            ps0.getPlayerName(),
                                            ps0.getAxeFighting(),
                                            ps0.getCharmPoints(),
                                            ps0.getClubFighting(),
                                            ps0.getDistanceFighting(),
                                            ps0.getFishing(),
                                            ps0.getFistFighting(),
                                            ps0.getGoshnarTaint(),
                                            ps0.getLoyaltyPoints(),
                                            ps0.getMagicLevel(),
                                            ps0.getShielding(),
                                            ps0.getSwordFighting()
                                    );

                                } else {

                                    ps0 = new PlayerSkills();

                                    /* Coloca o nick do char dono */
                                    ps.setPlayerName(elementsList.get(k + NAME_SKILLS));
                                }

                                /* Regras de negócio para cada skill */
                                switch (skills.get(x).toLowerCase()) {

                                    case "2":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getAxeFighting()))) {
                                            ps.setAxeFighting(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;
                                        }

                                        break;

                                    case "3":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getCharmPoints()))) {
                                            ps.setCharmPoints(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;
                                        }

                                        break;

                                    case "4":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getClubFighting()))) {
                                            ps.setClubFighting(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    case "5":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getDistanceFighting()))) {
                                            ps.setDistanceFighting(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    case "7":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getFishing()))) {
                                            ps.setFishing(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    case "8":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getFistFighting()))) {
                                            ps.setFistFighting(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    case "9":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getGoshnarTaint()))) {
                                            ps.setGoshnarTaint(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    case "11":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getMagicLevel()))) {
                                            ps.setMagicLevel(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    case "12":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getShielding()))) {
                                            ps.setShielding(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    case "13":

                                        /* Se a skill se alterou */
                                        if (!elementsList.get(k + POINTS_SKILLS).equals(String.valueOf(ps0.getSwordFighting()))) {
                                            ps.setSwordFighting(Integer.valueOf(elementsList.get(k + POINTS_SKILLS)));
                                            flagUpdate = true;

                                        }

                                        break;

                                    default:
                                        break;
                                }

                                /*  */
                                boolean flagUpdate2 = false;

                                /* Regra para vincular P.S com Player */
                                if (flagUpdate == true) {

                                    Player player = new PlayerDAO().returnCharacterByName(ps.getPlayerName());

                                    /* Player existe no bd - vincula L.A ao player */
                                    if (player != null) {

                                        ps.setPlayer(player);
                                        ps.setLastUpdate(Calendar.getInstance());
                                        new AbstractDAO<>(PlayerSkills.class).insert(ps);
                                        flagUpdate2 = true;

                                        /* Player não existe ou não foi atualizado */
                                    } else {

                                        /* procura os former names do nick no bd */
                                        List<String> fns = new CheckCharacter().getFormerNames(ps.getPlayerName());

                                        /* Se não tiver former names, o char não existe */
                                        if (fns != null) {

                                            for (String nick : fns) {
                                                Player playerRank = new PlayerDAO().returnCharacterByName(nick);

                                                /* Char trocou de nick e não foi atualizado */
                                                if (playerRank != null) {

                                                    ps.setPlayer(playerRank);
                                                    ps.setLastUpdate(Calendar.getInstance());
                                                    new AbstractDAO<>(PlayerSkills.class).insert(ps);
                                                    flagUpdate2 = true;

                                                    /* Achou o nick no bd, pare o código*/
                                                    break;
                                                }
                                            }

                                        }

                                    }

                                    /* Player dono do rank não existe ainda no bd */
                                    if (flagUpdate2 == false) {
                                        ps.setLastUpdate(Calendar.getInstance());
                                        new AbstractDAO<>(PlayerSkills.class).insert(ps);
                                    }

                                    /* Adiciona o L.A capturado */
                                    psList.add(ps);

                                } // fim if flag

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

        return psList;

    }

    public List<LoyaltyPoints> getRankLoyalty(List<String> worlds) {

        Long startTime = System.currentTimeMillis();
        List<LoyaltyPoints> lpList = new ArrayList<>();
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
                                + worlds.get(i) + "&category=" + "10" + "&profession=" + n + "&currentpage=" + j;

                        Document htmlContent = Jsoup.connect(url).get();
                        List<String> elementsList = htmlContent.getElementsByTag("td").eachText();

                        for (k = START_CONTENT_LOYALTY; k < elementsList.size() - TRASH_ELIMINATOR_LOYALTY; k += INCREMENTOR_LOYALTY) {

                            System.out.println(elementsList.get(k + NAME_LOYALTY));
                            System.out.println(elementsList.get(k + LOYALTY));
                            /* Busca último registro */
                            LoyaltyPoints lp0 = new LoyaltyPointsDAO().returnLastRegisterDESC(elementsList.get(k + NAME_LOYALTY));

                            /* flag para verificar se precisa vincular L.A com Player */
                            boolean flagUpdate = false;
                            LoyaltyPoints lp = null;

                            String strValue = elementsList.get(k + LOYALTY).replace(",", "");
                            Integer loyaltyValue = Integer.valueOf(strValue);

                            if (lp0 != null) {

                                /* Esse if só faz ter um update por dia */
                                if (DateUtil.sameDate(Calendar.getInstance(), lp0.getLastUpdate()) != true
                                        && !Objects.equals(lp0.getLoyaltyPoints(), loyaltyValue)) {

                                    lp = new LoyaltyPoints(
                                            loyaltyValue,
                                            elementsList.get(k + NAME_LOYALTY),
                                            Calendar.getInstance());

                                    new AbstractDAO<>(LoyaltyPoints.class).insert(lp);
                                    flagUpdate = true;
                                }

                            } else {

                                lp = new LoyaltyPoints(
                                        loyaltyValue,
                                        elementsList.get(k + NAME_LOYALTY),
                                        Calendar.getInstance());

                                new AbstractDAO<>(LoyaltyPoints.class).insert(lp);
                                flagUpdate = true;
                            }

                            if (flagUpdate == true) {
                                /* Regra para vincular L.A com Player */
                                if (flagUpdate == true) {

                                    Player player = new PlayerDAO().returnCharacterByName(lp.getPlayerName());

                                    /* Player existe no bd - vincula L.A ao player */
                                    if (player != null) {
                                        lp.setPlayer(player);
                                        new AbstractDAO<>(LoyaltyPoints.class).update(lp);

                                        /* Player não existe ou não foi atualizado */
                                    } else {

                                        /* procura os former names do nick no bd */
                                        List<String> fns = new CheckCharacter().getFormerNames(lp.getPlayerName());

                                        /* Se não tiver former names, o char não existe */
                                        if (fns != null) {

                                            for (String nick : fns) {
                                                Player playerRank = new PlayerDAO().returnCharacterByName(nick);

                                                /* Char trocou de nick e não foi atualizado */
                                                if (playerRank != null) {
                                                    lp.setPlayer(playerRank);
                                                    new AbstractDAO<>(LoyaltyPoints.class).update(lp);

                                                    /* Achou o nick no bd, pare o código*/
                                                    break;
                                                }
                                            }

                                        }

                                    }

                                    /* Adiciona o L.A capturado */
                                    lpList.add(lp);

                                } // fim if flag
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

        return lpList;
    }

    public List<LevelAdvance> getRankExperience(List<String> worlds) {

        Long startTime = System.currentTimeMillis();
        List<LevelAdvance> laList = new ArrayList<>();

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
                                + worlds.get(i) + "&category=" + "6" + "&profession=" + n + "&currentpage=" + j;

                        Document htmlContent = Jsoup.connect(url).get();
                        List<String> elementsList = htmlContent.getElementsByTag("td").eachText();

                        for (k = START_CONTENT_EXP; k < elementsList.size() - TRASH_ELIMINATOR_EXP; k += INCREMENTOR_EXP) {

                            /* Busca último registro */
                            LevelAdvance la0 = new LevelAdvanceDAO().returnLastRegisterDESC(elementsList.get(k + NAME));

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

                                Player player = new PlayerDAO().returnCharacterByName(la.getPlayerName());

                                /* Player existe no bd - vincula L.A ao player */
                                if (player != null) {
                                    la.setPlayer(player);
                                    new AbstractDAO<>(LevelAdvance.class).update(la);

                                    /* Player não existe ou não foi atualizado */
                                } else {

                                    /* procura os former names do nick no bd */
                                    List<String> fns = new CheckCharacter().getFormerNames(la.getPlayerName());

                                    /* Se não tiver former names, o char não existe */
                                    if (fns != null) {

                                        for (String nick : fns) {
                                            Player playerRank = new PlayerDAO().returnCharacterByName(nick);

                                            /* Char trocou de nick e não foi atualizado */
                                            if (playerRank != null) {
                                                la.setPlayer(playerRank);
                                                new AbstractDAO<>(LevelAdvance.class).update(la);

                                                /* Achou o nick no bd, pare o código*/
                                                break;
                                            }
                                        }

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
