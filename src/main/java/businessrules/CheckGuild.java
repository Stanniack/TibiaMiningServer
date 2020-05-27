package businessrules;

import DAO.AbstractDAO;
import DAO.GuildDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.GuildInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CheckGuild {

    private final int START_CONTENT = 3;

    /* Deve ser rodado uma vez por dia */
    public void getGuildsInfo(List<String> worlds) {

        Long startTime = System.currentTimeMillis();

        try {

            for (String world : worlds) {

                String url = "https://www.tibia.com/community/?subtopic=guilds&world=" + world;

                Document document = Jsoup.connect(url).get();
                Elements chosenElements = document.getElementsByClass("TableContentContainer");
                List<String> elementsList = chosenElements.get(0).getElementsByTag("b").eachText();

                List<String> guilds = new GuildDAO().listAllGuildNames(world);
                List<String> thisWorldGuilds = new ArrayList<>();

                for (int i = START_CONTENT; i < elementsList.size(); i++) {

                    /* Se não tem é porque a guilda é nova */
                    if (!guilds.contains(elementsList.get(i))) {

                        /* Persiste*/
                        new AbstractDAO<>(GuildInfo.class).insert(
                                new GuildInfo(
                                        elementsList.get(i),
                                        world,
                                        Calendar.getInstance()));
                    } else {

                        GuildInfo newGuild = new GuildDAO().returnGuildByWorld(world, elementsList.get(i));

                        /* A guilda é nova mas já existiu no passado */
                        if (newGuild != null && newGuild.getDateEnd() != null) {

                            new AbstractDAO<>(GuildInfo.class).insert(
                                    new GuildInfo(
                                            elementsList.get(i),
                                            world,
                                            Calendar.getInstance()));
                        }
                    }

                    /* Add guilda para regras posteriores */
                    thisWorldGuilds.add(elementsList.get(i));

                } // for página

                /* Verifica se a guilda foi deletada */
                for (String guild : guilds) {

                    /* Se não contém guilda é pq foi deletada */
                    if (!thisWorldGuilds.contains(guild)) {

                        /* Verifica se a guild deletada já foi capturada anteriormente */
                        GuildInfo deletedGuild = new GuildDAO().returnGuildByWorld(world, guild);

                        /* Se a data de encerramento não existir, atualiza */
                        if (deletedGuild.getDateEnd() == null) {

                            deletedGuild.setDateEnd(Calendar.getInstance());
                            new AbstractDAO<>(GuildInfo.class).update(deletedGuild);

                        }

                    }
                }

            } // for world

        } catch (IOException ex) {
            System.out.println("Error - CheckGuild - getGuildsInfo: " + ex);
        }

        System.out.println("Método terminado com " + ((System.currentTimeMillis() - startTime) / 1000) + " secs");

    }

    public void getInfoGuilds(List<String> worlds, List<String> elementsList) {

        List<String> guilds = new GuildDAO().listAllGuildNames("Zuna");
        List<String> thisWorldGuilds = new ArrayList<>();

        for (int i = 0; i < elementsList.size(); i++) {

            /* Se não tem é porque a guilda é nova */
            if (!guilds.contains(elementsList.get(i))) {

                /* Persiste*/
                new AbstractDAO<>(GuildInfo.class).insert(
                        new GuildInfo(
                                elementsList.get(i),
                                "Zuna",
                                Calendar.getInstance()));

            } else {

                GuildInfo newGuild = new GuildDAO().returnGuildByWorld("Zuna", elementsList.get(i));

                /* A guilda é nova mas já existiu no passado */
                if (newGuild != null && newGuild.getDateEnd() != null) {

                    new AbstractDAO<>(GuildInfo.class).insert(
                            new GuildInfo(
                                    elementsList.get(i),
                                    "Zuna",
                                    Calendar.getInstance()));
                }
            }

            /* Add guilda para regras posteriores */
            thisWorldGuilds.add(elementsList.get(i));

        } // for página

        /* Verifica se a guilda foi deletada */
        for (String guild : guilds) {

            /* Se não contém guilda é pq foi deletada */
            if (!thisWorldGuilds.contains(guild)) {

                /* Verifica se a guild deletada já foi capturada anteriormente */
                GuildInfo deletedGuild = new GuildDAO().returnGuildByWorld("Zuna", guild);

                /* Se a data de encerramente não existir, atualiza */
                if (deletedGuild.getDateEnd() == null) {

                    deletedGuild.setDateEnd(Calendar.getInstance());
                    new AbstractDAO<>(GuildInfo.class).update(deletedGuild);

                }

            }
        }

    }

    /* Captura todos os personagens membros de uma guilda de acordo com as listas de guildas existentes 
     * ESTE MÉTODO NÃO É DIÁRIO */
    public void getPlayersGuilds() {

        try {
            String url = "https://www.tibia.com/community/?subtopic=guilds&page=view&GuildName=Ahjar Dilenox";
            Document document = Jsoup.connect(url).get();
            Elements chosenElements = document.getElementsByClass("TableContentContainer");

            /*Se for maior que 0 é pq tem conteúdo a ser analisado */
            if (chosenElements.size() > 0) {
                List<String> elementsList = chosenElements.get(0).getElementsByTag("a").eachText();
                elementsList.forEach(System.out::println);
            }

        } catch (IOException ex) {
            System.out.println("Error - CheckGuild - getPlayerGuilds: " + ex);
        }

    }
}
