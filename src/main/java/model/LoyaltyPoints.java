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

    @Temporal(TemporalType.DATE)
    private Calendar registerDate;

    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;

    public LoyaltyPoints() {
    }

    public LoyaltyPoints(Personagem personagem, Integer loyaltyPoints, Calendar registerDate) {
        this.loyaltyPoints = loyaltyPoints;
        this.registerDate = registerDate;
        this.personagem = personagem;
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

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

}
