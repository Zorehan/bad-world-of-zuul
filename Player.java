import java.util.ArrayList;
import java.util.List;

public class Player {
    private double carryWeight;
    private List<Item> playerItemsList = new ArrayList<>();
    private Room playerCurrentRoom;

    public Player(double carryWeight, List<Item> playerItemsList, Room playerCurrentRoom) {
        this.carryWeight = carryWeight;
        this.playerItemsList = playerItemsList;
        this.playerCurrentRoom = playerCurrentRoom;
    }

    public Room getPlayerCurrentRoom() {
        return playerCurrentRoom;
    }

    public void setPlayerCurrentRoom(Room newRoom)
    {
        
    }

}
