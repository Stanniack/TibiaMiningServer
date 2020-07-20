package tibiamining;

import DAO.AbstractDAO;
import DAO.HouseInfoDAO;
import DAO.LevelAdvanceDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.persistence.NoResultException;
import model.Boss;
import model.FormerName;
import model.LevelAdvance;
import model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import businessrules.CheckCharacter;
import businessrules.CheckHouse;
import businessrules.CheckKillStatistics;
import businessrules.CheckOnline;
import businessrules.CheckRank;
import utils.DateUtil;
import utils.PopDatabaseUtil;
import utils.TibiaUtil;

public class Biridin {

    public static void main(String[] args) {
        new CheckRank().getRankLoyalty(TibiaUtil.getAllWorlds());
        
    }

}
