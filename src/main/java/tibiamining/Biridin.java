/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tibiamining;

import DAO.AbstractDAO;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import model.Personagem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckRank;

/**
 *
 * @author Mateus
 */
public class Biridin {

    public static void main(String[] args) throws IOException {

        //new CheckRank().rankGlobalExperience();
        String url = "https://www.tibia.com/community/?subtopic=highscores&world="
                + "Antica" + "&list=experience&profession=" + "1" + "&currentpage=" + "1";

        Document arquivoHtml = Jsoup.connect(url).get();
        List<String> listaDeElementos = arquivoHtml.getElementsByTag("td").eachText();

        for (int j = 14; j < listaDeElementos.size() - 1; j += 5) {
            
            String valorConvertido = listaDeElementos.get(j + 4).replace(",", "");
            Long valorCvtLong = Long.valueOf(valorConvertido);
            
            System.out.println(listaDeElementos.get(j + 1));
            System.out.println(listaDeElementos.get(j + 2));
            System.out.println(listaDeElementos.get(j + 3));
            System.out.println(listaDeElementos.get(j + 4));
            System.out.println(valorCvtLong);
            System.out.println("");
        }

    }

}
