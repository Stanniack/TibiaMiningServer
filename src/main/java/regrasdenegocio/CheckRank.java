package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.util.ArrayList;
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
import utils.MockSkillsTibia;

public class CheckRank {

    // Conteúdo na lista começa no índice 13
    private static final int CONTENT_START = 14;
    // O último elemento da lista é lixo
    private static final int TRASH_ELIMINATOR = 1;
    // O incrementador é 5 devido aos atributos de cada char
    private static final int INCREMENTOR = 5;

    private static final int PROFESSION = 4;
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = 12;
    private static final int NAME = 1;
    private static final int VOCATION = 2;
    private static final int LEVEL = 3;
    private static final int POINTS = 4;

    public void checkGlobalRank() {

        Long tempoInicial = System.currentTimeMillis();
        List<String> listaDeElementos = new ArrayList<>();
        List<CharacterRank> listaDeChars = new ArrayList<>();
        List<String> mundos = MockWorldsTibia.getWorldsTibia();

        for (int i = 0; i < 1; i++) {

            Long tempoInicialPorMundo = System.currentTimeMillis();

            for (int n = 1; n <= PROFESSION; n++) {
                Long tempoInicialPorProfissao = System.currentTimeMillis();

                for (int j = FIRST_PAGE; j <= LAST_PAGE; j++) {
                    String url = "https://www.tibia.com/community/?subtopic=highscores&world="
                            + mundos.get(i) + "&list=experience&profession=" + n + "&currentpage=" + j;

                    try {

                        Document arquivoHtml = Jsoup.connect(url).get();
                        listaDeElementos = arquivoHtml.getElementsByTag("td").eachText();

                        for (int k = CONTENT_START; k < listaDeElementos.size() - INCREMENTOR; k += 5) {

                            // Tiras as vírgulas da exp
                            String valorConvertido = listaDeElementos.get(k + POINTS).replace(",", "");
                            Long valorCvtLong = Long.valueOf(valorConvertido);

                            listaDeChars.add(new CharacterRank(
                                    listaDeElementos.get(k),
                                    listaDeElementos.get(k + NAME),
                                    listaDeElementos.get(k + VOCATION),
                                    listaDeElementos.get(k + LEVEL),
                                    valorCvtLong,
                                    mundos.get(i))
                            );
                        }

                    } catch (IOException e) {
                        System.out.println("Erro na execução." + e);

                    } catch (NumberFormatException e) {
                        System.out.println("Erro de conversão: " + e);
                    }

                } // for das páginas

                Long tempoFinalPorProfissao = System.currentTimeMillis();
                System.out.println("A profissão " + n + " foi minerada com "
                        + ((tempoFinalPorProfissao - tempoInicialPorProfissao) / 1000) + " segundos.");

            } // for das profissões

            Long tempoFinalPorMundo = System.currentTimeMillis();
            System.out.println("O servidor de " + mundos.get(i) + " gastou " + ((tempoFinalPorMundo - tempoInicialPorMundo) / 1000)
                    + " segundos para ser minerado");

        } // for dos mundos

        Long tempoFinal = System.currentTimeMillis();
        System.out.println("O tempo total para minerar todos os servidores foi de " + ((tempoFinal - tempoInicial) / 1000)
                + " segundos");

        /* Exibir conteúdo */
        this.exibeConteudo(listaDeChars);

    }// fim do metodo

