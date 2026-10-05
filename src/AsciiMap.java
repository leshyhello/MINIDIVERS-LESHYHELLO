import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class AsciiMap {
    private int width;
    private int height;
    private char[][] grid;
    private char emptyChar;
    private final Random enemyRandom = new Random();

    public int playerx = 5;
    public int playery = 4;
    private char tileUnderPlayer;
    private boolean playerPlaced;

    public char getTileUnderPlayer() {
    return tileUnderPlayer;}

    // Initialize the map with a default empty character
    public AsciiMap(int width, int height, char emptyChar) {
        this.width = width;
        this.height = height;
        this.emptyChar = emptyChar;
        this.grid = new char[height][width];
        clearMap();
    }
    // Fill the entire map with the empty character
    public void clearMap() {
        for (int i = 0; i < height; i++) {
            Arrays.fill(grid[i], emptyChar);
        }
    }
    // Edit a specific coordinate on the map
    public boolean setTile(int x, int y, char tile) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = tile;
            return true;
        }
        System.out.println("Error: Coordinates out of bounds!");
        return false;
    }
    public boolean placePlayer(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        playerx = x;
        playery = y;
        tileUnderPlayer = grid[y][x];
        grid[y][x] = 'P';
        playerPlaced = true;
        return true;
    }

    public boolean movePlayer(int dx, int dy) {
        int newX = playerx + dx;
        int newY = playery + dy;
        if (!playerPlaced || newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return false;
        }

        grid[playery][playerx] = tileUnderPlayer;
        tileUnderPlayer = grid[newY][newX];
        playerx = newX;
        playery = newY;
        grid[playery][playerx] = 'P';
        return true;
    }

    // Render the map to the console
public void draw() {
        // Clear console screen (optional, works in some terminals)
        System.out.flush();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {System.out.print(grid[y][x] + " ");}
            System.out.println(); // Move to the next row
        }
    }
private static boolean containsSymbol(char[] symbols, char tile) {
        for (char symbol : symbols) {
            if (symbol == tile) {
                return true;
            }
        }
        return false;
    }

