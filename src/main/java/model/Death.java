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
    private Integer idDeath;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Player player;

    /* Altera o tamanho da string no bd */
    @Lob
    private String deathInfo;
    
    private String deathDate;

    public Death() {
    }

    public Death(Player player, String deathInfo, String deathDate) {
        this.player = player;
        this.deathInfo = deathInfo;
        this.deathDate = deathDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
