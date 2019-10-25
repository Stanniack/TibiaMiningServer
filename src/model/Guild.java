package model;




import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Guild {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGuild;
    
    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private CharacterTibia character;
    
    private String nameGuild;
    private String positionMemberGuild;
    private Calendar dateBegin;
    private Calendar dateLeave;

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
