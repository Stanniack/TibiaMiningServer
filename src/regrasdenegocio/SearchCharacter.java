package regrasdenegocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.character.Character;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchCharacter {

    /* afterTitle = o conteúdo após o título do elemento */
    private int afterTitle = 1;

    public Character searchChar(String name) {
        Character personagem = new Character();

        try {

            List<String> listaDeElementos = new ArrayList<>();

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            Document document = Jsoup.connect(url).get();

            Elements elementosEscolhidos = document.getElementsByClass("Content");

            for (Element elemento : elementosEscolhidos) {
                listaDeElementos = elemento.getElementsByTag("td").eachText();
            }

            //System.out.println(listaDeElementos);

            /* Trabalhando com a lista de elementos do Tibia.com*/
            for (int i = 1; i < listaDeElementos.size() - 2; i++) {

                if (listaDeElementos.get(i).toLowerCase().equals("name:")) {
                    personagem.setName(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("former names:")) {

                    /* Pega todos os former names */
                    int j = 0;
                    while (!listaDeElementos.get(i + j).toLowerCase().equals("title:")) {
                        // Resolvero  bang aqui
                        System.out.println(listaDeElementos.get(i + j));
                        j++;
                    }

                } else if (listaDeElementos.get(i).toLowerCase().equals("title:")) {
                    personagem.setTitle(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("sex:")) {
                    personagem.setSex(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("vocation:")) {
                    personagem.setVocation(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("level:")) {
                    personagem.setLevel(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("achievement points:")) {
                    // transformar achievment points em uma classe
                    personagem.setAchievementPoints(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("world:")) {
                    personagem.setWorld(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("former world:")) {
                    personagem.setFormerWorld(listaDeElementos.get(i + afterTitle));
                    
                } else if (listaDeElementos.get(i).toLowerCase().equals("residence:")) {
                    personagem.setResidence(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("house:")) {
                    personagem.setHouse(listaDeElementos.get(i + afterTitle));
                    
                } else if (listaDeElementos.get(i).toLowerCase().equals("guild membership:")) {
                    personagem.setGuild(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("last login:")) {
                    personagem.setLastLogin(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("comment:")) {
                    personagem.setComment(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("account atatus:")) {
                    personagem.setAccountStatus(listaDeElementos.get(i + afterTitle));

                } else if (listaDeElementos.get(i).toLowerCase().equals("account achievements")
                        && !listaDeElementos.get(i + afterTitle).toLowerCase().equals("there are no achievements set to be displayed for this character.")) {
                    int j = 1;

                    while (!listaDeElementos.get(i + j).toLowerCase().equals("account information") && !listaDeElementos.get(i + j).toLowerCase().equals("search character")) {
                        personagem.getAchievments().add(listaDeElementos.get(i + j));
                        j++;
                    }

                    // Leva o laço ao elemento seguinte após o tratamento das informações pertencentes àquele dado
                    i += j;

                } else if (listaDeElementos.get(i).toLowerCase().equals("character deaths")) {
                    int j = 1;

                    while (!listaDeElementos.get(i + j).toLowerCase().equals("search character") && !listaDeElementos.get(i + j).toLowerCase().equals("account information")) {

                        personagem.getDeaths().add(listaDeElementos.get(i + j) + " " + listaDeElementos.get(i + j + 1));
                        j = j + 2;
                    }

                    i += j;

                } else if (listaDeElementos.get(i).toLowerCase().equals("account information")) {

                    personagem.getAccountInformation().setTitle(listaDeElementos.get(i + 2));
                    personagem.getAccountInformation().setDateCreate(listaDeElementos.get(i + 4));
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
