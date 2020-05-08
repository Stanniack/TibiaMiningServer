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

public class Biridin {

    public static void main(String[] args) throws IOException {

        List<LevelAdvance> list = new AbstractDAO<>(LevelAdvance.class).listAll();
        
        /* Testar update do método checkglobalrank */
        /* Verificar se o playerName do l.a existe na tabela de chars, 
         * se sim, atribui objeto ao char. Verificar no formerNames também e atualizar o playerName do l.a*/
        for (LevelAdvance la : list) {

            Player p = new PlayerDAO().returnCharacterByName(la.getPlayerName());

            /* Regras de negócio para vincular o LevelAdvance com o char pertencente */
            if (p != null) {
                la.setPlayer(p);
                new AbstractDAO<>(LevelAdvance.class).update(la);

            } else {
                FormerName pFN = new PlayerDAO().returnFormerNameByOldName(la.getPlayerName());

                if (pFN != null) {    
                    la.setPlayerName(pFN.getPlayer().getPlayerName());
                    la.setPlayer(pFN.getPlayer());
                    new AbstractDAO<>(LevelAdvance.class).update(la);
                }
            }
        }
    }

}
