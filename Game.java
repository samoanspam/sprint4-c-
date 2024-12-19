import java.io.*;
import java.util.*;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> inventory = new ArrayList<>();
    private static int points = 0;
    private static String characterName;
    private static String characterClass;
    private static String characterAbility;
    private static String saveKey;

    public static void main(String[] args) {
        Game game = new Game();
        game.showMenu();
    }

    // function to show the main menu
    public void showMenu() {
        System.out.println("Welcome to the Adventure Game!");
        System.out.println("1. Start New Game");
        System.out.println("2. Load Game");
        System.out.println("3. View Credits");
        System.out.println("4. Exit");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> startNewGame();
            case "2" -> loadGame();
            case "3" -> viewCredits();
            case "4" -> System.out.println("Thanks for playing!");
            default -> {
                System.out.println("Invalid choice. Please try again.");
                showMenu();
            }
        }

        // switch (choice) {
        //     case "1":
        //         startNewGame();
        //         break;
        //     case "2":
        //         loadGame();
        //         break;
        //     case "3":
        //         viewCredits();
        //         break;
        //     case "4":
        //         System.out.println("Thanks for playing!");
        //         break;
        //     default:
        //         System.out.println("Invalid choice. Please try again.");
        //         showMenu();
        // }

    }

    // function to start a new game
    public void startNewGame() {
        System.out.println("Enter your character's name:");
        characterName = scanner.nextLine();
        System.out.println("Choose your character class (e.g., Warrior, Mage, Archer):");
        characterClass = scanner.nextLine();
        System.out.println("Choose your special ability (e.g., Fireball, Healing, Stealth):");
        characterAbility = scanner.nextLine();
        
        System.out.println("Character created: " + characterName + ", " + characterClass + ", Ability: " + characterAbility);
        saveProgress();
        firstChoice();
    }

    // function to load a saved game
    public void loadGame() {
        System.out.println("Enter your save key to load your progress:");
        String key = scanner.nextLine();
        if (loadProgress(key)) {
            System.out.println("Progress loaded successfully.");
            firstChoice();
        } else {
            System.out.println("No progress found for this key. Starting a new game.");
            startNewGame();
        }
    }

    // function to save the game progress
    private void saveProgress() {
        saveKey = UUID.randomUUID().toString(); // generate a unique key for the player
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveKey + ".txt"))) {
            writer.write("Name:" + characterName + "\n");
            writer.write("Class:" + characterClass + "\n");
            writer.write("Ability:" + characterAbility + "\n");
            writer.write("Points:" + points + "\n");
            writer.write("Inventory:" + String.join(",", inventory) + "\n");
            System.out.println("Game saved with key: " + saveKey);
        } catch (IOException e) {
            System.out.println("Error saving game.");
        }
    }

    // function to load the game progress from a file
    private boolean loadProgress(String key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(key + ".txt"))) {
            characterName = reader.readLine().split(":")[1];
            characterClass = reader.readLine().split(":")[1];
            characterAbility = reader.readLine().split(":")[1];
            points = Integer.parseInt(reader.readLine().split(":")[1]);
            String inventoryData = reader.readLine().split(":")[1];
            inventory.clear();
            if (!inventoryData.isEmpty()) {
                inventory.addAll(Arrays.asList(inventoryData.split(",")));
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // function to view credits
    public void viewCredits() {
        System.out.println("=====================================");
        System.out.println("Adventure Game Credits");
        System.out.println("Game created by: Your Name");
        System.out.println("Special thanks to: OpenAI for AI assistance");
        System.out.println("=====================================");
        showMenu(); // return to the main menu after showing credits
    }

    // function for the first choice
    public void firstChoice() {
        System.out.println("You are standing at a crossroad. Do you want to go left, right, or visit the shop?");
        String choice = getUserChoice();

        if (choice.equalsIgnoreCase("left")) {
            goLeft();
        } else if (choice.equalsIgnoreCase("right")) {
            goRight();
        } else if (choice.equalsIgnoreCase("shop")) {
            visitShop();
        } else {
            System.out.println("Invalid choice. Please choose 'left', 'right', or 'shop'.");
            firstChoice();
        }
    }

    // function for going left
    public void goLeft() {
        System.out.println("You have encountered a monster! Do you fight, run, or hide?");
        String choice = getUserChoice();

        if (choice.equalsIgnoreCase("fight")) {
            System.out.println("You defeated the monster!");
            inventory.add("Monster's loot");
            points += 10; // earn points for defeating the monster
            nextStep();
        } else if (choice.equalsIgnoreCase("run")) {
            System.out.println("You ran away safely.");
            nextStep();
        } else if (choice.equalsIgnoreCase("hide")) {
            System.out.println("You hid in a nearby bush. The monster walks away.");
            nextStep();
        } else {
            System.out.println("Invalid choice. Please choose 'fight', 'run', or 'hide'.");
            goLeft();
        }
    }

    // function for going right
    public void goRight() {
        System.out.println("You find a treasure chest. Do you open it, ignore it, or kick it?");
        String choice = getUserChoice();

        if (choice.equalsIgnoreCase("open")) {
            System.out.println("You found a gold coin!");
            inventory.add("Gold coin");
            points += 5; // earn points for opening the chest
            nextStep();
        } else if (choice.equalsIgnoreCase("ignore")) {
            System.out.println("You walk away from the chest.");
            nextStep();
        } else if (choice.equalsIgnoreCase("kick")) {
            System.out.println("The chest opens, and you find a map to a hidden cave!");
            inventory.add("Map to hidden cave");
            points += 7; // earn points for finding the map
            nextStep();
        } else {
            System.out.println("Invalid choice. Please choose 'open', 'ignore', or 'kick'.");
            goRight();
        }
    }

    // function for a hidden cave
    public void hiddenCave() {
        System.out.println("You follow the map and find a hidden cave. Do you enter, turn back, or leave the map behind?");
        String choice = getUserChoice();

        if (choice.equalsIgnoreCase("enter")) {
            System.out.println("Inside the cave, you find a sleeping dragon! Do you sneak past it, fight it, or take its treasure?");
            String caveChoice = getUserChoice();

            if (caveChoice.equalsIgnoreCase("sneak")) {
                System.out.println("You sneak past the dragon and find a chest with rare gems!");
                inventory.add("Rare gems");
                points += 15; // earn points for sneaking past the dragon
                nextStep();
            } else if (caveChoice.equalsIgnoreCase("fight")) {
                System.out.println("You fought the dragon and won! You take its treasure.");
                inventory.add("Dragon's treasure");
                points += 20; // earn points for defeating the dragon
                nextStep();
            } else if (caveChoice.equalsIgnoreCase("take")) {
                System.out.println("You take the dragon's treasure while it sleeps. You escape safely.");
                inventory.add("Dragon's treasure");
                points += 18; // earn points for taking the treasure
                nextStep();
            } else {
                System.out.println("Invalid choice. Please choose 'sneak', 'fight', or 'take'.");
                hiddenCave();
            }
        } else if (choice.equalsIgnoreCase("turn back")) {
            System.out.println("You turn back and retrace your steps.");
            firstChoice();
        } else if (choice.equalsIgnoreCase("leave")) {
            System.out.println("You leave the map behind and head back to the crossroad.");
            firstChoice();
        } else {
            System.out.println("Invalid choice. Please choose 'enter', 'turn back', or 'leave'.");
            hiddenCave();
        }
    }

    // function to visit the shop
    public void visitShop() {
        System.out.println("Welcome to the shop! You currently have " + points + " points.");
        System.out.println("Do you want to buy a potion (10 points), a sword (20 points), or a shield (15 points)?");
        System.out.println("Or you can leave the shop (type 'leave').");

        String choice = getUserChoice();

        if (choice.equalsIgnoreCase("potion") && points >= 10) {
            inventory.add("Potion");
            points -= 10;
            System.out.println("You bought a potion!");
        } else if (choice.equalsIgnoreCase("sword") && points >= 20) {
            inventory.add("Sword");
            points -= 20;
            System.out.println("You bought a sword!");
        } else if (choice.equalsIgnoreCase("shield") && points >= 15) {
            inventory.add("Shield");
            points -= 15;
            System.out.println("You bought a shield!");
        } else if (choice.equalsIgnoreCase("leave")) {
            System.out.println("You leave the shop.");
        } else {
            System.out.println("You don't have enough points or you made an invalid choice.");
        }
        nextStep();
    }

    // function for the next step
    public void nextStep() {
        System.out.println("Your inventory: " + inventory);
        System.out.println("You have " + points + " points.");
        System.out.println("Do you want to continue your adventure? (yes/no)");
        String choice = getUserChoice();

        if (choice.equalsIgnoreCase("yes")) {
            firstChoice();
        } else {
            System.out.println("Thanks for playing!");
        }
    }

    // function to show loading animation while waiting for a decision
    private String getUserChoice() {
        String choice;
        System.out.print("Awaiting your decision");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(400); // wait for a bit
                System.out.print(".");
            } catch (InterruptedException e) {

            }
        }
        System.out.println();
        choice = scanner.nextLine(); // get the user's choice after animation
        return choice;
    }
}
