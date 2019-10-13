package model.character;

import java.util.ArrayList;
import java.util.List;

public class Character {

    private String name;
    private String title;
    private String sex;
    private String vocation;
    private String level;
    private String achievementPoints;
    private String world;
    private String formerWorld;
    private List<FormerWorld> formerWorlds;
    private String residence;
    private String house;
    private String guild;
    private String lastLogin;
    private String comment;
    private String accountStatus;
    private String sharedExpMinLevel;
    private String sharedExpMaxLevel;
    private List<String> achievments;
    private List<String> deaths;
    private AccountInformation accountInformation;
    private List<AccountCharacters> accountCharacters;
    private List<LevelAdvance> levelAdvances;
    private List<Guild> guilds;
    private List<Comment> comments;
    private List<House> houses;
    private List<CharacterRanking> ranking;

    public Character() {
        this.accountInformation = new AccountInformation();
        this.achievments = new ArrayList<>();
        this.deaths = new ArrayList<>();
        this.accountCharacters = new ArrayList<>();
        this.levelAdvances = new ArrayList<>();
        this.guilds = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.formerWorlds = new ArrayList<>();

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

    public List<String> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<String> deaths) {
        this.deaths = deaths;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(String achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<String> getAchievments() {
        return achievments;
    }

    public void setAchievments(List<String> achievments) {
        this.achievments = achievments;
    }

    public AccountInformation getAccountInformation() {
        return accountInformation;
    }

    public void setAccountInformation(AccountInformation accountInformation) {
        this.accountInformation = accountInformation;
    }

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public List<CharacterRanking> getRanking() {
        return ranking;
    }

    public void setRanking(List<CharacterRanking> ranking) {
        this.ranking = ranking;
    }

    public String getFormerWorld() {
        return formerWorld;
    }

    public void setFormerWorld(String formerWorld) {
        this.formerWorld = formerWorld;
    }

    public List<FormerWorld> getFormerWorlds() {
        return formerWorlds;
    }

    public void setFormerWorlds(List<FormerWorld> formerWorlds) {
        this.formerWorlds = formerWorlds;
    }

}
