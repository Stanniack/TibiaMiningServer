package tibiamining;

import DAO.AbstractDAO;
import model.Personagem;

public class TibiaMining {

    public static void main(String[] args) {

        Personagem c = new Personagem();
        c.setName("teste2");

        new AbstractDAO<>(Personagem.class).insert(c);

    }

}
