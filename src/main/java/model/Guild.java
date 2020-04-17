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
public class Guild implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGuild;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    private String nameGuild;
    private String positionMemberGuild;

    @Temporal(TemporalType.DATE)
    private Calendar dateBegin;
    @Temporal(TemporalType.DATE)
    private Calendar dateLeave;

    public Guild() {
    }

    public Guild(Personagem personagem, String nameGuild, String positionMemberGuild, Calendar dateBegin) {
        this.personagem = personagem;
        this.nameGuild = nameGuild;
        this.positionMemberGuild = positionMemberGuild;
        this.dateBegin = dateBegin;
    }

    public Guild(Personagem personagem, String nameGuild, String positionMemberGuild, Calendar dateBegin, Calendar dateLeave) {
        this.personagem = personagem;
        this.nameGuild = nameGuild;
        this.positionMemberGuild = positionMemberGuild;
        this.dateBegin = dateBegin;
        this.dateLeave = dateLeave;
    }

    public String getNameGuild() {
        return nameGuild;
    }

    public void setNameGuild(String nameGuild) {
        this.nameGuild = nameGuild;
    }

    public String getPositionMemberGuild() {
        return positionMemberGuild;
    }

    public void setPositionMemberGuild(String positionMemberGuild) {
        this.positionMemberGuild = positionMemberGuild;
    }

    public Calendar getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Calendar dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Calendar getDateLeave() {
        return dateLeave;
    }

    public void setDateLeave(Calendar dateLeave) {
        this.dateLeave = dateLeave;
    }

}
