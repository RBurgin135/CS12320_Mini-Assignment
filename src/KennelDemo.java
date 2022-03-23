import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class runs a Kennel
 *
 * @author Lynda Thomas and Chris Loftus
 * @version 23rd February 2021
 */
public class KennelDemo {
    private String filename; // holds the name of the file
    private Kennel kennel; // holds the kennel
    private Scanner scan; // so we can read from keyboard

    /*
     * Notice how we can make this constructor private, since we only call from main which
     * is in this class. We don't want this class to be used by any other class.
     */
    private KennelDemo() {
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename of kennel information: ");
        filename = scan.nextLine();

        kennel = new Kennel();
    }

    /*
     * initialise() method runs from the main and reads from a file
     */
    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            kennel.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + filename + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /*
     * runMenu() method runs from the main and allows entry of data etc
     */
    private void runMenu() {
        String response;
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    admitAnimal();
                    break;
                case "2":
                    changeKennelName();
                    break;
                case "3":
                    printAll();
                    break;
                case "4":
                    searchForAnimal();
                    break;
                case "5":
                    removeAnimal();
                    break;
                case "6":
                    setKennelCapacity();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Try again");
            }
        } while (!(response.equals("Q")));
    }

    private void printMenu() {
        System.out.println("1 -  add a new animal ");
        System.out.println("2 -  set up Kennel name");
        System.out.println("3 -  display all inmates");
        System.out.println("4 -  search for an animal");
        System.out.println("5 -  remove an animal");
        System.out.println("6 -  set kennel capacity");
        System.out.println("q - Quit");
    }

    private void setKennelCapacity() {
        System.out.print("Enter max number of animals: ");
        int max = scan.nextInt();
        scan.nextLine();
        kennel.setCapacity(max);
    }

    /**
     * printAll() method runs from the main and prints status
     */
    private void printAll() {
        // Display the animals
        kennel.sortAnimals();
        System.out.println(kennel);
    }

    private void removeAnimal() {
        System.out.println("which animal do you want to remove");
        String animalToBeRemoved;
        animalToBeRemoved = scan.nextLine();
        kennel.removeAnimal(animalToBeRemoved);
    }

    private void searchForAnimal() {
        System.out.println("which animal do you want to search for");
        String name = scan.nextLine();
        Animal a = kennel.search(name);
        if (a != null) {
            System.out.println(a.toString());
        } else {
            System.out.println("Could not find animal: " + name);
        }
    }

    private void changeKennelName() {
        System.out.println("what is the new name for the kennel?");
        String name = scan.nextLine();
        kennel.setName(name);
        System.out.println("the name for the kennel is now "+name);
    }


    /**
     * allows an admission of a new animal into the Kennel
     */
    private void admitAnimal(){
        //cat or dog
        System.out.println("Please enter the following information about the animal: \nIs the animal a dog or a cat? (D/C)");
        String animalType = scan.nextLine();
        //name
        System.out.println("What is their name?");
        String name = scan.nextLine();
        //food
        System.out.println("What is his/her favourite food?");
        String fav = scan.nextLine();
        //feeding freq
        System.out.println("How many times is he/she fed a day? (as a number)");
        int numTimes;
        try {
            numTimes = scan.nextInt(); // This can be improved (InputMismatchException?)
        } catch(Exception InputMismatchException){
            System.err.println("Invalid - not a valid number\nsetting to 1");
            numTimes = 1;
        }
        //specific animal
        //switch statement for easy expansion
        Animal a;
        switch (animalType) {
            case "D"-> {
                a = admitDog(name, fav, numTimes);
            }
            case "C"-> {
                a = admitCat(name, fav, numTimes);
            }
            default -> {
                System.err.println("Invalid - not a specified input");
                return;
            }
        }

        scan.nextLine(); // Clear the end of line characters because I didn't use a delimiter
        ArrayList<Owner> owners = getOwners();
        for(Owner o: owners){
            a.addOriginalOwner(o);
        }

        kennel.addAnimal(a);
    }

    /**
     * helper function for support of different animals - cat
     * @param name name of the cat
     * @param fav favourite food
     * @param numTimes number of times it is fed
     * @return Cat object representing the animal
     */
    private Cat admitCat(String name, String fav, int numTimes){
        //can share
        System.out.println("Can the cat share an exercise run?(Y/N)");
        boolean canShare = scan.nextLine().equalsIgnoreCase("Y");

        return new Cat(name, fav, numTimes, canShare);
    }

    /**
     * helper function for support of different animals - dog
     * @param name name of the dog
     * @param fav favourite food
     * @param numTimes number of times it is fed
     * @return Dog object representing the animal
     */
    private Dog admitDog(String name, String fav, int numTimes) {
        //likes bones
        System.out.println("Does the dog like bones?(Y/N)");
        boolean likesBones = scan.next().equalsIgnoreCase("Y");

        //needs walking
        System.out.println("Does the dog need to be walked?(Y/N)");
        boolean needsWalking = scan.nextLine().equalsIgnoreCase("Y");

        return new Dog(name, fav, numTimes, likesBones, needsWalking);
    }

    private ArrayList<Owner> getOwners() {
        ArrayList<Owner> owners = new ArrayList<Owner>();
        String answer;
        do {
            System.out.println("Enter on separate lines: owner-name owner-phone");
            String ownName = scan.nextLine();
            String ownPhone = scan.nextLine();
            Owner own = new Owner(ownName, ownPhone);
            owners.add(own);
            System.out.println("Another owner (Y/N)?");
            answer = scan.nextLine().toUpperCase();
        } while (!answer.equals("N"));
        return owners;
    }

    /*
     * save() method runs from the main and writes back to file
     */
    private void save() {
        try {
            kennel.save(filename);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + filename);
        }
    }

    // /////////////////////////////////////////////////
    public static void main(String args[]) {
        System.out.println("**********HELLO***********");
        KennelDemo demo = new KennelDemo();
        demo.initialise();
        demo.runMenu();
        demo.printAll();
        // MAKE A BACKUP COPY OF data.txt JUST IN CASE YOU CORRUPT IT
        demo.save();
        System.out.println("***********GOODBYE**********");
    }
}
