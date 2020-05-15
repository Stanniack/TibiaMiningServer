/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import regrasdenegocio.CheckRank;
import utils.PopDatabaseUtil;

public class Biridin {

    public static void main(String[] args) throws IOException {

        //List<Integer> list = new LevelAdvanceDAO().returnAllIds();

        //System.out.println(list.toString());
        
        for (LevelAdvance la : new LevelAdvanceDAO().returnAllRegisterById(1)) {
            System.out.println();
        }
    }

}
