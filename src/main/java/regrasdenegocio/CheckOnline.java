package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DateType;
import model.OnlineTime;
import model.Player;
import model.PlayersOnline;
import model.TotalPlayers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.DateUtil;
import utils.TibiaUtil;

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
        Set<String> setDay = new HashSet<>();
        Set<String> setMonth = new HashSet<>();
        Set<String> setYear = new HashSet<>();

        /* Colocar as datas atuais */
        int day = 17;
        int month = 5;
        int year = 20;
        int hour = 23;
        int maxPlayersOnlineDay = 0;

        List<Object[]> onlinePlayersList1 = new ArrayList<>();
        List<String> worlds = TibiaUtil.getWorlds();

        boolean flag1 = true;
        boolean flag2 = false;

        /* Looping infinito para verificar personagens */
        while (true) {

            int playersOnline = 0;
            Long onlineTimeStart = System.currentTimeMillis();
            List<String> onlinePlayersList2 = new ArrayList<>();

            for (int n = 0; n < worlds.size(); n++) {
                try {

                    String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + worlds.get(n);

                    Document htmlContents = Jsoup.connect(url).get();
                    Elements chosenElements = htmlContents.getElementsByClass("InnerTableContainer");
                    List<String> elementsList = chosenElements.get(PLAYERS_CONTENT).getElementsByTag("td").eachText();

                    /* Players */
                    for (int i = 0; i < elementsList.size(); i += PLAYER_INCREMENTOR) {

                        /* Adiciona players */
                        if (new PlayerDAO().returnCharacterByName(elementsList.get(i)) == null) {
                            new CheckCharacter().discoverCharacter(elementsList.get(i));
                        }

                        playersOnline++;

                        setDay.add(elementsList.get(i));
                        setMonth.add(elementsList.get(i));
                        setYear.add(elementsList.get(i));

                        /*  Regras de negócio para verificar tempo online de cada player */
                        if (flag1 == true) {

                            List<String> list = new ArrayList<>();

                            for (Object[] player : onlinePlayersList1) {
                                list.add(String.valueOf(player[0]));
                            }

                            if (!list.contains(elementsList.get(i))) {
                                onlinePlayersList1.add(new Object[]{elementsList.get(i), (long) 0});
                            }

                            flag1 = false;

                        } else {
                            onlinePlayersList2.add(elementsList.get(i));
                            flag1 = true;
                        }

                    }

                } catch (IOException ex) {
                    /* Se a net cair, volta no servidor */
                    n--;
                    System.out.println("Erro de I/O em CheckOnline.");
                }

            } // fim dos mundos

            /* Verificar tempo online dos players por dia */
            for (Object[] player : onlinePlayersList1) {

                if (onlinePlayersList2.contains(String.valueOf(player[0]))) {
                    /* Tempo em segundos */
                    player[1] = (long) player[1] + ((System.currentTimeMillis() - onlineTimeStart) / 1000);
                    //System.out.println(String.valueOf(player[0]) + " - " + (long) player[1] / 1000 + " secs on");
                }
            }

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

                /* Reseta a variável máx dia */
                maxPlayersOnlineDay = 0;

                /* Persiste dados de tempo online por player */
                for (Object[] player : onlinePlayersList1) {
                    Player p = new PlayerDAO().returnCharacterByName(String.valueOf(player[0]));

                    if (p != null) {
                        new AbstractDAO<>(OnlineTime.class)
                                .insert(new OnlineTime(p, (long) player[1], Calendar.getInstance()));
                    }
                }

                /* Reseta a onlinePlayersList1 */
                onlinePlayersList1.clear();

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

    }

}
