package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class HouseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHouseInfo;

    private String houseName;
    private String sqm;
    private String rent;
    private String status;
    private String world;
    private String city;
    private String type;

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public HouseInfo() {
    }

    public HouseInfo(String name, String sqm, String rent, String status, String world, String city, String type, Calendar lastUpdate) {
        this.houseName = name;
        this.sqm = sqm;
        this.rent = rent;
        this.status = status;
        this.lastUpdate = lastUpdate;
        this.world = world;
        this.city = city;
        this.type = type;
    }

    @Temporal(TemporalType.DATE)
    private Calendar lastUpdate;

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getSqm() {
        return sqm;
    }

    public void setSqm(String sqm) {
        this.sqm = sqm;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
