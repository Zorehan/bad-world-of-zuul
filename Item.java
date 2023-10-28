import java.util.ArrayList;
import java.util.List;

public class Item {
    private String itemDescription;
    private double weight;
    private Room belongsTo;
    private List<Item> itemList = new ArrayList<>();
    public Item(String itemDescription, double weight, Room belongsTo) {
        this.itemDescription = itemDescription;
        this.weight = weight;
        this.belongsTo = belongsTo;
    }

    public void setItemDescription(String newDescription)

    {
        this.itemDescription = newDescription;
    }

    public List<Item> getItemsList()
    {
        return this.itemList;
    }

    public void addToItemsList(Item item)
    {
        itemList.add(item);
    }
    public Room getBelongsTo()
    {
        return this.belongsTo;
    }
    public String getItemInfo()
    {
        return this.itemDescription + ".\n" + " Weight: " + this.weight + "kg";
    }

}
