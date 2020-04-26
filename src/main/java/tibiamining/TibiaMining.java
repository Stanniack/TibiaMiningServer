package tibiamining;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        
        Long startTime = System.currentTimeMillis();

        Long expStartTime = System.currentTimeMillis();
        new CheckRank().checkGlobalRankExperience();
        Long expFinalTime = System.currentTimeMillis();
        System.out.println((expFinalTime - expStartTime)/1000);
        
        Long loyaltyStartTime = System.currentTimeMillis();
        new CheckRank().checkGlobalRankLoyalty();
        Long loyaltyFinalTime = System.currentTimeMillis();
        System.out.println((loyaltyFinalTime - loyaltyStartTime)/1000);
        
        Long skillsStartTime = System.currentTimeMillis();
        new CheckRank().checkGlobalRankSkills();
        Long skillsFinalTime = System.currentTimeMillis();
        System.out.println((skillsFinalTime - skillsStartTime)/1000);
        
        Long finalTime = System.currentTimeMillis();
        
        System.out.println((finalTime - startTime)/1000);
    }

}
