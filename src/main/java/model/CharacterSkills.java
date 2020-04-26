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
    private Integer idCharacterSkills;

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

    public CharacterSkills() {
    }

    public CharacterSkills(int axeFighting, int clubFighting, int distanceFighting, int fishing, int fistFighting, int loyaltyPoints, int magicLevel, int shielding, int swordFighting) {
        this.axeFighting = axeFighting;
        this.clubFighting = clubFighting;
        this.distanceFighting = distanceFighting;
        this.fishing = fishing;
        this.fistFighting = fistFighting;
        this.loyaltyPoints = loyaltyPoints;
        this.magicLevel = magicLevel;
        this.shielding = shielding;
        this.swordFighting = swordFighting;
    }

    public Integer getIdCharacterSkills() {
        return idCharacterSkills;
    }

    public void setIdCharacterSkills(Integer idCharacterSkills) {
        this.idCharacterSkills = idCharacterSkills;
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

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public int getFistFighting() {
        return fistFighting;
    }

    public void setFistFighting(int fistFighting) {
        this.fistFighting = fistFighting;
    }

}
