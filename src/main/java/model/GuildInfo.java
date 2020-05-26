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
    private String world;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateBegin;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateEnd;

    public GuildInfo() {
    }

    public GuildInfo(String guildName, String world, Calendar dateBegin) {
        this.guildName = guildName;
        this.world = world;
        this.dateBegin = dateBegin;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
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

}
