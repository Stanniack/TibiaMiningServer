package businessrules;

import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CheckGuild {
    
    private final int START_CONTENT = 3;

    public void getInfoGuilds(List<String> worlds) {

        try {

            String url = "https://www.tibia.com/community/?subtopic=guilds&world=Zuna";

            Document document = Jsoup.connect(url).get();
            Elements chosenElements = document.getElementsByClass("TableContentContainer");
            List<String> elementsList = chosenElements.get(0).getElementsByTag("b").eachText();
            
            for (int i = START_CONTENT; i < elementsList.size(); i++) {
                System.out.println(elementsList.get(i));
            }
            

        } catch (IOException ex) {
            System.out.println("Error - CheckGuild: " + ex);
        }
        
    }
}
