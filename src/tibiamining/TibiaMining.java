package tibiamining;

import characaterdata.AccountCharacters;
import characaterdata.CharacterT;

public class TibiaMining {

   
    public static void main(String[] args) {
        CharacterT c = new SearchCharacter().searchChar("Kharsek");
        //System.out.println(c);
        
        for (AccountCharacters a : c.getAccountCharacters()) {
            System.out.println(a.getNumber());
        }
    }
    
}
