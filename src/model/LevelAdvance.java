package model;




import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class LevelAdvance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_levelAdvance;
    
    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private CharacterTibia character;
    
    private Double expDay;
    private int levelDay;
    private Calendar day;

    public Double getExpDay() {
        return expDay;
    }

    public void setExpDay(Double expDay) {
        this.expDay = expDay;
    }

    public int getLevelDay() {
        return levelDay;
    }

    public void setLevelDay(int levelDay) {
        this.levelDay = levelDay;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }
    
    
    
}
