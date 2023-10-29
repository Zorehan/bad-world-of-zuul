import java.util.ArrayList;
import java.util.List;

public class Item {
    private String itemDescription;
    private double weight;
    private Room belongsTo;
    private List<Item> itemList = new ArrayList<>();
    private String name;
    private boolean eatable;

    public Item(String name, String itemDescription, double weight, Room belongsTo, boolean eatable) {
        this.itemDescription = itemDescription;
        this.weight = weight;
        this.belongsTo = belongsTo;
        this.name = name;
        this.eatable = eatable;
    }

    public boolean isItemEatable()
    {
        return this.eatable;
    }

    public void setItemDescription(String newDescription) {
        this.itemDescription = newDescription;
    }

    public List<Item> getItemsList() {
        return this.itemList;
    }

    public void addToItemsList(Item item) {
        itemList.add(item);
    }

    public void removeItemsFromList(Item item)
    {
        itemList.remove(item);
    }

    public Room getBelongsTo() {
        return this.belongsTo;
    }

    public String getItemInfo() {
        return this.name + ".\n" + this.itemDescription + ".\n" + " Weight: " + this.weight + "kg";
    }


    public double getWeight()
    {
        return this.weight;
    }

    public String getName()
    {
        return this.name;
    }

}
