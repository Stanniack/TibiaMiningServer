package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.DateType;
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
        Set<String> setDay = new LinkedHashSet<>();
        Set<String> setMonth = new LinkedHashSet<>();
        Set<String> setYear = new LinkedHashSet<>();

        int day = 0;
        int month = 0;
        int year = 0;
        int hour = 0;
        int maxPlayersOnlineDay = 0;

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
                        setDay.add(elementsList.get(i));
                        setMonth.add(elementsList.get(i));
                        setYear.add(elementsList.get(i));

                        /* Adiciona players */
                        if (new PlayerDAO().returnCharacterByName(elementsList.get(i)) == null) {
                            new CheckCharacter().discoverCharacter(elementsList.get(i));
                        }

                    }

                } // fim dor mundos

                /* Verificar quantidade de players online de hora em hora */
                if (DateUtil.getHour() != hour || playersOnline > maxPlayersOnlineDay) {
                    new AbstractDAO<>(PlayersOnline.class)
                            .insert(new PlayersOnline(Calendar.getInstance(), DateUtil.getHourAndMinute(), playersOnline));

                    hour = DateUtil.getHour();
                    maxPlayersOnlineDay = playersOnline;

                }

                /* Total de player por dia */
                if (DateUtil.getDay() != day) {
                    new AbstractDAO<>(TotalPlayers.class)
                            .insert(new TotalPlayers(Calendar.getInstance(), DateType.DAY, setDay.size()));

                    maxPlayersOnlineDay = 0;
                    day = DateUtil.getDay();
                    setDay.clear();

                }

                /* Total de player por mês */
                if (DateUtil.getMonth() != month) {
                    new AbstractDAO<>(TotalPlayers.class)
                            .insert(new TotalPlayers(Calendar.getInstance(), DateType.MONTH, setMonth.size()));

                    month = DateUtil.getMonth();
                    setMonth.clear();

                }

                /* Total de player por semestre - coleta no primeiro dia do mês 7 */
                if (DateUtil.getMonth() == 7) {
                    new AbstractDAO<>(TotalPlayers.class)
                            .insert(new TotalPlayers(Calendar.getInstance(), DateType.SEMESTER, setYear.size()));

                }

                /* Total de player por ano */
                if (DateUtil.getYear() != year) {
                    new AbstractDAO<>(TotalPlayers.class)
                            .insert(new TotalPlayers(Calendar.getInstance(), DateType.YEAR, setYear.size()));

                    year = DateUtil.getYear();
                    setYear.clear();

                }
            }

        } catch (IOException ex) {
        }
    }

}
