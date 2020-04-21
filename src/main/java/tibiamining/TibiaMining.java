package tibiamining;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.util.Arrays;
import java.util.List;
import model.FormerName;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Personagem;

public class TibiaMining {

    public static void main(String[] args) {

        List<String> housesNovas = Arrays.asList("Thais");
        List<String> housesVelhas = Arrays.asList("Venore");
        
        for (String houseVelha : housesVelhas) {
            if (!housesNovas.toString().contains(houseVelha)) {
                System.out.println("Deixou de ter: " + houseVelha);
            } 
        }


    }

}
