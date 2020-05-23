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
import model.FormerName;
import model.LevelAdvance;
import model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckHouse;
import regrasdenegocio.CheckKillStatistics;
import regrasdenegocio.CheckOnline;
import regrasdenegocio.CheckRank;
import utils.DateUtil;
import utils.PopDatabaseUtil;
import utils.TibiaUtil;

public class Biridin {

    public static void main(String[] args) {
        new CheckKillStatistics().getBossesStatistics();
    }

}
