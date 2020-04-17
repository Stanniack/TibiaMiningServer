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
public class House implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHouse;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    private String houseName;

    @Temporal(TemporalType.DATE)
    private Calendar dateBid;
    @Temporal(TemporalType.DATE)
    private Calendar dateLeave;

    public House() {
    }

    public House(Personagem personagem, String houseName, Calendar dateBid) {
        this.personagem = personagem;
        this.houseName = houseName;
        this.dateBid = dateBid;
    }

    public House(Personagem personagem, String houseName, Calendar dateBid, Calendar dateLeave) {
        this.personagem = personagem;
        this.houseName = houseName;
        this.dateBid = dateBid;
        this.dateLeave = dateLeave;
    }

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
