package model.character;

import java.util.Calendar;

public class House {
    private String houseName;
    private Calendar dateBid;
    private Calendar dateLeave;

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Calendar getDateBid() {
        return dateBid;
    }

    public void setDateBid(Calendar dateBid) {
        this.dateBid = dateBid;
    }

    public Calendar getDateLeave() {
        return dateLeave;
    }

    public void setDateLeave(Calendar dateLeave) {
        this.dateLeave = dateLeave;
    }
    
    
}
