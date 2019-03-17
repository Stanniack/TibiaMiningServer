package tibiamining;

import characaterdata.AccountCharacters;
import characaterdata.CharacterT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchCharacter {

    public CharacterT searchChar(String name) {
        CharacterT personagem = new CharacterT();

        try {

            List<String> listaDeElementos = new ArrayList<>();

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;
            Document document = Jsoup.connect(url).get();
            Elements elementosEscolhidos = document.getElementsByClass("Content");

            for (Element elemento : elementosEscolhidos) {
                listaDeElementos = elemento.getElementsByTag("td").eachText();
            }

            /* Trabalhando com a lista de elementos */
            for (int i = 1; i < listaDeElementos.size() - 2; i++) {
                
                if (listaDeElementos.get(i).equals("Name:")) {
                    personagem.setName(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Sex:")) {
                    personagem.setSex(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Vocation:")) {
                    personagem.setVocation(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Level:")) {
                    personagem.setLevel(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Achievement Points:")) {
                    personagem.setAchievementPoints(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("World:")) {
                    personagem.setWorld(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Residence:")) {
                    personagem.setResidence(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("House:")) {
                    personagem.setHouse(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Guild Membership:")) {
                    personagem.setGuild(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Last Login:")) {
                    personagem.setLastLogin(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Comment:")) {
                    personagem.setComment(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Account Status:")) {
                    personagem.setAccountStatus(listaDeElementos.get(i + 1));
                    
                } else if (listaDeElementos.get(i).equals("Account Achievements") 
                        && !listaDeElementos.get(i + 1).equals("There are no achievements set to be displayed for this character.")) {
                    int j = 1;
                    
                    while (!listaDeElementos.get(i + j).equals("Account Information") && !listaDeElementos.get(i + j).equals("Search Character")) {
                        personagem.getAchievments().add(listaDeElementos.get(i + j));
                        j++;
                    }
                } else if (listaDeElementos.get(i).equals("Account Information")) {
                    personagem.getAccountInformation().setTitle(listaDeElementos.get(i + 2));
                    
                    personagem.getAccountInformation().setDateCreate(listaDeElementos.get(i + 4));
                } else if (listaDeElementos.get(i).equals("Character Deaths")) {
                    int j = 1;
                    
                    while (!listaDeElementos.get(i + j).equals("Account Information") && !listaDeElementos.get(i + j).equals("Search Character")){
                        personagem.getDeaths().add(listaDeElementos.get(i + j));
                        j++;
                    }
                } /*else if (listaDeElementos.get(i).equals("Characters")) {
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

            System.out.println(listaDeElementos);

        } catch (IOException e) {
            System.out.println("Erro na conexão Jsoup: " + e);
        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("Url inválida: " + e);
        }

        return personagem;
    }
}
