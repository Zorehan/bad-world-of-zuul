import java.util.Set;
import java.util.ArrayList;
import java.util.List;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item isThereItem;
    private Item currentItem;
    private Room lastRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrance, checkIn, bubgerKirg, terminal, shop, security, toilet, womanToilet, basement;


        entrance = new Room("At the entrance of the airport, just after the doorway, eerily empty");
        checkIn = new Room("An unmanned check-in room with metal detectors,scanning machines and luggage carts");
        bubgerKirg = new Room("A Bubger Kirg restaurant, lights are turned off, but you see small lights flashing from food storage units");
        terminal = new Room("Endless gates as far as the eyes can see, however all of them seem locked down");
        shop = new Room("A shop with infinite amounts of random stuff, from toys to cheap polish cigarettes, all yours for the taking");
        security = new Room ("A security room just like the ones you see in movies, 9 screens that each show footage from a cctv camera from somewhere on the airport");
        toilet = new Room ("A regular toilet with 3 stalls, some sinks and a big ass hole in the wall");
        womanToilet = new Room("A womans toilet booth, reeks of Wukong toplane and weed");
        basement = new Room("The Basement below the Bubger Kirg, smells of rotten burgers");

        basement.setExits("up", bubgerKirg);
        entrance.setExits("north", checkIn);
        entrance.setExits("east", security);
        checkIn.setExits("north", terminal);
        checkIn.setExits("west", bubgerKirg);
        checkIn.setExits("east", shop);
        checkIn.setExits("south", entrance);
        bubgerKirg.setExits("east", checkIn);
        bubgerKirg.setExits("down", basement);
        terminal.setExits("south", checkIn);
        shop.setExits("west", checkIn);
        shop.setExits("north", security);
        shop.setExits("east", toilet);
        security.setExits("north", shop);
        security.setExits("west", entrance);
        toilet.setExits("west", shop);
        toilet.setExits("north", womanToilet);
        womanToilet.setExits("south", toilet);


        Item borgar, hairline, secondborgar;
        isThereItem = new Item("Placeholder Item", 0.0, null);
        secondborgar = new Item("Dobbal cheeseborgar", 0.1, bubgerKirg);
        borgar = new Item("A X-Long Chili Chicken borgar mhmmm tasty", 0.2, bubgerKirg);
        hairline = new Item("An old wig, whoever used this must have had a crazy pushed back hairline", 0.01, security);
        isThereItem.addToItemsList(borgar);
        isThereItem.addToItemsList(hairline);
        isThereItem.addToItemsList(secondborgar);

        currentItem = null;
        currentRoom = entrance;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Airport of the Cob!");
        System.out.println("Airport of the Cob is a new hyper realistic text based adventure game");
        System.out.println("Your objective is to find the ghost ¨Cob¨ who is hidden somewhere in the airport and exorcise him");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if(commandWord.equals("look"))
        {
            look();
        }
        else if(commandWord.equals("eat"))
        {
            eat();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back"))
        {
            goBack();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    private void eat()
    {
        System.out.println("You have eaten and are not hungry anymore");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRoom = currentRoom;
            currentRoom = nextRoom;

            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    private void goBack()
    {
    currentRoom = lastRoom;
    printLocationInfo();
    }

    public void printLocationInfo()
    {

        System.out.println(currentRoom.getLongDescription());
        printItemInformation();
        System.out.println(currentRoom.getExitInformation());
    }

    public Item doesRoomHaveItem()
    {
        for(Item item : isThereItem.getItemsList())
        if(item.getBelongsTo() == currentRoom)
        {
            return item;
        }
        return null;
    }

    public List<Item> getItemsInRoom()
    {
        List<Item> itemsInRoom = new ArrayList<>();
        for(Item item : isThereItem.getItemsList())
        {
            if (item.getBelongsTo() == currentRoom)
            {
                itemsInRoom.add(item);
            }


        }
        return itemsInRoom;
    }

    public void printItemInformation()
    {
        List<Item> itemsInRoom = getItemsInRoom();
        if (!itemsInRoom.isEmpty())
        {
            System.out.println("You also see the following items:");

            for (Item item : itemsInRoom)
            {
                System.out.println(item.getItemInfo());
            }
        }
    }
}
