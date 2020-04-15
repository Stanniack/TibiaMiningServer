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
    private Integer id_achievementPoints;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    private Integer achievementPoints;

    @Temporal(TemporalType.DATE)
    private Calendar day;

    public AchievementPoints(Personagem personagem, Integer achievmentPoints, Calendar day) {
        this.personagem = personagem;
        this.achievementPoints = achievmentPoints;
        this.day = day;
    }

    public Integer getAchievmentPoints() {
        return achievementPoints;
    }

    public void setAchievmentPoints(Integer achievmentPoints) {
        this.achievementPoints = achievmentPoints;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

}
