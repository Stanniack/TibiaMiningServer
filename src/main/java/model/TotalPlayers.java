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
public class TotalPlayers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayersOnline;

    @Temporal(TemporalType.DATE)
    private Calendar lastUpdate;

    private int day;
    private int month;
    private int year;
    private DateType dateType;
    private int online;

    public TotalPlayers() {
    }

    public TotalPlayers(Calendar lastUpdate, DateType dateType, int online) {
        this.lastUpdate = lastUpdate;
        this.dateType = dateType;
        this.online = online;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public DateType getDateType() {
        return dateType;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

}
