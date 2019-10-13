package model.character;

import java.util.Calendar;


public class Comment {
    private String comment;
    private Calendar dateStart;
    private Calendar dateLeave;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public Calendar getDateLeave() {
        return dateLeave;
    }

    public void setDateLeave(Calendar dateLeave) {
        this.dateLeave = dateLeave;
    }
    
    
}
