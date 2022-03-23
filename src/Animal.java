import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * To support several types of animal
 * @author Richard Burgin
 * @version 14th March 2021
 */
public class Animal {
    ArrayList<Owner> originalOwners;
    String name;
    String favFood;
    int foodPerDay;

    /**
     * No argument constructor
     */
    public Animal (){
        this("unknown", "unknown", 1);
    }

    /**
     * Constructor for the Animal
     * @param name The animals name
     * @param food The kind of food it eats
     * @param mealsPerDay The number of feeds per day
     */
    public Animal(String name, String food, int mealsPerDay) {
        this.name = name;
        originalOwners = new ArrayList<Owner>();
        this.favFood = food;
        this.foodPerDay = mealsPerDay;
    }


    /**
     * Obtain the name of the animal
     * @return The name of the animal
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the animal
     * @param newName The name of the animal
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * How many times a day to feed the animal
     * @param feeds The number of feeds per day
     */
    public void setFeedsPerDay(int feeds){
        foodPerDay = feeds;
    }

    /**
     * The number of feeds per day the animal is fed
     * @return The number of feeds per day
     */
    public int getFeedsPerDay(){
        return foodPerDay;
    }

    /**
     * Sets the animals favourite food
     * @param food The food the animal likes
     */
    public void setFavouriteFood(String food){
        favFood = food;
    }

    /**
     * The food the animal likes to eat
     * @return The food
     */
    public String getFavouriteFood(){
        return favFood;
    }

    /**
     * To add an original owner
     * @param o An original owner
     */
    public void addOriginalOwner(Owner o){
        if (o != null) originalOwners.add(o);
    }

    /**
     * Returns a copy of the original owners
     * @return A copy of the original owners as an array
     */
    public Owner[] getOriginalOwners(){
        // Safer to return a copy rather than the original.
        // Encapsulation is preserved
        Owner[] result = new Owner[originalOwners.size()];
        result = originalOwners.toArray(result);
        return result;
    }

    /**
     * Compares equality based on the name of the animal
     * @param obj the other animal to compare against
     * @return true if equal
     */
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Animal a = (Animal) obj;
        return name.equals(a.name);
    }

    /**
     * Reads in information about the Animal from the file
     */
    void load(Scanner infile){
        if (infile == null) return;
        name = infile.next();
        int numOwners = infile.nextInt();
        originalOwners = new ArrayList<>();
        for (int oCount = 0; oCount < numOwners; oCount++) {
            String name = infile.next();
            String phone = infile.next();
            Owner owner = new Owner(name, phone);
            originalOwners.add(owner);
        }
        foodPerDay = infile.nextInt();
        favFood = infile.next();
    }

    /**
     * Writes information about the Animal to a file
     */
    void save(PrintWriter pw){
        if (pw == null) return;
        pw.println(name);
        pw.println(originalOwners.size());
        for (Owner o : originalOwners) {
            pw.println(o.getName());
            pw.println(o.getPhone());
        }
        pw.println(foodPerDay);
        pw.println(favFood);
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\nOriginal Owner: ").append(originalOwners);
        sb.append("\nFavourite food: ").append(favFood);
        sb.append("\nFood per day: ").append(foodPerDay);
        return sb.toString();
    }
}
