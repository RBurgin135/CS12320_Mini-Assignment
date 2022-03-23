import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * To model a Kennel - a collection of dogs
 *
 * @author Chris Loftus
 * @version 23rd February 2021
 */
public class Kennel {
    private String name;            //name of the Kennel
    private ArrayList<Animal> inmates;    //current occupants
    private int currentOccupancy;   //number of dogs occupying the kennel
    private int capacity;           //max occupants

    /**
     * Creates a kennel with a default size 20
     */
    public Kennel() {
        //runs the other constructor with 20 as a param
        this(20);
    }

    /**
     * Create a kennel
     *
     * @param maxNo The capacity of the kennel
     */
    public Kennel(int maxNo) {
        currentOccupancy = 0;
        capacity = maxNo;
        inmates = new ArrayList<Animal>(capacity);
    }

    /**
     * This method sets the value for the name attribute. The purpose of the
     * attribute is: The name of the kennel e.g. "DogsRUs"
     *
     * @param theName the new name
     */
    public void setName(String theName) {
        if  (theName != null) {
            name = theName;
        }
    }

    /**
     * Set the size of the kennel. The purpose of the attribute is the max
     * number of animals that the kennel can house
     *
     * @param capacity The max animals the kennel can house
     */
    public void setCapacity(int capacity) {
        if (capacity < currentOccupancy){
            System.err.println("Invalid, current inmates are more than the new capacity.");
            System.err.println("This would cause evictions");
        } else {
            this.capacity = capacity;
        }
    }

    /**
     * Maximum capacity of the kennels
     *
     * @return The max size of the kennel
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * This method gets the value for the name attribute. The purpose of the
     * attribute is: The name of the Kennel e.g. "DogsRUs"
     *
     * @return String The name of the kennel
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the number of animals in a kennel
     *
     * @return int Current number of animals in the kennel
     */
    public int getNumOfAnimals() {
        return currentOccupancy;
    }

    /**
     * Enables a user to add a new occupant to the Kennel
     * @param newInmate A new Animal to house
     */
    public void addAnimal(Animal newInmate) {
        //checks
        if (newInmate == null){
            System.out.println("Invalid, new occupant cannot be null");
            return;
        } else if (currentOccupancy >= capacity) {
            System.out.println("Sorry kennel is full - cannot add a new occupant");
            return;
        }
        // we add in the position indexed by currentOccupancy
        inmates.add(newInmate);

        // now increment index ready for next one
        currentOccupancy ++;
    }

    /**
     * Enables a user to delete an Animal from the Kennel. Removes the Animal object
     * with the name specified.
     * @param who The name of the Animal to remove
     */
    public void removeAnimal(String who) {
        Animal found = search(who);
        if(found != null) {
            inmates.remove(found);
            System.out.println("removed " + who);
            currentOccupancy--;
        }
    }

    /**
     * Returns an array of the inmates in the kennels
     * @return An array of length of capacity
     */
    public Animal[] obtainAllInmates() {
        Animal[] result = new Animal[currentOccupancy];
        result = inmates.toArray(result);
        return result;
    }

    /**
     * Searches for and returns the inmate if found, returns null if not
     * @param who The name of the inmate
     * @return The inmate or else null if not found
     */
    public Animal search(String who) {
        Animal result = null;
        if (who == null) {
            System.err.println("Invalid - name of the animal cannot be null");
            return null;
        }
        // Search for the dog by name
        for (Animal a : inmates) {
            if (who.equals(a.getName())) {
                result = a;
            }
        }
        if (result == null) {
            System.err.println("cannot find - not in kennel");
        }
        return result;
    }


    /**
     * Reads in Kennel information from the file
     * @param infileName The file to read from
     * @throws FileNotFoundException if file doesn't exist
     * @throws IOException if some other IO error occurs
     * @throws IllegalArgumentException if infileName is null or empty
     */
    public void load(String infileName) throws IOException, FileNotFoundException {
        // Using try-with-resource. We will cover this in workshop 15, but
        // what it does is to automatically close the file after the try / catch ends.
        // This means we don't have to worry about closing the file.
        try (FileReader fr = new FileReader(infileName);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a nextInt or nextBoolean
            infile.useDelimiter("\r?\n|\r");

            name = infile.next();
            capacity = infile.nextInt();

            while (infile.hasNext()) {
                Animal a;
                if(infile.next().equals("Dog")){
                    a = new Dog();
                } else{
                    a = new Cat();
                }
                a.load(infile);
                this.addAnimal(a);
            }
        }
    }

    /**
     * Saves the kennel information to a file
     * @param filename The file to save to
     * @throws IOException If some IO error occurs
     */
    public void save(String filename) throws IOException {
        // Again using try-with-resource so that I don't need to close the file explicitly
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {

            outfile.println(name);
            outfile.println(capacity);
            for (Animal a : inmates) {
                a.save(outfile);
            }
        }
    }

    /**
     * Sort the animals into alphabetical order by name
     */
    public void sortAnimals(){
        boolean sorted;
        do {
            sorted = true;
            for (int i=0; i<currentOccupancy-1; i++) {
                int comparison = inmates.get(i).getName().compareTo(inmates.get(i+1).getName());
                if(comparison > 0){
                    //swap
                    Animal a = inmates.remove(i);
                    inmates.add(i+1,a);
                    sorted = false;
                }
            }
        } while (!sorted);
    }

    /**
     * For outputting a summary of the current object
     * @return String showing all the information in the kennel
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data in Kennel ").append(name).append(" is: \n");
        sb.append("Capacity is ").append(capacity).append("\n");
        sb.append("Current number of occupants is ").append(currentOccupancy).append("\n");
        sb.append("Current occupants are: \n");
        for (Animal a : inmates) {
            sb.append("\t").append(a.toString()).append("\n");
        }
        return sb.toString();
    }
}
