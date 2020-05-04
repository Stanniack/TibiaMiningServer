package tibiamining;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import model.CharacterSkills;
import model.FormerName;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Personagem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckRank;
import utils.MockWorldsTibia;

public class TibiaMining {

    public static void main(String[] args) throws IOException {

        List<String> lista = Arrays.asList("Wynchesther", "Kommander", "Bobeek", 
                "Goraca", "Kharsek", "Mateusz Dragon Wielki", "Eternal Oblivion", "Bubble", "Arieswar", "Wyn");
        
        
        new CheckCharacter().discoverCharacter("Panzt");

        Long startTime = System.currentTimeMillis();        
        lista.forEach((item) -> {
            try {
                new CheckCharacter().getNick("Panzt");
            } catch (IOException ex) {
                Logger.getLogger(TibiaMining.class.getName()).log(Level.SEVERE, null, ex);
            }
            new CheckCharacter().discoverCharacter(item);
        });
        Long finalTime = System.currentTimeMillis();

        System.out.println((finalTime - startTime) / 1000);
    }

}
