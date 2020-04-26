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
    private Integer idLevelAdvance;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    private Long expDay;
    private int levelDay;

    @Temporal(TemporalType.DATE)
    private Calendar dayAdvance;

    public LevelAdvance() {
    }

    public LevelAdvance(Personagem personagem, Long expDay, int levelDay, Calendar dayAdvance) {
        this.personagem = personagem;
        this.expDay = expDay;
        this.levelDay = levelDay;
        this.dayAdvance = dayAdvance;
    }

    public LevelAdvance(Personagem personagem, int levelDay, Calendar dayAdvance) {
        this.personagem = personagem;
        this.levelDay = levelDay;
        this.dayAdvance = dayAdvance;
    }

    public Long getExpDay() {
        return expDay;
    }

    public void setExpDay(Long expDay) {
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
