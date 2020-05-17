package tibiamining;

import DAO.AbstractDAO;
import DAO.LevelAdvanceDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
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
import regrasdenegocio.CheckOnline;
import regrasdenegocio.CheckRank;
import utils.DateUtil;
import utils.PopDatabaseUtil;

public class Biridin {

    public static void main(String[] args) {
        System.out.println(DateUtil.getHourAndMinute());
        Set<String> t = new LinkedHashSet<>();
        Set<String> y = new LinkedHashSet<>();

        t.add("1");
        y.add("2");
        y.add("2");
        t.addAll(y);

        System.out.println(t.toString());

    }

}
