package tibiamining;

import DAO.AbstractDAO;
import DAO.LevelAdvanceDAO;
import DAO.PlayerDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
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
import utils.PopDatabaseUtil;

public class Biridin {

    public static void main(String[] args) {
        new CheckOnline().getOnlinePlayers();

    }

}
