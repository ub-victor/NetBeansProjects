package lab1;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class MushroomFinderConsole {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String result = "";

        System.out.println("===== Mushroom Finder =====");

        // Questions
        System.out.print("Does your mushroom have gills? (yes/no): ");
        String gills = scanner.nextLine().toLowerCase();

        System.out.print("Does it grow in a forest? (yes/no): ");
        String forest = scanner.nextLine().toLowerCase();

        System.out.print("Does it have a ring? (yes/no): ");
        String ring = scanner.nextLine().toLowerCase();

        System.out.print("Does it have a convex cup? (yes/no): ");
        String convex = scanner.nextLine().toLowerCase();

        // Logic (same as GUI)
        if (gills.equals("no")) {
            result = "Cepe de bordeau";
        }
        else if (forest.equals("no") && ring.equals("yes") && convex.equals("no")) {
            result = "Coprin chevelu";
        }
        else if (forest.equals("no") && ring.equals("yes")) {
            result = "Agaric jaunissant";
        }
        else if (convex.equals("yes") && ring.equals("yes")) {
            result = "Amanite tue-mouche";
        }
        else if (convex.equals("yes") && ring.equals("no")) {
            result = "Pied bleu";
        }
        else {
            result = "Girolle";
        }

        // Output
        System.out.println("\nYour mushroom is: " + result);

        // Save option
        System.out.print("\nDo you want to save the result? (yes/no): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("yes")) {
            saveResult(result);
        }

        System.out.println("\nThanks for using Mushroom Finder!");
        scanner.close();
    }

    // Save method
    public static void saveResult(String result) {
        try (FileWriter writer = new FileWriter("mushroom_result.txt")) {
            writer.write("Your mushroom is: " + result);
            System.out.println("Result saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}