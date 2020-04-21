package tibiamining;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.util.Arrays;
import java.util.List;
import javax.persistence.NoResultException;
import model.FormerName;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Personagem;
import regrasdenegocio.CheckCharacter;
import regrasdenegocio.CheckRank;
import utils.MockWorldsTibia;

public class TibiaMining {

    public static void main(String[] args) {
        
        
        System.out.println(new PersonagemDAO().returnID("zé"));

    }

}
