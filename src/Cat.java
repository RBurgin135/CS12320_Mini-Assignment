import java.io.PrintWriter;
import java.util.Scanner;

/**
 * To support an individual Cat
 * @author Richard Burgin
 * @version 14th March 2021
 */
public class Cat extends Animal{
    private boolean shareRun;

    /**
     * No argument constructor
     */
    public Cat (){
        this("unknown", "unknown", 1, false);
    }

    /**
     * Constructor for the Cat
     * @param name The cat's name
     * @param food The kind of food it eats
     * @param mealsPerDay The number of feeds per day
     * @param shareRun Whether the cat can share an exercise run
     */
    public Cat (String name, String food, int mealsPerDay, boolean shareRun){
        super(name,food,mealsPerDay);
        this.shareRun = shareRun;
    }

    /**
     * Obtain whether the cat can share a run
     * @return
     */
    public boolean canShareRun (){
        return shareRun;
    }

    /**
     * Reads in information about the Dog from the file
     */
    public void load(Scanner infile){
        if (infile == null) return;
        super.load(infile);
        shareRun = infile.nextBoolean();
    }

    /**
     * Writes information about the Dog to a file
     */
    public void save(PrintWriter pw){
        if (pw == null) return;
        pw.println("Cat");
        super.save(pw);
        pw.println(shareRun);
    }


    /**
     * A basic implementation to just return all the data in string form
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cat name: ").append(super.toString());
        sb.append("\nCan share: ").append(shareRun);
        return sb.toString();
    }
}
