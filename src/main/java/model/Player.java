package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCharacter;

    @Column(unique = true, nullable = false)
    private String playerName;

    private String title;
    private String sex;
    private String vocation;
    private String residence;
    private String world;
    private String lastLogin;
    private String accountStatus;
    private String titleAccountInformation;
    private boolean isDeleted;

    /* Data do personagme registrado no bd */
    @Temporal(TemporalType.DATE)
    private Calendar registerDate;

    /* Data de criação do personagem no Tibia */
    private String dateCreate;

    /* Data que o personagem foi deletado */
    @Temporal(TemporalType.DATE)
    private Calendar dateDeleted;
    
    /* Último update */
    @Temporal(TemporalType.DATE)
    private Calendar lastUpdate;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Death> deaths;

    /* P.s: usar sistema unidrecional quando as tabelas não forem fortes, usar bidrecional quando for usar cascade */
    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormerWorld> formerWorlds;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AchievementPoints> achievements;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LevelAdvance> levelAdvances;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guild> guilds;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<House> houses;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CharacterSkills> ranking;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormerName> formerNames;

    public Player() {
        this.deaths = new ArrayList<>();
        this.levelAdvances = new ArrayList<>();
        this.guilds = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.formerWorlds = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.houses = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.formerNames = new ArrayList<>();

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LevelAdvance> getLevelAdvances() {
        return levelAdvances;
    }

    public void setLevelAdvances(List<LevelAdvance> levelAdvances) {
        this.levelAdvances = levelAdvances;
    }

    public List<Guild> getGuilds() {
        return guilds;
    }

    public void setGuilds(List<Guild> guilds) {
        this.guilds = guilds;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public List<CharacterSkills> getRanking() {
        return ranking;
    }

    public void setRanking(List<CharacterSkills> ranking) {
        this.ranking = ranking;
    }

    public List<FormerWorld> getFormerWorlds() {
        return formerWorlds;
    }

    public void setFormerWorlds(List<FormerWorld> formerWorlds) {
        this.formerWorlds = formerWorlds;
    }

    public List<AchievementPoints> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementPoints> achievements) {
        this.achievements = achievements;
    }

    public Integer getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(Integer idCharacter) {
        this.idCharacter = idCharacter;
    }

    public List<FormerName> getFormerNames() {
        return formerNames;
    }

    public void setFormerNames(List<FormerName> formerNames) {
        this.formerNames = formerNames;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getTitleAccountInformation() {
        return titleAccountInformation;
    }

    public void setTitleAccountInformation(String titleAccountInformation) {
        this.titleAccountInformation = titleAccountInformation;
    }

    public List<Death> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<Death> deaths) {
        this.deaths = deaths;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Calendar getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Calendar dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
