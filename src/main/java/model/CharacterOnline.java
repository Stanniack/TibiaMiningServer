package model;

public class CharacterOnline {

    private String nick;
    private String level;
    private String vocation;

    public CharacterOnline(String nick, String level, String vocation) {
        this.nick = nick;
        this.level = level;
        this.vocation = vocation;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

}
