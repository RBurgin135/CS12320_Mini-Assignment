import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * To support an individual dog
 * @author Chris Loftus
 * @version 23rd February 2021
 */
public class Dog extends Animal{
	private boolean likesBones;
	private boolean needsWalking;

	/**
	 * No argument constructor
	 */
	public Dog(){
		this("unknown", "unknown", 1, false, false);
	}

	/**
	 * Constructor for the dog
	 * @param name The dog's name
	 * @param food The kind of food it eats
	 * @param mealsPerDay Number of feeds per day
	 * @param likeBones Does the dog like bones?
	 * @param needsWalking Whether the dog needs to be walked by a member of staff
	 */
	public Dog(String name, String food, int mealsPerDay, boolean likeBones, boolean needsWalking) {
		super(name, food, mealsPerDay);
		this.likesBones = likeBones;
		this.needsWalking = needsWalking;
	}

	/**
	 * Does the dog like bones?
	 * @return true if he does
	 */
	public boolean getLikesBones() {
		return likesBones;
	}

	/**
	 * Reads in information about the Dog from the file
	 */
	public void load(Scanner infile){
		if (infile == null) return;
		super.load(infile);
		likesBones = infile.nextBoolean();
		needsWalking = infile.nextBoolean();
	}

	/**
	 * Writes information about the Dog to a file
	 */
	public void save(PrintWriter pw){
		if (pw == null) return;
		pw.println("Dog");
		super.save(pw);
		pw.println(likesBones);
		pw.println(needsWalking);
	}

	/**
	 * A basic implementation to just return all the data in string form
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dog name: ").append(super.toString());
		sb.append("\nLikes bones: ").append(likesBones);
		sb.append("\nNeeds walking: ").append((needsWalking));
		return sb.toString();
	}

}
