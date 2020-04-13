package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Personagem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCharacter;

    /////////////////////////////// deixar nome único
    private String name;
    private String title;
    private String sex;
    private String vocation;
    private String residence;
    private String lastLogin;
    private String accountStatus;
    private String sharedExpMinLevel;
    private String sharedExpMaxLevel;
    private String titleAccountInformation;

    @Temporal(TemporalType.DATE)
    private Calendar dateCreate;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Death> deaths;

    /* P.s: usar sistema unidrecional quando as tabelas não forem fortes, usar bidrecional quando for usar cascade */
    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormerWorld> formerWorlds;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AchievementPoints> achievements;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountCharacters> accountCharacters;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LevelAdvance> levelAdvances;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guild> guilds;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment_> comments;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<House> houses;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ranking> ranking;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormerName> formerNames;

    public Personagem() {
        this.deaths = new ArrayList<>();
//        this.accountCharacters = new ArrayList<>();
        this.levelAdvances = new ArrayList<>();
        this.guilds = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.formerWorlds = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.houses = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.formerNames = new ArrayList<>();

    }

    public String getSharedExpMinLevel() {
        return sharedExpMinLevel;
    }

    public String getSharedExpMaxLevel() {
        return sharedExpMaxLevel;
    }

    public void setSharedExpMinLevel(String sharedExpMinLevel) {
        this.sharedExpMinLevel = sharedExpMinLevel;
    }

    public void setSharedExpMaxLevel(String sharedExpMaxLevel) {
        this.sharedExpMaxLevel = sharedExpMaxLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

//    public AccountInformation getAccountInformation() {
//        return accountInformation;
//    }
//
//    public void setAccountInformation(AccountInformation accountInformation) {
//        this.accountInformation = accountInformation;
//    }
    public List<AccountCharacters> getAccountCharacters() {
        return accountCharacters;
    }

    public void setAccountCharacters(List<AccountCharacters> accountCharacters) {
        this.accountCharacters = accountCharacters;
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

    public List<Comment_> getComments() {
        return comments;
    }

    public void setComments(List<Comment_> comments) {
        this.comments = comments;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public List<Ranking> getRanking() {
        return ranking;
    }

    public void setRanking(List<Ranking> ranking) {
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

    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
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

}
