package regrasdenegocio;

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

            List<String> listaDeElementos = new ArrayList<>();
            FormerName fn = new FormerName();

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            Document document = Jsoup.connect(url).get();

            Elements elementosEscolhidos = document.getElementsByClass("Content");

            for (Element elemento : elementosEscolhidos) {
                listaDeElementos = elemento.getElementsByTag("td").eachText();
            }

            /* Trabalhando com a lista de elementos do Tibia.com*/
            for (int i = 1; i < listaDeElementos.size() - 2; i++) {

                if (listaDeElementos.get(i).toLowerCase().equals(listaDeElementos.get(i + afterTitle))) {
                    String rName = new PersonagemDAO().searchName("");

                    personagem.setName(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("former names:")) {

                    /* Pega todos os former names */
                    String[] splitNames = listaDeElementos.get(i + afterTitle).trim().split(",");

                    for (String rname : splitNames) {

                    }

                    /* Regra de persistência */
                } else if (listaDeElementos.get(i).toLowerCase().equals("title:")) {
                    personagem.setTitle(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("sex:")) {
                    personagem.setSex(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("vocation:")) {
                    personagem.setVocation(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("level:")) {
                    personagem.setLevel_(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("achievement points:")) {
                    /* */

                } else if (listaDeElementos.get(i).toLowerCase().equals("world:")) {
                    /* */
                } else if (listaDeElementos.get(i).toLowerCase().equals("former world:")) {
                    /* */
                } else if (listaDeElementos.get(i).toLowerCase().equals("residence:")) {
                    personagem.setResidence(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("house:")) {
                    /* */

                } else if (listaDeElementos.get(i).toLowerCase().equals("guild membership:")) {
                    /* */

                } else if (listaDeElementos.get(i).toLowerCase().equals("last login:")) {
                    personagem.setLastLogin(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("comment:")) {
                    /* */

                } else if (listaDeElementos.get(i).toLowerCase().equals("account atatus:")) {
                    personagem.setAccountStatus(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("account achievements")
                        && !listaDeElementos.get(i + afterTitle).toLowerCase().equals("there are no achievements set to be displayed for this character.")) {
                    int j = 1;

                    while (!listaDeElementos.get(i + j).toLowerCase().equals("account information") && !listaDeElementos.get(i + j).toLowerCase().equals("search character")) {
                        //personagem.getAchievements().add(listaDeElementos.get(i + j));
                        /* */
                        j++;
                    }

                    // Leva o laço ao elemento seguinte após o tratamento das informações pertencentes àquele dado
                    i += j;

                } else if (listaDeElementos.get(i).toLowerCase().equals("character deaths")) {
                    int j = 1;

                    while (!listaDeElementos.get(i + j).toLowerCase().equals("search character") && !listaDeElementos.get(i + j).toLowerCase().equals("account information")) {

                        /* */
                        //personagem.getDeaths().add(listaDeElementos.get(i + j) + " " + listaDeElementos.get(i + j + 1));
                        j = j + 2;
                    }

                    i += j;

                } else if (listaDeElementos.get(i).toLowerCase().equals("account information")) {

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
