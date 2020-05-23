package regrasdenegocio;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.TibiaUtil;

public class CheckKillStatistics {

    private final int CONTENT_START = 12;
    private final int INCREMENTOR = 5;
    private final int TRASH_ELIMINATOR = 5;

    /* Atributos */
    private final int CREATURE = 0;
    private final int KILLED_PLAYERS = 1;
    private final int KILLED_BY_PLAYERS = 2;

    public void getKillStatistics() {

        Document htmlContent;
        try {

            for (String world : TibiaUtil.getAllWorlds()) {

                String url = "https://www.tibia.com/community/?subtopic=killstatistics&world=" + world;
                htmlContent = Jsoup.connect(url).get();
                List<String> elementsList = htmlContent.getElementsByTag("td").eachText();
                int flag = 0;

                for (int i = CONTENT_START; i < elementsList.size() - TRASH_ELIMINATOR; i += INCREMENTOR) {

                    if (elementsList.get(i + CREATURE).equals("Old Widow")) {
                        System.out.println(elementsList.get(i + CREATURE) + " " + elementsList.get(i + KILLED_BY_PLAYERS));
                    }

                } // fim página

            } // fim mundo

        } catch (IOException ex) {
            System.out.println("Error CheckKillStatistics: " + ex);
        }

    }
}
