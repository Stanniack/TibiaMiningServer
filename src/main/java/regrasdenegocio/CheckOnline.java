package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.PlayersOnline;
import model.TotalPlayers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.DateUtil;
import utils.WorldsTibiaUtil;

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

        /* Pega a quantidade total de players por período de tempo: dia, semana, mês, etc */
        Set<String> playersSetDay = new LinkedHashSet<>();
        Set<String> playersSetMonth = new LinkedHashSet<>();
        Set<String> playersSetYear = new LinkedHashSet<>();
        int hour = 0;
        int maxPlayersOnline = 0;

        try {
            /* Looping infinito para verificar personagens */
            while (true) {

                int playersOnline = 0;

                for (String world : WorldsTibiaUtil.getWorldsTibia()) {

                    String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + world;
                    Document contentElements = Jsoup.connect(url).get();
                    Elements chosenElements = contentElements.getElementsByClass("InnerTableContainer");
                    List<String> elementsList = chosenElements.get(PLAYERS_CONTENT).getElementsByTag("td").eachText();

                    /* Players */
                    for (int i = 0; i < elementsList.size(); i += PLAYER_INCREMENTOR) {
                        playersOnline++;

                        /* Adiciona players */
                        if (new PlayerDAO().returnCharacterByName(elementsList.get(i)) == null) {
                            //System.out.println(elementsList.get(i));

                        }

                    }

                } // fim dor mundos

                /* Verificar quantidade de players online de hora em hora */
                if (DateUtil.getHour() != hour || playersOnline > maxPlayersOnline) {
                    new AbstractDAO<>(PlayersOnline.class)
                            .insert(new PlayersOnline(Calendar.getInstance(), playersOnline));
                    
                    hour = DateUtil.getHour();
                    maxPlayersOnline = playersOnline;
                    
                }
                
                /* Verificar quantidade total de players */
                
                
            }

        } catch (IOException ex) {
        }
    }

    /* Verifica a qrd de players por tempo */
    public void verifyPlayers() {

    }
}