    /* Esse código rodará ...*/
    public void discoverIsRanked() {

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

                        String url = "https://www.tibia.com/community/?subtopic=highscores&world="
                                + worlds.get(i) + "&list=" + skills.get(x) + "&profession=" + n + "&currentpage=" + j;

                        try {

                            Document htmlContent = Jsoup.connect(url).get();
                            List<String> elementsList = htmlContent.getElementsByTag("td").eachText();
                            Personagem p;

                            for (int k = CONTENT_START; k < elementsList.size() - INCREMENTOR; k += 5) {

                                String lastNick = new CheckCharacter().getNick(elementsList.get(k + NAME));

                                /* !Char deletado ou não existe) */
                                if (lastNick != null) {

                                    Integer idCharacter = new PersonagemDAO().returnID(elementsList.get(k + NAME));

                                    /* Nick trocado e chãr não existe no BD */
                                    if (!lastNick.equals(elementsList.get(k + NAME)) && idCharacter == null) {

                                        new CheckCharacter().discoverCharacter(lastNick);
                                        p = new PersonagemDAO().returnCharacterByName(lastNick);
                                        System.out.println("Nick trocado e chãr não existe no BD");

                                        /* Nick trocado e char exite no BD */
                                    } else if (!lastNick.equals(elementsList.get(k + NAME)) && idCharacter != null) {

                                        new CheckCharacter().updateCharacter(lastNick);
                                        p = new PersonagemDAO().returnCharacterByName(lastNick);
                                        System.out.println("Nick trocado e char exite no BD ");

                                        /* Nick não foi trocado e char não existe no BD */
                                    } else if (lastNick.equals(elementsList.get(k + NAME)) && idCharacter == null) {

                                        new CheckCharacter().discoverCharacter(elementsList.get(k + NAME));
                                        p = new PersonagemDAO().returnCharacterByName(lastNick);
                                        System.out.println("Nick não foi trocado e char não existe no BD");

                                        /* Nick não foi trocado e char existe no BD */
                                    } else {
                                        p = new PersonagemDAO().returnCharacterByName(elementsList.get(k + NAME));
                                        System.out.println("Nick não foi trocado e char existe no BD");

                                    }

                                    try {

                                        /* Regras de negócio para cada skill */
                                        switch (skills.get(k).toLowerCase()) {

                                            case "axe fighting":

                                                Long register = new AbstractDAO<>(CharacterSkills.class)
                                                        .countRegistersById(p.getIdCharacter());

                                                /* Se tiver registros*/
                                                if (register > 0) {

                                                    CharacterSkills cs0 = new AbstractDAO<>(CharacterSkills.class)
                                                            .searchLastRegisterByIdDESC(p.getIdCharacter(), "registerDate");
                                                }

                                                CharacterSkills cs = new CharacterSkills();
                                                cs.setPersonagem(p);
                                                cs.setAxeFighting(Integer.valueOf(elementsList.get(k + POINTS)));

                                                break;

                                            case "club fighting":

                                                break;

                                            case "distance fighting":

                                                break;

                                            case "experience points":

                                                /* Tira as vírgulas da exp */
                                                String valorConvertido = elementsList.get(k + POINTS).replace(",", "");
                                                Long valorCvtLong = Long.valueOf(valorConvertido);

                                                LevelAdvance la = new LevelAdvance();
                                                la.setExpDay(Double.NaN);

                                                break;

                                            case "fishing":

                                                break;

                                            case "fist fighting":

                                                break;

                                            case "loyalty points":

                                                break;

                                            case "magic level":

                                                break;

                                            case "shielding":

                                                break;

                                            case "sword fighting":

                                                break;

                                            default:
                                                break;
                                        }

                                    } catch (NoResultException e) {
                                        System.out.println("Nenhum personagem encontrado para "
                                                + elementsList.get(k + NAME) + " - " + e);
                                    }

                                }

                            } // for personagens da página

                        } catch (IOException e) {
                            System.out.println("Erro na execução." + e);

                        } catch (NumberFormatException e) {
                            System.out.println("Erro de conversão: " + e);
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

    /* Eliminar este método futuramente */
    private void exibeConteudo(List<CharacterRank> lista) {

        System.out.println("___________________________________________________");
        Collections.sort(lista);

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " " + lista.get(i).getName() + " " + lista.get(i).getLevel() + " "
                    + lista.get(i).getWorld());

        }
    }

}
