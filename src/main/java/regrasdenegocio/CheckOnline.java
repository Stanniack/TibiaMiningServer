package regrasdenegocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.WorldsTibiaUtil;

public class CheckOnline {

    private List<String> listCharacters = new ArrayList<>();

    /* Elemento da lista que contém os players online */
    private final int PLAYERS_CONTENT = 2;
    /* Início do conteúdo que procuro */
    private final int START_CONTENT = 2;
    /* Eliminação de elemtentos na lista indesejáveis */
    private final int PLAYER_INCREMENTOR = 3;

    public synchronized void checkOnline() {
        int novos;
        List<String> worlds = WorldsTibiaUtil.getWorldsTibia();

        while (true) {

            for (int i = 0; i < worlds.size(); i++) {
                String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + worlds.get(i);
                novos = 0;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Erro na thread" + e);
                }

                try {

                    Document arquivoHtml = Jsoup.connect(url).get();

                    Elements elementosODD = arquivoHtml.getElementsByClass("Odd");
                    Elements elementosEven = arquivoHtml.getElementsByClass("Even");

                    for (Element elemento : elementosODD) {
                        String text = elemento.getElementsByTag("tr").text();

                        /* Remove o level e a vocação da string */
                        text = text.replaceAll("[0-9]{3}", "");
                        text = text.replaceAll("[0-9]{2}", "");
                        text = text.replaceAll("[0-9]{1}", "");
                        text = text.replace("Elite Knight", "");
                        text = text.replace("Elder Druid", "");
                        text = text.replace("Master Sorcerer", "");
                        text = text.replace("Royal Paladin", "");
                        text = text.replace("Knight", "");
                        text = text.replace("Druid", "");
                        text = text.replace("Sorcerer", "");
                        text = text.replace("paladin", "");

                        /* Adiciona na lista o resultado*/
                        if (!listCharacters.toString().contains(text)) {
                            listCharacters.add(text);
                            novos++;
                        }

                    }

                    for (Element elemento : elementosEven) {
                        String text = elemento.getElementsByTag("tr").text();

                        /* Remove o level e a vocação da string */
                        text = text.replaceAll("[0-9]{3}", "");
                        text = text.replaceAll("[0-9]{2}", "");
                        text = text.replaceAll("[0-9]{1}", "");
                        text = text.replace("Elite Knight", "");
                        text = text.replace("Elder Druid", "");
                        text = text.replace("Master Sorcerer", "");
                        text = text.replace("Royal Paladin", "");
                        text = text.replace("Knight", "");
                        text = text.replace("Druid", "");
                        text = text.replace("Sorcerer", "");
                        text = text.replace("paladin", "");

                        /* Adiciona na lista o resultado*/
                        if (!listCharacters.toString().contains(text)) {
                            listCharacters.add(text);
                            novos++;
                        }

                    }

                    if (novos != 0) {
                        System.out.println(novos + " novos personagens.");
                        /* Regra de persistência aqui */
                    }

                    System.out.println("Servidor: " + worlds.get(i));

                } catch (IOException e) {
                    System.out.println("Erro na execução." + e);
                }

            } // fim for worlds

        }

    }

    public void getOnlinePlayers() {
        String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + "Antica";

        try {

            Document contentElements = Jsoup.connect(url).get();
            Elements chosenElements = contentElements.getElementsByClass("InnerTableContainer");

            List<String> elementsList = chosenElements.get(PLAYERS_CONTENT).getElementsByTag("td").eachText();
            System.out.println(elementsList.toString());

        } catch (IOException ex) {
        }
    }
}
