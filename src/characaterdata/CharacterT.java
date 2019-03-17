
package characaterdata;

import java.util.ArrayList;
import java.util.List;


public class CharacterT {
    private String name;
    private String sex;
    private String vocation;
    private String level;
    private String achievementPoints;
    private String world;
    private String residence;
    private String house;
    private String guild;
    private String lastLogin;
    private String comment;
    private String accountStatus;
    private List<String> achievments;
    private List<String> deaths;
    private AccountInformation accountInformation;
    private List<AccountCharacters> accountCharacters;

    
    public CharacterT(){
        this.accountInformation = new AccountInformation();
        this.achievments = new ArrayList<>();
        this.deaths = new ArrayList<>();
        this.accountCharacters = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return  " Name: " + name 
                + "\n sex: " 
                + sex + "\n Vocation: " 
                + vocation + "\n Level: " 
                + level + "\n Achievement Points: " 
                + achievementPoints + "\n World: " 
                + world + "\n Residence: " 
                + residence + "\n House: " 
                + house + "\n Guild: " 
                + guild + "\n Last Login: " 
                + lastLogin + "\n Comment: " 
                + comment + "\n Account Status: " 
                + accountStatus + "\n Achievments: " 
                + achievments + "\n Account Information: " 
                + accountInformation.getTitle() + "\n Created:" + accountInformation.getDateCreate() + "\n Account Characters: " 
                + accountCharacters;
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
    
    
}

