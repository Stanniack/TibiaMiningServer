package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Death implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeaths;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    /* Altera o tamanho da string no bd */
    @Lob
    private String deathInfo;
    
    private String deathDate;

    public Death() {
    }

    public Death(Personagem personagem, String deathInfo, String deathDate) {
        this.personagem = personagem;
        this.deathInfo = deathInfo;
        this.deathDate = deathDate;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public String getDeathInfo() {
        return deathInfo;
    }

    public void setDeathInfo(String deathInfo) {
        this.deathInfo = deathInfo;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

}
