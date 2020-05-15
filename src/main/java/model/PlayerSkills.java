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
public class PlayerSkills implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlayerSkills;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Player player;

    @Temporal(TemporalType.DATE)
    private Calendar lastUpdate;

    private String playerName;
    private int axeFighting;
    private int clubFighting;
    private int distanceFighting;
    private int fishing;
    private int fistFighting;
    private int loyaltyPoints;
    private int magicLevel;
    private int shielding;
    private int swordFighting;

    public PlayerSkills() {
    }

    public PlayerSkills(String playerName, int axeFighting, int clubFighting, int distanceFighting, int fishing, int fistFighting, int loyaltyPoints, int magicLevel, int shielding, int swordFighting) {
        this.playerName = playerName;
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

    public Integer getIdPlayerSkills() {
        return idPlayerSkills;
    }

    public void setIdPlayerSkills(Integer idPlayerSkills) {
        this.idPlayerSkills = idPlayerSkills;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getFistFighting() {
        return fistFighting;
    }

    public void setFistFighting(int fistFighting) {
        this.fistFighting = fistFighting;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
