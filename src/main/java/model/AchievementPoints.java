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
    private Player player;

    private Integer achievementPoints;

    @Temporal(TemporalType.DATE)
    private Calendar registerDate;

    public AchievementPoints() {
    }

    public AchievementPoints(Player player, Integer achievementPoints, Calendar registerDate) {
        this.player = player;
        this.achievementPoints = achievementPoints;
        this.registerDate = registerDate;
    }

    public Integer getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(Integer achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

}
