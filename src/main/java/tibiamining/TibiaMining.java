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

        new CheckRank().checkGlobalRank();
        
    }

}
