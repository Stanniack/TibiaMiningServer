package model;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Boss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBoss;

    private String bossName;
    private int killedPlayers;
    private int killedByPlayers;
    private String world;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateApparition;

    public Boss() {
    }

    public Boss(String bossName, int killedPlayers, int killedByPlayers, String world, Calendar dateApparition) {
        this.bossName = bossName;
        this.killedPlayers = killedPlayers;
        this.killedByPlayers = killedByPlayers;
        this.dateApparition = dateApparition;
        this.world = world;
    }

    public Boss(String bossName) {
        this.bossName = bossName;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public int getKilledPlayers() {
        return killedPlayers;
    }

    public void setKilledPlayers(int killedPlayers) {
        this.killedPlayers = killedPlayers;
    }

    public int getKilledByPlayers() {
        return killedByPlayers;
    }

    public String getWorld() {
        return world;
    }

    public void setKilledByPlayers(int killedByPlayers) {
        this.killedByPlayers = killedByPlayers;
    }

    public Calendar getDateApparition() {
        return dateApparition;
    }

    public void setDateApparition(Calendar dateApparition) {
        this.dateApparition = dateApparition;
    }

}
