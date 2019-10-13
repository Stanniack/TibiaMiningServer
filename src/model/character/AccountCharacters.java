package model.character;

public class AccountCharacters {

    private String name;
    private String world;
    private String status;

    public AccountCharacters(String name, String world, String status) {
        this.name = name;
        this.world = world;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
