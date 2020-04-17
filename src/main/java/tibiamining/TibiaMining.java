package tibiamining;

import DAO.AbstractDAO;
import model.FormerName;
import model.Personagem;

public class TibiaMining {

    public static void main(String[] args) {

       // new AbstractDAO<>(Personagem.class).remove(13);
        //FormerName fn = new AbstractDAO<>(FormerName.class).searchLastRegisterById(4);
        Long r = new AbstractDAO<>(FormerName.class).countRegistersById(4);
       
        System.out.println(r);

    }

}
