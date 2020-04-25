package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.MockWorldsTibia;
import model.CharacterRank;
import model.CharacterSkills;
import model.LevelAdvance;
import model.Personagem;
import org.hibernate.PropertyValueException;
import utils.MockSkillsTibia;

public class CheckRank {

    // Conteúdo na lista começa no índice 13 para skills
    private static final int CONTENT_START_SKILLS = 13;
    // O último elemento da lista é lixo
    private static final int TRASH_ELIMINATOR_SKILLS = 1;
    // O incrementador é 5 devido aos atributos de cada char
    private static final int INCREMENTOR_SKILLS = 4;

    private static final int PROFESSION = 4;
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = 12;
    private static final int NAME = 1;
    private static final int VOCATION = 2;
    private static final int LEVEL = 3;
    private static final int POINTS = 3;
    private static final int EXPERIENCE = 4;

    /* Estem método é adaptado para rodar em qualquer circunstância, mas é recomendado que se use depois do discover ou updateCharacter*/
    public void checkGlobalRank() {

        Long startTime = System.currentTimeMillis();
        List<String> worlds = MockWorldsTibia.getWorldsTibia();
        List<String> skills = MockSkillsTibia.getSkillsTibia();

        for (int x = 0; x < MockSkillsTibia.getSkillsTibia().size(); x++) {

            Long serverStartTime = System.currentTimeMillis();

            for (int i = 0; i < MockWorldsTibia.getWorldsTibia().size(); i++) {

                Long worldStartTime = System.currentTimeMillis();

                for (int n = 1; n <= PROFESSION; n++) {

                    Long professionStartTime = System.currentTimeMillis();

                    for (int j = FIRST_PAGE; j <= LAST_PAGE; j++) {

                        try {

                            String url = "https://www.tibia.com/community/?subtopic=highscores&world="
                                    + worlds.get(i) + "&list=" + skills.get(x) + "&profession=" + n + "&currentpage=" + j;

                            Document htmlContent = Jsoup.connect(url).get();
                            List<String> elementsList = htmlContent.getElementsByTag("td").eachText();
                            Personagem p;

                            for (int k = CONTENT_START_SKILLS; k < elementsList.size() - TRASH_ELIMINATOR_SKILLS; k += INCREMENTOR_SKILLS) {

                                String lastNick = new CheckCharacter().getNick(elementsList.get(k + NAME));

                                /* !Char deletado ou não existe) */
                                if (lastNick != null) {

                                    Long isRegistered = new AbstractDAO<>(Personagem.class)
                                            .countRegistersByName(elementsList.get(k + NAME));

                                    /* Nick trocado e char não existe no BD */
                                    if (!lastNick.equals(elementsList.get(k + NAME)) && isRegistered == 0) {

                                        new CheckCharacter().discoverCharacter(lastNick);
                                        p = new PersonagemDAO().returnCharacterByName(lastNick);
                                        System.out.println("Nick trocado e char não existe no BD");

                                        /* Nick trocado e char exite no BD */
                                    } else if (!lastNick.equals(elementsList.get(k + NAME)) && isRegistered != 0) {

                                        new CheckCharacter().updateCharacter(lastNick);
                                        p = new PersonagemDAO().returnCharacterByName(lastNick);
                                        System.out.println("Nick trocado e char exite no BD ");

                                        /* Nick não foi trocado e char não existe no BD */
                                    } else if (lastNick.equals(elementsList.get(k + NAME)) && isRegistered == 0) {

                                        new CheckCharacter().discoverCharacter(elementsList.get(k + NAME));
                                        p = new PersonagemDAO().returnCharacterByName(lastNick);
                                        System.out.println("Nick não foi trocado e char não existe no BD");

                                        /* Nick não foi trocado e char existe no BD */
                                    } else {
                                        p = new PersonagemDAO().returnCharacterByName(elementsList.get(k + NAME));
                                        System.out.println("Nick não foi trocado e char existe no BD");

                                    }

                                    Long register = new AbstractDAO<>(CharacterSkills.class)
                                            .countRegistersById(p.getIdCharacter());
                                    CharacterSkills cs0;
                                    CharacterSkills cs = new CharacterSkills();


                                    /* Se tiver registro */
                                    if (register > 0) {

                                        cs0 = new AbstractDAO<>(CharacterSkills.class)
                                                .searchLastRegisterByIdDESC(p.getIdCharacter(), "registerDate");

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
                                        cs0.setPersonagem(p);
                                        new AbstractDAO<>(CharacterSkills.class).insert(cs0);
                                    }

                                    boolean flagUpdate = false;

                                    /* Regras de negócio para cada skill */
                                    switch (skills.get(x).toLowerCase()) {

                                        case "axe fighting":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getAxeFighting())) {
                                                cs.setAxeFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;
                                            }

                                            break;

                                        case "club fighting":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getClubFighting())) {
                                                cs.setClubFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "distance fighting":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getDistanceFighting())) {
                                                cs.setDistanceFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

//                                            case "experience points":
//
//                                                /* Tira as vírgulas da exp */
//                                                String valorConvertido = elementsList.get(k + POINTS).replace(",", "");
//                                                Long valorCvtLong = Long.valueOf(valorConvertido);
//
//                                                LevelAdvance la = new LevelAdvance();
//                                                la.setExpDay(Double.NaN);
//                                                la.setDayAdvance(Calendar.getInstance());
//                                                
//
//                                                break;
                                        case "fishing":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getFishing())) {
                                                cs.setFishing(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "fist fighting":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getFistFighting())) {
                                                cs.setFistFighting(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

//                                            case "loyalty points":
//
//                                                /* Se a skill se alterou */
//                                                if (!elementsList.get(k + POINTS).equals(cs0.getLoyaltyPoints())) {
//                                                    cs.setLoyaltyPoints(Integer.valueOf(elementsList.get(k + POINTS)));
//                                                    flagUpdate = true;
//
//                                                }
//
//                                                break;
                                        case "magic level":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getMagicLevel())) {
                                                cs.setMagicLevel(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "shielding":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getShielding())) {
                                                cs.setShielding(Integer.valueOf(elementsList.get(k + POINTS)));
                                                flagUpdate = true;

                                            }

                                            break;

                                        case "sword fighting":

                                            /* Se a skill se alterou */
                                            if (!elementsList.get(k + POINTS).equals(cs0.getSwordFighting())) {
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
                                        cs.setPersonagem(p);
                                        new AbstractDAO<>(CharacterSkills.class).update(cs);
                                    }

                                }

                            } // for personagens da página

                        } catch (IOException | NumberFormatException | NoResultException e) {
                            System.out.println("Erro para: " + e);
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

}
