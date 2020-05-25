package businessrules;

import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CheckGuild {

    public void getInfoGuilds(List<String> worlds) {

        try {

            String url = "https://www.tibia.com/community/?subtopic=guilds&world=Zuna";

            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("TableContentContainer");
            List<String> elementsList = elements.get(0).getElementsByTag("b").eachText();
            System.out.println(elementsList.size());
            elementsList.forEach(System.out::println);
            

        } catch (IOException ex) {
            System.out.println("Error - CheckGuild: " + ex);
        }
        
    }
}
