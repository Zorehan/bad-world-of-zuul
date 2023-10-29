import java.util.ArrayList;
import java.util.List;

public class Player {
    private final double carryWeight;
    private List<Item> playerItemsList = new ArrayList<>();
    private Room playerCurrentRoom;
    private double currentWeight = 0;

    public Player(double carryWeight, List<Item> playerItemsList, Room playerCurrentRoom, double currentWeight) {
        this.carryWeight = carryWeight;
        this.currentWeight = currentWeight;
        this.playerItemsList = playerItemsList;
        this.playerCurrentRoom = playerCurrentRoom;
    }

    public Room getPlayerCurrentRoom() {
        return playerCurrentRoom;
    }

    public void setPlayerCurrentRoom(Room newRoom)
    {
        playerCurrentRoom = newRoom;
    }

    public void addToInventory(Item item)
    {
        double itemWeight = item.getWeight();
        if(itemWeight + currentWeight < carryWeight)
        {
            playerItemsList.add(item);
            currentWeight = currentWeight + itemWeight;
            System.out.println("You picked up: " + item.getItemInfo());
        }
        else
        {
            System.out.println("you cannot carry that item");
        }




    }

}
