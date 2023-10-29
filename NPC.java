import java.util.ArrayList;
import java.util.List;

public class NPC {
    private String name;
    private String description;
    private List<String> talkOptions = new ArrayList<>();

    public NPC(String name, String description, List<String> talkOptions, Room npcBelongsTo )
    {
        this.name = name;
        this.description = description;
        this.talkOptions = talkOptions;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public List<String> getTalkOptions()
    {
        return this.talkOptions;
    }

    public void addToTalkOptions(String string)
    {
        this.talkOptions.add(string);
    }

}
