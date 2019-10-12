package regrasdenegocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.PopulaMundos;

/**
 *
 * @author Mateus Vitor Celestino
 */
public class RankServer {

    private String world = "Belobra";
    private final int firstIndex = 2;

    public void searchCharacters() {

        String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + this.world;
        List<String> listaDeElementos = new ArrayList<>();
        List<String> listaDePersonagens = new ArrayList<>();
        List<Integer> listaDeLevels = new ArrayList<>();

        try {
            Document arquivoHtml = Jsoup.connect(url).get();
            Elements elementosTagEscolhida = arquivoHtml.getElementsByClass("Table2");

            for (int i = 0; i < elementosTagEscolhida.size(); i++) {
                listaDeElementos = elementosTagEscolhida.get(i).getElementsByTag("tr").eachText();

            }

            //System.out.println(listaDeElementos.toString());
            for (int i = this.firstIndex; i < listaDeElementos.size(); i++) {

                if (listaDeElementos.get(i).contains("Elite Knight")) {
                    listaDePersonagens.add(listaDeElementos.get(i).replace("Elite Knight", "Knight"));

                } else if (listaDeElementos.get(i).contains("Royal Paladin")) {
                    listaDePersonagens.add(listaDeElementos.get(i).replace("Royal Paladin", "Paladin"));

                } else if (listaDeElementos.get(i).contains("Master Sorcerer")) {
                    listaDePersonagens.add(listaDeElementos.get(i).replace("Master Sorcerer", "Sorcerer"));

                } else {
                    listaDePersonagens.add(listaDeElementos.get(i).replace("Elder Druid", "Druid"));

                }

                //listaDePersonagens.add(listaDeElementos.get(i).replace(" ", ","));
            }

            String[] arrayDeItens = listaDePersonagens.toString().split(" ");
            //System.out.println(listaDePersonagens.size());

            for (int i = 0; i < arrayDeItens.length; i++) {

                try {
                    listaDeLevels.add(Integer.valueOf(arrayDeItens[i]));
                    //System.out.println(arrayDeItens[i]);
                } catch (Exception e) {

                }
            }

            Collections.sort(listaDeLevels);
            for (int i = listaDeLevels.size() - 1; i > 0; i--) {
                System.out.println(String.valueOf(Math.abs(i - listaDeLevels.size())) + ": " + listaDeLevels.get(i));
            }

        } catch (IOException e) {
            System.out.println("Erro de execução: " + e);
        }

        //System.out.println(listaDeElementos);
    }

    public void searchAllCharacters() {

        List<Integer> listaDeLevels = new ArrayList<>();

        for (int i = 0; i < PopulaMundos.populaMundos().size(); i++) {
            List<String> listaDeElementos = new ArrayList<>();
            List<String> listaDePersonagens = new ArrayList<>();
            Long timeI = System.currentTimeMillis();

            String url = "https://www.tibia.com/community/?subtopic=worlds&world=" + PopulaMundos.populaMundos().get(i);

            try {
                Document arquivoHtml = Jsoup.connect(url).get();
                Elements elementosTagEscolhida = arquivoHtml.getElementsByClass("Table2");

                for (int j = 0; j < elementosTagEscolhida.size(); j++) {
                    listaDeElementos = elementosTagEscolhida.get(j).getElementsByTag("tr").eachText();

                }

                //System.out.println(listaDeElementos.toString());
                for (int j = this.firstIndex; j < listaDeElementos.size(); j++) {

                    if (listaDeElementos.get(j).contains("Elite Knight")) {
                        listaDePersonagens.add(listaDeElementos.get(j).replace("Elite Knight", "Knight"));

                    } else if (listaDeElementos.get(j).contains("Royal Paladin")) {
                        listaDePersonagens.add(listaDeElementos.get(j).replace("Royal Paladin", "Paladin"));

                    } else if (listaDeElementos.get(j).contains("Master Sorcerer")) {
                        listaDePersonagens.add(listaDeElementos.get(j).replace("Master Sorcerer", "Sorcerer"));

                    } else {
                        listaDePersonagens.add(listaDeElementos.get(j).replace("Elder Druid", "Druid"));

                    }

                    //listaDePersonagens.add(listaDeElementos.get(i).replace(" ", ","));
                }

                String[] arrayDeItens = listaDePersonagens.toString().split(" ");
                //System.out.println(listaDePersonagens.size());

                for (int j = 0; j < arrayDeItens.length; j++) {

                    try {
                        listaDeLevels.add(Integer.valueOf(arrayDeItens[j]));
                        //System.out.println(arrayDeItens[j]);
                    } catch (Exception e) {

                    }
                }

                Long timeF = System.currentTimeMillis();

                System.out.println(((timeF - timeI) / 1000) + " segundos para " + PopulaMundos.populaMundos().get(i));

            } catch (IOException e) {
                System.out.println("Erro de execução: " + e);
            }

            //System.out.println(listaDeElementos);
        }

        Collections.sort(listaDeLevels);
        for (int i = listaDeLevels.size() - 1; i > 0; i--) {
            System.out.println(String.valueOf(Math.abs(i - listaDeLevels.size())) + ": " + listaDeLevels.get(i));
        }

    }

}
