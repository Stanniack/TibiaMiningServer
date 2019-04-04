package tibiamining;

import characaterdata.CharacterT;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TibiaMining {

   
    public static void main(String[] args) {
        CharacterT c = new SearchCharacter().searchChar("Kharsek");
        //System.out.println(c.getSharedExpMinLevel());
        //System.out.println(c.getSharedExpMaxLevel());
        
        for (String s : c.getLevelAdvances()) {
            System.out.println(s);
        }
        
      
        
        
        
        //System.out.println(c);
    }
    
}
