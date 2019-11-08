package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Death implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeaths;
    
    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;
    
    private String death;

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }
    
    
}
