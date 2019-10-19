package regrasdenegocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.PopulaMundos;

public class CheckOnline {

    private List<String> listaDeChar = new ArrayList<>();

    public synchronized void checkOnline() {
        int novos;
        List<String> mundos = PopulaMundos.populaMundos();

        while (true) {
            
            for (int i = 0; i < PopulaMundos.populaMundos().size(); i++) {
                String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + mundos.get(i);
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
                        if (!listaDeChar.toString().contains(text)) {
                            /* Regras de persistência aqui */
                            
                            listaDeChar.add(text);
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
                        if (!listaDeChar.toString().contains(text)) {
                            listaDeChar.add(text);
                            novos++;
                        }

                    }

                    if (novos != 0) {
                        System.out.println(novos + " novos personagens.");
                    }

                    System.out.println("Servidor: " + mundos.get(i));

                } catch (IOException e) {
                    System.out.println("Erro na execução." + e);
                }
            }

        }

    }
}
