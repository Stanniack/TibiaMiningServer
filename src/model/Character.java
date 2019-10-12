package model;

public class Character implements Comparable<Character> {

    private String rankWorld;
    private String world;
    private String name;
    private String vocation;
    private String level;
    private Long points;
    private String rankTibia;

    public Character(String rankWorld, String name, String vocation, String level, Long points, String world) {
        this.rankWorld = rankWorld;
        this.name = name;
        this.vocation = vocation;
        this.level = level;
        this.points = points;
        this.world = world;
    }
    
    public Character (String name, String level, String vocation) {
        this.name = name;
        this.level = level;
        this.vocation = vocation;
    }

    public String getRankWorld() {
        return rankWorld;
    }

    public void setRankWorld(String rankWorld) {
        this.rankWorld = rankWorld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getRankTibia() {
        return rankTibia;
    }

    public void setRankTibia(String rankTibia) {
        this.rankTibia = rankTibia;
    }

    @Override
    public String toString() {
        return "Rank World: " + rankWorld
                + "\nNome: " + name + "\n"
                + "Vocation: " + vocation + "\n"
                + "Level: " + level + "\n"
                + "Experience points: " + points + '\n'
                + "World: " + world + '\n';
    }

    @Override
    public int compareTo(Character outro) {
        if(this.points < outro.points){
            return 1;
        }
        
        if(this.points > outro.points){
            return -1;
        }
            
        return 0;
    }

}
