package regrasdenegocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.PopulaMundos;
import model.CharacterRank;

public class RankGlobal {

    // Conteúdo na lista começa no índice 5
    private static final int CONTENT_START = 5;
    // O último elemento da lista é lixo
    private static final int TRASH_ELIMINATOR = 1;
    // O incrementador é 5 devido aos atributos de cada char
    private static final int INCREMENTOR = 5;

    private static final int PROFESSION = 4;
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = 12;
    private static final int TOTAL_WORLDS = 62;
    private static final int NAME = 1;
    private static final int VOCATION = 2;
    private static final int LEVEL = 3;
    private static final int POINTS = 4;

    public void rankGlobal() {
        Long tempoInicial = System.currentTimeMillis();
        List<String> listaDeElementos = new ArrayList<>();
        List<CharacterRank> listaDeChars = new ArrayList<>();
        List<String> mundos = PopulaMundos.populaMundos();

        for (int i = 0; i < TOTAL_WORLDS; i++) {

            Long tempoInicialPorMundo = System.currentTimeMillis();

            for (int n = 1; n <= PROFESSION; n++) {
                Long tempoInicialPorProfissao = System.currentTimeMillis();

                for (int j = FIRST_PAGE; j <= LAST_PAGE; j++) {
                    String url = "https://www.tibia.com/community/?subtopic=highscores&world=" + mundos.get(i) + "&list=experience&profession=" + n + "&currentpage=" + j;

                    try {

                        Document arquivoHtml = Jsoup.connect(url).get();
                        Elements elementosTagEscolhida = arquivoHtml.getElementsByClass("TableContent");

                        for (Element elemento : elementosTagEscolhida) {
                            listaDeElementos = elemento.getElementsByTag("td").eachText();
                            //System.out.println(listaDeElementos);

                        }

                    } catch (IOException e) {
                        System.out.println("Erro na execução." + e);
                    }

                    for (int k = CONTENT_START; k < listaDeElementos.size() - INCREMENTOR; k += 5) {
                        // Tiras as vírgulas da exp
                        String valorConvertido = listaDeElementos.get(k + POINTS).replace(",", "");
                        Long valorCvtLong = Long.valueOf(valorConvertido);

                        listaDeChars.add(new CharacterRank(
                                listaDeElementos.get(k),
                                listaDeElementos.get(k + NAME),
                                listaDeElementos.get(k + VOCATION),
                                listaDeElementos.get(k + LEVEL),
                                valorCvtLong,
                                mundos.get(i))
                        );
                    }

                } // for das páginas

                Long tempoFinalPorProfissao = System.currentTimeMillis();
                System.out.println("A profissão " + n + " foi minerada com "
                        + ((tempoFinalPorProfissao - tempoInicialPorProfissao) / 1000) + " segundos.");
            } // for das profissões

            Long tempoFinalPorMundo = System.currentTimeMillis();
            System.out.println("O servidor de " + mundos.get(i) + " gastou " + ((tempoFinalPorMundo - tempoInicialPorMundo) / 1000)
                    + " segundos para ser minerado");
        } // for dos mundos

        Long tempoFinal = System.currentTimeMillis();
        System.out.println("O tempo total para minerar todos os servidores foi de " + ((tempoFinal - tempoInicial) / 1000)
                + " segundos");

        /*  */
        this.exibeConteudo(listaDeChars);

    }// fim do metodo

    private void exibeConteudo(List<CharacterRank> lista) {
        
        System.out.println("___________________________________________________");
        Collections.sort(lista);
        
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " " + lista.get(i).getName() + " " + lista.get(i).getLevel() + " "
                    + lista.get(i).getWorld());

        }
    }

}
