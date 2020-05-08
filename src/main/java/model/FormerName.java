package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FormerName implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFormerName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCharacter", nullable = true)
    private Player player;

    private String oldName;

    @Temporal(TemporalType.DATE)
    private Calendar dateBegin;
    
    @Temporal(TemporalType.DATE)
    private Calendar dateEnd;

    public FormerName() {
    }

    public FormerName(String oldName, Calendar dateBegin, Calendar dateEnd) {
        this.oldName = oldName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public FormerName(Player player, String oldName, Calendar dateBegin, Calendar dateEnd) {
        this.player = player;
        this.oldName = oldName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
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

    public Integer getIdFormerName() {
        return idFormerName;
    }

    public void setIdFormerName(Integer idFormerName) {
        this.idFormerName = idFormerName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
