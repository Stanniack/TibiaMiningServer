package tibiamining;

import DAO.AbstractDAO;
import DAO.GuildDAO;
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
import model.GuildInfo;
import utils.DateUtil;
import utils.PopDatabaseUtil;
import utils.TibiaUtil;

public class TibiaMining {

    public static void main(String[] args) {

        List<String> guilds = new GuildDAO().listAll("Zuna");

        List<String> list = Arrays.asList(
                "Alt Eff Four",
                "Evil Geniuses",
                "Highheels",
                "Noname",
                "Paladin Syndicate",
                "Zuna Lovers",
                "Zunera Cartel"
        );

        new CheckGuild().getInfoGuilds(TibiaUtil.getAllWorlds(), list);
    }

}
