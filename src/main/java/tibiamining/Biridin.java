/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tibiamining;

import DAO.AbstractDAO;
import DAO.LevelAdvanceDAO;
import DAO.PersonagemDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.persistence.NoResultException;
import model.LevelAdvance;
import model.Personagem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckRank;

/**
 *
 * @author Mateus
 */
public class Biridin {

    public static void main(String[] args) throws IOException {

        List<LevelAdvance> list = new AbstractDAO<>(LevelAdvance.class).listAll();
        
        /* Verificar se o playerName do l.a existe na tabela de chars, 
         * se sim, atribui objeto ao char. Verificar no formerNames também e atualizar o playerName do l.a*/

    }

}
