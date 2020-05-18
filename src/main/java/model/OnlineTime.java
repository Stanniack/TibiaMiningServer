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

@Entity
public class OnlineTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOnlineTime;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Player player;

    private Long secondsOnline;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar lastUpdate;

    public OnlineTime() {
    }

    public OnlineTime(Player player, Long secondsOnline, Calendar lastUpdate) {
        this.player = player;
        this.secondsOnline = secondsOnline;
        this.lastUpdate = lastUpdate;
    }

    public Long getSecondsOnline() {
        return secondsOnline;
    }

    public void setSecondsOnline(Long secondsOnline) {
        this.secondsOnline = secondsOnline;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
