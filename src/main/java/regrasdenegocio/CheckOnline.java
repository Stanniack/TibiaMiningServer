package regrasdenegocio;

import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CheckOnline {


    /* Elemento da lista que contém os players online */
    private final int PLAYERS_CONTENT = 2;
    /* Início do conteúdo que procuro */
    private final int START_CONTENT = 2;
    /* Eliminação de elemtentos na lista indesejáveis */
    private final int PLAYER_INCREMENTOR = 3;

    /* Atributos */
    private final int NAME = 0;
    private final int LEVEL = 1;
    private final int PROFESSION = 2;

    public void getOnlinePlayers() {
        String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + "Antica";

        try {

            Document contentElements = Jsoup.connect(url).get();
            Elements chosenElements = contentElements.getElementsByClass("InnerTableContainer");

            List<String> elementsList = chosenElements.get(PLAYERS_CONTENT).getElementsByTag("td").eachText();
            int z = 0;

            for (int i = 0; i < elementsList.size(); i += PLAYER_INCREMENTOR) {
                System.out.println(elementsList.get(i));
                z++;
            }

            System.out.println(z);

        } catch (IOException ex) {
        }
    }
}
