package utils;

import DAO.AbstractDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Boss;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TibiaUtil {

    public static List<String> getSkills() {
        List<String> skills = Arrays.asList(
                "2",
                "3",
                "4",
                "5",
                "7",
                "8",
                "9",
                "11",
                "12",
                "13"
        );

        return skills;
    }

    public static List<String> getAllWorlds() {
        List<String> worlds = Arrays.asList(
                "Adra",
                "Antica",
                "Assombra",
                "Astera",
                "Belobra",
                "Belluma",
                "Bona",
                "Calmera",
                "Carnera",
                "Celebra",
                "Celesta",
                "Cosera",
                "Concorda",
                "Damora",
                "Descubra",
                "Dibra",
                "Duna",
                "Epoca",
                "Emera",
                "Estela",
                "Faluna",
                "Ferobra",
                "Firmera",
                "Funera",
                "Furia",
                "Garnera",
                "Gentebra",
                "Gladera",
                "Harmonia",
                "Helera",
                "Honbra",
                "Impera",
                "Inabra",
                "Javibra",
                "Jonera",
                "Kalibra",
                "Kenora",
                "Libertabra",
                "Lobera",
                "Luminera",
                "Lutabra",
                "Macabra",
                "Menera",
                "Mitigera",
                "Monza",
                "Nefera",
                "Noctera",
                "Nossobra",
                "Olera",
                "Ombra",
                "Pacera",
                "Pacembra",
                "Peloria",
                "Premia",
                "Pyra",
                "Quelibra",
                "Quintera",
                "Refugia",
                "Ragna",
                "Relania",
                "Relembra",
                "Secura",
                "Serenebra",
                "Serdebra",
                "Solidera",
                "Talera",
                "Tortura",
                "Torpera",
                "Unica",
                "Utobra",
                "Venebra",
                "Vita",
                "Vunira",
                "Wintera",
                "Wizera",
                "Xandebra",
                "Xylona",
                "Yonabra",
                "Ysolera",
                "Zenobra",
                "Zuna",
                "Zunera"
        );

        return worlds;
    }

    public static List<String> getSouthAmericaWorlds() {
        List<String> worlds = Arrays.asList(
                "Assombra",
                "Belobra",
                "Celebra",
                "Descubra",
                "Dibra",
                "Ferobra",
                "Gentebra",
                "Honbra",
                "Inabra",
                "Javibra",
                "Kalibra",
                "Libertabra",
                "Lutabra",
                "Macabra",
                "Nossobra",
                "Ombra",
                "Pacembra",
                "Quelibra",
                "Relembra",
                "Serdebra",
                "Serenebra",
                "Venebra",
                "Utobra",
                "Yonabra",
                "Xandebra",
                "Zenobra"
        );

        return worlds;
    }

    public static List<String> getNorthAmericaWorlds() {
        List<String> worlds = Arrays.asList(
                "Astera",
                "Calmera",
                "Carnera",
                "Cosera",
                "Emera",
                "Firmera",
                "Funera",
                "Garnera",
                "Gladera",
                "Helera",
                "Impera",
                "Jonera",
                "Lobera",
                "Luminera",
                "Menera",
                "Mitigera",
                "Nefera",
                "Noctera",
                "Olera",
                "Pacera",
                "Quintera",
                "Solidera",
                "Talera",
                "Torpera",
                "Wintera",
                "Wizera",
                "Ysolera",
                "Zunera"
        );

        return worlds;
    }

    public static List<String> getEuropeWorlds() {
        List<String> worlds = Arrays.asList(
                "Adra",
                "Antica",
                "Belluma",
                "Bona",
                "Celesta",
                "Concorda",
                "Damora",
                "Duna",
                "Epoca",
                "Estela",
                "Faluna",
                "Furia",
                "Harmonia",
                "Kenora",
                "Monza",
                "Peloria",
                "Premia",
                "Pyra",
                "Ragna",
                "Refugia",
                "Relania",
                "Secura",
                "Tortura",
                "Unica",
                "Vita",
                "Vunira",
                "Xylona",
                "Zuna"
        );

        return worlds;
    }

    public static List<String> getCities() {
        List<String> cities = Arrays.asList(
                "Ab'Dendriel",
                "Ankrahmun",
                "Carlin",
                "Darashia",
                "Edron",
                "Farmine",
                "Gray Beach",
                "Issavi",
                "Kazordoon",
                "Liberty Bay",
                "Port Hope",
                "Rathleton",
                "Svargrond",
                "Thais",
                "Venore",
                "Yalahar"
        );

        return cities;
    }

    public static List<String> houseType() {
        List<String> types = Arrays.asList(
                "houses",
                "guildhalls"
        );

        return types;
    }

    /* Bosses que devem ser ignorados na captura */
    public static List<String> getIgnoredBosses() {
        List<String> taskBosses = Arrays.asList(
                "The Snapper",
                "Hide",
                "Deathbine",
                "The Bloodtusk",
                "Shardhead",
                "Esmeralda",
                "Fleshcrawler",
                "Ribstride",
                "The Bloodweb",
                "Thul",
                "The Old Widow",
                "Hemming",
                "Tormentor",
                "Flameborn",
                "Fazzrah",
                "Tromphonyte",
                "Sulphur Scuttler",
                "Bruise Payne",
                "The Many",
                "Paiz the Pauperizer",
                "Bretzecutioner",
                "The Noxious Spawn",
                "Gorgo",
                "Stonecracker",
                "Leviathan",
                "Kerberos",
                "Ethershreck",
                "Zanakeph",
                "Tiquandas Revenge",
                "Demodras"
        );

        return taskBosses;
    }

    /* Pega as informações de todos os bosses no Guildstats - Deve ser rodado apenas 1x antes de começar a popular o bd */
    public static List<String> getAllBosses() {

        List<String> bosses = new ArrayList<>();
        int CONTENT_START = 2;
        int INCREMENTOR = 8;
        int BOSS = 1;

        try {

            String url = "https://guildstats.eu/bosses?lang=pt";
            Document htmlContent = Jsoup.connect(url).get();
            Element element = htmlContent.getElementById("myTable");
            List<String> elementsList = element.getElementsByTag("td").eachText();

            for (int i = CONTENT_START; i < elementsList.size(); i += INCREMENTOR) {
                new AbstractDAO<>(Boss.class).insert(new Boss(elementsList.get(i + BOSS)));
            }

        } catch (IOException ex) {
            System.out.println("Error TibiaUtil - getAllBosses: " + ex);
        }

        return bosses;
    }
}
