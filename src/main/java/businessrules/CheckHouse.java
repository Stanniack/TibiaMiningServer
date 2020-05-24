package businessrules;

import DAO.AbstractDAO;
import DAO.HouseInfoDAO;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import model.HouseInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.TibiaUtil;

public class CheckHouse {

    private final int CONTENT_START = 6;
    private final int TRASH_ELIMINATOR = 10;
    private final int INCREMENTOR = 4;

    /* Atributos*/
    private final int NAME = 0;
    private final int SQM = 1;
    private final int RENT = 2;
    private final int STATUS = 3;

    /* Adiciona e atualiza as casas */
    public void getHouses(List<String> worlds) {

        Long start = System.currentTimeMillis();

        for (String world : worlds) {

            for (String city : TibiaUtil.getCities()) {

                for (String houseType : TibiaUtil.houseType()) {

                    try {

                        String url = "https://www.tibia.com/community/?subtopic=houses&world=" + world
                                + "&town=" + city + "&type=" + houseType;

                        Document htmlContent = Jsoup.connect(url).get();
                        List<String> elementsList = htmlContent.getElementsByTag("td").eachText();

                        for (int i = CONTENT_START; i < elementsList.size() - TRASH_ELIMINATOR; i += INCREMENTOR) {

                            HouseInfo houseInfo = new HouseInfoDAO().returnHouseInfoByName(elementsList.get(i + NAME), world);

                            if (houseInfo != null) {

                                /* Verifica atualizações */
                                if (!houseInfo.getHouseName().equals(elementsList.get(i + NAME))
                                        || !houseInfo.getSqm().equals(elementsList.get(i + SQM))
                                        || !houseInfo.getRent().equals(elementsList.get(i + RENT))
                                        || !houseInfo.getStatus().equals(elementsList.get(i + STATUS))) {

                                    new AbstractDAO<>(HouseInfo.class)
                                            .insert(new HouseInfo(
                                                    elementsList.get(i + NAME),
                                                    elementsList.get(i + SQM),
                                                    elementsList.get(i + RENT),
                                                    elementsList.get(i + STATUS),
                                                    world,
                                                    city,
                                                    houseType,
                                                    Calendar.getInstance())
                                            );
                                    
                                    System.out.println(elementsList.get(i + NAME));  
                                }

                            } else {

                                new AbstractDAO<>(HouseInfo.class)
                                        .insert(new HouseInfo(
                                                elementsList.get(i + NAME),
                                                elementsList.get(i + SQM),
                                                elementsList.get(i + RENT),
                                                elementsList.get(i + STATUS),
                                                world,
                                                city,
                                                houseType,
                                                Calendar.getInstance())
                                        );
                            }

                        } // fim for da página

                    } catch (IOException ex) {
                        System.out.println("Error CheckHouse: " + ex);
                    }

                }
            }

            System.out.println(world);
        }

        System.out.println((System.currentTimeMillis() - start) / 1000 + " secs");
    }

}
