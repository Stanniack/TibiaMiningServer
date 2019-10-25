package model;



import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHouse;
    
    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private CharacterTibia character;
    
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
