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
import model.PlayerSkills;
import model.FormerName;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import businessrules.CheckCharacter;
import businessrules.CheckGuild;
import businessrules.CheckRank;
import utils.DateUtil;
import utils.PopDatabaseUtil;
import utils.TibiaUtil;

public class TibiaMining {

    public static void main(String[] args) {
//        new CheckGuild().getInfoGuilds(TibiaUtil.getAllWorlds());
new CheckCharacter().discoverCharacter("Wynchesther");
    }

}
