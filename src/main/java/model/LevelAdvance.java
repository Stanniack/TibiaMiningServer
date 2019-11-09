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
public class LevelAdvance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_levelAdvance;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    private Double expDay;
    private int levelDay;

    @Temporal(TemporalType.DATE)
    private Calendar dayAdvance;

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

    public Calendar getDayAdvance() {
        return dayAdvance;
    }

    public void setDayAdvance(Calendar dayAdvance) {
        this.dayAdvance = dayAdvance;
    }

}
