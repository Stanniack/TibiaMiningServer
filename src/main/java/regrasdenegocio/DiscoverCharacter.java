package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.FormerName;
import model.Personagem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DiscoverCharacter {

    /* afterTitle = o conteúdo após o título do elemento */
    private final int afterTitle = 1;
    /* Tamanho da lista quando não é o conteúdo que procuro */
    private final int characterDoesNotExists = 6;

    public Personagem searchChar(String name) {
        Personagem personagem = new Personagem();

        try {

            List<String> elementsList;
            int idPers;

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            /* Conecta com o domínio */
            Document document = Jsoup.connect(url).get();

            /* Coloca os itens numa lista */
            Elements chosenElements = document.getElementsByClass("Content");
            elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

            /* Trabalhando com a lista de elementos do Tibia.com*/
            for (int i = 1; i < elementsList.size() - 2; i++) {

                if (elementsList.get(i).toLowerCase().equals("name:")) {
                    /* É preciso persistir uma vez aqui*/
                    personagem.setName(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("former names:")) {

                    /* Pega todos os former names */
                    String[] splitNames = elementsList.get(i + afterTitle).trim().split(",");

                    for (String rname : splitNames) {
                        /* Persiste aqui */
                    }

                    /* Regra de persistência */
                } else if (elementsList.get(i).toLowerCase().equals("title:")) {
                    personagem.setTitle(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("sex:")) {
                    personagem.setSex(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("vocation:")) {
                    personagem.setVocation(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("level:")) {
                    personagem.setLevel_(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("achievement points:")) {
                    /* */

                } else if (elementsList.get(i).toLowerCase().equals("world:")) {
                    /* */
                } else if (elementsList.get(i).toLowerCase().equals("former world:")) {
                    /* */
                } else if (elementsList.get(i).toLowerCase().equals("residence:")) {
                    personagem.setResidence(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("house:")) {
                    /* */

                } else if (elementsList.get(i).toLowerCase().equals("guild membership:")) {
                    /* */

                } else if (elementsList.get(i).toLowerCase().equals("last login:")) {
                    personagem.setLastLogin(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("comment:")) {
                    /* */

                } else if (elementsList.get(i).toLowerCase().equals("account atatus:")) {
                    // está com problema
                    personagem.setAccountStatus(elementsList.get(i + afterTitle));

                } else if (elementsList.get(i).toLowerCase().equals("account achievements")
                        && !elementsList.get(i + afterTitle).toLowerCase().equals("there are no achievements set to be displayed for this character.")) {
                    int j = 1;

                    while (!elementsList.get(i + j).toLowerCase().equals("account information") && !elementsList.get(i + j).toLowerCase().equals("search character")) {
                        //personagem.getAchievements().add(listaDeElementos.get(i + j));
                        /* */
                        j++;
                    }

                    // Leva o laço ao elemento seguinte após o tratamento das informações pertencentes àquele dado
                    i += j;

                } else if (elementsList.get(i).toLowerCase().equals("character deaths")) {
                    int j = 1;

                    while (!elementsList.get(i + j).toLowerCase().equals("search character") && !elementsList.get(i + j).toLowerCase().equals("account information")) {

                        /* */
                        //personagem.getDeaths().add(listaDeElementos.get(i + j) + " " + listaDeElementos.get(i + j + 1));
                        j = j + 2;
                    }

                    i += j;

                } else if (elementsList.get(i).toLowerCase().equals("account information")) {

                    //personagem.getAccountInformation().setTitle(listaDeElementos.get(i + 2));
                    //personagem.getAccountInformation().setDateCreate(listaDeElementos.get(i + 4));
                }
                /*else if (listaDeElementos.get(i).equals("Characters")) {
                    int j = 1;
                    while (!listaDeElementos.get(i + j).equals("Search Character")) {
                        personagem.getAccountCharacters().add(new AccountCharacters(
                                listaDeElementos.get(i + j),
                                listaDeElementos.get(i + j),
                                listaDeElementos.get(i + j),
                                listaDeElementos.get(i + j)));
                        j++;
                    }
                }*/
            }

            //System.out.println(elementsList.toString());
            new AbstractDAO<>(Personagem.class).insert(personagem);

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
