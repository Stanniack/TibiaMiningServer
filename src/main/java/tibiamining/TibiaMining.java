package tibiamining;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import model.FormerName;
import model.LevelAdvance;
import model.Personagem;

public class TibiaMining {

    public static void main(String[] args) {

       // new AbstractDAO<>(Personagem.class).remove(13);
       // LevelAdvance r = new AbstractDAO<>(LevelAdvance.class).searchLastRegisterById(4, "levelday");
        //Long r = new AbstractDAO<>(FormerName.class).countRegistersById(4);
       Personagem r = new PersonagemDAO().returnCharacterByName("Panzt");
        
        System.out.println(r.getIdCharacter());

    }

}
