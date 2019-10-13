
package model.character;

import java.util.Calendar;

public class Guild {
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
