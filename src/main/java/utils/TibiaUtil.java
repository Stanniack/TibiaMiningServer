package utils;

import java.util.Arrays;
import java.util.List;

public class TibiaUtil {

    public static List<String> getSkills() {
        List<String> skills = Arrays.asList(
                "axe",
                "club",
                "distance",
                "fishing",
                "fist",
                "magic",
                "shielding",
                "sword"
        );

        return skills;
    }

    public static List<String> getWorlds() {
        List<String> worlds = Arrays.asList(
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
                "Venebra",
                "Vita",
                "Vunira",
                "Wintera",
                "Xandebra",
                "Zuna",
                "Zunera"
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
}
