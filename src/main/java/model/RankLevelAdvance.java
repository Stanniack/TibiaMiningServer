package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/* Classe criada para capturar o levelAdvance dos personagens ranqueados, para evitar a demora na atualização */
@Entity
public class RankLevelAdvance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRankLevelAdvance;

    private Long expDay;
    private int levelDay;
    private String playerName;

    @Temporal(TemporalType.DATE)
    private Calendar lastUpdate;

    public RankLevelAdvance() {
    }

    public RankLevelAdvance(Long expDay, int levelDay, String playerName, Calendar lastUpdate) {
        this.expDay = expDay;
        this.levelDay = levelDay;
        this.playerName = playerName;
        this.lastUpdate = lastUpdate;
    }

    public Integer getIdRankLevelAdvance() {
        return idRankLevelAdvance;
    }

    public void setIdRankLevelAdvance(Integer idRankLevelAdvance) {
        this.idRankLevelAdvance = idRankLevelAdvance;
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

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
