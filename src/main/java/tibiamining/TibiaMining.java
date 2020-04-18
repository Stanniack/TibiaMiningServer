package tibiamining;

import DAO.AbstractDAO;
import DAO.PersonagemDAO;
import java.util.List;
import model.FormerName;
import model.House;
import model.LevelAdvance;
import model.Personagem;

public class TibiaMining {

    public static void main(String[] args) {

        House h = new AbstractDAO<>(House.class).searchObjectByColumn("housename", "Market Street 2 (Venore)");
        
        System.out.println(h.getIdHouse());

    }

}
