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
public class CharacterSkills implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRanking;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;
    
    @Temporal(TemporalType.DATE)
    private Calendar registerDate;

    private int axeFighting;
    private int clubFighting;
    private int distanceFighting;
    private int fishing;
    private int fistFighting;
    private int loyaltyPoints;
    private int magicLevel;
    private int shielding;
    private int swordFighting;

    public Integer getIdRanking() {
        return idRanking;
    }

    public void setIdRanking(Integer idRanking) {
        this.idRanking = idRanking;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public int getAxeFighting() {
        return axeFighting;
    }

    public void setAxeFighting(int axeFighting) {
        this.axeFighting = axeFighting;
    }

    public int getClubFighting() {
        return clubFighting;
    }

    public void setClubFighting(int clubFighting) {
        this.clubFighting = clubFighting;
    }

    public int getDistanceFighting() {
        return distanceFighting;
    }

    public void setDistanceFighting(int distanceFighting) {
        this.distanceFighting = distanceFighting;
    }

    public int getFishing() {
        return fishing;
    }

    public void setFishing(int fishing) {
        this.fishing = fishing;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getMagicLevel() {
        return magicLevel;
    }

    public void setMagicLevel(int magicLevel) {
        this.magicLevel = magicLevel;
    }

    public int getShielding() {
        return shielding;
    }

    public void setShielding(int shielding) {
        this.shielding = shielding;
    }

    public int getSwordFighting() {
        return swordFighting;
    }

    public void setSwordFighting(int swordFighting) {
        this.swordFighting = swordFighting;
    }

}
