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
        Room entrance, checkIn, bubgerKirg, terminal, shop, security, toilet, womanToilet;

        entrance = new Room("At the entrance of the airport, just after the doorway, eerily empty");
        checkIn = new Room("An unmanned check-in room with metal detectors,scanning machines and luggage carts");
        bubgerKirg = new Room("A Bubger Kirg restaurant, lights are turned off, but you see small lights flashing from food storage units");
        terminal = new Room("Endless gates as far as the eyes can see, however all of them seem locked down");
        shop = new Room("A shop with infinite amounts of random stuff, from toys to cheap polish cigarettes, all yours for the taking");
        security = new Room ("A security room just like the ones you see in movies, 9 screens that each show footage from a cctv camera from somewhere on the airport");
        toilet = new Room ("A regular toilet with 3 stalls, some sinks and a big ass hole in the wall");
        womanToilet = new Room("A womans toilet booth, reeks of Wukong toplane and weed");

        entrance.setExits("north", checkIn);
        entrance.setExits("east", security);
        checkIn.setExits("north", terminal);
        checkIn.setExits("west", bubgerKirg);
        checkIn.setExits("east", shop);
        checkIn.setExits("south", entrance);
        bubgerKirg.setExits("east", checkIn);
        terminal.setExits("south", checkIn);
        shop.setExits("west", checkIn);
        shop.setExits("north", security);
        shop.setExits("east", toilet);
        security.setExits("north", shop);
        security.setExits("west", entrance);
        toilet.setExits("west", shop);
        toilet.setExits("north", womanToilet);
        womanToilet.setExits("south", toilet);

        // initialise room exits

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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");

        printLocationInfo();
        System.out.println();
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
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
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
        System.out.println("   go quit help");
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

    public void printLocationInfo()
    {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.println("Exits: ");
        if(currentRoom.getExit("north") != null)
        {
            System.out.println("North");
        }

        if(currentRoom.getExit("east") != null)
        {
            System.out.println("East");
        }

        if(currentRoom.getExit("south") != null)
        {
            System.out.println("South");
        }

        if(currentRoom.getExit("west") != null)
        {
            System.out.println("west");
        }

        public String getExitString()
        {

        }
    }
}
