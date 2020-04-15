package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.FormerNameDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.AccountCharacters;
import model.AchievementPoints;
import model.Death;
import model.FormerName;
import model.FormerWorld;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Personagem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckCharacter {

    /* afterTitle = o conteúdo após o título do elemento */
    private final int afterTitle = 1;
    /* Tamanho da lista quando não é o conteúdo que procuro */
    private final int characterDoesNotExists = 6;
    /* Eliminar conteúdo lixo da lista de elementos */
    private final int trashEliminator = 2;

    public Personagem discoverCharacter(String name) {
        Personagem personagem = new Personagem();

        try {

            List<String> elementsList;
            int idPers;
            int idCharacter = 0;
            Personagem p = new Personagem();

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
                
                System.out.println(elementsList.toString());
                for (int i = 1; i < elementsList.size() - trashEliminator; i++) {
                    
                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    if (elementsList.get(i).toLowerCase().equals("name:")) {
                        personagem.setName(elementsList.get(i + afterTitle));

                        /* Primeira persistência para salvar as tabelas fracas em relacionamento bilateral*/
                        new AbstractDAO<>(Personagem.class).insert(personagem);
                        /* Retorna id para outras persistências */
                        idCharacter = new PersonagemDAO().returnID(name);
                        p.setIdCharacter(idCharacter);

                    } else if (elementsList.get(i).toLowerCase().equals("former names:")) {

                        /* Pega todos os former names */
                        String[] splitNames = elementsList.get(i + afterTitle).trim().split(",");

                        for (String rname : splitNames) {
                            /* Regra de persistência bilateral*/
                            FormerName fn = new FormerName(rname, Calendar.getInstance(), Calendar.getInstance());
                            fn.setPersonagem(p);

                            new AbstractDAO<>(FormerName.class).insert(fn);

                        }

                    } else if (elementsList.get(i).toLowerCase().equals("title:")) {
                        personagem.setTitle(elementsList.get(i + afterTitle));

                    } else if (elementsList.get(i).toLowerCase().equals("sex:")) {
                        personagem.setSex(elementsList.get(i + afterTitle));

                    } else if (elementsList.get(i).toLowerCase().equals("vocation:")) {
                        personagem.setVocation(elementsList.get(i + afterTitle));

                    } else if (elementsList.get(i).toLowerCase().equals("level:")) {

                        LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                Calendar.getInstance());
                        new AbstractDAO<>(LevelAdvance.class).insert(la);

                    } else if (elementsList.get(i).toLowerCase().equals("achievement points:")) {

                        AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                Calendar.getInstance());
                        new AbstractDAO<>(AchievementPoints.class).insert(ap);

                    } else if (elementsList.get(i).toLowerCase().equals("world:")) {

                        personagem.setWorld(elementsList.get(i + afterTitle));

                    } else if (elementsList.get(i).toLowerCase().equals("former world:")) {

                        FormerWorld fw = new FormerWorld(p, elementsList.get(i + afterTitle), Calendar.getInstance());
                        new AbstractDAO<>(FormerWorld.class).insert(fw);

                    } else if (elementsList.get(i).toLowerCase().equals("residence:")) {

                        personagem.setResidence(elementsList.get(i + afterTitle));

                    } else if (elementsList.get(i).toLowerCase().equals("house:")) {

                        String[] nameHouse = elementsList.get(i + afterTitle).split(" is ");
                        House h = new House(p, nameHouse[0], Calendar.getInstance());
                        new AbstractDAO<>(House.class).insert(h);

                    } else if (elementsList.get(i).toLowerCase().equals("guild membership:")) {

                        String[] guildInfo = elementsList.get(i + afterTitle).split("of the");
                        Guild g = new Guild(p, guildInfo[0], guildInfo[1], Calendar.getInstance());
                        new AbstractDAO<>(Guild.class).insert(g);

                    } else if (elementsList.get(i).toLowerCase().equals("last login:")) {
                        personagem.setLastLogin(elementsList.get(i + afterTitle));

                    }  else if (elementsList.get(i).toLowerCase().equals("account status:")) {

                        personagem.setAccountStatus(elementsList.get(i + afterTitle));

                    } else if (elementsList.get(i).toLowerCase().equals("character deaths")) {
                        int j = 1;

                        while (!elementsList.get(i + j).toLowerCase().equals("search character")
                                && !elementsList.get(i + j).toLowerCase().equals("account information")) {

                            /* Regras de negócio */
                            Death d = new Death(p, elementsList.get(i + j + 1), elementsList.get(i + j));
                            new AbstractDAO<>(Death.class).insert(d);
                            
                            j = j + 2;
                        }

                        i += j;

                    } else if (elementsList.get(i).toLowerCase().equals("account information")) {

                        personagem.setTitleAccountInformation(elementsList.get(i + 2));
                        personagem.setDateCreate(Calendar.getInstance());
                    }  
//                    } else if (elementsList.get(i).equals("Characters")) {
//                        int j = 1;
//                        
//                        while (!elementsList.get(i + j).equals("Search Character")) {
//                                
//                                  j++;
//                        }
//                    }

                } // fim FOR

            } // fim ELSE

        } catch (IOException e) {
            System.out.println("Erro na conexão Jsoup: " + e);

        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("Url inválida: " + e);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Quebra de array: " + e);

        }

        return personagem;
    }
}
