package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AchievementPoints implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAchievementPoints;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    private Integer achievementPoints;

    @Temporal(TemporalType.DATE)
    private Calendar registerDate;

    public AchievementPoints() {
    }

    public AchievementPoints(Personagem personagem, Integer achievmentPoints, Calendar registerDate) {
        this.personagem = personagem;
        this.achievementPoints = achievmentPoints;
        this.registerDate = registerDate;
    }

    public Integer getAchievmentPoints() {
        return achievementPoints;
    }

    public void setAchievmentPoints(Integer achievmentPoints) {
        this.achievementPoints = achievmentPoints;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

}
