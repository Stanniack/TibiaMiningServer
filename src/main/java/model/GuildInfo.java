package model;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class GuildInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGuildInfo;

    private String guildName;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateBegin;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateEnd;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar lastUpdate;

    public GuildInfo() {
    }

    public GuildInfo(String guildName, Calendar dateBegin, Calendar lastUpdate) {
        this.guildName = guildName;
        this.dateBegin = dateBegin;
        this.lastUpdate = lastUpdate;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public Calendar getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Calendar dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