public static void main(String[] args) {
        
        // Create a 10x5 map using '.' as grass/empty space
        AsciiMap map = new AsciiMap(10, 7, '.');
        char[] encampmentlist = { '0', '1', '2', '3', '4', '5', '6', '7'};
        char[] symbolList = {'A', 'L', 'M', 'B', '.', '.', '.'}; //A for SEAF Artillery, L for LIDAR, M for SAMSITE, B for Terminate Illegal Broadcast, and * for sample POIs
        char[] objectiveList = {'D', 'S', 'N', 'C', 'R'}; //Destroy Transmission Network, Secure Blackbox, Secure Evidence, Neutralise Orbital Defenses, Destroy Command Bunker, Sabotage Supply Bases
        

        int poi1 = (int) (Math.random() * symbolList.length);
        int poi2 = (int) (Math.random() * symbolList.length);
        int poi3 = (int) (Math.random() * symbolList.length);
        int poi4 = (int) (Math.random() * symbolList.length);

        int camp1 = (int) (Math.random() * encampmentlist.length);
        int camp2 = (int) (Math.random() * encampmentlist.length);
        int camp3 = (int) (Math.random() * encampmentlist.length);
        int camp4 = (int) (Math.random() * encampmentlist.length);

        int objective = (int) (Math.random() * objectiveList.length);

        // Edit the map by adding structures/characters
       
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height), symbolList[poi1]);
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height), symbolList[poi2]);
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height), symbolList[poi3]);
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height), symbolList[poi4]);
        char currentObjective = objectiveList[objective];
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height), currentObjective);
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height),encampmentlist[camp1]);
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height),encampmentlist[camp2]);
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height),encampmentlist[camp3]);
        map.setTile((int) (Math.random() * map.width),(int) (Math.random() * map.height),encampmentlist[camp4]);
        map.placePlayer(map.playerx, map.playery);
        
        // Collect four valid stratagem entries before displaying the map.
        try (Scanner playermove = new Scanner(System.in)) {
        String[] validStratagems = {"500K", "ESTRIKE", "ORAIL", "OLASER", "OBURST", "PODS"};
        System.out.println("Choose four stratagems from: " + String.join(", ", validStratagems));
        String[] entries = new String[4];
        for (int i = 0; i < entries.length; i++) {
            while (true) {
                // Prompt BEFORE reading, so the player always knows input is expected.
                System.out.print("Stratagem " + (i + 1) + " of " + entries.length + ": ");
                System.out.flush();
                if (!playermove.hasNextLine()) {
                    System.out.println("\nNo more input. Exiting.");
                    return;
                }
                String entry = playermove.nextLine().trim();
                String match = null;
                for (String allowed : validStratagems) {
                    if (allowed.equalsIgnoreCase(entry)) {
                        match = allowed;
                        break;
                    }
                }
                if (match != null) {
                    entries[i] = match; // store the canonical spelling, e.g. "ESTRIKE" not "estrike"
                    System.out.println("  Equipped " + match + ".");
                    break;
                }
                System.out.println("  Invalid stratagem. Choose one of: "
                        + String.join(", ", validStratagems));
            }
        }
        Stratagem loadout = new Stratagem(entries[0], entries[1], entries[2], entries[3]);
        System.out.println("Loadout: " + String.join(", ", entries));

        // Keep using this generated map until combat or the objective is reached.
        map.draw();
        boolean gameOver = false;

        while (!gameOver) {
            System.out.print("Move (N/E/S/W): ");
            System.out.flush();
            if (!playermove.hasNextLine()) break;
            String direction = playermove.nextLine().trim();

            boolean moved = true;
            if (direction.equalsIgnoreCase("n")) moved = map.movePlayer(0, -1);
            else if (direction.equalsIgnoreCase("e")) moved = map.movePlayer(1, 0);
            else if (direction.equalsIgnoreCase("s")) moved = map.movePlayer(0, 1);
            else if (direction.equalsIgnoreCase("w")) moved = map.movePlayer(-1, 0);
            else moved = false;

            if (!moved) {
                System.out.println("Invalid direction or move is out of bounds.");
                continue;
            }

            map.draw();
            char tile = map.getTileUnderPlayer();

            if (tile == currentObjective) {
                System.out.println("You reached the objective: " + currentObjective);
                gameOver = true;
            } else if (tile != '.' && containsSymbol(symbolList, tile)) {
                System.out.println("You reached a point of interest: " + tile);
                if(tile == 'A'){
                    //minigame
                }
                if(tile == 'L'){
                    //minigame
                }
                if(tile == 'M'){
                    //minigame
                }
                if(tile == 'B'){
                    //minigame
                }

            }
            
else if (tile != '.' && containsSymbol(encampmentlist, tile)) {
            System.out.println("You have reached an encampment, difficulty " + tile);
                    String[] enemyList = {"Marauder", "Devastator", "Hulk", "WarStrider"};
                    int randomEnemyType = (int) (Math.random() * enemyList.length);
                    int patrolSize = (3*(tile - '0')) + (int) (Math.random() * 20);
                    int patrolHealth;
                    if (enemyList[randomEnemyType].equals("Marauder")) {patrolHealth = 150 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Devastator")) {patrolHealth = 500 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Hulk")) {patrolHealth = 1000 + (patrolSize - 1) * 100;} 
                        else {patrolHealth = 5000 + (patrolSize - 1) * 100;} 
                    Patrol encampmentGuards = new Patrol(patrolHealth, patrolSize, enemyList[randomEnemyType]);
                    System.out.println("Health: " + encampmentGuards.combinedHealth);
                    System.out.println("Size: " + encampmentGuards.patrolMembers);
                    System.out.println("Strongest member: " + encampmentGuards.strongestMember);
                gameOver = true;
            }

else if (tile == '.') {
int enemyRoll = map.enemyRandom.nextInt(10);
if (enemyRoll >= 8) {
                    System.out.println("big patrol combat");
                    String[] enemyList = {"Soldier", "Meelee", "Marauder", "Devastator", "Hulk"};
                    int randomEnemyType = (int) (Math.random() * enemyList.length);
                    int patrolSize = 10 + (int) (Math.random() * 20);
                    int patrolHealth;
                    if (enemyList[randomEnemyType].equals("Soldier")) {patrolHealth = 100 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Meelee")) {patrolHealth = 150 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Marauder")) {patrolHealth = 150 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Devastator")) {patrolHealth = 500 + (patrolSize - 1) * 100;} 
                        else {patrolHealth = 1000 + (patrolSize - 1) * 100;}
                        Patrol bigPatrol = new Patrol(patrolHealth, patrolSize, enemyList[randomEnemyType]);
                        System.out.println("Health: " + bigPatrol.combinedHealth);
                        System.out.println("Size: " + bigPatrol.patrolMembers);
                        System.out.println("Strongest member: " + bigPatrol.strongestMember);
                boolean combat = true;
                while (combat) {
                    // Print the prompt first; hasNextLine() blocks, so calling it in the
                    // loop condition made the game wait for input before showing this.
                    System.out.println("Please pick one of the combat options: PRIMARY, SECONDARY, "
                            + loadout.getStratagemOne() + ", " + loadout.getStratagemTwo() + ", "
                            + loadout.getStratagemThree() + ", " + loadout.getStratagemFour() + ", MEELEE");
                    if (!playermove.hasNextLine()) break;
                    String attack = playermove.nextLine().trim();

                    boolean isEquippedStratagem = attack.equalsIgnoreCase(loadout.getStratagemOne())
                            || attack.equalsIgnoreCase(loadout.getStratagemTwo())
                            || attack.equalsIgnoreCase(loadout.getStratagemThree())
                            || attack.equalsIgnoreCase(loadout.getStratagemFour());
                    boolean isStandardAttack = attack.equalsIgnoreCase("PRIMARY")
                            || attack.equalsIgnoreCase("SECONDARY")
                            || attack.equalsIgnoreCase("MEELEE");

                    if (!isEquippedStratagem && !isStandardAttack) {
                        System.out.println("Invalid command or stratagem not in your loadout.");
                        continue;
                    }

                    if (attack.equalsIgnoreCase("OBURST")) {
                        System.out.println("You used orbital airburst.");
                        int burstDeviation = 250- (int) (Math.random()*50);
                        bigPatrol.combinedHealth -= burstDeviation;
                    } 

                    else if(attack.equalsIgnoreCase("500K")) {
                        System.out.println("You used a 500kg bomb.");
                        int bombDeviation = 1000 - (int) (Math.random()*50);
                        bigPatrol.combinedHealth -= bombDeviation;
                    }

                    else if(attack.equalsIgnoreCase("ESTRIKE")) {
                        System.out.println("You used an eagle airstrike.");
                        int strikeDeviation = 750 - (int) (Math.random()*50);
                        bigPatrol.combinedHealth -= strikeDeviation;
                    }

                    else if(attack.equalsIgnoreCase("ORAIL")) {
                        System.out.println("You used an orbital railgun.");
                        int railDeviation = 1500;
                        bigPatrol.combinedHealth -= railDeviation;
                        }

                    else if(attack.equalsIgnoreCase("OLASER")) {
                        System.out.println("You used an orbital laser.");
                        int laserDeviation = 500 - (int) (Math.random()*50);
                        bigPatrol.combinedHealth -= laserDeviation;
                        }

                    else if(attack.equalsIgnoreCase("PODS")) {
                        int podDeviation = 150 - (int) (Math.random()*50);
                        System.out.println("You used 100mm rocket pods.");
                        bigPatrol.combinedHealth -= podDeviation;
                        }
                    if(bigPatrol.combinedHealth <= 0){
                        System.out.println("You defeated the Automaton Patrol!");
                        combat = false;
                    }
                }
            }
 else if (enemyRoll >= 7) {
                    System.out.println("small patrol combat");
                    String[] enemyList = {"Soldier", "Meelee", "Marauder", "Devastator", "Hulk"};
                    int randomEnemyType = (int) (Math.random() * enemyList.length);
                    int patrolSize = 5 + (int) (Math.random() * 10);
                    int patrolHealth;
                    if (enemyList[randomEnemyType].equals("Soldier")) {patrolHealth = 100 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Meelee")) {patrolHealth = 150 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Marauder")) {patrolHealth = 150 + (patrolSize - 1) * 100;} 
                        else if (enemyList[randomEnemyType].equals("Devastator")) {patrolHealth = 500 + (patrolSize - 1) * 100;} 
                        else {patrolHealth = 1000 + (patrolSize - 1) * 100;}
                        Patrol smallPatrol = new Patrol(patrolHealth, patrolSize, enemyList[randomEnemyType]);
                        System.out.println("Health: " + smallPatrol.combinedHealth);
                        System.out.println("Size: " + smallPatrol.patrolMembers);
                        System.out.println("Strongest member: " + smallPatrol.strongestMember);
                }
            }
        }
    }}}