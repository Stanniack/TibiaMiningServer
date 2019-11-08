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
public class FormerWorld implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_formerWorld;
    
    @ManyToOne
    @JoinColumn(name = "idCharacter", nullable = true)
    private Personagem personagem;
    
    private String world;
    
    @Temporal(TemporalType.DATE)
    private Calendar dateBegin;
    @Temporal(TemporalType.DATE)
    private Calendar dateLeave;
    
    

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
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
