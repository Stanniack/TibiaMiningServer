package businessrules;

import DAO.AbstractDAO;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import model.Boss;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.TibiaUtil;

public class CheckKillStatistics {

    private static final int CONTENT_START = 12;
    private static final int INCREMENTOR = 5;
    private static final int TRASH_ELIMINATOR = 5;

    /* Atributos */
    private static final int CREATURE = 0;
    private static final int KILLED_PLAYERS = 1;
    private static final int KILLED_BY_PLAYERS = 2;

    /* Método não tem lastUpdate, por isso deve ser rodado apenas 1x por dia */
    public void getBossesStatistics(List<String> worlds) {
        List<String> bosses = new AbstractDAO<>(Boss.class).listAll("bossName");
        List<String> taskBosses = TibiaUtil.getIgnoredBosses();

        try {

            for (String world : worlds) {

                String url = "https://www.tibia.com/community/?subtopic=killstatistics&world=" + world;
                Document htmlContent = Jsoup.connect(url).get();
                List<String> elementsList = htmlContent.getElementsByTag("td").eachText();
                int flag = 0;

                for (int i = CONTENT_START; i < elementsList.size() - TRASH_ELIMINATOR; i += INCREMENTOR) {

                    if (bosses.contains(elementsList.get(i + CREATURE))) {

                        /* Se o boss matou ou foi morto em no último dia e não está na lista de boss que não é preciso checar */
                        if (!taskBosses.contains(elementsList.get(i + CREATURE))
                                && (Integer.valueOf(elementsList.get(i + KILLED_PLAYERS)) != 0
                                || Integer.valueOf(elementsList.get(i + KILLED_BY_PLAYERS)) != 0)) {

                            /* Persiste boss */
                            new AbstractDAO<>(Boss.class).insert(
                                    new Boss(
                                            elementsList.get(i + CREATURE),
                                            Integer.valueOf(elementsList.get(i + KILLED_PLAYERS)),
                                            Integer.valueOf(elementsList.get(i + KILLED_BY_PLAYERS)),
                                            world,
                                            Calendar.getInstance()
                                    ));

                        }

                    }

                } // fim página

            } // fim mundo

        } catch (IOException ex) {
            System.out.println("Error CheckKillStatistics: " + ex);
        }

    }

}
