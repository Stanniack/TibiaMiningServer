package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PlayersOnline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayersOnline;

    @Temporal(TemporalType.DATE)
    private Calendar lastUpdate;

    private String hourAndMinute;
    private int online;

    public PlayersOnline() {
    }

    public PlayersOnline(Calendar lastUpdate, String hourAndMinute, int online) {
        this.lastUpdate = lastUpdate;
        this.online = online;
        this.hourAndMinute = hourAndMinute;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getHourAndMinute() {
        return hourAndMinute;
    }

    public void setHourAndMinute(String hourAndMinute) {
        this.hourAndMinute = hourAndMinute;
    }
    
    

}
