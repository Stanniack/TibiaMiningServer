package tibiamining;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.util.List;
import model.FormerName;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Personagem;

public class TibiaMining {

    public static void main(String[] args) {

        new AbstractDAO<>(Personagem.class).remove(1);


    }

}
