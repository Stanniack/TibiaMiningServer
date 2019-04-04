package tibiamining;

import characaterdata.AccountCharacters;
import characaterdata.CharacterT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchCharacter {

    public CharacterT searchChar(String name) {
        CharacterT personagem = new CharacterT();

        try {

            List<String> listaDeElementos = new ArrayList<>();
            List<String> listaDeElementos2 = new ArrayList<>();

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;
            String url2 = "https://www.tibiaring.com/char.php?c=" + name + "&lang=pt";

            Document document = Jsoup.connect(url).get();
            Document document2 = Jsoup.connect(url2).get();

            Elements elementosEscolhidos = document.getElementsByClass("Content");
            Elements elementosEscolhidos2 = document2.getElementsByClass("flex w ja as");

            for (Element elemento : elementosEscolhidos) {
                listaDeElementos = elemento.getElementsByTag("td").eachText();
            }

            for (Element elemento : elementosEscolhidos2) {
                listaDeElementos2 = elemento.getElementsByTag("tr").eachText();
            }

            /* Trabalhando com a lista de elementos do Tibia.com*/
            for (int i = 1; i < listaDeElementos.size() - 2; i++) {

                if (listaDeElementos.get(i).toLowerCase().equals("name:")) {
                    personagem.setName(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("sex:")) {
                    personagem.setSex(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("vocation:")) {
                    personagem.setVocation(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("level:")) {
                    personagem.setLevel(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("achievement points:")) {
                    personagem.setAchievementPoints(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("world:")) {
                    personagem.setWorld(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("residence:")) {
                    personagem.setResidence(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("house:")) {
                    personagem.setHouse(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("guild membership:")) {
                    personagem.setGuild(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("last login:")) {
                    personagem.setLastLogin(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("comment:")) {
                    personagem.setComment(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("account atatus:")) {
                    personagem.setAccountStatus(listaDeElementos.get(i + 1));

                } else if (listaDeElementos.get(i).toLowerCase().equals("account achievements")
                        && !listaDeElementos.get(i + 1).toLowerCase().equals("there are no achievements set to be displayed for this character.")) {
                    int j = 1;

                    while (!listaDeElementos.get(i + j).toLowerCase().equals("account information") && !listaDeElementos.get(i + j).toLowerCase().equals("search character")) {
                        personagem.getAchievments().add(listaDeElementos.get(i + j));
                        j++;
                    }

                    // Leva o laço ao elemento seguinte após o tratamento das informações pertencentes àquele dado
                    i += j;

                } else if (listaDeElementos.get(i).toLowerCase().equals("account information")) {
                    personagem.getAccountInformation().setTitle(listaDeElementos.get(i + 2));

                    personagem.getAccountInformation().setDateCreate(listaDeElementos.get(i + 4));
                } else if (listaDeElementos.get(i).toLowerCase().equals("character deaths")) {
                    int j = 1;

                    while (!listaDeElementos.get(i + j).toLowerCase().equals("account information") && !listaDeElementos.get(i + j).toLowerCase().equals("search Character")) {
                        personagem.getDeaths().add(listaDeElementos.get(i + j));
                        j++;
                    }

                    i += j;
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

            for (int i = 1; i < listaDeElementos2.size(); i++) {

                // Pega level min sharedexp
                if (listaDeElementos2.get(i).toLowerCase().contains("min level")) {
                    String[] elementos = listaDeElementos2.get(i).split(" ");
                    personagem.setSharedExpMinLevel(elementos[elementos.length - 1]);

                } else if (listaDeElementos2.get(i).toLowerCase().contains("max level")) {
                    String[] elementos = listaDeElementos2.get(i).split(" ");
                    personagem.setSharedExpMaxLevel(elementos[elementos.length - 1]);

                } else if (listaDeElementos2.get(i).toLowerCase().equals("data level")) {
                    int j = 1;
                    System.out.println("debuga");
                    // Regex para datas yyyy-mm-dd
                    String regex = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";
                    Pattern p = Pattern.compile(regex);

                    while (true) {

                        Matcher m = p.matcher(listaDeElementos2.get(i + j));

                        // Se nao houver mais strings contendo datas, então pare
                        if (!m.find()) {
                            break;
                        }

                        personagem.getLevelAdvances().add(listaDeElementos2.get(i + j));
                        j++;

                    }

                    i += j;

                }

            }

            System.out.println(listaDeElementos2);

        } catch (IOException e) {
            System.out.println("Erro na conexão Jsoup: " + e);
        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("Url inválida: " + e);
        }

        return personagem;
    }
}
