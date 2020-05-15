package tibiamining;

import DAO.AbstractDAO;
import DAO.LevelAdvanceDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import model.CharacterSkills;
import model.FormerName;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckRank;
import utils.DateUtil;
import utils.WorldsTibiaUtil;

public class TibiaMining {

    public static void main(String[] args) throws IOException {

        List<String> elementsList = Arrays.asList("1", "Creisim", "Elder Druid", "941", "102");

        new CheckRank().checkGlobalRankExperience(elementsList);
    }

}
