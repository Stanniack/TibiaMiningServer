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

    private String guildName;
    private String memberPositionGuild;

    @Temporal(TemporalType.DATE)
    private Calendar dateBegin;
    @Temporal(TemporalType.DATE)
    private Calendar dateLeave;

    public Guild() {
    }

    public Guild(Personagem personagem, String guildName, String memberPositionGuild, Calendar dateBegin) {
        this.personagem = personagem;
        this.guildName = guildName;
        this.memberPositionGuild = memberPositionGuild;
        this.dateBegin = dateBegin;
    }

    public Guild(Personagem personagem, String guildName, String memberPositionGuild, Calendar dateBegin, Calendar dateLeave) {
        this.personagem = personagem;
        this.guildName = guildName;
        this.memberPositionGuild = memberPositionGuild;
        this.dateBegin = dateBegin;
        this.dateLeave = dateLeave;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String nameGuild) {
        this.guildName = nameGuild;
    }

    public String getMemberPositionGuild() {
        return memberPositionGuild;
    }

    public void setMemberPositionGuild(String memberPositionGuild) {
        this.memberPositionGuild = memberPositionGuild;
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
