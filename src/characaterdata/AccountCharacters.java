
package characaterdata;

public class AccountCharacters {
    private String number;
    private String name;
    private String world;
    private String status;

    public AccountCharacters(String number, String name, String world, String status) {
        this.number = number;
        this.name = name;
        this.world = world;
        this.status = status;
    }
    
    

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
