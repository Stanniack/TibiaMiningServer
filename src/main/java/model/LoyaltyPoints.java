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
public class LoyaltyPoints implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLoyaltyPoints;

    private Integer loyaltyPoints;

    private String playerName;

    @Temporal(TemporalType.DATE)
    private Calendar lastUpdate;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Player player;

    public LoyaltyPoints() {
    }

    public LoyaltyPoints(Player player, Integer loyaltyPoints, Calendar lastUpdate) {
        this.loyaltyPoints = loyaltyPoints;
        this.lastUpdate = lastUpdate;
        this.player = player;
    }

    public Integer getIdLoyaltyPoints() {
        return idLoyaltyPoints;
    }

    public void setIdLoyaltyPoints(Integer idLoyaltyPoints) {
        this.idLoyaltyPoints = idLoyaltyPoints;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
