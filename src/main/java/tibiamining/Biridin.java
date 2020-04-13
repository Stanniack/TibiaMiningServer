/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tibiamining;

import DAO.FormerNameDAO;
import DAO.PersonagemDAO;
import java.util.List;
import model.Personagem;
import regrasdenegocio.CheckOnline;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.RankGlobal;

/**
 *
 * @author Mateus
 */
public class Biridin {

    public static void main(String[] args) {

        //new discoverCharacter().discoverCharacter("Pa Blim");
        //System.out.println(new PersonagemDAO().returnID("teste2"));
        Personagem p = new CheckCharacter().discoverCharacter("Virallata");
        
//        System.out.println(new PersonagemDAO().returnID("dsdsdsds"));
//        System.out.println("t: " + new FormerNameDAO().listAll("2"));
//       

    }

}
