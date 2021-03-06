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
public class FormerWorld implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFormerWorld;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Player player;

    private String world;

    @Temporal(TemporalType.DATE)
    private Calendar dateBegin;

    @Temporal(TemporalType.DATE)
    private Calendar dateEnd;

    public FormerWorld() {
    }

    public FormerWorld(Player player, String world, Calendar dateBegin) {
        this.player = player;
        this.world = world;
        this.dateBegin = dateBegin;
    }

    public FormerWorld(Player player, String world, Calendar dateBegin, Calendar dateLeave) {
        this.player = player;
        this.world = world;
        this.dateBegin = dateBegin;
        this.dateEnd = dateLeave;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
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
