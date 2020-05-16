package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import model.AchievementPoints;
import model.Death;
import model.FormerName;
import model.FormerWorld;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Player;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CheckCharacter {

    /* afterTitle = o conteúdo após o título do elemento */
    private final int AFTER_TITLE = 1;
    /* Tamanho da lista quando não é o conteúdo que procuro */
    private final int DOES_NOT_EXISTS = 6;
    /* Eliminar conteúdo lixo da lista de elementos */
    private final int TRASH_ELIMINATOR = 2;
    /* Eliminar retorno de personagens com caracteres especiais */
    private final int CHAR_ELIMINATOR = 3;

    public Player discoverCharacter(String name) {
        Player player = null;

        try {

            List<String> elementsList;

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            /* Conecta com o domínio */
            Document document = Jsoup.connect(url).get();

            /* Coloca os itens numa lista */
            Elements chosenElements = document.getElementsByClass("Content");
            elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

            /* Se não existe o char ou é char com caractere especial retorna null */
            if (elementsList.get(0).toLowerCase().equals("could not find character") || elementsList.size() == 3) {
                return null;

            } else {
                /* Se o char existir, inicia as chamadas do banco */
                player = new Player();
                Player p = new Player();

                for (int i = 1; i < elementsList.size() - TRASH_ELIMINATOR; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {

                        case "name:":

                            /* Caso o char tenha sido deletado pelo player, será preciso usar o split*/
                            String[] splitName = elementsList.get(i + AFTER_TITLE).split(",");

                            player.setPlayerName(splitName[0]);
                            /* Primeira persistência para salvar as tabelas fracas em relacionamento bilateral*/
                            new AbstractDAO<>(Player.class).insert(player);

                            /* Retorna id para outras persistências */
                            int idCharacter = new PlayerDAO().returnID(splitName[0]);
                            p.setIdCharacter(idCharacter);

                            break;

                        case "former names:":

                            /* Pega todos os former names */
                            String[] splitNames = elementsList.get(i + AFTER_TITLE).trim().split(",");

                            for (String rname : splitNames) {
                                /* Regra de persistência bilateral*/
                                FormerName fn = new FormerName(rname, Calendar.getInstance(), Calendar.getInstance());
                                fn.setPlayer(p);

                                new AbstractDAO<>(FormerName.class).insert(fn);

                            }

                            break;

                        case "title:":

                            player.setTitle(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "sex:":

                            player.setSex(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "vocation:":

                            player.setVocation(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "level:":

                            LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(LevelAdvance.class).insert(la);

                            break;

                        case "achievement points:":

                            AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(AchievementPoints.class).insert(ap);

                            break;

                        case "world:":

                            player.setWorld(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "former world:":

                            FormerWorld fw = new FormerWorld(p, elementsList.get(i + AFTER_TITLE), Calendar.getInstance(), Calendar.getInstance());
                            new AbstractDAO<>(FormerWorld.class).insert(fw);

                            break;

                        case "residence:":

                            player.setResidence(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "house:":

                            String[] nameHouse = elementsList.get(i + AFTER_TITLE).split(" is ");
                            House h = new House(p, nameHouse[0], Calendar.getInstance());
                            new AbstractDAO<>(House.class).insert(h);

                            break;

                        case "guild membership:":

                            String[] guildInfo = elementsList.get(i + AFTER_TITLE).split("of the");
                            Guild g = new Guild(p, guildInfo[1], guildInfo[0], Calendar.getInstance());
                            new AbstractDAO<>(Guild.class).insert(g);

                            break;

                        case "last login:":

                            player.setLastLogin(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "account status:":

                            player.setAccountStatus(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "character deaths":

                            int j = 1;
                            while (!elementsList.get(i + j).toLowerCase().equals("search character")
                                    && !elementsList.get(i + j).toLowerCase().equals("account information")) {

                                /* Regras de negócio */
                                Death d = new Death(p, elementsList.get(i + j + 1), elementsList.get(i + j));
                                new AbstractDAO<>(Death.class).insert(d);

                                j = j + 2;
                            }

                            i += j;

                            break;

                        case "account information":

                            player.setTitleAccountInformation(elementsList.get(i + 2));
                            player.setDateCreate(elementsList.get(i + 4));

                            break;

                        default:
                            break;
                    }

                } // fim FOR

                /* Persiste Player */
                player.setRegisterDate(Calendar.getInstance());
                player.setLastUpdate(Calendar.getInstance());
                new AbstractDAO<>(Player.class).update(player);

            } // fim ELSE

        } catch (UnknownHostException e) {
            System.out.println("Conexion error: " + e);
            e.printStackTrace();

        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException
                | ServiceException | JDBCConnectionException | NonUniqueResultException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();

        }

        return player;
    }

    public Player discoverCharacter(List<String> elementsList) {
        Player player = null;

        try {

            /* Se não existe o char, retorna null */
            if (elementsList.get(0).toLowerCase().equals("could not find character:")) {
                return null;

            } else {

                /* Se o char existir, inicia as chamadas do banco */
                player = new Player();
                Player p = new Player();

                for (int i = 1; i < elementsList.size() - TRASH_ELIMINATOR; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {

                        case "name:":

                            /* Caso o char tenha sido deletado pelo player, será preciso usar o split*/
                            String[] splitName = elementsList.get(i + AFTER_TITLE).split(",");

                            player.setPlayerName(splitName[0]);
                            /* Primeira persistência para salvar as tabelas fracas em relacionamento bilateral*/
                            new AbstractDAO<>(Player.class).insert(player);

                            /* Retorna id para outras persistências */
                            int idCharacter = new PlayerDAO().returnID(splitName[0]);
                            p.setIdCharacter(idCharacter);

                            break;

                        case "former names:":

                            /* Pega todos os former names */
                            String[] splitNames = elementsList.get(i + AFTER_TITLE).trim().split(",");

                            for (String rname : splitNames) {
                                /* Regra de persistência bilateral*/
                                FormerName fn = new FormerName(rname, Calendar.getInstance(), Calendar.getInstance());
                                fn.setPlayer(p);

                                new AbstractDAO<>(FormerName.class).insert(fn);

                            }

                            break;

                        case "title:":

                            player.setTitle(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "sex:":

                            player.setSex(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "vocation:":

                            player.setVocation(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "level:":

                            LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(LevelAdvance.class).insert(la);

                            break;

                        case "achievement points:":

                            AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(AchievementPoints.class).insert(ap);

                            break;

                        case "world:":

                            player.setWorld(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "former world:":

                            FormerWorld fw = new FormerWorld(p, elementsList.get(i + AFTER_TITLE), Calendar.getInstance(), Calendar.getInstance());
                            new AbstractDAO<>(FormerWorld.class).insert(fw);

                            break;

                        case "residence:":

                            player.setResidence(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "house:":

                            String[] nameHouse = elementsList.get(i + AFTER_TITLE).split(" is ");
                            House h = new House(p, nameHouse[0], Calendar.getInstance());
                            new AbstractDAO<>(House.class).insert(h);

                            break;

                        case "guild membership:":

                            String[] guildInfo = elementsList.get(i + AFTER_TITLE).split("of the");
                            Guild g = new Guild(p, guildInfo[1], guildInfo[0], Calendar.getInstance());
                            new AbstractDAO<>(Guild.class).insert(g);

                            break;

                        case "last login:":

                            player.setLastLogin(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "account status:":

                            player.setAccountStatus(elementsList.get(i + AFTER_TITLE));

                            break;

                        case "character deaths":

                            int j = 1;
                            while (!elementsList.get(i + j).toLowerCase().equals("search character")
                                    && !elementsList.get(i + j).toLowerCase().equals("account information")) {

                                /* Regras de negócio */
                                Death d = new Death(p, elementsList.get(i + j + 1), elementsList.get(i + j));
                                new AbstractDAO<>(Death.class).insert(d);

                                j = j + 2;
                            }

                            i += j;

                            break;

                        case "account information":

                            player.setTitleAccountInformation(elementsList.get(i + 2));
                            player.setDateCreate(elementsList.get(i + 4));

                            break;

                        default:
                            break;
                    }

                } // fim FOR

                /* Persiste Player */
                player.setRegisterDate(Calendar.getInstance());
                player.setLastUpdate(Calendar.getInstance());
                new AbstractDAO<>(Player.class).update(player);

            } // fim ELSE

        } catch (IllegalArgumentException | IndexOutOfBoundsException
                | ServiceException | JDBCConnectionException | NonUniqueResultException e) {
            System.out.println("Erro para: " + e);
            e.printStackTrace();

        }

        return player;
    }

    /* Verificar se o personagem trocou de nick para regras externas */
    public List<Object> getNick(String name) throws IOException {
        List<Object> objectList = new ArrayList<>();

        List<String> elementsList;

        String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

        /* Conecta com o domínio */
        Document document = Jsoup.connect(url).get();

        /* Coloca os itens numa lista */
        Elements chosenElements = document.getElementsByClass("Content");
        elementsList = chosenElements.get(0).getElementsByTag("td").eachText();
        objectList.add(elementsList);

        /* Se não existe o char, char foi deletado */
        if (elementsList.get(0).toLowerCase().equals("could not find character:")) {
            return null;

        } else {

            for (int i = 1; i < elementsList.size() - TRASH_ELIMINATOR; i++) {

                if (elementsList.get(i).toLowerCase().equals("name:")) {

                    /* Retorna o atual nick do personagem */
                    String[] splitNames = elementsList.get(i + AFTER_TITLE).split(",");
                    objectList.add(splitNames[0]);

                    return objectList;

                } // fim switch

            } // fim for

        } // fim else


        /* Implementar */
        //objectList.add("NULL");
        return null;
    }

    public List<String> getFormerNames(String name) throws IOException {
        List<String> fns = new ArrayList<>();

        List<String> elementsList;

        String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

        /* Conecta com o domínio */
        Document document = Jsoup.connect(url).get();

        /* Coloca os itens numa lista */
        Elements chosenElements = document.getElementsByClass("Content");
        elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

        /* Se não existe o char, char foi deletado */
        if (elementsList.get(0).toLowerCase().equals("could not find character:")) {
            return null;

        } else {

            for (int i = 1; i < elementsList.size() - TRASH_ELIMINATOR; i++) {

                if (elementsList.get(i).toLowerCase().equals("former names:")) {

                    /* Retorna o atual nick do personagem */
                    String[] splitNames = elementsList.get(i + AFTER_TITLE).split(",");

                    fns.addAll(Arrays.asList(splitNames));

                    return fns;

                } // fim switch

            } // fim for

        } // fim else


        /* Implementar */
        //objectList.add("NULL");
        return null;
    }

    /* Método para ser aplicado 1x por dia
     * Esse método só funciona para personagens que já existem no bando de dados! */
    public Player updateCharacter(String name) {

        Player player = null;

        try {

            List<String> elementsList;

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            /* Conecta com o domínio */
            Document document = Jsoup.connect(url).get();

            /* Coloca os itens numa lista */
            Elements chosenElements = document.getElementsByClass("Content");
            elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

            /* Se não existe o char, char foi deletado */
            if (elementsList.get(0).toLowerCase().equals("could not find character")) {

                player = new PlayerDAO().returnCharacterByName(name);

                /* Se player existir é porque foi deletado, senão é porque o nick está errado. */
                if (player != null) {

                    player.setIsDeleted(true);
                    player.setDateDeleted(Calendar.getInstance());
                    new AbstractDAO<>(Player.class).update(player);
                }

                return null;

            } else {

                /* Check houses */
                List<String> housesNovas = new ArrayList<>();
                boolean isHouseUpdate = false;

                /* Se o char existir, inicia as chamadas do banco */
                player = new PlayerDAO().returnCharacterByName(name);
                int idCharacter = new PlayerDAO().returnID(name);
                Player p = new Player();
                p.setIdCharacter(idCharacter);

                /* Atualizar apenas se o personagem teve alteração */
                int flagUpdate = 0;

                for (int i = 1; i < elementsList.size() - TRASH_ELIMINATOR; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {

                        case "name:":

                            /* Split para evitar personagens deletados pelos players */
                            String[] splitName = elementsList.get(i + AFTER_TITLE).split(",");

                            /* Se o nome for diferente do bd, é que mudou de nick */
                            if (!player.getPlayerName().equals(splitName[0])) {

                                /* Coloca o antigo nick como formerName 
                                 * Se for igual a 0 é porque não tem fns e esse será o primeiro fn */
                                if ((new AbstractDAO<>(FormerName.class).countRegistersById(idCharacter)) == 0) {

                                    FormerName fn = new FormerName(p, name, player.getRegisterDate(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerName.class).insert(fn);

                                } else {
                                    /* Senão, pega último dataEnd do último fn registrado */
                                    FormerName fn0 = new AbstractDAO<>(FormerName.class).returnLastRegisterDESC(idCharacter, "datebegin");
                                    FormerName fn = new FormerName(p, name, fn0.getDateEnd(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerName.class).insert(fn);
                                }

                                /* Atualiza novo nick */
                                player.setPlayerName(splitName[0]);

                                flagUpdate = 1;

                            }

                            break;

                        case "title:":
                            if (!player.getTitle().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setTitle(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "sex:":
                            if (!player.getSex().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setSex(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "level:":

                            /* Procura último level upado e compara com o atual level */
                            LevelAdvance la0 = new AbstractDAO<>(LevelAdvance.class)
                                    .returnLastRegisterDESC(idCharacter, "dayAdvance");

                            if (!String.valueOf(la0.getLevelDay()).equals(elementsList.get(i + AFTER_TITLE))) {
                                /* Persiste novo level */
                                LevelAdvance la = new LevelAdvance(p,
                                        Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                        Calendar.getInstance());
                                new AbstractDAO<>(LevelAdvance.class).insert(la);

                                flagUpdate = 1;
                            }

                            break;

                        case "achievement points:":

                            AchievementPoints ap0 = new AbstractDAO<>(AchievementPoints.class)
                                    .returnLastRegisterDESC(idCharacter, "achievementpoints");

                            if (!String.valueOf(ap0.getAchievementPoints()).equals(elementsList.get(i + AFTER_TITLE))) {

                                AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                        Calendar.getInstance());
                                new AbstractDAO<>(AchievementPoints.class).insert(ap);

                                flagUpdate = 1;
                            }

                            break;

                        case "world:":

                            /* Se o world nome for diferente do bd, é que mudou de world */
                            if (!player.getWorld().equals(elementsList.get(i + AFTER_TITLE))) {

                                /* Coloca o antigo world como FormerWorld 
                                 * Se for igual a 0 é porque não tem fws e esse será o primeiro fw */
                                if ((new AbstractDAO<>(FormerWorld.class).countRegistersById(idCharacter)) == 0) {
                                    FormerWorld fw = new FormerWorld(p, player.getWorld(), player.getRegisterDate(),
                                            Calendar.getInstance());
                                    new AbstractDAO<>(FormerWorld.class).insert(fw);

                                } else {
                                    /* Senão, pega último fn */
                                    FormerWorld fw0 = new AbstractDAO<>(FormerWorld.class).returnLastRegisterDESC(idCharacter, "datebegin");
                                    /* Adiciona novo fw */
                                    FormerWorld fw = new FormerWorld(p, player.getWorld(), fw0.getDateEnd(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerWorld.class).insert(fw);
                                }

                                /* Atualiza novo world */
                                player.setWorld(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;

                            }

                            break;

                        case "residence:":

                            if (!player.getResidence().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setResidence(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "house:":

                            List<String> houses = new AbstractDAO<>(House.class)
                                    .listAll(String.valueOf(idCharacter), "houseName");

                            String[] nameHouse = elementsList.get(i + AFTER_TITLE).split(" is ");
                            housesNovas.add(nameHouse[0]);

                            /* Se não cocntém é porque é house nova */
                            if (!houses.contains(nameHouse[0])) {
                                House h = new House(p, nameHouse[0], Calendar.getInstance());

                                /* Adc nova house */
                                new AbstractDAO<>(House.class).insert(h);

                                flagUpdate = 1;
                                isHouseUpdate = true;
                            }

                            break;

                        case "guild membership:":

                            Guild oldGuild = new AbstractDAO<>(Guild.class).returnLastRegisterDESC(idCharacter, "datebegin");
                            String[] guildInfo = elementsList.get(i + AFTER_TITLE).split("of the");

                            /* Se a guilda for diferente do último registro, é que mudou de guilda */
                            if (!oldGuild.getGuildName().equals(guildInfo[1])) {

                                /* Guild nova */
                                Guild newGuild = new Guild(p, guildInfo[1], guildInfo[0], Calendar.getInstance());
                                new AbstractDAO<>(Guild.class).insert(newGuild);

                                /* Atualiza data de saída da velha guild */
                                oldGuild.setDataEnd(Calendar.getInstance());
                                new AbstractDAO<>(Guild.class).update(oldGuild);

                                flagUpdate = 1;

                            } /* Se a guilda for igual e a posição do membro for diferente, é porque a posição foi alterada */ else if (oldGuild.getGuildName().equals(guildInfo[1])
                                    && !oldGuild.getMemberPositionGuild().equals(guildInfo[0])) {

                                /* Atualiza nova posição na guilda */
                                oldGuild.setMemberPositionGuild(guildInfo[0]);
                                new AbstractDAO<>(Guild.class).update(oldGuild);

                                flagUpdate = 1;
                            }

                            break;

                        case "last login:":

                            if (!player.getLastLogin().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setLastLogin(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "account status:":

                            if (!player.getAccountStatus().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setAccountStatus(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "character deaths":

                            List<String> deathList = new AbstractDAO<>(Death.class)
                                    .listAll(String.valueOf(idCharacter), "deathDate");

                            int j = 1;

                            while (!elementsList.get(i + j).toLowerCase().equals("search character")
                                    && !elementsList.get(i + j).toLowerCase().equals("account information")) {

                                /* Se não conter é porque tem novas mortes */
                                if (!deathList.contains(elementsList.get(i + j))) {
                                    Death d = new Death(p, elementsList.get(i + j + 1), elementsList.get(i + j));
                                    new AbstractDAO<>(Death.class).insert(d);
                                }

                                j = j + 2;
                            }

                            i += j;

                            break;

                        case "account information":

                            if (!player.getTitleAccountInformation().equals(elementsList.get(i + 2))) {

                                player.setTitleAccountInformation(elementsList.get(i + 2));
                                flagUpdate = 1;
                            }

                            if (!player.getDateCreate().equals(elementsList.get(i + 4))) {

                                player.setDateCreate(elementsList.get(i + 4));
                                flagUpdate = 1;
                            }

                            break;

                        default:
                            break;
                    }

                } // fim FOR

                List<String> houses = new AbstractDAO<>(House.class)
                        .listAll(String.valueOf(idCharacter), "houseName");

                if (isHouseUpdate == true) {

                    for (String house : houses) {

                        /* Se não contém é porque o char deixou de ter a casa */
                        if (!housesNovas.toString().contains(house)) {

                            House h = new AbstractDAO<>(House.class)
                                    .returnObjectByColumn("houseName", house);
                            h.setDataEnd(Calendar.getInstance());

                            /* Atualiza House */
                            new AbstractDAO<>(House.class).update(h);

                            flagUpdate = 1;
                        }
                    }
                }

                /* Persistir Player se houver alterações*/
                if (flagUpdate == 1) {

                    player.setLastUpdate(Calendar.getInstance());
                    new AbstractDAO<>(Player.class).update(player);
                    System.out.println("Itens Persistidos");

                } else {
                    System.out.println("Nenhum item persistido.");
                }

            } // fim ELSE

        } catch (UnknownHostException e) {
            System.out.println("Conexion Error:: " + e);
            e.printStackTrace();

        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException
                | ServiceException | JDBCConnectionException | NonUniqueResultException e) {
            System.out.println("Eror: " + e);
            e.printStackTrace();

        }

        return player;
    }

    public Player updateCharacter(String name, List<String> elementsList) {

        Player player = null;

        try {

            /* Se não existe o char, char foi deletado */
            if (elementsList.get(0).toLowerCase().equals("could not find character:")) {

                player = new PlayerDAO().returnCharacterByName(name);
                player.setIsDeleted(true);
                player.setDateDeleted(Calendar.getInstance());
                new AbstractDAO<>(Player.class).update(player);

                return null;

            } else {

                /* Check houses */
                List<String> housesNovas = new ArrayList<>();
                boolean isHouseUpdate = false;

                /* Se o char existir, inicia as chamadas do banco */
                player = new PlayerDAO().returnCharacterByName(name);
                int idCharacter = new PlayerDAO().returnID(name);
                Player p = new Player();
                p.setIdCharacter(idCharacter);

                /* Atualizar apenas se o personagem teve alteração */
                int flagUpdate = 0;

                for (int i = 1; i < elementsList.size() - TRASH_ELIMINATOR; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {

                        case "name:":

                            /* Split para evitar personagens deletados pelos players */
                            String[] splitName = elementsList.get(i + AFTER_TITLE).split(",");

                            /* Se o nome for diferente do bd, é que mudou de nick */
                            if (!player.getPlayerName().equals(splitName[0])) {

                                /* Coloca o antigo nick como formerName 
                                 * Se for igual a 0 é porque não tem fns e esse será o primeiro fn */
                                if ((new AbstractDAO<>(FormerName.class).countRegistersById(idCharacter)) == 0) {

                                    FormerName fn = new FormerName(p, name, player.getRegisterDate(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerName.class).insert(fn);

                                } else {
                                    /* Senão, pega último dataEnd do último fn registrado */
                                    FormerName fn0 = new AbstractDAO<>(FormerName.class).returnLastRegisterDESC(idCharacter, "datebegin");
                                    FormerName fn = new FormerName(p, name, fn0.getDateEnd(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerName.class).insert(fn);
                                }

                                /* Atualiza novo nick */
                                player.setPlayerName(splitName[0]);

                                flagUpdate = 1;

                            }

                            break;

                        case "title:":
                            if (!player.getTitle().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setTitle(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "sex:":
                            if (!player.getSex().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setSex(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "level:":

                            /* Procura último level upado e compara com o atual level */
                            LevelAdvance la0 = new AbstractDAO<>(LevelAdvance.class)
                                    .returnLastRegisterDESC(idCharacter, "dayAdvance");

                            if (!String.valueOf(la0.getLevelDay()).equals(elementsList.get(i + AFTER_TITLE))) {
                                /* Persiste novo level */
                                LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                        Calendar.getInstance());
                                new AbstractDAO<>(LevelAdvance.class).insert(la);

                                flagUpdate = 1;
                            }

                            break;

                        case "achievement points:":

                            AchievementPoints ap0 = new AbstractDAO<>(AchievementPoints.class)
                                    .returnLastRegisterDESC(idCharacter, "achievementpoints");

                            if (!String.valueOf(ap0.getAchievementPoints()).equals(elementsList.get(i + AFTER_TITLE))) {

                                AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + AFTER_TITLE)),
                                        Calendar.getInstance());
                                new AbstractDAO<>(AchievementPoints.class).insert(ap);

                                flagUpdate = 1;
                            }

                            break;

                        case "world:":

                            /* Se o world nome for diferente do bd, é que mudou de world */
                            if (!player.getWorld().equals(elementsList.get(i + AFTER_TITLE))) {

                                /* Coloca o antigo world como FormerWorld 
                                 * Se for igual a 0 é porque não tem fws e esse será o primeiro fw */
                                if ((new AbstractDAO<>(FormerWorld.class).countRegistersById(idCharacter)) == 0) {
                                    FormerWorld fw = new FormerWorld(p, player.getWorld(), player.getRegisterDate(),
                                            Calendar.getInstance());
                                    new AbstractDAO<>(FormerWorld.class).insert(fw);

                                } else {
                                    /* Senão, pega último fn */
                                    FormerWorld fw0 = new AbstractDAO<>(FormerWorld.class).returnLastRegisterDESC(idCharacter, "datebegin");
                                    /* Adiciona novo fw */
                                    FormerWorld fw = new FormerWorld(p, player.getWorld(), fw0.getDateEnd(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerWorld.class).insert(fw);
                                }

                                /* Atualiza novo world */
                                player.setWorld(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;

                            }

                            break;

                        case "residence:":

                            if (!player.getResidence().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setResidence(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "house:":

                            List<String> houses = new AbstractDAO<>(House.class)
                                    .listAll(String.valueOf(idCharacter), "houseName");

                            String[] nameHouse = elementsList.get(i + AFTER_TITLE).split(" is ");
                            housesNovas.add(nameHouse[0]);

                            /* Se não cocntém é porque é house nova */
                            if (!houses.contains(nameHouse[0])) {
                                House h = new House(p, nameHouse[0], Calendar.getInstance());

                                /* Adc nova house */
                                new AbstractDAO<>(House.class).insert(h);

                                flagUpdate = 1;
                                isHouseUpdate = true;
                            }

                            break;

                        case "guild membership:":

                            Guild oldGuild = new AbstractDAO<>(Guild.class).returnLastRegisterDESC(idCharacter, "datebegin");
                            String[] guildInfo = elementsList.get(i + AFTER_TITLE).split("of the");

                            /* Se a guilda for diferente do último registro, é que mudou de guilda */
                            if (!oldGuild.getGuildName().equals(guildInfo[1])) {

                                /* Guild nova */
                                Guild newGuild = new Guild(p, guildInfo[1], guildInfo[0], Calendar.getInstance());
                                new AbstractDAO<>(Guild.class).insert(newGuild);

                                /* Atualiza data de saída da velha guild */
                                oldGuild.setDataEnd(Calendar.getInstance());
                                new AbstractDAO<>(Guild.class).update(oldGuild);

                                flagUpdate = 1;

                            } /* Se a guilda for igual e a posição do membro for diferente, é porque a posição foi alterada */ else if (oldGuild.getGuildName().equals(guildInfo[1])
                                    && !oldGuild.getMemberPositionGuild().equals(guildInfo[0])) {

                                /* Atualiza nova posição na guilda */
                                oldGuild.setMemberPositionGuild(guildInfo[0]);
                                new AbstractDAO<>(Guild.class).update(oldGuild);

                                flagUpdate = 1;
                            }

                            break;

                        case "last login:":

                            if (!player.getLastLogin().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setLastLogin(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "account status:":

                            if (!player.getAccountStatus().equals(elementsList.get(i + AFTER_TITLE))) {
                                player.setAccountStatus(elementsList.get(i + AFTER_TITLE));

                                flagUpdate = 1;
                            }

                            break;

                        case "character deaths":

                            List<String> deathList = new AbstractDAO<>(Death.class)
                                    .listAll(String.valueOf(idCharacter), "deathDate");

                            int j = 1;

                            while (!elementsList.get(i + j).toLowerCase().equals("search character")
                                    && !elementsList.get(i + j).toLowerCase().equals("account information")) {

                                /* Se não conter é porque tem novas mortes */
                                if (!deathList.contains(elementsList.get(i + j))) {
                                    Death d = new Death(p, elementsList.get(i + j + 1), elementsList.get(i + j));
                                    new AbstractDAO<>(Death.class).insert(d);
                                }

                                j = j + 2;
                            }

                            i += j;

                            break;

                        case "account information":

                            if (!player.getTitleAccountInformation().equals(elementsList.get(i + 2))) {

                                player.setTitleAccountInformation(elementsList.get(i + 2));
                                flagUpdate = 1;
                            }

                            if (!player.getDateCreate().equals(elementsList.get(i + 4))) {

                                player.setDateCreate(elementsList.get(i + 4));
                                flagUpdate = 1;
                            }

                            break;

                        default:
                            break;
                    }

                } // fim FOR

                List<String> houses = new AbstractDAO<>(House.class)
                        .listAll(String.valueOf(idCharacter), "houseName");

                if (isHouseUpdate == true) {

                    for (String house : houses) {

                        /* Se não contém é porque o char deixou de ter a casa */
                        if (!housesNovas.toString().contains(house)) {

                            House h = new AbstractDAO<>(House.class)
                                    .returnObjectByColumn("houseName", house);
                            h.setDataEnd(Calendar.getInstance());

                            /* Atualiza House */
                            new AbstractDAO<>(House.class).update(h);

                            flagUpdate = 1;
                        }
                    }
                }

                /* Persistir Player se houver alterações*/
                if (flagUpdate == 1) {

                    player.setLastUpdate(Calendar.getInstance());
                    new AbstractDAO<>(Player.class).update(player);
                    System.out.println("Itens Persistidos");

                } else {
                    System.out.println("Nenhum item persistido.");
                }

            } // fim ELSE

        } catch (IllegalArgumentException | IndexOutOfBoundsException
                | ServiceException | JDBCConnectionException | NonUniqueResultException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();

        }

        return player;
    }

}
