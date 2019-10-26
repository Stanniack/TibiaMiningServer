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

    private String achievmentPoints;
    
    @Temporal(TemporalType.DATE)
    private Calendar day;

    public String getAchievmentPoints() {
        return achievmentPoints;
    }

    public void setAchievmentPoints(String achievmentPoints) {
        this.achievmentPoints = achievmentPoints;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }
    
    

}
