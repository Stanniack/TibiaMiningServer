package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
import model.Personagem;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CheckCharacter {

    /* afterTitle = o conteúdo após o título do elemento */
    private final int afterTitle = 1;
    /* Tamanho da lista quando não é o conteúdo que procuro */
    private final int characterDoesNotExists = 6;
    /* Eliminar conteúdo lixo da lista de elementos */
    private final int trashEliminator = 2;

    public Personagem discoverCharacter(String name) {
        Personagem personagem = null;

        try {

            List<String> elementsList;

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            /* Conecta com o domínio */
            Document document = Jsoup.connect(url).get();

            /* Coloca os itens numa lista */
            Elements chosenElements = document.getElementsByClass("Content");
            elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

            /* Se não existe o char, retorna null */
            if (elementsList.get(0).toLowerCase().equals("could not find character:")) {
                return null;

            } else {

                /* Se o char existir, inicia as chamadas do banco */
                personagem = new Personagem();
                Personagem p = new Personagem();

                for (int i = 1; i < elementsList.size() - trashEliminator; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {

                        case "name:":

                            /* Caso o char tenha sido deletado pelo player, será preciso usar o split*/
                            String[] splitName = elementsList.get(i + afterTitle).split(",");

                            personagem.setPlayerName(splitName[0]);
                            /* Primeira persistência para salvar as tabelas fracas em relacionamento bilateral*/
                            new AbstractDAO<>(Personagem.class).insert(personagem);

                            /* Retorna id para outras persistências */
                            int idCharacter = new PersonagemDAO().returnID(splitName[0]);
                            p.setIdCharacter(idCharacter);

                            break;

                        case "former names:":

                            /* Pega todos os former names */
                            String[] splitNames = elementsList.get(i + afterTitle).trim().split(",");

                            for (String rname : splitNames) {
                                /* Regra de persistência bilateral*/
                                FormerName fn = new FormerName(rname, Calendar.getInstance(), Calendar.getInstance());
                                fn.setPersonagem(p);

                                new AbstractDAO<>(FormerName.class).insert(fn);

                            }

                            break;

                        case "title:":

                            personagem.setTitle(elementsList.get(i + afterTitle));

                            break;

                        case "sex:":

                            personagem.setSex(elementsList.get(i + afterTitle));

                            break;

                        case "vocation:":

                            personagem.setVocation(elementsList.get(i + afterTitle));

                            break;

                        case "level:":

                            LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(LevelAdvance.class).insert(la);

                            break;

                        case "achievement points:":

                            AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(AchievementPoints.class).insert(ap);

                            break;

                        case "world:":

                            personagem.setWorld(elementsList.get(i + afterTitle));

                            break;

                        case "former world:":

                            FormerWorld fw = new FormerWorld(p, elementsList.get(i + afterTitle), Calendar.getInstance(), Calendar.getInstance());
                            new AbstractDAO<>(FormerWorld.class).insert(fw);

                            break;

                        case "residence:":

                            personagem.setResidence(elementsList.get(i + afterTitle));

                            break;

                        case "house:":

                            String[] nameHouse = elementsList.get(i + afterTitle).split(" is ");
                            House h = new House(p, nameHouse[0], Calendar.getInstance());
                            new AbstractDAO<>(House.class).insert(h);

                            break;

                        case "guild membership:":

                            String[] guildInfo = elementsList.get(i + afterTitle).split("of the");
                            Guild g = new Guild(p, guildInfo[1], guildInfo[0], Calendar.getInstance());
                            new AbstractDAO<>(Guild.class).insert(g);

                            break;

                        case "last login:":

                            personagem.setLastLogin(elementsList.get(i + afterTitle));

                            break;

                        case "account status:":

                            personagem.setAccountStatus(elementsList.get(i + afterTitle));

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

                            personagem.setTitleAccountInformation(elementsList.get(i + 2));
                            personagem.setDateCreate(elementsList.get(i + 4));

                            break;

                        default:
                            break;
                    }

                } // fim FOR

                /* Persiste Personagem */
                personagem.setRegisterDate(Calendar.getInstance());
                new AbstractDAO<>(Personagem.class).update(personagem);

            } // fim ELSE

        } catch (UnknownHostException e) {
            System.out.println("Erro na conexão: " + e);
            e.printStackTrace();

        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException
                | ServiceException | JDBCConnectionException | NonUniqueResultException e) {
            System.out.println("Erro para: " + e);
            e.printStackTrace();

        }

        return personagem;
    }

    /* Verificar se o personagem trocou de nick para regras externas - CheckRank */
    public String getNick(String name) {
        try {

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

                for (int i = 1; i < elementsList.size() - trashEliminator; i++) {

                    if (elementsList.get(i).toLowerCase().equals("name:")) {

                        /* Retorna o atual nick do personagem */
                        String[] splitName = elementsList.get(i + afterTitle).split(",");

                        return splitName[0];

                    } // fim switch

                } // fim for

            } // fim else

        } catch (UnknownHostException e) {
            System.out.println("Erro na conexão: " + e);

        } catch (IOException | java.lang.IllegalArgumentException | IndexOutOfBoundsException
                | ServiceException | JDBCConnectionException e) {
            System.out.println("Erro para: " + e);

        }
        /* Implementar */

        return null;
    }

    /* Método para ser aplicado 1x por dia */
 /* Esse método só funciona para personagens que já existem no bando de dados! */
    //////////////////////////////////////////////////////////////////* Construir um facade para todos algoritmos */////////////////////////////////////////
    public Personagem updateCharacter(String name) {

        Personagem personagem = null;

        try {

            List<String> elementsList;

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            /* Conecta com o domínio */
            Document document = Jsoup.connect(url).get();

            /* Coloca os itens numa lista */
            Elements chosenElements = document.getElementsByClass("Content");
            elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

            /* Se não existe o char, char foi deletado */
            if (elementsList.get(0).toLowerCase().equals("could not find character:")) {

                personagem = new PersonagemDAO().returnCharacterByName(name);
                personagem.setIsDeleted(true);
                personagem.setDateDeleted(Calendar.getInstance());
                new AbstractDAO<>(Personagem.class).update(personagem);

                return null;

            } else {

                /* Check houses */
                List<String> housesNovas = new ArrayList<>();
                boolean isHouseUpdate = false;

                /* Se o char existir, inicia as chamadas do banco */
                personagem = new PersonagemDAO().returnCharacterByName(name);
                int idCharacter = new PersonagemDAO().returnID(name);
                Personagem p = new Personagem();
                p.setIdCharacter(idCharacter);

                /* Atualizar apenas se o personagem teve alteração */
                int flagUpdate = 0;

                for (int i = 1; i < elementsList.size() - trashEliminator; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {

                        case "name:":

                            /* Split para evitar personagens deletados pelos players */
                            String[] splitName = elementsList.get(i + afterTitle).split(",");

                            /* Se o nome for diferente do bd, é que mudou de nick */
                            if (!personagem.getPlayerName().equals(splitName[0])) {

                                /* Coloca o antigo nick como formerName 
                                 * Se for igual a 0 é porque não tem fns e esse será o primeiro fn */
                                if ((new AbstractDAO<>(FormerName.class).countRegistersById(idCharacter)) == 0) {

                                    FormerName fn = new FormerName(p, name, personagem.getRegisterDate(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerName.class).insert(fn);

                                } else {
                                    /* Senão, pega último dataEnd do último fn registrado */
                                    FormerName fn0 = new AbstractDAO<>(FormerName.class).searchLastRegisterByIdDESC(idCharacter, "datebegin");
                                    FormerName fn = new FormerName(p, name, fn0.getDateEnd(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerName.class).insert(fn);
                                }

                                /* Atualiza novo nick */
                                personagem.setPlayerName(splitName[0]);

                                flagUpdate = 1;

                            }

                            break;

                        case "title:":
                            if (!personagem.getTitle().equals(elementsList.get(i + afterTitle))) {
                                personagem.setTitle(elementsList.get(i + afterTitle));

                                flagUpdate = 1;
                            }

                            break;

                        case "sex:":
                            if (!personagem.getSex().equals(elementsList.get(i + afterTitle))) {
                                personagem.setSex(elementsList.get(i + afterTitle));

                                flagUpdate = 1;
                            }

                            break;

                        case "level:":

                            /* Procura último level upado e compara com o atual level */
                            LevelAdvance la0 = new AbstractDAO<>(LevelAdvance.class)
                                    .searchLastRegisterByIdDESC(idCharacter, "dayAdvance");

                            if (!String.valueOf(la0.getLevelDay()).equals(elementsList.get(i + afterTitle))) {
                                /* Persiste novo level */
                                LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                        Calendar.getInstance());
                                new AbstractDAO<>(LevelAdvance.class).insert(la);

                                flagUpdate = 1;
                            }

                            break;

                        case "achievement points:":

                            AchievementPoints ap0 = new AbstractDAO<>(AchievementPoints.class)
                                    .searchLastRegisterByIdDESC(idCharacter, "achievementpoints");

                            if (!String.valueOf(ap0.getAchievmentPoints()).equals(elementsList.get(i + afterTitle))) {

                                AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                        Calendar.getInstance());
                                new AbstractDAO<>(AchievementPoints.class).insert(ap);

                                flagUpdate = 1;
                            }

                            break;

                        case "world:":

                            /* Se o world nome for diferente do bd, é que mudou de world */
                            if (!personagem.getWorld().equals(elementsList.get(i + afterTitle))) {

                                /* Coloca o antigo world como FormerWorld 
                                 * Se for igual a 0 é porque não tem fws e esse será o primeiro fw */
                                if ((new AbstractDAO<>(FormerWorld.class).countRegistersById(idCharacter)) == 0) {
                                    FormerWorld fw = new FormerWorld(p, personagem.getWorld(), personagem.getRegisterDate(),
                                            Calendar.getInstance());
                                    new AbstractDAO<>(FormerWorld.class).insert(fw);

                                } else {
                                    /* Senão, pega último fn */
                                    FormerWorld fw0 = new AbstractDAO<>(FormerWorld.class).searchLastRegisterByIdDESC(idCharacter, "datebegin");
                                    /* Adiciona novo fw */
                                    FormerWorld fw = new FormerWorld(p, personagem.getWorld(), fw0.getDateEnd(), Calendar.getInstance());
                                    new AbstractDAO<>(FormerWorld.class).insert(fw);
                                }

                                /* Atualiza novo world */
                                personagem.setWorld(elementsList.get(i + afterTitle));

                                flagUpdate = 1;

                            }

                            break;

                        case "residence:":

                            if (!personagem.getResidence().equals(elementsList.get(i + afterTitle))) {
                                personagem.setResidence(elementsList.get(i + afterTitle));

                                flagUpdate = 1;
                            }

                            break;

                        case "house:":

                            List<String> houses = new AbstractDAO<>(House.class)
                                    .listAll(String.valueOf(idCharacter), "houseName");

                            String[] nameHouse = elementsList.get(i + afterTitle).split(" is ");
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

                            Guild oldGuild = new AbstractDAO<>(Guild.class).searchLastRegisterByIdDESC(idCharacter, "datebegin");
                            String[] guildInfo = elementsList.get(i + afterTitle).split("of the");

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

                            if (!personagem.getLastLogin().equals(elementsList.get(i + afterTitle))) {
                                personagem.setLastLogin(elementsList.get(i + afterTitle));

                                flagUpdate = 1;
                            }

                            break;

                        case "account status:":

                            if (!personagem.getAccountStatus().equals(elementsList.get(i + afterTitle))) {
                                personagem.setAccountStatus(elementsList.get(i + afterTitle));

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

                            if (!personagem.getTitleAccountInformation().equals(elementsList.get(i + 2))) {

                                personagem.setTitleAccountInformation(elementsList.get(i + 2));
                                flagUpdate = 1;
                            }

                            if (!personagem.getDateCreate().equals(elementsList.get(i + 4))) {

                                personagem.setDateCreate(elementsList.get(i + 4));
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
                                    .searchObjectByColumn("houseName", house);
                            h.setDataEnd(Calendar.getInstance());

                            /* Atualiza House */
                            new AbstractDAO<>(House.class).update(h);

                            flagUpdate = 1;
                        }
                    }
                }

                /* Persistir Personagem se houver alterações*/
                if (flagUpdate == 1) {

                    System.out.println("Itens Persistidos");
                    new AbstractDAO<>(Personagem.class).update(personagem);

                } else {
                    System.out.println("Nenhum item persistido.");
                }

            } // fim ELSE

        } catch (UnknownHostException e) {
            System.out.println("Erro na conexão: " + e);
            e.printStackTrace();

        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException
                | ServiceException | JDBCConnectionException | NonUniqueResultException e) {
            System.out.println("Erro para: " + e);
            e.printStackTrace();

        }

        return personagem;
    }

}
